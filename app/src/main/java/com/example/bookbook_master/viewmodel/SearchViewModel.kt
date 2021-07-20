package com.example.bookbook_master.viewmodel

import androidx.lifecycle.*
import com.example.bookbook_master.adapter.BookListAdapter
import com.example.bookbook_master.adapter.callback.OnSearchActionListener
import com.example.bookbook_master.model.data.BookRepository
import com.example.bookbook_master.model.data.Document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * 도서 검색 뷰모델
 * @author philippe
 */
class SearchViewModel(private val bookRepository: BookRepository) : BaseViewModel(),
    OnSearchActionListener {

    private var searchBookJob = Job()

    private val _bookListViewType = MutableLiveData(BookListAdapter.TEXT_VIEW_TYPE)
    val bookListViewType: LiveData<Int> = _bookListViewType

    private val searchKeyword = MutableLiveData<String>()

    val inputKeyword = MutableLiveData("")

    var mainBookList = searchKeyword.switchMap {
        requestBookList(it, isRefreshBookList)
    }

    private val _isEmptyBookList = MutableLiveData<Boolean>()
    val isEmptyBookList: LiveData<Boolean> = _isEmptyBookList

    var isEndBookList: Boolean = false              // 도서 목록 API 호출 완료여부 (더이상 호출할 페이지가 없음)
    private var bookListPageNo: Int = 1             // 현재 도서 목록 페이지 번호
    private var isRefreshBookList: Boolean = true   // 도서 목록 API 호출시 기존 데이터 초기화 여부

    /**
     * 도서 목록의 뷰타입 설정
     * @param viewType 리스트뷰 뷰타입 (TEXT, IMAGE)
     */
    private fun setBookListViewType(viewType: Int) = _bookListViewType.postValue(viewType)

    /**
     * 도서 목록의 뷰타입 변경
     * @param viewType 리스트뷰 뷰타입 (TEXT, IMAGE)
     */
    fun toggleBookListViewType(viewType: Int?) {
        viewType?.let {
            if (it == BookListAdapter.TEXT_VIEW_TYPE) {
                setBookListViewType(BookListAdapter.IMAGE_VIEW_TYPE)
            } else {
                setBookListViewType(BookListAdapter.TEXT_VIEW_TYPE)
            }
        }
    }

    /**
     * 도서 검색 요청
     * @param keyword 검색할 도서 제목
     */
    fun searchBookList(keyword: String) {
        if (keyword.isBlank()) {
            return
        }

        isRefreshBookList = true
        viewModelScope.launch {
            fetchSearchKeyword(keyword)
        }
    }

    /**
     * 다음 페이지 도서 목록 요청
     */
    fun searchMoreBookList() {
        isRefreshBookList = false
        viewModelScope.launch {
            searchKeyword.value?.let {
                fetchSearchKeyword(it)
            }
        }
    }

    /**
     * 검색 결과가 없을시 텍스트 표시
     * @param isEmpty 검색결과 존재 유무
     */
    fun setEmptyBookList(isEmpty: Boolean) {
        _isEmptyBookList.value = isEmpty
    }

    /**
     * 해당 검색어 변경을 요청
     * @param keyword 검색할 도서 제목
     */
    private fun fetchSearchKeyword(keyword: String) {
        searchKeyword.value = keyword
    }

    /**
     * 도서 목록 API 요청
     * @param keyword 검색할 도서 제목
     * @param isRefresh 기존 데이터 초기화 여부
     */
    private fun requestBookList(keyword: String, isRefresh: Boolean = true): LiveData<List<Document>> {
        showLoading()
        if (searchBookJob.isActive) {
            searchBookJob.cancel()
            searchBookJob = Job()
        }

        if (isRefresh) {
            isEndBookList = false
            bookListPageNo = 1
        }

        return liveData(CoroutineScope(searchBookJob + Dispatchers.IO).coroutineContext) {
            try {
                bookRepository.getBookList(keyword, bookListPageNo).run {
                    isEndBookList = this.meta.isEnd
                    mainBookList.value?.let {
                        val bookList = it.toMutableList()
                        if (isRefresh) {
                            bookList.clear()
                        }
                        bookList.addAll(this.documents)
                        emit(bookList)
                    } ?: emit(this.documents)

                    bookListPageNo++
                }
                hideLoading()
            } catch (ex: Exception) {
                ex.message?.let {
                    showNetworkError(it)
                }
                hideLoading()
            }
        }
    }

    /**
     * 검색 버튼 선택
     * @param keyword 검색할 도서 제목
     */
    override fun onSearchEditorAction(keyword: String) {
        searchBookList(keyword)
    }

    override fun onCleared() {
        super.onCleared()
        searchBookJob.cancel()
    }

}