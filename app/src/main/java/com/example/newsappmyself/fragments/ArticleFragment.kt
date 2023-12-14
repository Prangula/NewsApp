package com.example.newsappmyself.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsappmyself.R
import com.example.newsappmyself.ui.NewsActivity
import com.example.newsappmyself.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment:Fragment(R.layout.fragment_article) {


    private lateinit var viewModel: NewsViewModel
    private val args:ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel

        val article = args.article

        webview?.let {

            webview.webViewClient = WebViewClient()
            webview.loadUrl(article.url)
        }

        toolbar_article.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolbar_article.setNavigationOnClickListener {

            requireActivity().onBackPressed()

        }


        fab.setOnClickListener {

            viewModel.saveArticle(article)
            Toast.makeText(activity,"Article Saved",Toast.LENGTH_SHORT).show()

        }



    }
}