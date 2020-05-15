package com.example.data.entities.db

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.LinkingObjects

class TournamentORM(@LinkingObjects val roundList: RealmList<RoundORM>) : RealmObject() {
    companion object {
        const val FIELD_ROUND_LIST = "roundList"
    }
}