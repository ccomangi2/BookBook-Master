package com.example.bookbook_master.adapter

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bookbook_master.R
import com.example.bookbook_master.adapter.callback.OnSearchActionListener
import com.google.android.material.textfield.TextInputEditText
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 공통으로 사용하는 바인딩 어댑터
 * @author philippe
 */
object BaseBindingAdapters {

    private const val INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    private const val OUTPUT_DATE_FORMAT = "yyyy-MM-dd"

    @JvmStatic
    @BindingAdapter("visibleOrGone")
    fun setVisibleOrGone(view: View, isVisible: Boolean) {
        if (isVisible) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    @BindingAdapter("isShowLoading")
    @JvmStatic
    fun setLoading(loadingBar: ContentLoadingProgressBar, isShow: Boolean) {
        if (isShow) {
            loadingBar.show()
        } else {
            loadingBar.hide()
        }
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImageUrl(imageView: AppCompatImageView, imageUrl: String?) {
        imageUrl?.let {
            if (it.isNotBlank()) {
                Glide.with(imageView.context)
                    .load(it)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .into(imageView)
            }
        }
    }

    @BindingAdapter("dateTimeText")
    @JvmStatic
    fun setDateTimeText(textView: AppCompatTextView, dateTime: String?) {
        dateTime?.let {
            if (it.isNotBlank()) {
                val inputFormat = SimpleDateFormat(INPUT_DATE_FORMAT, Locale.getDefault())
                val outputFormat = SimpleDateFormat(OUTPUT_DATE_FORMAT, Locale.getDefault())
                try {
                    inputFormat.parse(dateTime)?.let { date ->
                        val resultDate = outputFormat.format(date)
                        textView.text = resultDate
                    } ?: run {
                        textView.text = ""
                    }
                } catch (ex: ParseException) {
                    textView.text = ""
                }
            } else {
                textView.text = ""
            }
        }
    }

    @BindingAdapter("bookListViewType")
    @JvmStatic
    fun setBookListViewType(imageView: AppCompatImageView, viewType: Int) {
        when (viewType) {
            BookListAdapter.TEXT_VIEW_TYPE -> imageView.setImageResource(R.drawable.ic_list_on_30)
            BookListAdapter.IMAGE_VIEW_TYPE -> imageView.setImageResource(R.drawable.ic_grid_on_30)
            else -> imageView.setImageResource(R.drawable.ic_list_on_30)
        }
    }

    @BindingAdapter("onSearchActionListener")
    @JvmStatic
    fun setOnSearchActionListener(editText: TextInputEditText, listener: OnSearchActionListener) {
        editText.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                listener.onSearchEditorAction(view.text.toString())
            }
            false
        }
    }

    @BindingAdapter("bookAuthors")
    @JvmStatic
    fun setBookAuthors(textView: AppCompatTextView, authors: List<String>) {
        if (authors.isEmpty()) {
            textView.text = ""
        } else {
            textView.text = authors.joinToString(",")
        }
    }

    @BindingAdapter("isFavoriteBook")
    @JvmStatic
    fun setFavoriteBook(imageButton: AppCompatImageButton, isFavorite: Boolean) {
        if (isFavorite) {
            imageButton.setImageResource(R.drawable.ic_favorite_on_24)
        } else {
            imageButton.setImageResource(R.drawable.ic_favorite_off_24)
        }
    }
}