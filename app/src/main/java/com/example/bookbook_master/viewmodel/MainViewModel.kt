package com.example.bookbook_master.viewmodel

import android.app.Application
import android.os.NetworkOnMainThreadException
import android.os.Parcelable
import androidx.lifecycle.*
import com.example.bookbook_master.adapter.BookListAdapter
import com.example.bookbook_master.adapter.MainListAdapter
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.AppDatabase
import com.example.bookbook_master.model.roomDB.dao.RecentDAO
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.example.bookbook_master.model.roomDB.repository.RecentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

/**
 * 메인 화면(최근 본 상품) 뷰모델
 * @author philippe
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RecentRepository(application)
    private val items = repository.getAll()


    //뷰 타입을 이미지뷰타입으로 설정
    private val _bookListViewType = MutableLiveData(MainListAdapter.RECENT_VIEW_TYPE)
    val bookListViewType: LiveData<Int> = _bookListViewType


    fun getAll(): LiveData<List<Recent>> {
        return items
    }

    fun addRecent(recent: Recent){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRecent(recent)
        }
    }

    // 리소스 정리
    override fun onCleared() {
        super.onCleared()
    }
}
