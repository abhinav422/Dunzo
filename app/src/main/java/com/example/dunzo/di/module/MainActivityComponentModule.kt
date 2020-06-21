package com.example.dunzo.di.module

import com.example.dunzo.db.AppDatabase
import com.example.dunzo.repository.Repository
import com.example.dunzo.repository.RepositoryImpl
import com.example.dunzo.webservice.WebService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainActivityComponentModule {

    @Provides
    fun getRepository(retrofit: Retrofit?,appDatabase : AppDatabase) : Repository {
        return RepositoryImpl(retrofit?.create(WebService::class.java),appDatabase)
    }

}