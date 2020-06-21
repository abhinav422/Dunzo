package com.example.dunzo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.dunzo.model.Content
import com.example.dunzo.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject

abstract class MainActivityViewModel(val app: Application) : AndroidViewModel(app) {

    @Inject
    public lateinit var repository : Repository


    abstract fun getImageList() : Observable<Content>
    abstract fun getPageNo(pageNo: Int, checkIfInternetConnected: Boolean)

    abstract fun setTitle(title: String, checkIfInternetConnected: Boolean)

}