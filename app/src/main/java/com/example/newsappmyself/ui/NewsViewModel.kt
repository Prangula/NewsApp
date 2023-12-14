package com.example.newsappmyself.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.newsappmyself.models.Article
import com.example.newsappmyself.models.NewsResponse
import com.example.newsappmyself.repository.NewsRepository
import com.example.newsappmyself.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository,app:Application): AndroidViewModel(app) {


    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val pageSize = 100

    val searchNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val pageNumber = 1

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(country:String) =

if(isNetworkAvailable()){
    viewModelScope.launch {

        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(country,pageSize)
        breakingNews.postValue(handleBreakingNewsResponse(response))



    }
}else{
    Toast.makeText(getApplication(),"You dont have internet connection", Toast.LENGTH_SHORT).show()

}


    fun searchNews(searchQuery:String) =
        viewModelScope.launch {
            searchNews.postValue(Resource.Loading())
            val response = newsRepository.getsearchNews(searchQuery,pageNumber)
            searchNews.postValue(handleSearchNewsResponse(response))
        }


    fun saveArticle(article: Article) =

        viewModelScope.launch {


            newsRepository.upsert(article)
        }

    fun deleteArticle(article: Article) =

        viewModelScope.launch {

            newsRepository.delete(article)
        }

    fun getSavedNews() = newsRepository.getSavedNews()




    private fun handleBreakingNewsResponse(response:Response<NewsResponse>):Resource<NewsResponse>{

        if(response!!.isSuccessful){

            response.body().let {resultResponse->

                return Resource.Success(resultResponse)

            }


        }
        return Resource.Error(response.message())



    }

    private fun handleSearchNewsResponse(response:Response<NewsResponse>):Resource<NewsResponse>{

        if(response!!.isSuccessful){

            response.body().let {resultResponse->

                return Resource.Success(resultResponse)

            }


        }
        return Resource.Error(response.message())



    }

    fun isNetworkAvailable(): Boolean {
        // It answers the queries about the state of network connectivity.
        val connectivityManager =getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network      = connectivityManager.activeNetwork ?: return false
            val activeNetWork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            // Returns details about the currently active default data network.
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnectedOrConnecting
        }
    }

}