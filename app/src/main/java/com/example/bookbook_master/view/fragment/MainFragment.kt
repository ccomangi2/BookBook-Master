package com.example.bookbook_master.view.fragment

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.R
import com.example.bookbook_master.adapter.MainListAdapter
import com.example.bookbook_master.adapter.listener.OnBookClickListener
import com.example.bookbook_master.databinding.FragmentMainBinding
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.example.bookbook_master.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * 메인 화면
 */
class MainFragment : BaseFragment<FragmentMainBinding>(), View.OnClickListener {
    companion object {
        private const val DEFAULT_VIEW_TYPE = MainListAdapter.RECENT_VIEW_TYPE

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    // 뷰모델, 어댑터 변경 해야 함
    private val mainViewModel : MainViewModel by viewModel()
    private lateinit var mainListAdapter: MainListAdapter
    private var currentListViewType = DEFAULT_VIEW_TYPE

    //DB
    private val bookClickListener = object : OnBookClickListener {
        override fun onClickBook(document: Document) {
            // 도서 상세 화면으로 이동
            // 로컬 디비에 저장 ( 최근 본 상품 )
            val recent = Recent(0, document)
            mainViewModel.addRecent(recent)
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, DetailFragment.newInstance(document))
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_main

    override fun initData() {
        mainListAdapter = MainListAdapter(DEFAULT_VIEW_TYPE, bookClickListener)
    }

    override fun initView(viewDataBinding: FragmentMainBinding) {
        // 뷰모델
        viewDataBinding.viewModel = mainViewModel
        // 어댑터 연결
        viewDataBinding.rvLookBookList.adapter = mainListAdapter
        // 클릭리스너
        viewDataBinding.clickListener = this

        // 데이터 불러오기
        mainViewModel.getAll().observe(this, Observer {
            Log.d("recent_data", it.toString())
            mainListAdapter.setData(it)
            mainListAdapter.notifyDataSetChanged()
        })

        // 뷰타입 설정
        mainViewModel.bookListViewType.observe(this@MainFragment, {
            currentListViewType = it
            setListViewType(viewDataBinding.rvLookBookList, it)
        })
    }

    /**
     * 도서 리스트 뷰타입 변경
     * @param bookListView 도서 리스트뷰
     * @param viewType 리스트뷰 뷰타입
     */
    private fun setListViewType(bookListView: RecyclerView, viewType: Int) {
        when (viewType) {
            MainListAdapter.RECENT_VIEW_TYPE -> {
                bookListView.layoutManager = GridLayoutManager(bookListView.context, 3)
            }
        }

        bookListView.adapter?.let {
            if (it is MainListAdapter) {
                it.itemViewType = viewType
            }
        }
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