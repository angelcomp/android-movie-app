package com.angelica.myfavs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.angelica.myfavs.R
import com.angelica.myfavs.models.Favorite

class HomeAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>()  {

    private var items: List<Favorite> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)

        return ViewHolder(view)
    }

    interface OnItemClickListener {
        fun favoriteClick(position: Int)
    }
    fun updateList(newList: List<Favorite>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
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
                listener.favoriteClick(position)
            }
        }

        fun bindView(item: Favorite) = with(itemView) {
            val tvTitle = findViewById<TextView>(R.id.title_favorite)
            val tvType = findViewById<TextView>(R.id.hidden_type)
            val tvPlot = findViewById<TextView>(R.id.hidden_plot)
            val btnExpand = findViewById<ImageView>(R.id.expand_button)

            item.let {
                tvTitle.text = it.title
                tvType.text = it.type
                tvPlot.text = it.plot
            }

            var isExpanded = false

            btnExpand.setOnClickListener {
                if (isExpanded) {
                    tvPlot.visibility = GONE
                    isExpanded = false
                    btnExpand.animate().rotationX(0F)
                } else {
                    tvPlot.visibility = VISIBLE
                    isExpanded = true
                    btnExpand.animate().rotationX(180F)
                }
            }
        }
    }
}