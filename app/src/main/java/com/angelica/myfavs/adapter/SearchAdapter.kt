package com.angelica.myfavs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.angelica.myfavs.R
import com.angelica.myfavs.models.Search
import com.bumptech.glide.Glide
import java.util.*

class SearchAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var items: List<Search> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)

        return ViewHolder(view)
    }

    interface OnItemClickListener {
        fun movieClick(position: Int)
    }

    fun updateList(newList: List<Search>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bindView(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

            if (RecyclerView.NO_POSITION != position) {
                listener.movieClick(position)
            }
        }

        fun bindView(item: Search?) = with(itemView) {
            val imgMovie = findViewById<ImageView>(R.id.movieImage)
            val tvName = findViewById<TextView>(R.id.movieName)
            val tvType = findViewById<TextView>(R.id.movieType)

            item?.let {
                val progress_bar = progressbar(itemView.context)

                Glide.with(itemView.context).load(it.poster).placeholder(progress_bar).error(R.drawable.img_error).into(imgMovie)
                tvName.text = it.title
                tvType.text = it.type.toUpperCase(Locale.ROOT) + "\n " + it.year

                when (it.type) {
                    "movie" -> tvType.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.red
                        )
                    )
                    "series" -> tvType.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.green
                        )
                    )
                    "game" -> tvType.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.blue
                        )
                    )
                    else -> tvType.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.white
                        )
                    )
                }
            }
        }

        private fun progressbar(context: Context): CircularProgressDrawable {
            var prog = CircularProgressDrawable(context)
            prog.strokeWidth = 7f
            prog.centerRadius = 90f
            val accent = ContextCompat.getColor(context, R.color.purple_700)
            val white = ContextCompat.getColor(context, R.color.purple_200)
            prog.setColorSchemeColors(accent, white)
            prog.start()
            return prog
        }
    }
}