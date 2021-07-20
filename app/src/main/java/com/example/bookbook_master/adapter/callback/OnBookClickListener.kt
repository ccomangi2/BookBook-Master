package com.example.bookbook_master.adapter.callback

import com.example.bookbook_master.model.data.Document
/**
 * 도서 목록 클릭 리스너
 * @author philippe
 */
interface OnBookClickListener {
    fun onClickBook(document: Document)
}