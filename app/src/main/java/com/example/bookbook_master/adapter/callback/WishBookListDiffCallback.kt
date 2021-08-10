package com.example.bookbook_master.adapter.callback

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.example.bookbook_master.model.roomDB.entity.Wish

/**
 * 기존 도서 목록과 신규 도서 목록을 비교하는 callback
 * @author philippe
 */
class WishBookListDiffCallback : DiffUtil.ItemCallback<Wish>() {
    override fun areItemsTheSame(oldItem: Wish, newItem: Wish): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Wish, newItem: Wish): Boolean {
        return oldItem.document == newItem.document
    }
}