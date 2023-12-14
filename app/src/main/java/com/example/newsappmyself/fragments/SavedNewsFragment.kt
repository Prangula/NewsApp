package com.example.newsappmyself.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmyself.R
import com.example.newsappmyself.adapters.ArticleAdapter
import com.example.newsappmyself.models.Article
import com.example.newsappmyself.ui.NewsActivity
import com.example.newsappmyself.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedNewsFragment:Fragment(R.layout.fragment_saved_news) {

    private lateinit var viewModel:NewsViewModel
    private var dialog: Dialog? =null
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

            findNavController().navigate(R.id.action_savedNewsFragment_to_articleFragment,
                bundle)


        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {article->

            items.clear()
            items.addAll(article)
            newsAdapter.notifyDataSetChanged()

        })

        delete()


    }

    private fun delete() {
        val itemTouch = object : ItemTouchHelper.SimpleCallback(

            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = items[position]
                alertDialog(item)


            }


        }

        ItemTouchHelper(itemTouch).apply {

            attachToRecyclerView(rv_saved_news)
        }
    }

    private fun setUpRecyclerView(items:ArrayList<Article>){

        newsAdapter = ArticleAdapter(items)

        rv_saved_news.apply {

            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)


        }



    }


    fun alertDialog(article:Article){

        val dialog = AlertDialog.Builder(activity)
        dialog.setTitle("წაშლა")
        dialog.setMessage("დარწმუნებილი ხართ?")

        dialog.setPositiveButton("დიახ"){dialogInterface,_->

            viewModel.deleteArticle(article)
            dialogInterface.dismiss()
            Toast.makeText(activity,"პოსტი წაშლილია მოწონების სიიდან.", Toast.LENGTH_LONG)
                .show()

        }


        dialog.setNegativeButton("არა"){dialogInterface,_->

            dialogInterface.dismiss()
            viewModel.saveArticle(article)

        }

        val alertDialog = dialog.create()
        alertDialog.setCancelable(false)
        alertDialog.show()


    }



}