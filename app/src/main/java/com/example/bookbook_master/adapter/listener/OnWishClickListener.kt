package com.example.bookbook_master.adapter.listener

import com.example.bookbook_master.model.data.Document
/**
 * 좋아요 클릭 리스너
 * @author philippe
 */
interface OnWishClickListener {
    fun onClickWish(document: Document)
}