package com.example.bookbook_master.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * 도서 정보
 * @author philippe
 */
@Parcelize
data class Document(
    @field:SerializedName("title") val title: String,
    @field:SerializedName("contents") val contents: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("isbn") val isbn: String,
    @field:SerializedName("datetime") val datetime: String,
    @field:SerializedName("authors") val authors: List<String>,
    @field:SerializedName("publisher") val publisher: String,
    @field:SerializedName("translators") val translators: List<String>,
    @field:SerializedName("price") val price: Int,
    @field:SerializedName("sale_price") val salePrice: Int,
    @field:SerializedName("thumbnail") val thumbnail: String,
    @field:SerializedName("status") val status: String,
    var isFavorite: Boolean = false
) : Parcelable