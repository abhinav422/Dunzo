package com.example.dunzo.repository

import com.example.dunzo.model.Content
import io.reactivex.Observable

interface Repository {

    fun getImageListObservable() : Observable<Content>

    fun getImagesList(pageNo: Int = 1, checkIfInternetConnected: Boolean)
    fun setTitle(title: String, checkIfInternetConnected: Boolean)
}