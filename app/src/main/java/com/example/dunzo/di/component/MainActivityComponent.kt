package com.example.dunzo.di.component

import com.example.dunzo.di.module.MainActivityComponentModule
import com.example.dunzo.di.module.UtilityModule
import com.example.dunzo.viewmodel.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(MainActivityComponentModule::class, UtilityModule::class))
interface MainActivityComponent {

    fun inject(mainActivityViewModel: MainActivityViewModel)
}