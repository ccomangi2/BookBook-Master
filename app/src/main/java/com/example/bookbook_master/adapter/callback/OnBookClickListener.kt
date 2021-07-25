package com.example.bookbook_master.adapter.callback

import com.example.bookbook_master.model.data.Document
/**
 * 도서 목록 클릭 리스너
 * @author philippe
 */
//도서 아이템을 클릭했을 경우 도서 상세정보 페이지로 이동합니다.
interface OnBookClickListener {
    fun onClickBook(document: Document)
}