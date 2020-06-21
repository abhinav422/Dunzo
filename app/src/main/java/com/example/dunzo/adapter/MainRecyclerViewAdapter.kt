package com.example.dunzo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dunzo.R
import com.example.dunzo.model.DataModel
import com.example.dunzo.model.Photo
import com.squareup.picasso.Picasso

class MainRecyclerAdapter(val list : MutableList<Photo>, val layoutInflater: LayoutInflater) : RecyclerView.Adapter<MainAdapterViewHolder>() {

    var title: String = ""
    var pageNo: Int = 1

    override fun onBindViewHolder(holder: MainAdapterViewHolder, position: Int) {
        Picasso.get().load("https://farm" + list[position].farm+".staticflickr.com/"+list[position].server+"/"+list[position].id+"_"+list[position].secret+"_m.jpg").into(holder.imageView)
        holder.textView.text = list[position].title
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapterViewHolder{
        return MainAdapterViewHolder(layoutInflater.inflate(R.layout.item,parent,false))
    }


    override fun getItemCount(): Int {
        return list.size
    }


}

class MainAdapterViewHolder(val view : View) : RecyclerView.ViewHolder(view) {

    val imageView = view.findViewById<ImageView>(R.id.image_view)
    val textView = view.findViewById<TextView>(R.id.title)
}