package com.example.data.repositories

import com.example.data.data_source.PlayerDataSource
import com.example.domain.entities.Player
import com.example.domain.entities.Tournament
import com.example.domain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow

class PlayerRepositoryImpl(private val playerLocalDataSource: PlayerDataSource) : PlayerRepository {
    override fun addUser(player: Player) = flow {
        playerLocalDataSource.addPlayer(player)
        emit(Unit)
    }

    override fun subscribeToPlayerData() =
        playerLocalDataSource.subscribeToPlayerList().consumeAsFlow()

    override fun getAllPlayers(): Flow<List<Player>> = flow {
        emit(playerLocalDataSource.findAllPlayers())
    }

    override fun modifyPlayer(player: Player): Flow<Unit> = flow {
        playerLocalDataSource.modifyPlayer(player)
        emit(Unit)
    }

    override fun deletePlayer(id: Long): Flow<Unit> = flow {
        playerLocalDataSource.deletePlayer(id)
        emit(Unit)
    }
}