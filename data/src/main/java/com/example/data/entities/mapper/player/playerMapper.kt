package com.example.data.entities.mapper.player

import com.example.data.entities.db.PlayerORM
import com.example.domain.entities.Player

fun toDomain(playerORM: PlayerORM): Player =
    Player(
        playerORM.id,
        playerORM.name,
        playerORM.nick,
        playerORM.points,
        playerORM.victoryPoints,
        playerORM.bigTradeRoute,
        playerORM.bigCavalryArmy
    )