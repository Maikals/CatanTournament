package com.example.catantournament.ui.matches

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.catantournament.interfaces.AdapterOnClickListenerInterface
import com.example.domain.entities.Encounter
import com.example.domain.entities.Player
import kotlinx.android.synthetic.main.item_list_encounter.view.*

class EncounterViewHolder(
    view: View,
    private val listener: AdapterOnClickListenerInterface.Adapter
) : RecyclerView.ViewHolder(view) {

    fun bindView(encounter: Encounter) {
        setOnClickListener()
        setPlayer(
            encounter.playerList[PLAYER_1_ID],
            itemView.first_player_name,
            itemView.first_player_nick
        )
        setPlayer(
            encounter.playerList[PLAYER_2_ID],
            itemView.second_player_name,
            itemView.second_player_nick
        )
        setPlayer(
            encounter.playerList[PLAYER_3_ID],
            itemView.third_player_name,
            itemView.third_player_nick
        )
        if (encounter.playerList.size > 3) {
            itemView.fourth_player_container.visibility = View.VISIBLE
            setPlayer(
                encounter.playerList[PLAYER_4_ID],
                itemView.fourth_player_name,
                itemView.fourth_player_nick
            )
        }
    }

    private fun setPlayer(
        player: Player,
        playerName: AppCompatTextView,
        playerNick: AppCompatTextView
    ) {
        playerName.text = player.name
        playerNick.text = player.nick
    }

    private fun setOnClickListener() {
        listener.onClick(adapterPosition)
    }

    companion object {
        private const val PLAYER_1_ID: Int = 0
        private const val PLAYER_2_ID: Int = 1
        private const val PLAYER_3_ID: Int = 2
        private const val PLAYER_4_ID: Int = 3
    }
}
