package com.example.domain.use_case

import com.example.domain.entities.DeletePlayerParams
import com.example.domain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow

class DeletePlayerUseCase(private val playerRepository: PlayerRepository) :
    BaseUseCase<DeletePlayerParams, Unit>() {
    override fun buildCall(params: DeletePlayerParams): Flow<Unit> =
        playerRepository.deletePlayer(params.id)
}