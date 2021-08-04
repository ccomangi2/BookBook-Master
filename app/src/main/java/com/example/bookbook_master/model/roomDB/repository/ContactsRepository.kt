package com.example.bookbook_master.model.roomDB.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.bookbook_master.model.roomDB.AppDatabase
import com.example.bookbook_master.model.roomDB.dao.ContactsDao
import com.example.bookbook_master.model.roomDB.entity.Contacts

class ContactsRepository(application: Application) {
    private val contactsDao: ContactsDao
    private val contactsList: LiveData<List<Contacts>>

    init {
        var db = AppDatabase.getInstance(application)
        contactsDao = db!!.contactsDao()
        contactsList = db.contactsDao().getAll()
    }

    fun insert(contacts: Contacts) {
        contactsDao.insertAll(contacts)
    }

    fun delete(contacts: Contacts){
        contactsDao.delete(contacts)
    }

    fun getAll(): LiveData<List<Contacts>> {
        return contactsDao.getAll()
    }
}