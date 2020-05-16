package com.example.domain.repositories

import com.example.domain.entities.EncounterResult
import com.example.domain.entities.Round
import com.example.domain.entities.Tournament
import kotlinx.coroutines.flow.Flow

interface TournamentRepository {
    fun createTournament(generateTournament: Tournament): Flow<Tournament>
    fun getTournament(): Flow<Tournament?>
    fun getRound(id: Long): Flow<Round>
    fun getEncountersFromPlayerId(id: Long): Flow<List<EncounterResult>>
}
