package com.example.bookbook_master.view.fragment

import android.os.Bundle
import android.view.View
import com.example.bookbook_master.R
import com.example.bookbook_master.databinding.FragmentDetailBinding
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

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

    private var document: Document? = null

    override fun getLayoutRes(): Int = R.layout.fragment_detail

    override fun initData() {
        arguments?.let {
            document = it.getParcelable(DETAIL_INFO_KEY)
            document?.let { document ->
                detailViewModel.setDocument(document)
            }
        }
    }

    override fun initView(viewDataBinding: FragmentDetailBinding) {
        viewDataBinding.viewModel = detailViewModel
        viewDataBinding.clickListener = this
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.b_back -> {
                activity?.onBackPressed()
            }
        }
    }

}