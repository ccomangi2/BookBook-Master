package com.example.bookbook_master.adapter.viewholder

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.adapter.listener.OnBookClickListener
import com.example.bookbook_master.databinding.ItemWishTypeBookBinding
import com.example.bookbook_master.model.roomDB.entity.Wish

/**
 * 위시리스트 타입 도서 뷰홀더
 * @author philippe
 */
class BookWishTypeViewHolder(private val binding: ItemWishTypeBookBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(wish: Wish) {
        binding.wish = wish
        binding.executePendingBindings()
    }

    fun item_number(position: Int) {
        binding.wish?.id = position
        binding.wish?.document?.item_number = position.toString()
        binding.tvItemNumber.text = binding.wish?.id.toString()
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