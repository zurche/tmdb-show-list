package com.az.tmdbshowlist.di

import com.az.tmdbshowlist.BuildConfig
import com.az.tmdbshowlist.data.TVSRepository
import com.az.tmdbshowlist.data.remote.TVSApi
import com.az.tmdbshowlist.data.remote.TVSRemoteDataSource
import com.az.tmdbshowlist.ui.TVSViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<TVSApi> {
        val okHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                }).build()

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(TVSApi::class.java)
    }
    single { TVSRemoteDataSource(get(), apiKey = BuildConfig.TMDB_API_KEY) }
    single { TVSRepository(get()) }
}

val uiModule = module {
    viewModel { TVSViewModel(get()) }
}