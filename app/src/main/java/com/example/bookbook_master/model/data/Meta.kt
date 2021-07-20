package com.example.bookbook_master.model.data

import com.google.gson.annotations.SerializedName

/**
 * API 메타 정보
 * @author philippe
 */
data class Meta(
    @field:SerializedName("total_count") val totalCount: String,
    @field:SerializedName("pageable_count") val pageableCount: Int,
    @field:SerializedName("is_end") val isEnd: Boolean
)