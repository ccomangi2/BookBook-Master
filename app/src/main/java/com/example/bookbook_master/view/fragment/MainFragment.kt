package com.example.bookbook_master.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookbook_master.R
import com.example.bookbook_master.adapter.BookListAdapter
import com.example.bookbook_master.adapter.callback.OnBookClickListener
import com.example.bookbook_master.databinding.FragmentDetailBinding
import com.example.bookbook_master.databinding.FragmentMainBinding
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.viewmodel.DetailViewModel
import com.example.bookbook_master.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 메인 화면
 */
class MainFragment : BaseFragment<FragmentMainBinding>(), View.OnClickListener {
    companion object {
        private const val DEFAULT_VIEW_TYPE = BookListAdapter.IMAGE_VIEW_TYPE

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    // 뷰모델, 어댑터 변경 해야 함
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

    override fun getLayoutRes(): Int = R.layout.fragment_main


    override fun initData() {
        bookListAdapter = BookListAdapter(DEFAULT_VIEW_TYPE, bookClickListener)
    }

    override fun initView(viewDataBinding: FragmentMainBinding) {
        // 수정 필요
        viewDataBinding.viewModel = searchViewModel
        viewDataBinding.clickListener = this
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.b_wishView -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.container, WishFragment.newInstance())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
            R.id.b_searchView -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.container, SearchFragment.newInstance())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }
    }
}