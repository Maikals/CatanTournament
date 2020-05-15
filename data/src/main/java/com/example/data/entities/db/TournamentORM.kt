package com.example.data.entities.db

import io.realm.RealmList
import io.realm.RealmObject

open class TournamentORM(var roundList: RealmList<RoundORM> = RealmList()) : RealmObject() {
    companion object {
        const val FIELD_ROUND_LIST = "roundList"
    }
}