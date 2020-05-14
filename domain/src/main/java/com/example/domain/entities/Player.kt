package com.example.domain.entities

data class Player(
    val id: Long = 0L, val name: String, val nick: String,
    var points: Int = 0,
    var victoryPoints: Int = 0,
    var bigTradeRoute: Int = 0,
    var bigCavalryArmy: Int = 0
) : Comparable<Player> {
    override fun compareTo(other: Player): Int =
        if (points == other.points) {
            if (victoryPoints == other.victoryPoints) {
                if (bigTradeRoute == other.bigTradeRoute) {
                    bigCavalryArmy.compareTo(other.bigCavalryArmy)
                } else bigTradeRoute.compareTo(other.bigTradeRoute)
            } else victoryPoints.compareTo(other.victoryPoints)
        } else points.compareTo(other.points)
}