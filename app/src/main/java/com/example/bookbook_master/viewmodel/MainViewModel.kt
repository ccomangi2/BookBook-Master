package com.example.bookbook_master.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookbook_master.adapter.BookListAdapter
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.example.bookbook_master.model.roomDB.repository.RecentRepository

class MainViewModel (private val repository: RecentRepository) : BaseViewModel() {
    private val items = repository.getAll()

    //뷰 타입을 이미지뷰타입으로 설정
    private val _bookListViewType = MutableLiveData(BookListAdapter.IMAGE_VIEW_TYPE)
    val bookListViewType: LiveData<Int> = _bookListViewType


    fun insert(recent: Recent) {
        repository.insert(recent)
    }

    fun delete(recent: Recent){
        repository.delete(recent)
    }

    fun getAll(): LiveData<List<Recent>> {
        return items
    }

    override fun onCleared() {
        super.onCleared()
    }
}