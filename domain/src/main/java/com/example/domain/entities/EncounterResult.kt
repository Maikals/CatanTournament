package com.example.domain.entities

import java.util.UUID

data class EncounterResult(
    val id: UUID = UUID.randomUUID(),
    val playerId: Long = 0,
    val points: Int = 0,
    val matchPoints: Int = 0,
    val victoryPoints: Int = 0,
    val bigTradeRoutePoints: Int = 0,
    val cavalryArmyPoints: Int = 0
)
