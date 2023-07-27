package com.example.newsapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.ui.Repositroy.NewsRepository
import com.example.newsapp.utils.Resource
import com.example.newsapp.utils.model.Article
import com.example.newsapp.utils.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository): ViewModel() {

    var breakingNewsPage=1
    var searchNewsPage=1
    var breakingNewsResponse:NewsResponse?=null
    var searchNewsResponse:NewsResponse?=null
    fun getBreakingNews(countryCode: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
            if (response.isSuccessful) {



                emit(Resource.Success(response.body()))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.toString()))
        }
    }
    fun getSearchNews(searchQuery:String)=liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            val response=newsRepository.getSearchNews(searchQuery,searchNewsPage)
            if(response.isSuccessful())
            {

                emit(Resource.Success(response.body()))
            }
            else
            {
                emit(Resource.Error(response.message()))
            }
        }catch (e:Exception){
            emit(Resource.Error(e.toString()))
        }
    }
    fun getArticles()=newsRepository.getArticles()
    fun upsert(article:Article)=viewModelScope.launch {
        newsRepository.upsert(article   )

    }
    fun deleteArticle(article:Article)=viewModelScope.launch {
        newsRepository.delete(article)
    }
}
