package com.example.bookbook_master.model.roomDB.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.bookbook_master.model.roomDB.AppDatabase
import com.example.bookbook_master.model.roomDB.dao.RecentDAO
import com.example.bookbook_master.model.roomDB.dao.WishDAO
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.example.bookbook_master.model.roomDB.entity.Wish

class WishRepository(application: Application) {
    private val wishDAO: WishDAO
    private val wishList: LiveData<List<Wish>>

    init {
        var db = AppDatabase.getInstance(application)
        wishDAO = db!!.wishDao()
        wishList = db.wishDao().getAll()
    }

    suspend fun addWish(wish: Wish) {
        wishDAO.addWish(wish)
    }

    suspend fun updateWish(wish: Wish) {
        wishDAO.updateWish(wish)
    }

    fun deleteWish(title: String) {
        wishDAO.deleteWish(title)
    }

    fun getAll(): LiveData<List<Wish>> {
        return wishDAO.getAll()
    }

    fun getDateAll(): LiveData<List<Wish>> {
        return wishDAO.getDateAll()
    }

    fun getLowAll(): LiveData<List<Wish>> {
        return wishDAO.getLowAll()
    }

    fun getHighAll(): LiveData<List<Wish>> {
        return wishDAO.getHighAll()
    }

    fun deleteAll() {
        return wishDAO.deleteAll()
    }
}