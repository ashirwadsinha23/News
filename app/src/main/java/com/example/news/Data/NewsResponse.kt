package com.example.news.Data

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)