package com.example.data.entities.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PlayerORM(
    @PrimaryKey var id: Long = 0,
    var name: String = "",
    var nick: String = "",
    var points: Int = 0,
    var victoryPoints: Int = 0,
    var bigTradeRoute: Int = 0,
    var bigCavalryArmy: Int = 0
) : RealmObject() {

    companion object {
        const val FIELD_ID = "id"
        const val FIELD_NAME = "name"
        const val FIELD_NICK = "nick"
        const val FIELD_POINTS = "points"
        const val FIELD_VICTORY_POINTS = "victoryPoints"
        const val FIELD_BIG_TRADE_ROUTE = "bigTradeRoute"
        const val FIELD_BIG_CAVALRY_ARMY = "bigCavalryArmy"
    }
}