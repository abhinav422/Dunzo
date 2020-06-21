package com.example.dunzo

import android.app.Application
import com.example.dunzo.db.AppDatabase
import com.example.dunzo.model.*
import com.example.dunzo.repository.RepositoryImpl
import com.example.dunzo.viewmodel.MainActivityViewModelImpl
import com.example.dunzo.webservice.WebService
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ViewModelTest {
    @Mock
    lateinit var repositoryImpl  : RepositoryImpl


    @get:Rule
    var mockitoRule = MockitoJUnit.rule()


    val application = Mockito.mock(Application::class.java)


    @Test
    fun viewModel_test1() {

        val mainActivityViewModelImpl = MainActivityViewModelImpl(application)
        mainActivityViewModelImpl.repository = repositoryImpl

        mainActivityViewModelImpl.getPageNo(1,true)

        verify(repositoryImpl).getImagesList(1,true)
    }

    @Test
    fun viewModel_test2() {

        val mainActivityViewModelImpl = MainActivityViewModelImpl(application)

        mainActivityViewModelImpl.repository = repositoryImpl

        var emitter : ObservableEmitter<Content>? = null
        var pageListObservable = Observable.create<Content> {
            emitter = it
        }
        Mockito.`when`(repositoryImpl.getImageListObservable()).thenReturn(pageListObservable)
        Mockito.`when`(repositoryImpl.getImagesList(1,true)).then {
            emitter?.onNext(DataModel(PhotosPage(1,ArrayList<Photo>()),"title"))
        }

        mainActivityViewModelImpl.getPageNo(1,true)
        mainActivityViewModelImpl.getImageList().subscribe {
            assert(it.getContentType() == ContentType.IMAGE_MODEL)

        }

    }

}
