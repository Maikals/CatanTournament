package com.example.data.repositories

import com.example.data.data_source.TournamentDataSource
import com.example.domain.entities.Encounter
import com.example.domain.entities.EncounterResult
import com.example.domain.entities.Round
import com.example.domain.entities.Tournament
import com.example.domain.repositories.TournamentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class TournamentRepositoryImpl(private val tournamentCloudDataSource: TournamentDataSource) :
    TournamentRepository {
    override fun createTournament(generateTournament: Tournament) = flow {
        emit(tournamentCloudDataSource.createTournament(generateTournament))
    }

    override fun getTournament(): Flow<Tournament?> = flow {
        val tournament: Tournament? = tournamentCloudDataSource.getTournament()
        emit(tournament)
    }

    override fun getRound(id: String?): Flow<Round> = flow {
        emit(tournamentCloudDataSource.getRound(id))
    }

    override fun getEncountersFromPlayerId(id: UUID): Flow<List<EncounterResult>> = flow {
        emit(tournamentCloudDataSource.getEncountersFromPlayerId(id))
    }

    override fun getEncounter(id: String): Flow<Encounter> = flow {
        emit(tournamentCloudDataSource.getEncounter(id))
    }

    override fun saveEncounter(encounter: Encounter): Flow<Unit> = flow {
        emit(tournamentCloudDataSource.saveEncounter(encounter))
    }
}
