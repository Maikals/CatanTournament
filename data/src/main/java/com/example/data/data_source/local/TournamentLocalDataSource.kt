package com.example.data.data_source.local

import com.example.data.data_source.TournamentDataSource
import com.example.data.db.RealmInstance
import com.example.data.entities.db.EncounterORM
import com.example.data.entities.db.EncounterResultORM
import com.example.data.entities.db.PlayerORM
import com.example.data.entities.db.RoundORM
import com.example.data.entities.db.TournamentORM
import com.example.data.entities.mapper.tournament.toDomain
import com.example.domain.entities.Encounter
import com.example.domain.entities.EncounterResult
import com.example.domain.entities.Player
import com.example.domain.entities.Round
import com.example.domain.entities.Tournament
import io.realm.RealmList
import java.util.UUID

class TournamentLocalDataSource : TournamentDataSource {

    override fun createTournament(tournament: Tournament): Tournament {
        var roundMaxId: Long = 0
        var encounterMaxId: Long = 0
        resetTournament()
        RealmInstance.queryScope { realm ->
            roundMaxId = realm.where(RoundORM::class.java).max(RoundORM.FIELD_ID)?.toLong() ?: 0
            encounterMaxId =
                realm.where(EncounterORM::class.java).max(EncounterORM.FIELD_ID)?.toLong() ?: 0
        }
        return RealmInstance.transactionScope { realm ->
            val tournamentORM =
                createTournamentORMFromTournament(
                    tournament,
                    roundMaxId,
                    encounterMaxId
                )
            realm.insertOrUpdate(tournamentORM)
            toDomain(tournamentORM)
        }!!
    }

    override fun getEncountersFromPlayerId(id: Long): List<EncounterResult> =
        RealmInstance.queryScope { realm ->
            realm.where(EncounterResultORM::class.java)
                .equalTo(EncounterResultORM.FIELD_PLAYER_ID, id).findAll().map {
                    createEncounterResult(it)
                }
        }

    private fun createEncounterResultList(encounterResults: List<EncounterResult>): RealmList<EncounterResultORM> =
        RealmList<EncounterResultORM>().apply {
            encounterResults.forEach {
                add(
                    toDomain(it)
                )
            }
        }

    private fun createEncounterResult(encounterResultORM: EncounterResultORM): EncounterResult =
        EncounterResult(
            UUID.fromString(encounterResultORM.id),
            encounterResultORM.playerId,
            encounterResultORM.points,
            encounterResultORM.matchPoints,
            encounterResultORM.victoryPoints,
            encounterResultORM.bigTradeRoutePoints,
            encounterResultORM.cavalryArmyPoints
        )

    override fun getTournament() =
        RealmInstance.queryScope { realm ->
            toDomain(realm.where(TournamentORM::class.java).findFirst())
        }

    override fun getRound(id: Long): Round =
        RealmInstance.queryScope { realm ->
            toDomain(realm.where(RoundORM::class.java).equalTo(RoundORM.FIELD_ID, id).findFirst()!!)
        }

    private fun resetTournament() {
        RealmInstance.transactionScope { realm ->
            realm.where(EncounterORM::class.java).findAll().deleteAllFromRealm()
            realm.where(RoundORM::class.java).findAll().deleteAllFromRealm()
            realm.where(TournamentORM::class.java).findAll().deleteAllFromRealm()
        }
    }

    private fun createRoundORMFromRound(
        roundMaxId: Long,
        round: Round,
        encounterMaxId: Long
    ): RoundORM = RoundORM(roundMaxId, RealmList<EncounterORM>().apply {
        val id = encounterMaxId + roundMaxId * round.encounterList.size
        round.encounterList.forEachIndexed { i, encounter ->
            add(
                createEncounterORMFromEncounter(
                    (id + i) * roundMaxId,
                    encounter
                )
            )
        }
    })

    private fun createEncounterORMFromEncounter(
        encounterMaxId: Long,
        encounter: Encounter
    ): EncounterORM = EncounterORM(
        encounterMaxId, RealmList<PlayerORM>().apply {
            encounter.playerList.forEach { player ->
                add(createPlayerORMFromPlayer(player))
            }
        },
        createEncounterResultList(encounter.encounterResults)
    )

    private fun createPlayerORMFromPlayer(
        player: Player
    ): PlayerORM =
        PlayerORM(
            player.id.toString(),
            player.name,
            player.nick,
            createEncounterResultList(player.encounterResults)
        )

    private fun createTournamentORMFromTournament(
        tournament: Tournament,
        roundMaxId: Long,
        encounterMaxId: Long
    ): TournamentORM = TournamentORM(roundList = RealmList<RoundORM>().apply {
        tournament.roundList.forEachIndexed { i, round ->
            add(
                createRoundORMFromRound(
                    roundMaxId + 1 + i,
                    round,
                    encounterMaxId
                )
            )
        }
    })
}