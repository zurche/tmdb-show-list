package com.az.tmdbshowlist.di

import com.az.tmdbshowlist.data.TVSRepository
import com.az.tmdbshowlist.data.remote.TVSRemoteDataSource
import com.az.tmdbshowlist.ui.TVSViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { }
    single { TVSRemoteDataSource(get()) }
    single { TVSRepository(get()) }
}

val uiModule = module {
    viewModel { TVSViewModel(get()) }
}