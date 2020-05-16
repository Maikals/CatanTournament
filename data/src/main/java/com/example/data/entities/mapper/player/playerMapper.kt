package com.example.data.entities.mapper.player

import com.example.data.entities.db.PlayerORM
import com.example.data.entities.mapper.tournament.toDomain
import com.example.domain.entities.Player
import java.util.UUID

fun toDomain(playerORM: PlayerORM): Player =
    Player(
        UUID.fromString(playerORM.id),
        playerORM.name,
        playerORM.nick,
        toDomain(playerORM.encounterResults)
    )