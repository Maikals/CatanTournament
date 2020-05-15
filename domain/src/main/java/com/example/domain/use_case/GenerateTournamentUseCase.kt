package com.example.domain.use_case

import com.example.domain.entities.Encounter
import com.example.domain.entities.Round
import com.example.domain.entities.Tournament
import com.example.domain.entities.params.GenerateTournamentParams
import com.example.domain.repositories.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GenerateTournamentUseCase(private val playerRepository: PlayerRepository) :
    BaseUseCase<GenerateTournamentParams, Tournament>() {
    override fun buildCall(params: GenerateTournamentParams): Flow<Tournament> = flow {
        playerRepository.getAllPlayers().collect { playerList ->
            val listOfRounds = ArrayList<Round>()
            repeat(params.numberOfRounds) {
                val listOfEncounter = ArrayList<Encounter>()
                var i = playerList.size
                while (i > 0) {
                    if (i % 3 == 0) {

                    } else {

                    }
                }

            }
        }
    }
}