package com.example.bookbook_master.model.modul

import com.example.bookbook_master.api.BookService
import com.example.bookbook_master.model.data.BookRepository
import com.example.bookbook_master.viewmodel.DetailViewModel
import com.example.bookbook_master.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        DetailViewModel()
    }
}

val repositoryModule = module {
    single { BookRepository(get()) }
}

val apiModule = module {
    single { BookService.create() }
}
