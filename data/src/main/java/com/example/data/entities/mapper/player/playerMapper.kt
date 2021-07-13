package com.example.data.entities.mapper.player

import com.example.data.entities.db.PlayerORM
import com.example.data.entities.mapper.tournament.toData
import com.example.data.entities.mapper.tournament.toDomain
import com.example.domain.entities.Player
import io.realm.RealmList
import java.util.UUID

fun toDomain(playerORM: PlayerORM): Player =
    Player(
        UUID.fromString(playerORM.id),
        playerORM.name,
        playerORM.nick,
        toDomain(playerORM.encounterResults)
    )

fun toData(playerList: List<Player>): RealmList<PlayerORM> = RealmList<PlayerORM>().apply {
    playerList.forEach { player ->
        add(toData(player))
    }
}

fun toData(player: Player): PlayerORM = PlayerORM(
    name = player.name,
    nick = player.nick,
    encounterResults = toData(player.encounterResults)
)
