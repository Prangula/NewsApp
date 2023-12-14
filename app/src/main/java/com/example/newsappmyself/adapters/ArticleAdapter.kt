package com.example.newsappmyself.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappmyself.R
import com.example.newsappmyself.models.Article
import kotlinx.android.synthetic.main.news_item.view.*

class ArticleAdapter(private val items:ArrayList<Article>)
    :RecyclerView.Adapter<ArticleAdapter.ViewHolder>(){


        inner class ViewHolder(itemView:View)
            :RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.apply {

            Glide
                .with(this)
                .load(item.urlToImage)
                .into(item_iv)

            item_title.text = item.title
            item_description.text = item.description
            item_source.text = item.source.name
            item_date.text = item.publishedAt

            setOnClickListener {

                onItemClickListener?.let {

                    it(item)


                }

            }


        }


    }

    private var onItemClickListener:((Article)->Unit)? = null


    fun setOnItemClickListener(listener:(Article)->Unit) {

        onItemClickListener = listener

    }



}