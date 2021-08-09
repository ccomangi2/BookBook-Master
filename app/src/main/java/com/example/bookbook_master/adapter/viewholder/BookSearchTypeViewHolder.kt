package com.example.bookbook_master.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.adapter.callback.OnBookClickListener
import com.example.bookbook_master.databinding.ItemSearchTypeBookBinding
import com.example.bookbook_master.model.data.Document

/**
 * 이미지 타입 도서 뷰홀더 (사실상 둘 다 똑같음)
 * @author philippe
 */
class BookSearchTypeViewHolder(private val binding: ItemSearchTypeBookBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(document: Document) {
        binding.document = document
        binding.executePendingBindings()
    }

    fun item_number(position: Int) {
        binding.document?.item_number = position.toString()
        binding.tvItemNumber.text = binding.document?.item_number
    }

    companion object {
        fun from(parent: ViewGroup, bookClickListener: OnBookClickListener): BookSearchTypeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemSearchTypeBookBinding.inflate(layoutInflater, parent, false)
            binding.bookClickListener = bookClickListener
            return BookSearchTypeViewHolder(binding)
        }
    }
}