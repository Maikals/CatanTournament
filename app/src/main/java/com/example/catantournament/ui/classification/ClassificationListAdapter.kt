package com.example.catantournament.ui.classification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catantournament.R
import com.example.domain.entities.Player

class ClassificationListAdapter(private val data: List<Player>) :
    RecyclerView.Adapter<ClassificationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassificationViewHolder =
        ClassificationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_classification, parent, false)
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ClassificationViewHolder, position: Int) {
        holder.bind(data[position])
    }
}
