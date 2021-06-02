package com.angelica.myfavs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.angelica.myfavs.R
import com.angelica.myfavs.models.Info
import com.bumptech.glide.Glide

class MoviesAdapter (
    private val items: List<Info>
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bindView(item)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Info?) = with(itemView) {
            val imgMovie = findViewById<ImageView>(R.id.movieImage)
            //val tvYear = findViewById<TextView>(R.id.year_movie)
            val tvName = findViewById<TextView>(R.id.movieName)
            //val tvType = findViewById<TextView>(R.id.type_movie)

            item?.let {
                Glide.with(itemView.context).load(it.poster).into(imgMovie)

                //tvYear.text = it.year
                tvName.text = it.title
                //tvType.text = it.type.capitalize()
            }
        }
    }
}