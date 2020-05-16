package com.example.domain.repositories

import com.example.domain.entities.Player
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface PlayerRepository {
    fun addUser(player: Player): Flow<Unit>
    fun subscribeToPlayerData(): Flow<List<Player>>
    fun getAllPlayers(): Flow<List<Player>>
    fun modifyPlayer(player: Player): Flow<Unit>
    fun deletePlayer(id: UUID): Flow<Unit>

}