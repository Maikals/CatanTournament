package com.example.data.data_source.local

import com.example.data.data_source.PlayerDataSource
import com.example.data.db.RealmInstance
import com.example.data.entities.db.PlayerORM
import com.example.domain.entities.Player
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PlayerLocalDataSource : PlayerDataSource {
    init {
        val fn: (Realm) -> Unit = { realmEntity ->
            realmEntity.where(PlayerORM::class.java).findAll().addChangeListener { elements, _ ->
                val element = elements.map {
                    toDomain(it)
                }
                dataSourceScope.launch {
                    playerListChannel.send(element)
                }
            }
        }
        RealmInstance.queryScope(fn)
    }

    private val playerListChannel = BroadcastChannel<List<Player>>(Channel.BUFFERED).apply {
        RealmInstance.queryScope { realmEntity ->
            val allPlayers = realmEntity.where(PlayerORM::class.java).findAll()
            val listOfPlayer = allPlayers.map { toDomain(it) }
            runBlocking {
                send(listOfPlayer)
            }
        }
    }
    private val dataSourceScope = CoroutineScope(Job() + Dispatchers.IO)

    private fun toDomain(playerORM: PlayerORM): Player =
        Player(
            playerORM.id,
            playerORM.name,
            playerORM.nick
        )

    override fun addPlayer(player: Player) {
        val max = RealmInstance.queryScope {
            it.where(PlayerORM::class.java).max(PlayerORM.FIELD_ID)?.toLong() ?: 1
        }

        RealmInstance.transactionScope { realmInstance ->
            val playerORM = PlayerORM(max + 1, player.name, player.nick)
            realmInstance.insertOrUpdate(playerORM)
        }
    }

    override fun findAllPlayers(): List<Player> =
        RealmInstance.transactionScope { realmInstance ->
            realmInstance.where(PlayerORM::class.java).findAll()
                .map { Player(it.id, it.name, it.nick) }
        }!!

    override fun subscribeToPlayerList(): ReceiveChannel<List<Player>> =
        playerListChannel.openSubscription()
}