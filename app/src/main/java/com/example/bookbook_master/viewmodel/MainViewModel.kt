package com.example.bookbook_master.viewmodel

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.*
import com.example.bookbook_master.adapter.BookListAdapter
import com.example.bookbook_master.model.roomDB.AppDatabase
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.example.bookbook_master.model.roomDB.repository.RecentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull

class MainViewModel (application: Application) : AndroidViewModel(application) {
    val readAllData : LiveData<List<Recent>>
    private val repository : RecentRepository

    //뷰 타입을 이미지뷰타입으로 설정
    private val _bookListViewType = MutableLiveData(BookListAdapter.TEXT_VIEW_TYPE)
    val bookListViewType: LiveData<Int> = _bookListViewType

    init {
        val userDao = AppDatabase.getInstance(application)!!.recentDao()
        repository = RecentRepository(userDao)
        readAllData = repository.readAllData.asLiveData()
    }

    fun addRecent(recent: Recent){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRecent(recent)
        }
    }

    fun updateRecent(recent : Recent){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRecent(recent)
        }
    }

    fun deleteRecent(recent : Recent){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRecent(recent)
        }
    }

    // ViewModel에 파라미터를 넘기기 위해서, 파라미터를 포함한 Factory 객체를 생성하기 위한 클래스
    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(application) as T
        }
    }
}