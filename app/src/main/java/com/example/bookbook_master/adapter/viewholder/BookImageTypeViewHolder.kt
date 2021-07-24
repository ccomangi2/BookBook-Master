package com.example.bookbook_master.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.adapter.callback.OnBookClickListener
import com.example.bookbook_master.databinding.ItemImageTypeBookBinding
import com.example.bookbook_master.model.data.Document

/**
 * 이미지 타입 도서 뷰홀더 (사실상 둘 다 똑같음)
 * @author philippe
 */
class BookImageTypeViewHolder(private val binding: ItemImageTypeBookBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(document: Document) {
        binding.document = document
        binding.executePendingBindings()
    }

    fun item_number(position: Int) {
        binding.document?.item_number = position.toString()
        binding.tvItemNumber.text = binding.document?.item_number
    }

    companion object {
        fun from(parent: ViewGroup, bookClickListener: OnBookClickListener): BookImageTypeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemImageTypeBookBinding.inflate(layoutInflater, parent, false)
            binding.bookClickListener = bookClickListener
            return BookImageTypeViewHolder(binding)
        }
    }
}