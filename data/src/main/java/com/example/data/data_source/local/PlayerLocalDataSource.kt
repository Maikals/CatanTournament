package com.example.data.data_source.local

import com.example.data.data_source.PlayerDataSource
import com.example.data.db.RealmInstance
import com.example.data.entities.db.PlayerORM
import com.example.data.entities.mapper.player.toDomain
import com.example.domain.entities.Player
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID

class PlayerLocalDataSource : PlayerDataSource {
    private val playerList = RealmInstance.getRealmInstance().where(PlayerORM::class.java).findAll()

    override fun addPlayer(player: Player) {
        RealmInstance.transactionScope { realmInstance ->
            val playerORM = PlayerORM(UUID.randomUUID().toString(), player.name, player.nick)
            realmInstance.insertOrUpdate(playerORM)
        }
    }

    override fun findAllPlayers(): List<Player> =
        RealmInstance.queryScope { realmInstance ->
            realmInstance.where(PlayerORM::class.java).findAll()
                .map { Player(UUID.fromString(it.id), it.name, it.nick) }
        }!!

    override fun subscribeToPlayerList() = callbackFlow<List<Player>> {
        playerList.addChangeListener { elements, _ ->
            val element = elements.map {
                toDomain(it)
            }
            sendBlocking(element)
        }
        sendBlocking(playerList.map { toDomain(it) })
        awaitClose { playerList.removeAllChangeListeners() }
    }

    override fun modifyPlayer(player: Player) =
        RealmInstance.transactionScope { realmInstance ->
            realmInstance.insertOrUpdate(PlayerORM(player.id.toString(), player.name, player.nick))
        }!!

    override fun deletePlayer(id: UUID) =
        RealmInstance.deleteEntity(PlayerORM.FIELD_ID, id.toString(), PlayerORM::class.java)
}