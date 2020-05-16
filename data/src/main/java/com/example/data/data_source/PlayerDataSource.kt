package com.example.data.data_source

import com.example.domain.entities.Player
import kotlinx.coroutines.channels.ReceiveChannel
import java.util.UUID

interface PlayerDataSource {
    fun addPlayer(player: Player)
    fun findAllPlayers(): List<Player>
    fun subscribeToPlayerList(): ReceiveChannel<List<Player>>
    fun modifyPlayer(player: Player)
    fun deletePlayer(id: UUID)
}