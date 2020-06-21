package com.example.dunzo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dunzo.model.DataModel

@Dao
interface DataModelDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dataModel : DataModel)

    @Query("Select * From DataModel where title = :query")
    fun getImageInfo(query : String) : DataModel?
}