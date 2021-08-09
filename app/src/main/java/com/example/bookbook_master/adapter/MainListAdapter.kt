package com.example.bookbook_master.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.R
import com.example.bookbook_master.adapter.callback.BookListDiffCallback
import com.example.bookbook_master.adapter.callback.OnBookClickListener
import com.example.bookbook_master.adapter.callback.RecentBookListDiffCallback
import com.example.bookbook_master.adapter.viewholder.BookImageTypeViewHolder
import com.example.bookbook_master.adapter.viewholder.BookRecentTypeViewHolder
import com.example.bookbook_master.adapter.viewholder.BookTextTypeViewHolder
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.AppDatabase
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.example.bookbook_master.view.fragment.MainFragment
import kotlinx.android.synthetic.main.item_text_type_book.view.*

/**
 * 도서 리스트 어댑터
 * @author philippe
 */
class MainListAdapter(var itemViewType: Int, private val bookClickListener: OnBookClickListener) :
    ListAdapter<Recent, RecyclerView.ViewHolder>(RecentBookListDiffCallback()) {

    private var recentList = ArrayList<Recent>()

    companion object {
        const val RECENT_VIEW_TYPE = 3
    }

    // 뷰홀더가 생성되는 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            RECENT_VIEW_TYPE -> BookRecentTypeViewHolder.from(parent, bookClickListener)
            else -> BookRecentTypeViewHolder.from(parent, bookClickListener)
        }
    }

    // 생성된 뷰홀더에 데이터를 바인딩 해주는 함수
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BookRecentTypeViewHolder -> {
                holder.bind(recentList[position])
                holder.item_number(position+1)
            }
        }
    }

    // 리스트 갱신
    fun setData(recent : List<Recent>){
        recentList.clear()
        recentList.addAll(recent)
    }

    // 뷰 타입 함수 (지금은 필요없는 함수)
    override fun getItemViewType(position: Int): Int {
        return itemViewType
    }

    override fun getItemCount(): Int {
        return recentList.size
    }
}