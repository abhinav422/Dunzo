package com.example.dunzo.viewmodel

import android.app.Application
import com.example.dunzo.di.component.DaggerMainActivityComponent
import com.example.dunzo.di.module.UtilityModule
import com.example.dunzo.model.Content
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivityViewModelImpl(application: Application) : MainActivityViewModel(application) {


    init {
        DaggerMainActivityComponent.builder().utilityModule(UtilityModule(application)).build().inject(this)
    }

    override fun getImageList(): Observable<Content> {

        return repository.getImageListObservable()
    }


    override fun getPageNo(pageNo: Int, checkIfInternetConnected: Boolean) {
        repository.getImagesList(pageNo,checkIfInternetConnected)
    }

    override fun setTitle(title: String, checkIfInternetConnected: Boolean) {
        repository.setTitle(title,checkIfInternetConnected)
    }


}