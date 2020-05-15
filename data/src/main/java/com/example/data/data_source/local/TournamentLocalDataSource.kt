package com.example.data.data_source.local

import com.example.data.data_source.TournamentDataSource
import com.example.data.db.RealmInstance
import com.example.data.entities.db.EncounterORM
import com.example.data.entities.db.PlayerORM
import com.example.data.entities.db.RoundORM
import com.example.data.entities.db.TournamentORM
import com.example.data.entities.mapper.tournament.toDomain
import com.example.domain.entities.Encounter
import com.example.domain.entities.Player
import com.example.domain.entities.Round
import com.example.domain.entities.Tournament
import io.realm.RealmList
import kotlin.math.round

class TournamentLocalDataSource : TournamentDataSource {

    override fun createTournament(tournament: Tournament) {
        var roundMaxId: Long = 1
        var encounterMaxId: Long = 1
        RealmInstance.queryScope { realm ->
            roundMaxId = realm.where(RoundORM::class.java).max(RoundORM.FIELD_ID)?.toLong() ?: 1
            encounterMaxId =
                realm.where(EncounterORM::class.java).max(EncounterORM.FIELD_ID)?.toLong() ?: 1
        }
        RealmInstance.transactionScope { realm ->
            val tournamentORM =
                createTournamentORMFromTournament(tournament, roundMaxId, encounterMaxId)
            realm.insertOrUpdate(tournamentORM)
        }
    }

    private fun createRoundORMFromRound(
        roundMaxId: Long,
        round: Round,
        encounterMaxId: Long
    ): RoundORM {
        return RoundORM(roundMaxId + 1, RealmList<EncounterORM>().apply {
            round.encounterList.forEach { encounter ->
                add(createEncounterORMFromEncounter(encounterMaxId, encounter))
            }
        })
    }

    private fun createEncounterORMFromEncounter(
        encounterMaxId: Long,
        encounter: Encounter
    ): EncounterORM {
        return EncounterORM(encounterMaxId + 1, RealmList<PlayerORM>().apply {
            encounter.playerList.forEach { player ->
                add(
                    createPlayerORMFromPlayer(player)
                )
            }
        })
    }

    private fun createPlayerORMFromPlayer(player: Player): PlayerORM {
        return PlayerORM(
            player.id,
            player.name,
            player.nick,
            player.points,
            player.victoryPoints,
            player.bigTradeRoute,
            player.bigCavalryArmy
        )
    }

    private fun createTournamentORMFromTournament(
        tournament: Tournament,
        roundMaxId: Long,
        encounterMaxId: Long
    ): TournamentORM {
        return TournamentORM(roundList = RealmList<RoundORM>().apply {
            tournament.roundList.forEach { round ->
                add(createRoundORMFromRound(roundMaxId, round, encounterMaxId))
            }
        })
    }

    override fun getTournament() =
        RealmInstance.queryScope { realm ->
            toDomain(realm.where(TournamentORM::class.java).findFirst())
        }

}