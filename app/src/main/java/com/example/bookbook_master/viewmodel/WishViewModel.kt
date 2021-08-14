package com.example.bookbook_master.viewmodel

import android.app.Application
import android.os.NetworkOnMainThreadException
import android.os.Parcelable
import androidx.lifecycle.*
import com.example.bookbook_master.adapter.BookListAdapter
import com.example.bookbook_master.adapter.MainListAdapter
import com.example.bookbook_master.adapter.WishListAdapter
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.AppDatabase
import com.example.bookbook_master.model.roomDB.dao.RecentDAO
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.example.bookbook_master.model.roomDB.entity.Wish
import com.example.bookbook_master.model.roomDB.repository.RecentRepository
import com.example.bookbook_master.model.roomDB.repository.WishRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class WishViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = WishRepository(application)


    //뷰 타입을 이미지뷰타입으로 설정
    private val _bookListViewType = MutableLiveData(WishListAdapter.WISH_VIEW_TYPE)
    val bookListViewType: LiveData<Int> = _bookListViewType


    fun getAll(): LiveData<List<Wish>> {
        return repository.getAll()
    }

    fun getDateAll(): LiveData<List<Wish>> {
        return repository.getDateAll()
    }

    fun getLowAll(): LiveData<List<Wish>> {
        return repository.getLowAll()
    }

    fun getHighAll(): LiveData<List<Wish>> {
        return repository.getHighAll()
    }

    fun addWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWish(wish)
        }
    }

    fun updateWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWish(wish)
        }
    }

    fun deleteWish(title: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWish(title)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    // 리소스 정리
    override fun onCleared() {
        super.onCleared()
    }
}
