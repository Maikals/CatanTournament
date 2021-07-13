package com.example.catantournament.ui.matches

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.catantournament.databinding.ItemListEncounterBinding
import com.example.catantournament.interfaces.AdapterOnClickListenerInterface
import com.example.domain.entities.Encounter
import com.example.domain.entities.Player

class EncounterViewHolder(
    view: View,
    private val listener: AdapterOnClickListenerInterface.Adapter
) : RecyclerView.ViewHolder(view) {

    fun bindView(encounter: Encounter) {
        val bind = ItemListEncounterBinding.bind(itemView)
        setOnClickListener()
        setPlayer(
            encounter.playerList[PLAYER_1_ID],
            bind.firstPlayerName,
            bind.firstPlayerNick
        )
        setPlayer(
            encounter.playerList[PLAYER_2_ID],
            bind.secondPlayerName,
            bind.secondPlayerNick
        )
        setPlayer(
            encounter.playerList[PLAYER_3_ID],
            bind.thirdPlayerName,
            bind.thirdPlayerNick
        )
        if (encounter.playerList.size > 3) {
            bind.fourthPlayerContainer.visibility = View.VISIBLE
            setPlayer(
                encounter.playerList[PLAYER_4_ID],
                bind.fourthPlayerName,
                bind.fourthPlayerNick
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
        itemView.setOnClickListener {
            listener.onClick(absoluteAdapterPosition)
        }
    }

    companion object {
        private const val PLAYER_1_ID: Int = 0
        private const val PLAYER_2_ID: Int = 1
        private const val PLAYER_3_ID: Int = 2
        private const val PLAYER_4_ID: Int = 3
    }
}
