package com.example.bookbook_master.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.adapter.callback.OnBookClickListener
import com.example.bookbook_master.databinding.ItemTextTypeBookBinding
import com.example.bookbook_master.model.data.Document

/**
 * 텍스트 타입 도서 뷰홀더
 * @author philippe
 */
class BookTextTypeViewHolder(private val binding: ItemTextTypeBookBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(document: Document) {
        binding.document = document
        binding.executePendingBindings()
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