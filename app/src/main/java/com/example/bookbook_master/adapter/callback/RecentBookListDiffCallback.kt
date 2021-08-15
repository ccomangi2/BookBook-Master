package com.example.bookbook_master.adapter.callback

import androidx.recyclerview.widget.DiffUtil
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.entity.Recent

/**
 * 최근 본 상품 callback
 * @author philippe
 */
class RecentBookListDiffCallback : DiffUtil.ItemCallback<Recent>() {
    override fun areItemsTheSame(oldItem: Recent, newItem: Recent): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Recent, newItem: Recent): Boolean {
        return oldItem.document == newItem.document
    }
}