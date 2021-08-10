package com.example.bookbook_master.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.adapter.callback.BookListDiffCallback
import com.example.bookbook_master.adapter.listener.OnBookClickListener
import com.example.bookbook_master.adapter.viewholder.BookSearchTypeViewHolder
import com.example.bookbook_master.model.data.Document

/**
 * 도서 리스트 어댑터 - 검색 화면
 * @author philippe
 */
class BookListAdapter(var itemViewType: Int, private val bookClickListener: OnBookClickListener) :
    ListAdapter<Document, RecyclerView.ViewHolder>(BookListDiffCallback()) {

    companion object {
        const val SEARCH_VIEW_TYPE = 2
    }

    // 뷰홀더가 생성되는 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SEARCH_VIEW_TYPE -> BookSearchTypeViewHolder.from(parent, bookClickListener)
            else -> BookSearchTypeViewHolder.from(parent, bookClickListener)
        }
    }

    // 생성된 뷰홀더에 데이터를 바인딩 해주는 함수
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BookSearchTypeViewHolder -> {
                holder.bind(getItem(position))
                holder.item_number(position+1)
            }
        }
    }

    // 뷰 타입 함수 (지금은 필요없는 함수)
    override fun getItemViewType(position: Int): Int {
        return itemViewType
    }
}