package com.example.droidhub

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageAdapter(val context: Context, val urls:List<String>): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        //inflate the layout for the adapter
        var view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_images, parent,false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = urls[position]
        Glide.with(context).load(url).into(holder.image)

    }

    override fun getItemCount(): Int {
      return  urls.size

    }
   inner  class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       var image:ImageView = itemView.findViewById(R.id.imageView_files)


   }
}
