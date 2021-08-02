package com.example.bookbook_master.view.fragment

import android.content.Context
import android.renderscript.ScriptGroup
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.size
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.R
import com.example.bookbook_master.adapter.BookListAdapter
import com.example.bookbook_master.adapter.callback.EndlessRecyclerViewScrollListener
import com.example.bookbook_master.adapter.callback.OnBookClickListener
import com.example.bookbook_master.databinding.FragmentSearchBinding
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.view.activity.MainActivity
import com.example.bookbook_master.viewmodel.DetailViewModel
import com.example.bookbook_master.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_toolbar_search.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.concurrent.thread

/**
 * 도서 검색 화면
 * @author philippe
 */
class SearchFragment : BaseFragment<FragmentSearchBinding>(){
    companion object {
        private const val DEFAULT_VIEW_TYPE = BookListAdapter.IMAGE_VIEW_TYPE

        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    private val searchViewModel: SearchViewModel by viewModel()

    private lateinit var bookListAdapter: BookListAdapter
    private var currentListViewType = DEFAULT_VIEW_TYPE

    private val bookClickListener = object : OnBookClickListener {
        override fun onClickBook(document: Document) {
            // 도서 상세 화면으로 이동
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
        viewDataBinding.viewModel = searchViewModel
        initBookListView(viewDataBinding.rvBookList, DEFAULT_VIEW_TYPE, bookListAdapter)
        initBookListScrollListener(viewDataBinding.rvBookList)

        searchViewModel.bookListViewType.observe(this@SearchFragment, {
            currentListViewType = it
            setListViewType(viewDataBinding.rvBookList, it)
        })

        searchViewModel.mainBookList.observe(this, {
            hideKeyboard(viewDataBinding.layoutToolbarSearch.etSearchKeyword)
            bookListAdapter.submitList(it)
            searchViewModel.setEmptyBookList(it.isEmpty())
        })

        searchViewModel.showNetworkError.observe(this, { _ ->
            activity?.let {
                showAlertDialog(it, getString(R.string.message_network_error))
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
     * 도서 목록 리스트 초기화
     * @param bookListView 도서 리스트뷰
     * @param viewType 리스트뷰 뷰타입 (TEXT, IMAGE)
     * @param bookListAdapter 도서 리스트 어댑터
     */
    private fun initBookListView(bookListView: RecyclerView, viewType: Int, bookListAdapter: BookListAdapter) {
        when (viewType) {
            BookListAdapter.TEXT_VIEW_TYPE -> {
                //검색 결과 목록은 3xN  Grid Recyclerview 로 나타납니다.
                bookListView.layoutManager = GridLayoutManager(bookListView.context, 3)
            }
            BookListAdapter.IMAGE_VIEW_TYPE -> {
                bookListView.layoutManager = GridLayoutManager(bookListView.context, 3)
            }
        }

        bookListView.adapter = bookListAdapter
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
                        BookListAdapter.TEXT_VIEW_TYPE -> {
                            (bookListView.layoutManager as GridLayoutManager)
                        }
                        BookListAdapter.IMAGE_VIEW_TYPE -> {
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
     * 알림창 띄우기
     */
    private fun showAlertDialog(context: Context, msg: String) {
        val alertDialog = AlertDialog.Builder(context).apply {
            setMessage(msg)
            setPositiveButton(R.string.btn_confirm) { dialog, _ ->
                dialog.dismiss()
            }
            create()
        }
        alertDialog.show()
    }

    /*
     * 토스트 메시지 띄우기
     */
    private fun showToastMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
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
            BookListAdapter.TEXT_VIEW_TYPE -> {
                bookListView.layoutManager = GridLayoutManager(bookListView.context, 3)
            }
            BookListAdapter.IMAGE_VIEW_TYPE -> {
                bookListView.layoutManager = GridLayoutManager(bookListView.context, 3)
            }
        }

        bookListView.adapter?.let {
            if (it is BookListAdapter) {
                it.itemViewType = viewType
            }
        }
    }
}