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

    private val _isShowLoading = MutableLiveData<Boolean>()
    val isShowLoading: LiveData<Boolean> = _isShowLoading

    private val _showNetworkError = MutableLiveData<String>()
    val showNetworkError: LiveData<String> = _showNetworkError

    init {
        hideLoading()
    }

    fun showLoading() {
        _isShowLoading.postValue(true)
    }

    fun hideLoading() {
        _isShowLoading.postValue(false)
    }

    fun isLoading(): Boolean {
        _isShowLoading.value?.let {
            return it
        }

        return false
    }

    /**
     * 네트워크 오류 표시
     * @param msg 오류 메시지
     */
    fun showNetworkError(msg: String) {
        _showNetworkError.value = msg
    }

}