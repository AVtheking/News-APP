package com.example.newsapp.ui.Repositroy


import android.print.PageRange
import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.database.NewsDatabase
import com.example.newsapp.utils.model.NewsResponse
import retrofit2.Response
import retrofit2.Retrofit

class NewsRepository(private val db:NewsDatabase) {
        suspend fun getBreakingNews(countryCode:String,pageNumber: Int)=
            RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)



}