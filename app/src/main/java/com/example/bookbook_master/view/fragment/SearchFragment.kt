package com.example.bookbook_master.view.fragment

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.R
import com.example.bookbook_master.adapter.BookListAdapter
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
        private const val DEFAULT_VIEW_TYPE = BookListAdapter.TEXT_VIEW_TYPE

        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    private val searchViewModel: SearchViewModel by viewModel()
    private val detailViewModel: DetailViewModel by sharedViewModel()

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

                if (searchViewModel.isLoading() || searchViewModel.isEndBookList) {
                    return
                }

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

                    if (layoutManager.findLastCompletelyVisibleItemPosition() > it.itemCount - 6) {
                        searchViewModel.searchMoreBookList()
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
