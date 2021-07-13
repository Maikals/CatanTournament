package com.example.catantournament.ui.classification

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.catantournament.databinding.ItemClassificationBinding
import com.example.domain.entities.Player

class ClassificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(player: Player) {
        val bind = ItemClassificationBinding.bind(itemView)
        bind.position.text = (absoluteAdapterPosition + 1).toString()
        bind.position.text = (absoluteAdapterPosition + 1).toString()
        bind.playerName.text = player.name
        bind.points.text = player.points.toString()
        bind.victoryPoints.text = player.victoryPoints.toString()
        bind.bigTradingRoutePoints.text = player.bigTradeRoute.toString()
        bind.bigCavalryArmyPoints.text = player.bigCavalryArmy.toString()
    }
}
