package com.example.data.entities.db

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey

class EncounterORM(
    @PrimaryKey var id: Long,
    @LinkingObjects val playerList: RealmList<PlayerORM>
) : RealmObject() {
    companion object {
        const val FIELD_ID = "id"
        const val FIELD_PLAYER_LIST = "playerList"
    }
}