package com.example.data.entities.mapper.tournament

import com.example.data.entities.db.EncounterORM
import com.example.data.entities.db.EncounterResultORM
import com.example.data.entities.db.RoundORM
import com.example.data.entities.db.TournamentORM
import com.example.data.entities.mapper.player.toData
import com.example.data.entities.mapper.player.toDomain
import com.example.domain.entities.Encounter
import com.example.domain.entities.EncounterResult
import com.example.domain.entities.Player
import com.example.domain.entities.Round
import com.example.domain.entities.Tournament
import io.realm.RealmList
import java.util.UUID

fun toDomain(tournamentORM: TournamentORM?): Tournament? =
    tournamentORM?.let {
        Tournament(
            ArrayList<Round>().apply {
                tournamentORM.roundList.forEach { roundORM ->
                    add(toDomain(roundORM))
                }
            }
        )
    }

fun toDomain(roundORM: RoundORM): Round =
    Round(
        roundORM.id,
        ArrayList<Encounter>().apply {
            roundORM.roundList.forEach {
                add(toDomain(it))
            }
        }
    )

fun toDomain(encounterORM: EncounterORM): Encounter =
    Encounter(
        encounterORM.id,
        ArrayList<Player>().apply {
            encounterORM.playerList.forEach {
                add(toDomain(it))
            }
        },
        toDomain(encounterORM.results)
    )

fun toDomain(encounterORMList: RealmList<EncounterResultORM>): List<EncounterResult> =
    ArrayList<EncounterResult>().apply {
        encounterORMList.forEach {
            add(toDomain(it))
        }
    }

fun toDomain(encounterResultORM: EncounterResultORM): EncounterResult =
    EncounterResult(
        UUID.fromString(encounterResultORM.id),
        UUID.fromString(encounterResultORM.playerId),
        encounterResultORM.points,
        encounterResultORM.matchPoints,
        encounterResultORM.victoryPoints,
        encounterResultORM.bigTradeRoutePoints,
        encounterResultORM.cavalryArmyPoints,
        encounterResultORM.numberOfCities
    )

fun toData(encounterResult: EncounterResult): EncounterResultORM = EncounterResultORM(
    encounterResult.id.toString(),
    encounterResult.playerId.toString(),
    encounterResult.matchPoints,
    encounterResult.points,
    encounterResult.victoryPoints,
    encounterResult.bigTradeRoutePoints,
    encounterResult.cavalryArmyPoints
)

fun toData(encounter: Encounter): EncounterORM = EncounterORM(
    encounter.id,
    toData(encounter.playerList),
    toData(encounter.encounterResults)
)

fun toData(encounterResultList: List<EncounterResult>): RealmList<EncounterResultORM> =
    RealmList<EncounterResultORM>().apply {
        encounterResultList.forEach { encounterResult ->
            add(toData(encounterResult))
        }
    }
