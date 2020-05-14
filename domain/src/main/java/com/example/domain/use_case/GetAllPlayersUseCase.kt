package com.example.domain.use_case

import com.example.domain.entities.EmptyParams
import com.example.domain.entities.Player
import com.example.domain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow

class GetAllPlayersUseCase(private val playerRepository: PlayerRepository) :
    BaseUseCase<EmptyParams, List<Player>>() {
    override fun buildCall(params: EmptyParams): Flow<List<Player>> =
        playerRepository.getAllPlayers()
}