package com.example.dunzo.repository

import android.os.Looper
import com.example.dunzo.db.AppDatabase
import com.example.dunzo.model.ConnectInternet
import com.example.dunzo.model.Content
import com.example.dunzo.model.DataModel
import com.example.dunzo.model.EmptyResult
import com.example.dunzo.webservice.WebService
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import kotlinx.coroutines.CoroutineScope
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Handler

class RepositoryImpl(var webService : WebService?, val appDatabase : AppDatabase?) : Repository {

    lateinit var emitter : ObservableEmitter<Content>
    private var title = ""

    var pageListObservable = Observable.create<Content> {
        emitter = it
    }
    override fun getImagesList(pageNo: Int, checkIfInternetConnected: Boolean) {

        if (!checkIfInternetConnected) {

            object:Thread() {
                override fun run() {
                    if (appDatabase?.getImageDao()?.getImageInfo(title) != null) {

                        emitter.onNext(appDatabase.getImageDao().getImageInfo(title)!!)

                    }
                    else {
                        emitter.onNext(ConnectInternet())
                    }
                }
            }.start()

        }
        else {
            webService?.getPhotosList(title,pageNo)?.enqueue(object :
                Callback<DataModel>{
                override fun onFailure(call: Call<DataModel>, t: Throwable) {

                    emitter.onNext(EmptyResult())
                }

                override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                    val dataModel = response.body()!!
                    dataModel.title = title
                    insertContentToDb(dataModel)
                    emitter.onNext(dataModel)

                }

            })
        }



    }

    private fun insertContentToDb(dataModel: DataModel) {
        object : Thread() {
            override fun run() {
                appDatabase?.getImageDao()?.getImageInfo(title).let {
                    if (it == null) {
                        appDatabase?.getImageDao()?.insert(dataModel)
                    }
                    else {
                        it.photos.photo.addAll(dataModel.photos.photo)
                        appDatabase?.getImageDao()?.insert(it)
                    }
                }
            }
        }.start()

    }


    override fun getImageListObservable(): Observable<Content> {
        return pageListObservable
    }

    override fun setTitle(title: String, checkIfInternetConnected: Boolean) {
        this.title = title
        getImagesList(1, checkIfInternetConnected)
    }


}