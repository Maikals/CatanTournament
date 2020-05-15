package com.example.data.entities.db

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey

open class EncounterORM(
    @PrimaryKey var id: Long = 0,
    var playerList: RealmList<PlayerORM> = RealmList()
) : RealmObject() {
    companion object {
        const val FIELD_ID = "id"
        const val FIELD_PLAYER_LIST = "playerList"
    }
}