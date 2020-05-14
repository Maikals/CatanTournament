package com.example.catantournament.interfaces

import com.example.domain.entities.Player

interface PlayerListMenuListener {
    interface AdapterMenuListener {
        fun modify(position: Int)
        fun delete(position: Int)
    }

    interface ScreenMenuListener {
        fun modify(player: Player)
        fun delete(player: Player)
    }
}