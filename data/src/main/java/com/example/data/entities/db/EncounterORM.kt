package com.example.data.entities.db

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.UUID

open class EncounterORM(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var playerList: RealmList<PlayerORM> = RealmList(),
    var results: RealmList<EncounterResultORM> = RealmList()
) : RealmObject() {
    companion object {
        const val FIELD_ID = "id"
        const val FIELD_PLAYER_LIST = "playerList"
        const val FIELD_RESULTS = "results"
    }
}
