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

    // 로딩
    private val _isShowLoading = MutableLiveData<Boolean>()
    val isShowLoading: LiveData<Boolean> = _isShowLoading

    // 네트워크(인터넷) 오류
    private val _showNetworkError = MutableLiveData<String>()
    val showNetworkError: LiveData<String> = _showNetworkError

    init {
        hideLoading()
    }

    // 프로그레스바 보이기
    fun showLoading() {
        _isShowLoading.postValue(true)
    }

    // 프로그레스바 없애기
    fun hideLoading() {
        _isShowLoading.postValue(false)
    }

    // 로딩중인가?
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
        _showNetworkError.postValue(msg)
    }

}