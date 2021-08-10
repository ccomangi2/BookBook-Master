package com.example.bookbook_master.view.fragment

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.R
import com.example.bookbook_master.adapter.WishListAdapter
import com.example.bookbook_master.adapter.listener.OnBookClickListener
import com.example.bookbook_master.databinding.FragmentWishlistBinding
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.example.bookbook_master.viewmodel.MainViewModel
import com.example.bookbook_master.viewmodel.WishViewModel
import kotlinx.android.synthetic.main.fragment_wishlist.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 메인 화면
 */
class WishFragment : BaseFragment<FragmentWishlistBinding>(), View.OnClickListener {
    companion object {
        private const val DEFAULT_VIEW_TYPE = WishListAdapter.WISH_VIEW_TYPE

        @JvmStatic
        fun newInstance() = WishFragment()
    }

    // 뷰모델 변경 해야함
    private val wishViewModel: WishViewModel by viewModel()
    private val mainViewModel : MainViewModel by viewModel()

    // 어댑터 변경 해야함
    private lateinit var wishListAdapter: WishListAdapter
    private var currentListViewType = DEFAULT_VIEW_TYPE

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

    override fun getLayoutRes(): Int = R.layout.fragment_wishlist

    override fun initData() {
        wishListAdapter = WishListAdapter(DEFAULT_VIEW_TYPE, bookClickListener)
    }

    override fun initView(viewDataBinding: FragmentWishlistBinding) {
        // 뷰 모델
        viewDataBinding.viewModel = wishViewModel
        // 어댑터 연결
        viewDataBinding.rvWishBookList.adapter = wishListAdapter
        // 클릭 리스너
        viewDataBinding.clickListener = this

        // 데이터 불러오기
        wishViewModel.getAll().observe(this, Observer {
            Log.d("wish_data", it.toString())
            wishListAdapter.setData(it)
            wishListAdapter.notifyDataSetChanged()
        })

        // 뷰타입 설정
        wishViewModel.bookListViewType.observe(this@WishFragment, {
            currentListViewType = it
            setListViewType(viewDataBinding.rvWishBookList, it)
        })
    }

    /**
     * 도서 리스트 뷰타입 변경
     * @param bookListView 도서 리스트뷰
     * @param viewType 리스트뷰 뷰타입
     */
    private fun setListViewType(bookListView: RecyclerView, viewType: Int) {
        when (viewType) {
            WishListAdapter.WISH_VIEW_TYPE -> {
                bookListView.layoutManager = LinearLayoutManager(bookListView.context)
            }
        }
        bookListView.adapter?.let {
            if (it is WishListAdapter) {
                it.itemViewType = viewType
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.b_back -> {
                activity?.onBackPressed()
            }
            R.id.b_delete -> {
                wishViewModel.deleteAll()
            }
            R.id.b_date -> {
                b_date.setImageResource(R.drawable.date)
                b_lowPrice.setImageResource(R.drawable.low_non)
                b_highPrice.setImageResource(R.drawable.high_non)
                wishViewModel.getAll().observe(this, Observer {
                    Log.d("wish_data", it.toString())
                    wishListAdapter.setData(it)
                    wishListAdapter.notifyDataSetChanged()
                })
            }
            R.id.b_lowPrice -> {
                b_date.setImageResource(R.drawable.date_non)
                b_lowPrice.setImageResource(R.drawable.low)
                b_highPrice.setImageResource(R.drawable.high_non)
                wishViewModel.getLowAll().observe(this, Observer {
                    Log.d("wish_data", it.toString())
                    wishListAdapter.setData(it)
                    wishListAdapter.notifyDataSetChanged()
                })
            }
            R.id.b_highPrice -> {
                b_date.setImageResource(R.drawable.date_non)
                b_lowPrice.setImageResource(R.drawable.low_non)
                b_highPrice.setImageResource(R.drawable.high)
                wishViewModel.getHighAll().observe(this, Observer {
                    Log.d("wish_data", it.toString())
                    wishListAdapter.setData(it)
                    wishListAdapter.notifyDataSetChanged()
                })
            }
        }
    }
}