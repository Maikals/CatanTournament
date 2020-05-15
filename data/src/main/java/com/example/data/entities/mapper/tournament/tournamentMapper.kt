package com.example.data.entities.mapper.tournament

import com.example.data.entities.db.EncounterORM
import com.example.data.entities.db.RoundORM
import com.example.data.entities.db.TournamentORM
import com.example.data.entities.mapper.player.toDomain
import com.example.domain.entities.Encounter
import com.example.domain.entities.Player
import com.example.domain.entities.Round
import com.example.domain.entities.Tournament

fun toDomain(tournamentORM: TournamentORM?): Tournament? =
    tournamentORM?.let { tournamentOrmIt ->
        Tournament(ArrayList<Round>().apply {
            tournamentORM.roundList.forEach {
                add(toDomain(it))
            }
        })
    }
fun toDomain(roundORM: RoundORM): Round =
    Round(roundORM.id, ArrayList<Encounter>().apply {
        roundORM.roundList.forEach {
            add(toDomain(it))
        }
    })

fun toDomain(encounterORM: EncounterORM): Encounter =
    Encounter(encounterORM.id, ArrayList<Player>().apply {
        encounterORM.playerList.forEach {
            add(toDomain(it))
        }
    })