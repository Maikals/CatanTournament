package com.example.domain.use_case

import com.example.domain.entities.Player
import com.example.domain.entities.params.EmptyParams
import com.example.domain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllPlayersSortedUseCase(private val playerRepository: PlayerRepository) :
    BaseUseCase<EmptyParams, List<Player>>() {
    override fun buildCall(params: EmptyParams): Flow<List<Player>> =
        playerRepository.getAllPlayers().map {
            it.sorted()
        }
}