package com.example.domain.repositories

import com.example.domain.entities.Encounter
import com.example.domain.entities.EncounterResult
import com.example.domain.entities.Round
import com.example.domain.entities.Tournament
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TournamentRepository {
    fun createTournament(generateTournament: Tournament): Flow<Tournament>
    fun getTournament(): Flow<Tournament?>
    fun getRound(id: Long): Flow<Round>
    fun getEncountersFromPlayerId(id: UUID): Flow<List<EncounterResult>>
    fun getEncounter(id: Long): Flow<Encounter>
}
