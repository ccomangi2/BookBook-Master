package com.example.bookbook_master.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.adapter.callback.OnBookClickListener
import com.example.bookbook_master.databinding.ItemImageTypeBookBinding
import com.example.bookbook_master.databinding.ItemRecentTypeBookBinding
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.entity.Recent

/**
 * 이미지 타입 도서 뷰홀더 (사실상 둘 다 똑같음)
 * @author philippe
 */
class BookRecentTypeViewHolder(private val binding: ItemRecentTypeBookBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(recent: Recent) {
        binding.recent = recent
        binding.executePendingBindings()
    }

    fun item_number(position: Int) {
        binding.recent?.id = position
        binding.tvItemNumber.text = binding.recent?.id.toString()
    }

    companion object {
        fun from(parent: ViewGroup, bookClickListener: OnBookClickListener): BookRecentTypeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentTypeBookBinding.inflate(layoutInflater, parent, false)
            binding.bookClickListener = bookClickListener
            return BookRecentTypeViewHolder(binding)
        }
    }
}