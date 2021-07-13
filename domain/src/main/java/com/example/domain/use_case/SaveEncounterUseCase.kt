package com.example.domain.use_case

import com.example.domain.entities.Encounter
import com.example.domain.entities.params.SaveEncounterParams
import com.example.domain.repositories.TournamentRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class SaveEncounterUseCase(private val tournamentRepository: TournamentRepository) :
    BaseUseCase<SaveEncounterParams, Unit>() {
    override fun buildCall(params: SaveEncounterParams) = flow {
        tournamentRepository.getEncounter(params.encounter.id).collect { encounter ->
            saveEncounters(encounter, params)
            addEncountersToUsers(encounter)
            tournamentRepository.saveEncounter(encounter)
            emit(Unit)
        }
    }

    private fun addEncountersToUsers(encounter: Encounter) {
        (encounter.playerList.toMutableList()).let { playerList ->
            playerList.forEachIndexed { i, player ->
                (player.encounterResults.toMutableList()).let { encounterResults ->
                    encounterResults.add(encounter.encounterResults[i])
                }
            }
        }
    }

    private fun saveEncounters(
        encounter: Encounter,
        params: SaveEncounterParams
    ) {
        (encounter.encounterResults.toMutableList()).let { encounterResults ->
            encounterResults.clear()
            encounterResults.addAll(params.encounter.encounterResults)
        }
    }
}
