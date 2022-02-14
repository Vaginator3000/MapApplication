package com.template.mapapplication.ui.places.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.template.mapapplication.R

class PlacesRVAdapter : RecyclerView.Adapter<PlacesRVAdapter.ViewHolder>() {
    private val listOfPlaces = listOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfPlaces[position])
    }

    override fun getItemCount() =
        listOfPlaces.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(someValue : Int) {
            val textView = itemView.findViewById<TextView>(R.id.itemTextView)
            textView.text = someValue.toString()
        }
    }
}