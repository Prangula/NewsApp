package com.example.newsappmyself.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsappmyself.models.Article

@Dao
interface ArticleDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)

    suspend fun upsert(article: Article):Long

    @Delete
    suspend fun delete(article: Article)

   @Query("SELECT * FROM `news-table`")

   fun getAllArticles():LiveData<List<Article>>


}