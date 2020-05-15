package com.example.data.entities.db

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey

class RoundORM(
    @PrimaryKey var id: Long = 0,
    @LinkingObjects val roundList: RealmList<EncounterORM>
) : RealmObject() {
    companion object {
        const val FIELD_ID = "id"
        const val FIELD_ROUND_LIST = "roundList"
    }
}