package com.example.catantournament.ui.player_list

import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.catantournament.R
import com.example.catantournament.interfaces.PlayerListMenuListener
import com.example.domain.entities.Player
import kotlinx.android.synthetic.main.player_list_item.view.*

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
        itemView.setOnClickListener {
            itemView.showContextMenu()
        }
        itemView.player_name.text = player.name
        itemView.player_nick.text = player.nick
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
