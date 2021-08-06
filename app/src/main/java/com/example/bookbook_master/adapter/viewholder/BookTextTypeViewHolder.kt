package com.example.bookbook_master.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.adapter.callback.OnBookClickListener
import com.example.bookbook_master.databinding.ItemTextTypeBookBinding
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.entity.Recent

/**
 * 텍스트 타입 도서 뷰홀더 (사실상 둘 다 똑같음)
 * @author philippe
 */
class BookTextTypeViewHolder(private val binding: ItemTextTypeBookBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(document: Document) {
        binding.document = document
        binding.executePendingBindings()
    }

    fun recent_bind(recent: Recent) {
        binding.document = recent.document
        binding.executePendingBindings()
    }

    fun item_number(position: Int) {
        binding.document?.item_number = position.toString()
        binding.tvItemNumber.text = binding.document?.item_number
    }

    companion object {
        fun from(parent: ViewGroup, bookClickListener: OnBookClickListener): BookTextTypeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemTextTypeBookBinding.inflate(layoutInflater, parent, false)
            binding.bookClickListener = bookClickListener
            return BookTextTypeViewHolder(binding)
        }
    }
}