package com.example.bookbook_master.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.adapter.callback.BookListDiffCallback
import com.example.bookbook_master.adapter.callback.OnBookClickListener
import com.example.bookbook_master.adapter.callback.WishBookListDiffCallback
import com.example.bookbook_master.adapter.viewholder.BookSearchTypeViewHolder
import com.example.bookbook_master.adapter.viewholder.BookWishTypeViewHolder
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.example.bookbook_master.model.roomDB.entity.Wish

/**
 * 도서 리스트 어댑터 - 위시리스트 화면
 * @author philippe
 */
class WishListAdapter(var itemViewType: Int, private val bookClickListener: OnBookClickListener) :
    ListAdapter<Wish, RecyclerView.ViewHolder>(WishBookListDiffCallback()) {

    private var wishList = ArrayList<Wish>()

    companion object {
        const val WISH_VIEW_TYPE = 1
    }

    // 뷰홀더가 생성되는 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            WISH_VIEW_TYPE -> BookWishTypeViewHolder.from(parent, bookClickListener)
            else -> BookWishTypeViewHolder.from(parent, bookClickListener)
        }
    }

    // 생성된 뷰홀더에 데이터를 바인딩 해주는 함수
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BookWishTypeViewHolder -> {
                holder.bind(getItem(position))
                holder.item_number(position+1)
            }
        }
    }

    // 리스트 갱신
    fun setData(wish : List<Wish>){
        wishList.clear()
        wishList.addAll(wish)
    }

    // 뷰 타입 함수 (지금은 필요없는 함수)
    override fun getItemViewType(position: Int): Int {
        return itemViewType
    }

    override fun getItemCount(): Int {
        return wishList.size
    }
}