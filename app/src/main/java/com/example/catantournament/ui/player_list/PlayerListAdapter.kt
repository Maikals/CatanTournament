package com.example.catantournament.ui.player_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catantournament.R
import com.example.catantournament.interfaces.PlayerListMenuListener
import com.example.domain.entities.Player

class PlayerListAdapter(
    private val playerList: List<Player>,
    private val listener: PlayerListMenuListener.ScreenMenuListener
) :
    RecyclerView.Adapter<PlayerViewHolder>(), PlayerListMenuListener.AdapterMenuListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder =
        PlayerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.player_list_item, parent, false),
            this
        )

    override fun getItemCount(): Int = playerList.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(playerList[position])
    }

    override fun modify(position: Int) = listener.modify(playerList[position])

    override fun delete(position: Int) = listener.delete(playerList[position])
}
