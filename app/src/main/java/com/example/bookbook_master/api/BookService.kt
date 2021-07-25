package com.example.bookbook_master.api

import com.example.bookbook_master.BuildConfig
import com.example.bookbook_master.model.data.BookListResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 도서 검색 API
 * @author philippe
 */
interface BookService {
    @GET("/v3/search/book")
    suspend fun searchBooks(
        @Query("query") query: String, //검색을 원하는 질의어
        @Query("size") size: Int = MAX_PAGE_COUNT, //한 페이지에 보여질 문서 수
        @Query("target") target: String = TARGET_TITLE, //검색 필드 제한(title)
        @Query("page") page: Int //결과 페이지 번호
    ): BookListResponse

    companion object {
        private const val BASE_URL = "https://dapi.kakao.com/" //url
        private const val HEADER_AUTHORIZATION = "Authorization" //KaKaoAK

        private const val MAX_PAGE_COUNT = 15       // 한 페이지에 보여질 문서 수
        private const val TARGET_TITLE = "title"    // 검색 필드 (title)

        fun create(): BookService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }

            val responseInterceptor = Interceptor {
                val newRequest = it.request().newBuilder()
                    .addHeader(HEADER_AUTHORIZATION, BuildConfig.KAKAO_AK)
                    .build()
                return@Interceptor it.proceed(newRequest)
            }

            //http 통신
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(responseInterceptor)
                .build()

            //api 연동
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BookService::class.java)
        }
    }

}