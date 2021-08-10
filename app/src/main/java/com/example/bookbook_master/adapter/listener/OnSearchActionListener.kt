package com.example.bookbook_master.adapter.listener

import androidx.constraintlayout.widget.ConstraintLayout

/**
 * 도서 검색 리스너 (키보드 검색 Action)
 * @author philippe
 */
interface OnSearchActionListener {
    fun onSearchEditorAction(keyword: String, loading: ConstraintLayout)
}