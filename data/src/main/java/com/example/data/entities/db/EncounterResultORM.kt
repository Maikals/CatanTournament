package com.example.data.entities.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.UUID

open class EncounterResultORM(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var playerId: String = UUID.randomUUID().toString(),
    var matchPoints: Int = 0,
    var points: Int = 0,
    var victoryPoints: Int = 0,
    var bigTradeRoutePoints: Int = 0,
    var cavalryArmyPoints: Int = 0,
    var numberOfCities: Int = 0
) : RealmObject() {
    companion object {
        const val FIELD_ID = "id"
        const val FIELD_PLAYER_ID = "playerId"
        const val FIELD_MATCH_POINTS = "matchPoints"
        const val FIELD_POINTS = "points"
        const val FIELD_VICTORY_POINTS = "victoryPoints"
        const val FIELD_TRADE_ROUTE_POINTS = "bigTradeRoutePoints"
        const val FIELD_CAVALRY_ROUTE_POINTS = "cavalryArmyPoints"
    }
}
