package com.example.domain.use_case

import com.example.domain.entities.Round
import com.example.domain.entities.params.GetRoundParams
import com.example.domain.repositories.TournamentRepository
import kotlinx.coroutines.flow.Flow

class GetRoundUseCase(private val tournamentRepository: TournamentRepository) :
    BaseUseCase<GetRoundParams, Round>() {
    override fun buildCall(params: GetRoundParams): Flow<Round> =
        tournamentRepository.getRound(params.id)
}
