package com.example.newsappmyself.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsappmyself.R
import com.example.newsappmyself.db.ArticleDatabase
import com.example.newsappmyself.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    lateinit var viewModel:NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProvider = NewsViewModelProviderFactory(application,newsRepository)
        viewModel = ViewModelProvider(this@NewsActivity,viewModelProvider).get(NewsViewModel::class.java)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottom_nav.setupWithNavController(navController)

    }
}