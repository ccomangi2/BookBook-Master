package com.example.bookbook_master.model.data

import com.google.gson.annotations.SerializedName

/**
 * 도서 검색 API 응답 데이터
 * @author philippe
 */
data class BookListResponse(
    @field:SerializedName("meta") val meta: Meta,
    @field:SerializedName("documents") val documents: List<Document>
)