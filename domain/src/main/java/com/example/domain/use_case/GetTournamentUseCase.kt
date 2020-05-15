package com.example.domain.use_case

import com.example.domain.entities.Tournament
import com.example.domain.entities.params.EmptyParams
import com.example.domain.repositories.TournamentRepository
import kotlinx.coroutines.flow.Flow

class GetTournamentUseCase(private val tournamentRepository: TournamentRepository) :
    BaseUseCase<EmptyParams, Tournament?>() {
    override fun buildCall(params: EmptyParams): Flow<Tournament?> =
        tournamentRepository.getTournament()

}
