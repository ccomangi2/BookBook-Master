package com.example.bookbook_master.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookbook_master.model.data.Document

/**
 * 도서 상세정보 뷰모델
 * @author philippe
 */
class DetailViewModel : BaseViewModel() {

    private val _document = MutableLiveData<Document>()
    val document: LiveData<Document> = _document

    /**
     * 도서 정보 세팅
     * @param document 도서 정보
     */
    fun setDocument(document: Document) {
        _document.value = document
    }
}