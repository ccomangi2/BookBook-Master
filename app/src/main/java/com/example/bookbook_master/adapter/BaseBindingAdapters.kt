package com.example.bookbook_master.adapter

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bookbook_master.R
import com.example.bookbook_master.adapter.callback.OnSearchActionListener
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

/**
 * 공통으로 사용하는 바인딩 어댑터
 * @author philippe
 */
object BaseBindingAdapters {

    private const val INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    private const val OUTPUT_DATE_FORMAT = "yyyy-MM-dd"

    // 검색 결과가 없을 경우, "검색 결과가 없습니다" 문구 띄우기
    @JvmStatic
    @BindingAdapter("visibleOrGone")
    fun setVisibleOrGone(view: View, isVisible: Boolean) {
        if (isVisible) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    // 도서 표지 이미지 띄우기
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

    // 도서 출판 날짜 띄우기
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

    // 검색창
    @BindingAdapter("onSearchActionListener")
    @JvmStatic
    fun setOnSearchActionListener(editText: TextInputEditText, listener: OnSearchActionListener) {
        editText.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //listener.onSearchEditorAction(view.text.toString())
            }
            false
        }
    }

    // 저자
    @SuppressLint("SetTextI18n")
    @BindingAdapter("bookAuthors")
    @JvmStatic
    fun setBookAuthors(textView: AppCompatTextView, authors: List<String>) {
        if (authors.isEmpty()) {
            textView.text = ""
        } else {
            textView.text = authors.joinToString(",")
        }
        // 저자가 두명 이상일 경우 세번째 저자부터는 생략 기호 (...) 표시
        if (authors.size > 2) {
            textView.text = authors.get(0) + "," + authors.get(1) + "..."
        }
    }

    // 아이템 번호
    @BindingAdapter("itemNumber")
    @JvmStatic
    fun setBookItemNumber(textView: AppCompatTextView, item_number: Int) {
        textView.text = item_number.toString()
    }
}