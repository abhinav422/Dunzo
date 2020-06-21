package com.example.dunzo.view

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dunzo.R
import com.example.dunzo.adapter.MainRecyclerAdapter
import com.example.dunzo.model.ConnectInternet
import com.example.dunzo.model.DataModel
import com.example.dunzo.model.EmptyResult
import com.example.dunzo.model.Photo
import com.example.dunzo.viewmodel.MainActivityViewModel
import com.example.dunzo.viewmodel.MainViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var menu: Menu
    private lateinit var mainActivityViewModel:MainActivityViewModel
    private var isLoading = false

    private val adapter by lazy {
        MainRecyclerAdapter(ArrayList<Photo>(),LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityViewModel = ViewModelProviders.of(this, MainViewModelFactory(application)).get(
            MainActivityViewModel::class.java)
        initData()
    }

    private fun initData() {
        if (checkIfInternetConnected()) {
            main_recycler_view.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            main_recycler_view.adapter = adapter

            setSupportActionBar(my_toolbar)

            mainActivityViewModel.getImageList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
                isLoading = false

                if (it is ConnectInternet) {
                    Toast.makeText(MainActivity@this,it.label,Toast.LENGTH_LONG).show()
                    return@subscribe
                }
                else if (it is EmptyResult) {
                    Toast.makeText(MainActivity@this,it.message,Toast.LENGTH_LONG).show()
                    return@subscribe
                }
                if (!adapter.title.equals((it as DataModel).title)) {
                    adapter.list.clear()
                }

                if (it.photos.photo.isEmpty()) {
                    Toast.makeText(MainActivity@this,"No Data found",Toast.LENGTH_LONG).show()
                }
                adapter.list.addAll((it as DataModel).photos.photo)

                adapter.pageNo = (it as DataModel).photos.page
                adapter.title = it.title
                adapter.notifyDataSetChanged()
            }

            editText.setOnEditorActionListener { v, actionId, event ->
                if (!editText.text.toString().trim().isEmpty()) {
                    fetchForTitle(editText.text.toString().trim())
                    return@setOnEditorActionListener true
                }
                false
            }

            ok.setOnClickListener {
                if (!editText.text.toString().trim().isEmpty()) {
                    fetchForTitle(editText.text.toString().trim())
                }
            }

            main_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager
                    val visibleItemCount = layoutManager?.getChildCount()
                    val totalItemCount = layoutManager?.getItemCount()
                    val firstVisibleItemPosition =
                        (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (!isLoading()) {
                        if (visibleItemCount!! + firstVisibleItemPosition >= totalItemCount!! - 2 && firstVisibleItemPosition >= 0) {
                            if (checkIfInternetConnected()) {
                                isLoading = true
                                mainActivityViewModel.getPageNo(
                                    (recyclerView.adapter as MainRecyclerAdapter).pageNo + 1,
                                    checkIfInternetConnected()
                                )
                            }
                        }
                    }
                }

                fun isLoading() : Boolean {
                    return isLoading
                }
            })

            mainActivityViewModel.setTitle("catalysts",true)
        }
        else {
            setContentView(R.layout.refresh_internet)
            findViewById<ImageView>(R.id.refresh).setOnClickListener {
                setContentView(R.layout.activity_main)
                initData()
            }
        }
    }

    private fun fetchForTitle(text: String) {
        mainActivityViewModel.setTitle(
            text,
            checkIfInternetConnected()
        )
        editText.setText("")
        search_edit_text.visibility = View.GONE
        menu.findItem(R.id.search).setVisible(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            search_edit_text.visibility = View.VISIBLE
            if (::menu.isInitialized) {
                menu.findItem(R.id.search).setVisible(false)
            }
            return true
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    private fun checkIfInternetConnected() : Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        return (activeNetworkInfo != null && activeNetworkInfo.isConnected())
    }

}
