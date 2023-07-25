package com.example.newsapp.api

import com.example.newsapp.utils.model.NewsResponse
import com.example.newsapp.utils.constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/v2/top-headlines")
    suspend fun  getBreakingNews(
        @Query("country")
        countryCode:String="us",
        @Query("page")
        pageNumber:Int=1,
        @Query("apikey")
        apikey:String=API_KEY
    ):Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun  SearchNews(
        @Query("q")
        searchQuery:String,
        @Query("page")
        pageNumber:Int=1,
        @Query("apikey")
        apikey:String=API_KEY
    ):Response<NewsResponse>
}