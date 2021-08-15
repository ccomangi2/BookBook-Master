package com.example.bookbook_master.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

/**
 * 공통 뷰모델
 * @author philippe
 */
open class BaseViewModel : ViewModel() {
    //MutableLiveData 는 get/set 모두 허용 = 읽기/변경 가능
    //LiveData는 get만 허용 = 읽기만 가능

    // 네트워크(인터넷) 오류.
    private val _showNetworkError = MutableLiveData<String>()
    val showNetworkError: LiveData<String> = _showNetworkError

    init { }

    /**
     * 네트워크 오류 표시
     * @param msg 오류 메시지
     */
    fun showNetworkError(msg: String) {
        _showNetworkError.postValue(msg)
    }
}