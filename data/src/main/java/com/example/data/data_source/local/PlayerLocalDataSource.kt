package com.example.data.data_source.local

import com.example.data.data_source.PlayerDataSource
import com.example.data.db.RealmInstance
import com.example.data.entities.db.PlayerORM
import com.example.data.entities.mapper.player.toDomain
import com.example.domain.entities.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import java.util.UUID

class PlayerLocalDataSource : PlayerDataSource {
    init {
        RealmInstance.queryScope { realmEntity ->
            realmEntity.where(PlayerORM::class.java).findAll().addChangeListener { elements, _ ->
                val element = elements.map {
                    toDomain(it)
                }
                dataSourceScope.launch {
                    playerListChannel.send(element)
                }
            }
        }
    }

    private val playerListChannel = BroadcastChannel<List<Player>>(Channel.BUFFERED)

    private val dataSourceScope = CoroutineScope(Job() + Dispatchers.IO)

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

    override fun subscribeToPlayerList(): ReceiveChannel<List<Player>> =
        playerListChannel.openSubscription()

    override fun modifyPlayer(player: Player) =
        RealmInstance.transactionScope { realmInstance ->
            realmInstance.insertOrUpdate(PlayerORM(player.id.toString(), player.name, player.nick))
        }!!

    override fun deletePlayer(id: UUID) =
        RealmInstance.deleteEntity(PlayerORM.FIELD_ID, id.toString(), PlayerORM::class.java)
}