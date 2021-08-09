package com.example.bookbook_master.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.adapter.callback.OnBookClickListener
import com.example.bookbook_master.databinding.ItemWishTypeBookBinding
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.entity.Wish

/**
 * 텍스트 타입 도서 뷰홀더 (사실상 둘 다 똑같음)
 * @author philippe
 */
class BookWishTypeViewHolder(private val binding: ItemWishTypeBookBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(wish: Wish) {
        binding.document = wish.document
        binding.executePendingBindings()
    }

    fun item_number(position: Int) {
        binding.document?.item_number = position.toString()
        binding.tvItemNumber.text = binding.document?.item_number
    }

    companion object {
        fun from(parent: ViewGroup, bookClickListener: OnBookClickListener): BookWishTypeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemWishTypeBookBinding.inflate(layoutInflater, parent, false)
            binding.bookClickListener = bookClickListener
            return BookWishTypeViewHolder(binding)
        }
    }
}