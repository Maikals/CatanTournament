package com.example.domain.repositories

import com.example.domain.entities.Tournament
import kotlinx.coroutines.flow.Flow

interface TournamentRepository {
    fun createTournament(generateTournament: Tournament): Flow<Unit>
    fun getTournament(): Flow<Tournament?>
}
