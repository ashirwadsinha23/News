package com.example.news.Repository

import com.example.news.api.RetrofitInstance


class NewsRepository() {
    suspend fun getBreakingNews(countryCode:String,pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun searchNews (searchQuery:String,pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)

}