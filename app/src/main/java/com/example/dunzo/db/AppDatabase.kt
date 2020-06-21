package com.example.dunzo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dunzo.model.DataModel

@Database(entities = arrayOf(DataModel::class), version = 1)
@TypeConverters(Converter::class)
abstract class  AppDatabase : RoomDatabase(){

    public abstract fun getImageDao() : DataModelDao
}