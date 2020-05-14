package com.example.catantournament.ui.classification

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.Player
import kotlinx.android.synthetic.main.item_classification.view.*

class ClassificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(player: Player) {
        itemView.player_name.text = player.name
        itemView.points.text = player.points.toString()
        itemView.victory_points.text = player.victoryPoints.toString()
        itemView.big_trading_route_points.text = player.bigTradeRoute.toString()
        itemView.big_cavalry_army_points.text = player.bigCavalryArmy.toString()
    }
}
