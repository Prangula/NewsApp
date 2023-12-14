package com.example.newsappmyself.api

import com.example.newsappmyself.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

   private val retrofit by lazy {

       Retrofit.Builder()
           .baseUrl(Constants.BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()

    }

    val api by lazy {

        retrofit.create(NewsApi::class.java)


    }



}