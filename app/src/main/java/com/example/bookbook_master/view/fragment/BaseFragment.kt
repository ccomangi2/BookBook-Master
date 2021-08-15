package com.example.bookbook_master.view.fragment

import android.app.ActionBar
import android.app.Notification
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.bookbook_master.R

/**
 * 데이터 바인딩 프레그먼트
 * @author philippe
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    private lateinit var viewDataBinding: T

    abstract fun getLayoutRes(): Int

    abstract fun initData()

    abstract fun initView(viewDataBinding: T)

    /*
     * 토스트 메시지 띄우기
     */
    fun showToastMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 알림창 띄우기
     */
    fun showAlertDialog(context: Context, msg: String) {
        val alertDialog = AlertDialog.Builder(context).apply {
            setMessage(msg)
            setPositiveButton(R.string.btn_confirm) { dialog, _ ->
                var pid: Int = android.os.Process.myPid()
                android.os.Process.killProcess(pid)
            }
            create()
        }
        alertDialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        viewDataBinding.lifecycleOwner = this
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(viewDataBinding)
    }
}