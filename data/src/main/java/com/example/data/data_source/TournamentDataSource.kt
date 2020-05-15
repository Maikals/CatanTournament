package com.example.data.data_source

import com.example.domain.entities.Tournament

interface TournamentDataSource {
    fun createTournament(tournament: Tournament)
    fun getTournament(): Tournament?
}
