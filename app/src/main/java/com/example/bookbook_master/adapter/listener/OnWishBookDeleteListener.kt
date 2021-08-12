package com.example.bookbook_master.adapter.listener

import com.example.bookbook_master.model.data.Document

/**
 * 위시리스트 도서 삭제 리스너
 * @author philippe
 */
interface OnWishBookDeleteListener {
    fun onSwapWish(document: Document)
}