package com.example.dunzo.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.simpleName.equals("MainActivityViewModel")) {
            return MainActivityViewModelImpl(application) as T
        }
        return modelClass.newInstance()
    }
}