package com.example.news.Repository

import com.example.news.api.NewsApi
import com.example.news.api.RetrofitInstance
import javax.inject.Inject


class NewsRepository @Inject constructor(val newsApi: NewsApi) {
    suspend fun getBreakingNews(countryCode:String,pageNumber:Int)=
        newsApi.getBreakingNews(countryCode,pageNumber)
//        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun searchNews (searchQuery:String,pageNumber: Int) =
        newsApi.searchForNews(searchQuery,pageNumber)
//        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)

}