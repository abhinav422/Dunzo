package com.example.dunzo.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


sealed class Content {

    abstract fun getContentType() : ContentType
}

@Entity
class DataModel(@Embedded val photos : PhotosPage, @PrimaryKey var title : String) : Content(),Serializable {

    override fun getContentType(): ContentType {
        return ContentType.IMAGE_MODEL
    }
}

data class PhotosPage(val page : Int,val photo : MutableList<Photo>) : Content(),Serializable {

    override fun getContentType(): ContentType {
        return ContentType.PHOTO_PAGE
    }

}

data class Photo(val id : String,val farm : String, val secret : String,val server : String,val title : String) : Content() {
    override fun getContentType(): ContentType {
        return ContentType.PHOTO
    }
}

class ConnectInternet() : Content() {

    val label = "Pease connect to the internet"
    override fun getContentType(): ContentType {
        return ContentType.CONNECT_INTERNET
    }

}

class EmptyResult() : Content() {

    var message = "No result found"

    override fun getContentType(): ContentType {
        return ContentType.EMPTY_RESULT
    }

}

enum class ContentType {

    IMAGE_MODEL,PHOTO,PHOTO_PAGE,CONNECT_INTERNET,EMPTY_RESULT

}