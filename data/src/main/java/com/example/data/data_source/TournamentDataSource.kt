package com.example.data.data_source

import com.example.domain.entities.EncounterResult
import com.example.domain.entities.Round
import com.example.domain.entities.Tournament

interface TournamentDataSource {
    fun createTournament(tournament: Tournament): Tournament
    fun getTournament(): Tournament?
    fun getRound(id: Long): Round
    fun getEncountersFromPlayerId(id: Long): List<EncounterResult>
}
