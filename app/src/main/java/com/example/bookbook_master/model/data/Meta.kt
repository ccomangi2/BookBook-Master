package com.example.bookbook_master.model.data

import com.google.gson.annotations.SerializedName

/**
 * API 메타 정보
 * @author philippe
 */
data class Meta(
    //검색된 문서 수
    @field:SerializedName("total_count") val totalCount: String,
    //중복된 문서를 제외하고, 처음부터 요청 페이지까지의 노출 가능 문서 수
    @field:SerializedName("pageable_count") val pageableCount: Int,
    //현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
    @field:SerializedName("is_end") val isEnd: Boolean
)