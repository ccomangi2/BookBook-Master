package com.example.bookbook_master.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookbook_master.R
import com.example.bookbook_master.adapter.callback.OnBookClickListener
import com.example.bookbook_master.adapter.callback.OnWishClickListener
import com.example.bookbook_master.databinding.FragmentDetailBinding
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.example.bookbook_master.model.roomDB.entity.Wish
import com.example.bookbook_master.viewmodel.DetailViewModel
import com.example.bookbook_master.viewmodel.WishViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 도서 상세 정보 화면
 * @author philippe
 */
class DetailFragment : BaseFragment<FragmentDetailBinding>(), View.OnClickListener {

    companion object {
        private const val DETAIL_INFO_KEY = "detail_info"

        @JvmStatic
        fun newInstance(document: Document) = DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(DETAIL_INFO_KEY, document)
            }
        }
    }

    private val detailViewModel: DetailViewModel by sharedViewModel()
    private val wishViewModel: WishViewModel by viewModel()

    private var document: Document? = null

    override fun getLayoutRes(): Int = R.layout.fragment_detail

    override fun initData() {
        arguments?.let {
            document = it.getParcelable(DETAIL_INFO_KEY)
            document?.let { document -> detailViewModel.setDocument(document) }
        }
    }

    override fun initView(viewDataBinding: FragmentDetailBinding) {
        viewDataBinding.viewModel = detailViewModel
        viewDataBinding.clickListener = this
    }

    // 위시리스트
    private val wishClickListener = object : OnWishClickListener {
        override fun onClickWish(document: Document) {
            // 로컬 디비에 저장 ( 위시리스트 )
            val wish = Wish(0, document)
            wishViewModel.addWish(wish)
            Log.d("좋아요", "추가")
            showToastMessage("${wish.document.title} 이(가) 위시리스트에 추가 되었습니다.")
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            // 뒤로가기
            R.id.b_back -> {
                activity?.onBackPressed()
            }
            // 좋아요 버튼
            R.id.ib_favorite_book -> {
                document?.let { wishClickListener.onClickWish(it) }
            }
        }
    }

}