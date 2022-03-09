package com.template.mapapplication.ui.places.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.template.mapapplication.R
import com.template.models.VisitedPlaceModel

class PlacesRVAdapter : RecyclerView.Adapter<PlacesRVAdapter.ViewHolder>() {
    private var listOfPlaces = listOf<VisitedPlaceModel>()

    fun updateList(places : List<VisitedPlaceModel>) {
        listOfPlaces = places
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfPlaces[position])
    }

    override fun getItemCount() =
        listOfPlaces.size

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(value : VisitedPlaceModel) {
            val tvAddress = itemView.findViewById<TextView>(R.id.addressTextView)
            val tvTime = itemView.findViewById<TextView>(R.id.timeTextView)
            val tvDate = itemView.findViewById<TextView>(R.id.dateTextView)
            tvAddress.text = value.address
            tvTime.text = value.time
            tvDate.text = value.date
        }
    }
}