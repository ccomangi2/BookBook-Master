package com.example.bookbook_master.view.fragment

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.R
import com.example.bookbook_master.adapter.BookListAdapter
import com.example.bookbook_master.adapter.listener.OnBookClickListener
import com.example.bookbook_master.databinding.FragmentSearchBinding
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.network.NetWorkStatus
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.example.bookbook_master.view.activity.MainActivity
import com.example.bookbook_master.viewmodel.MainViewModel
import com.example.bookbook_master.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.concurrent.thread

/**
 * 도서 검색 화면
 * @author philippe
 */
class SearchFragment : BaseFragment<FragmentSearchBinding>(), View.OnClickListener {
    companion object {
        private const val DEFAULT_VIEW_TYPE = BookListAdapter.SEARCH_VIEW_TYPE

        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    private val searchViewModel: SearchViewModel by viewModel()
    private val mainViewModel : MainViewModel by viewModel()

    private lateinit var bookListAdapter: BookListAdapter
    private var currentListViewType = DEFAULT_VIEW_TYPE

    //DB
    private val bookClickListener = object : OnBookClickListener {
        override fun onClickBook(document: Document) {
            // 도서 상세 화면으로 이동
            // 로컬 디비에 저장 ( 최근 본 상품 )
            val recent = Recent(0, document)
            mainViewModel.addRecent(recent)
            Log.d("DB","상품 정보 : $recent")
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, DetailFragment.newInstance(document))
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

    // xml
    override fun getLayoutRes(): Int = R.layout.fragment_search

    override fun initData() {
        bookListAdapter = BookListAdapter(DEFAULT_VIEW_TYPE, bookClickListener)
    }

    override fun initView(viewDataBinding: FragmentSearchBinding) {
        // 뷰모델
        viewDataBinding.viewModel = searchViewModel
        // 어댑터 연결
        viewDataBinding.rvBookList.adapter = bookListAdapter
        // 클릭리스너
        viewDataBinding.clickListener = this
        // 스크롤
        initBookListScrollListener(viewDataBinding.rvBookList)

        // 뷰 타입 변경
        searchViewModel.bookListViewType.observe(this@SearchFragment, {
            currentListViewType = it
            setListViewType(viewDataBinding.rvBookList, it)
        })

        // 리스트 불러오기
        searchViewModel.mainBookList.observe(this, {
            hideKeyboard(viewDataBinding.etSearchKeyword)
            bookListAdapter.submitList(it)
            searchViewModel.setEmptyBookList(it.isEmpty())
        })

        // 네트워크 오류
        val connection = context?.let { NetWorkStatus(it) }
        connection?.observe(this, Observer { isConnected ->
            if (!isConnected)
            {
                activity?.let {
                    showAlertDialog(it, getString(R.string.message_network_error))
                }
            }
        })

        // 검색 버튼 클릭 시
        b_search.setOnClickListener {
            //검색창에 문자를 입력 후 1초가 지나면 자동으로 검색이 됩니다.
            //데이터를 가져오는 동안 ProgressBar 가 나타나며 목록이 생성되면 사라집니다.
            search()
        }

        et_search_keyword.addTextChangedListener(SearchEditWatcher())
    }

    // 문자열이 바뀔때
    inner class SearchEditWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            search()
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            search()
        }
        override fun afterTextChanged(s: Editable?) {
            search()
        }
    }

    // 프로그레스바
    fun showProgress(isShow: Boolean) {
        if(isShow) v_loading.visibility = View.VISIBLE
        else v_loading.visibility = View.GONE
    }

    //자동 검색
    //검색창에 문자를 입력 후 1초가 지나면 자동으로 검색이 됩니다.
    //데이터를 가져오는 동안 ProgressBar 가 나타나며 목록이 생성되면 사라집니다.
    fun search() {
        showProgress(true) // ProgressBar 나타남
        thread(start = true) {
            Thread.sleep(3000) //1초
            activity?.runOnUiThread() {
                showProgress(false) // ProgressBar 사라짐
                searchViewModel.searchBookList(et_search_keyword.text.toString(), v_loading) // 목록 생성
            }
        }
    }

    /**
     * 도서 리스트뷰 스크롤 리스너 초기화
     * @param bookListView 도서 리스트뷰
     */
    private fun initBookListScrollListener(bookListView: RecyclerView) {
        bookListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                recyclerView.adapter?.let {
                    val layoutManager = when (currentListViewType) {
                        BookListAdapter.SEARCH_VIEW_TYPE -> {
                            (bookListView.layoutManager as GridLayoutManager)
                        }
                        else -> (bookListView.layoutManager as GridLayoutManager)
                    }

                    if(!bookListView.canScrollVertically(1)) {
                        //검색 결과가 있을 경우 데이터는 15개씩 Paging 처리합니다. 최초 15개의 데이터를 가져온 후 스크롤 할때마다 15개씩 데이터가 추가됩니다.
                        if (it.itemCount % 15 == 0) {
                            //검색어가 변경되면 1초 후 목록이 자동으로 갱신되고 다시 데이터를 가져옵니다.
                            //데이터를 가져오는 동안 ProgressBar 가 나타나며 목록이 생성되면 사라집니다.
                            showProgress(true) // ProgressBar 나타남
                            thread(start = true) {
                                Thread.sleep(3000) //1초
                                activity?.runOnUiThread() {
                                    showProgress(false) // ProgressBar 사라짐
                                    searchViewModel.searchMoreBookList() // 목록 생성
                                    Log.d("BookBook : ", it.itemCount.toString())
                                }
                            }
                        }
                        else if(it.itemCount < 15) { }
                        else {
                            //데이터가 더 이상 없는 경우 '마지막 페이지입니다'는 Toast 메시지가 나타납니다.
                            showToastMessage("마지막 페이지 입니다.")
                        }
                    }
                }
            }
        })
    }

    /**
     * 키보드 숨기기
     * @param focusView 현재 포커스를 가진 뷰 (TextInputEditText)
     */
    private fun hideKeyboard(focusView: View) {
        activity?.let {
            if (it is MainActivity) {
                it.hideKeyboard(focusView)
            }
        }
    }

    /**
     * 도서 리스트 뷰타입 변경
     * @param bookListView 도서 리스트뷰
     * @param viewType 리스트뷰 뷰타입 (TEXT, IMAGE)
     */
    private fun setListViewType(bookListView: RecyclerView, viewType: Int) {
        when (viewType) {
            BookListAdapter.SEARCH_VIEW_TYPE -> {
                bookListView.layoutManager = GridLayoutManager(bookListView.context, 3)
            }
        }

        bookListView.adapter?.let {
            if (it is BookListAdapter) {
                it.itemViewType = viewType
            }
        }
    }

    // 뒤로 가기
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.b_back -> {
                activity?.onBackPressed()
            }
        }
    }
}