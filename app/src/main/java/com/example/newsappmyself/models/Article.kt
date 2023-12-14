package com.example.newsappmyself.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serial

@Entity(tableName = "news-table")
data class Article(

    @PrimaryKey(autoGenerate = true)
    val id:Int= 0,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
):java.io.Serializable{

    override fun hashCode(): Int {
        var result = id.hashCode()
        if(url.isNullOrEmpty()){
            result = 31 * result + url.hashCode()
        }
        return result
    }
}