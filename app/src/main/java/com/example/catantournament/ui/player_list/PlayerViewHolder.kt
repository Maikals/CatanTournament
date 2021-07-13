package com.example.catantournament.ui.player_list

import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.catantournament.R
import com.example.catantournament.databinding.PlayerListItemBinding
import com.example.catantournament.interfaces.PlayerListMenuListener
import com.example.domain.entities.Player

class PlayerViewHolder(
    view: View,
    playerListAdapter: PlayerListMenuListener.AdapterMenuListener
) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
    init {
        view.setOnCreateContextMenuListener(this)
    }

    private val onMenuItemClickListener = MenuItem.OnMenuItemClickListener {
        when (it.itemId) {
            MENU_ITEM_MODIFY -> playerListAdapter.modify(adapterPosition)
            MENU_ITEM_DELETE -> playerListAdapter.delete(adapterPosition)
        }
        true
    }

    fun bind(player: Player) {
        val bind = PlayerListItemBinding.bind(itemView)
        itemView.setOnClickListener {
            itemView.showContextMenu()
        }
        bind.playerName.text = player.name
        bind.playerNick.text = player.nick
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu?.add(Menu.NONE, MENU_ITEM_MODIFY, 0, R.string.player_view_holder_menu_modify)?.run {
            setOnMenuItemClickListener(onMenuItemClickListener)
        }
        menu?.add(Menu.NONE, MENU_ITEM_DELETE, 1, R.string.player_view_holder_menu_delete)?.run {
            setOnMenuItemClickListener(onMenuItemClickListener)
        }
    }

    companion object {
        const val MENU_ITEM_MODIFY = 0
        const val MENU_ITEM_DELETE = 1
    }
}
