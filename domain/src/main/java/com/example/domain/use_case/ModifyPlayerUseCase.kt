package com.example.domain.use_case

import com.example.domain.entities.ModifyPlayerParams
import com.example.domain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow

class ModifyPlayerUseCase(private val playerRepository: PlayerRepository) :
    BaseUseCase<ModifyPlayerParams, Unit>() {
    override fun buildCall(params: ModifyPlayerParams): Flow<Unit> =
        playerRepository.modifyPlayer(params.player)
}