package com.example.data.data_source.local

import com.example.data.data_source.TournamentDataSource
import com.example.data.db.RealmInstance
import com.example.data.entities.db.EncounterORM
import com.example.data.entities.db.EncounterResultORM
import com.example.data.entities.db.PlayerORM
import com.example.data.entities.db.RoundORM
import com.example.data.entities.db.TournamentORM
import com.example.data.entities.mapper.tournament.toData
import com.example.data.entities.mapper.tournament.toDomain
import com.example.domain.entities.Encounter
import com.example.domain.entities.EncounterResult
import com.example.domain.entities.Player
import com.example.domain.entities.Round
import com.example.domain.entities.Tournament
import io.realm.RealmList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID

class TournamentLocalDataSource : TournamentDataSource {

    private val tournamentRealm =
        RealmInstance.getRealmInstance().where(TournamentORM::class.java).findAll()

    override fun createTournament(tournament: Tournament): Tournament {
        resetTournament()
        return RealmInstance.transactionScope { realm ->
            val tournamentORM =
                createTournamentORMFromTournament(
                    tournament
                )
            realm.insertOrUpdate(tournamentORM)
            toDomain(tournamentORM)
        }!!
    }

    override fun getEncountersFromPlayerId(id: UUID): List<EncounterResult> =
        RealmInstance.queryScope { realm ->
            realm.where(EncounterResultORM::class.java)
                .equalTo(EncounterResultORM.FIELD_PLAYER_ID, id.toString()).findAll().map {
                    toDomain(it)
                }
        }

    override fun getEncounter(id: String): Encounter =
        RealmInstance.queryScope { realm ->
            toDomain(
                realm.where(EncounterORM::class.java).equalTo(EncounterORM.FIELD_ID, id)
                    .findFirst()!!
            )
        }

    @ExperimentalCoroutinesApi
    override fun subscribeToTournament() = callbackFlow<Tournament?> {
        tournamentRealm.addChangeListener { elements, _ ->
            sendBlocking(toDomain(elements.first()))
        }
        sendBlocking(toDomain(tournamentRealm.first()))
        awaitClose()
    }

    override fun saveEncounter(encounter: Encounter) {
        RealmInstance.transactionScope { realm ->
            realm.copyToRealmOrUpdate(toData(encounter))
        }
    }

    override fun getTournament() =
        RealmInstance.queryScope { realm ->
            toDomain(realm.where(TournamentORM::class.java).findFirst())
        }

    override fun getRound(id: String?): Round =
        RealmInstance.queryScope { realm ->
            toDomain(
                realm.where(RoundORM::class.java).equalTo(RoundORM.FIELD_ID, id)
                    .findFirst()!!
            )
        }

    private fun resetTournament() {
        RealmInstance.transactionScope { realm ->
            realm.where(EncounterResultORM::class.java).findAll().deleteAllFromRealm()
            realm.where(EncounterORM::class.java).findAll().deleteAllFromRealm()
            realm.where(RoundORM::class.java).findAll().deleteAllFromRealm()
            realm.where(TournamentORM::class.java).findAll().deleteAllFromRealm()
        }
    }

    private fun createEncounterResultList(encounterResults: List<EncounterResult>): RealmList<EncounterResultORM> =
        RealmList<EncounterResultORM>().apply {
            encounterResults.forEach {
                add(toData(it))
            }
        }

    private fun createRoundORMFromRound(
        round: Round
    ): RoundORM = RoundORM(
        roundList = RealmList<EncounterORM>().apply {
            round.encounterList.forEach { encounter ->
                add(
                    createEncounterORMFromEncounter(
                        encounter
                    )
                )
            }
        }
    )

    private fun createEncounterORMFromEncounter(
        encounter: Encounter
    ): EncounterORM = EncounterORM(
        playerList = RealmList<PlayerORM>().apply {
            encounter.playerList.forEach { player ->
                add(createPlayerORMFromPlayer(player))
            }
        }
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
        tournament: Tournament
    ): TournamentORM = TournamentORM(
        roundList = RealmList<RoundORM>().apply {
            tournament.roundList.forEach { round ->
                add(
                    createRoundORMFromRound(
                        round
                    )
                )
            }
        }
    )
}
