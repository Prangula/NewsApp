package com.example.newsappmyself.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.GeneratedAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappmyself.R
import com.example.newsappmyself.adapters.ArticleAdapter
import com.example.newsappmyself.models.Article
import com.example.newsappmyself.ui.NewsActivity
import com.example.newsappmyself.ui.NewsViewModel
import com.example.newsappmyself.utils.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment:Fragment(R.layout.fragment_breaking_news) {

    private lateinit var viewModel:NewsViewModel
    private var dialog:Dialog? =null
    private var items:ArrayList<Article> = ArrayList()
    private lateinit var newsAdapter: ArticleAdapter
    val TAG = "Error"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel

        setUpRecyclerView(items)

        newsAdapter.setOnItemClickListener {


            val bundle = Bundle().apply {

                putSerializable("article",it)

            }

            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment,
            bundle)


        }


        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {response->

            when(response){


                is Resource.Success->{
                    hideDialog()
                    response.data!!.let {newsResponse->

                        items.clear()
                        items.addAll(newsResponse!!.articles)
                        newsAdapter.notifyDataSetChanged()

                    }



                }

                is Resource.Error->{
                    hideDialog()
                    response.message!!.let {message->

                        Log.e(TAG,"Error, $message")

                    }


                }

                is Resource.Loading->{

                    showDialog()
                }


            }



        })

    }


    private fun setUpRecyclerView(items:ArrayList<Article>){

        newsAdapter = ArticleAdapter(items)

        rv_breaking_news.apply {

            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)


        }



    }


    private fun showDialog(){

        dialog = activity?.let { Dialog(it)


        }
        dialog!!.setCancelable(false)

        dialog!!.setContentView(R.layout.dialog)
        dialog!!.show()


    }

    private fun hideDialog(){

        if(dialog!=null){

            dialog!!.dismiss()
        }


    }


}