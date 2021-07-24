package com.example.bookbook_master.viewmodel

import android.content.Context
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookbook_master.adapter.BookListAdapter
import com.example.bookbook_master.adapter.callback.OnSearchActionListener
import com.example.bookbook_master.model.data.BookRepository
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.view.fragment.SearchFragment
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

/**
 * 도서 검색 뷰모델
 * @author philippe
 */
class SearchViewModel(private val bookRepository: BookRepository) : BaseViewModel(),
    OnSearchActionListener {

    private lateinit var bookListView: RecyclerView

    //coroutines의 상태
    private var searchBookJob = Job()

    //뷰 타입을 텍스트뷰타입으로 설정(상관없음)
    private val _bookListViewType = MutableLiveData(BookListAdapter.TEXT_VIEW_TYPE)
    val bookListViewType: LiveData<Int> = _bookListViewType

    //검색어
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
        // 프로그래스바 보이기
        showLoading()
        if (searchBookJob.isActive) {
            searchBookJob.cancel() // 취소 상태
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
                    if(isEndBookList == true) {
                        showToastMessage("마지막 페이지 입니다.")
                    } else {
                        mainBookList.value?.let {
                            val bookList = it.toMutableList()
                            if (isRefresh) {
                                bookList.clear()
                            }
                            bookList.addAll(this.documents)
                            emit(bookList)
                        } ?: emit(this.documents)
                        bookListPageNo++ // 도서 목록 페이지 번호 증가
                    }
                }
                // 프로그레스바 없애기
                hideLoading()
            } catch (ex: Exception) { //네트워크 오류
                ex.message?.let {
                    showNetworkError(it)
                }
                // 프로그래스바 없애기
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

    // 리소스 정리
    override fun onCleared() {
        super.onCleared()
        searchBookJob.cancel()
    }

    /*
     * 토스트 메시지 띄우기
     */
    private fun showToastMessage(msg: String) {
        Toast.makeText(bookListView.context, msg, Toast.LENGTH_SHORT).show()
    }
}