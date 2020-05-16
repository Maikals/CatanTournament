package com.example.domain.entities

import java.util.UUID

data class Player(
    val id: UUID = UUID.randomUUID(), val name: String, val nick: String,
    val encounterResults: List<EncounterResult> = ArrayList()
) : Comparable<Player> {
    val points: Int
        get() = sumAllVictoryPoints()
    val victoryPoints: Int
        get() = sumAllPoints()
    val bigTradeRoute: Int
        get() = sumAllBigTradeRoute()
    val bigCavalryArmy: Int
        get() = sumAllBigCavalryArmy()

    override fun compareTo(other: Player): Int =
        if (points == other.points) {
            if (victoryPoints == other.victoryPoints) {
                if (bigTradeRoute == other.bigTradeRoute) {
                    bigCavalryArmy.compareTo(other.bigCavalryArmy)
                } else bigTradeRoute.compareTo(other.bigTradeRoute)
            } else victoryPoints.compareTo(other.victoryPoints)
        } else points.compareTo(other.points)

    private fun sumAllPoints(): Int =
        encounterResults.sumBy { it.matchPoints }

    private fun sumAllVictoryPoints(): Int =
        encounterResults.sumBy { it.victoryPoints }

    private fun sumAllBigTradeRoute(): Int =
        encounterResults.sumBy { it.bigTradeRoutePoints }

    private fun sumAllBigCavalryArmy(): Int =
        encounterResults.sumBy { it.cavalryArmyPoints }
}