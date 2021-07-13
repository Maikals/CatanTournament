package com.example.domain.use_case

import com.example.domain.entities.Tournament
import com.example.domain.entities.TournamentGenerator
import com.example.domain.entities.params.GenerateTournamentParams
import com.example.domain.repositories.PlayerRepository
import com.example.domain.repositories.TournamentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GenerateTournamentUseCase(
    private val playerRepository: PlayerRepository,
    private val tournamentRepository: TournamentRepository
) : BaseUseCase<GenerateTournamentParams, Tournament>() {
    override fun buildCall(params: GenerateTournamentParams): Flow<Tournament> = flow {
        playerRepository.getAllPlayers().collect { playerList ->
            val tournament =
                TournamentGenerator.generateTournament(params.numberOfRounds, playerList)
            tournamentRepository.createTournament(tournament).collect { result ->
                emit(result)
            }
        }
    }
}
