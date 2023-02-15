package com.example.thememovie.main.view.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thememovie.R
import com.example.thememovie.customviews.AspectRatioImageView
import com.example.thememovie.main.model.MovieSimple
import com.example.thememovie.main.view.recycler.clicklistener.OnMovieClickListener

class MainRecyclerView(private val clickListener: OnMovieClickListener) :
    PagingDataAdapter<MovieSimple, MainRecyclerView.PhotoViewHolder>(IMAGECOMPARATOR) {


    inner class PhotoViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val imageView: AspectRatioImageView = view.findViewById(R.id.rawSimpleImage)
        private val title: TextView = view.findViewById(R.id.rawSimpleTitle)
        private val forAdultsText: TextView = view.findViewById(R.id.rawSimpleAdultText)
        private val forAdultsCircle: View = view.findViewById(R.id.rawSimpleAdultCircle)


        fun bindImage(item: MovieSimple) {
            Glide.with(imageView.context)
                .load("https://image.tmdb.org/t/p/w500/" + item.posterPath)
                .into(imageView)
            if (!item.adult) {
                forAdultsText.isVisible = false
                forAdultsCircle.isVisible = false
            }
            title.text = item.originalTitle
            imageView.setOnClickListener {
                Log.i("test", "id = ${item.id}")
                clickListener.movieWasClicked(item.id) }

        }
    }

    companion object {
        val IMAGECOMPARATOR = object : DiffUtil.ItemCallback<MovieSimple>() {
            override fun areContentsTheSame(oldItem: MovieSimple, newItem: MovieSimple): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: MovieSimple, newItem: MovieSimple): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindImage(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.raw_simple_movie, parent, false))
    }
}
