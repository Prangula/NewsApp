package com.example.newsappmyself.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsappmyself.repository.NewsRepository

class NewsViewModelProviderFactory(val app:Application,val newsRepository: NewsRepository):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository,app) as T
    }

}