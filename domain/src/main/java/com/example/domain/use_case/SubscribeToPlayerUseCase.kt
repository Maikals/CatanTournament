package com.example.domain.use_case

import com.example.domain.entities.Player
import com.example.domain.entities.params.EmptyParams
import com.example.domain.repositories.PlayerRepository

class SubscribeToPlayerUseCase(private val playerRepository: PlayerRepository) :
    BaseUseCase<EmptyParams, List<Player>>() {
    override fun buildCall(params: EmptyParams) =
        playerRepository.subscribeToPlayerData()
}