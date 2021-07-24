package com.example.bookbook_master.view.activity

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.bookbook_master.R
import com.example.bookbook_master.view.fragment.SearchFragment
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment.newInstance()).commitNow()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let { focusView ->
            clearTextInputFocus(focusView, ev)
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 키보드 숨김
     * @param focusView 현재 포커스를 가진 뷰
     */
    fun hideKeyboard(focusView: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(focusView.windowToken, 0)
    }

    /**
     * 검색어 입력창을 제외한 다른 영역 터치시 키보드 숨김
     * @param focusView 현재 포커스를 가진 뷰 (TextInputEditText)
     * @param ev MotionEvent
     */
    private fun clearTextInputFocus(focusView: View, ev: MotionEvent?) {
        if (focusView is TextInputEditText) {
            val rect = Rect()
            focusView.getLocalVisibleRect(Rect())
            ev?.let {
                val x = it.x.toInt()
                val y = it.y.toInt()
                if (!rect.contains(x, y)) {
                    hideKeyboard(focusView)
                    focusView.clearFocus()
                }
            }
        }
    }
}