package com.example.domain.use_case

import com.example.domain.entities.Encounter
import com.example.domain.entities.params.GetEncounterParams
import com.example.domain.repositories.TournamentRepository
import kotlinx.coroutines.flow.Flow

class GetEncounterUseCase(private val tournamentRepository: TournamentRepository) :
    BaseUseCase<GetEncounterParams, Encounter>() {
    override fun buildCall(params: GetEncounterParams): Flow<Encounter> =
        tournamentRepository.getEncounter(params.id)
}
