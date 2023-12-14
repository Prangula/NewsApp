package com.example.newsappmyself.repository

import com.example.newsappmyself.api.RetrofitInstance
import com.example.newsappmyself.db.ArticleDatabase
import com.example.newsappmyself.models.Article

class NewsRepository(val db:ArticleDatabase) {

    suspend fun getBreakingNews(country:String,pageSize:Int) =

        RetrofitInstance.api.getBreakingNews(country,pageSize)

    suspend fun getsearchNews(searchQuery:String,pageNumber:Int) =
        RetrofitInstance.api.searchNews(searchQuery,pageNumber)

    suspend fun upsert(article:Article) = db.getArticleDao().upsert(article)

    suspend fun delete(article: Article) = db.getArticleDao().delete(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()


}