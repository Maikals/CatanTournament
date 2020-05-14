package com.example.domain.use_case

import com.example.domain.entities.AddPlayerParams
import com.example.domain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow

class AddPlayerUseCase(private val playerRepository: PlayerRepository) :
    BaseUseCase<AddPlayerParams, Unit>() {
    override fun buildCall(params: AddPlayerParams): Flow<Unit> =
        playerRepository.addUser(params.player)
}