package com.example.data.repositories

import com.example.data.data_source.TournamentDataSource
import com.example.domain.entities.Tournament
import com.example.domain.repositories.TournamentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TournamentRepositoryImpl(private val tournamentCloudDataSource: TournamentDataSource) :
    TournamentRepository {
    override fun createTournament(tournament: Tournament): Flow<Unit> = flow {
        tournamentCloudDataSource.createTournament(tournament)
        emit(Unit)
    }

    override fun getTournament(): Flow<Tournament?> = flow {
        val tournament: Tournament? = tournamentCloudDataSource.getTournament()
        emit(tournament)
    }
}