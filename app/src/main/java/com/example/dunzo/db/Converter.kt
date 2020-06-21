package com.example.dunzo.db

import androidx.room.TypeConverter
import com.example.dunzo.model.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converter {
    @TypeConverter
    fun fromString(value: String): List<Photo> {
        val listType = object : TypeToken<ArrayList<Photo>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<Photo>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}