package com.example.catantournament.ui.matches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catantournament.R
import com.example.catantournament.interfaces.AdapterOnClickListenerInterface
import com.example.domain.entities.Encounter

class RoundAdapter(
    private val encounterList: List<Encounter>,
    private val listener: AdapterOnClickListenerInterface.Screen<Encounter>
) :
    RecyclerView.Adapter<EncounterViewHolder>(), AdapterOnClickListenerInterface.Adapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncounterViewHolder =
        EncounterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_encounter, parent, false),
            this
        )

    override fun getItemCount(): Int = encounterList.size

    override fun onBindViewHolder(holder: EncounterViewHolder, position: Int) {
        holder.bindView(encounterList[position])
    }

    override fun onClick(position: Int) {
        listener.onClick(encounterList[position])
    }
}
