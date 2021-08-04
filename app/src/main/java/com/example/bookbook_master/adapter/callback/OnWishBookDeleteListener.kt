package com.example.bookbook_master.adapter.callback

import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.entity.Contacts

/**
 * 위시리스트 도서 삭제 리스너
 * @author philippe
 */
interface OnWishBookDeleteListener {
    fun deleteTodo(contacts: Contacts)
}