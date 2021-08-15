package com.example.bookbook_master.adapter.callback

import androidx.recyclerview.widget.DiffUtil
import com.example.bookbook_master.model.data.Document
/**
 * 기존 도서 목록과 신규 도서 목록을 비교하는 callback
 * @author philippe
 */
class BookListDiffCallback : DiffUtil.ItemCallback<Document>() {
    override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
        return (oldItem.isbn == newItem.isbn && oldItem.isFavorite == newItem.isFavorite) // 좋아요 여부(사용하지 않음)
    }

    override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
        return oldItem == newItem
    }
}