package com.example.dunzo.webservice

import com.example.dunzo.model.DataModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("services/rest?method=flickr.photos.search&api_key=062a6c0c49e4de1d78497d13a7dbb360&format=json&nojsoncallback=1&per_page=10")
    fun getPhotosList(@Query("text") title : String,@Query("page") pageNo  :Int) : Call<DataModel>
}