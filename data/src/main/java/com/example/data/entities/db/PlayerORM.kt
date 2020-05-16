package com.example.data.entities.db

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PlayerORM(
    @PrimaryKey var id: String = "",
    var name: String = "",
    var nick: String = "",
    var encounterResults: RealmList<EncounterResultORM> = RealmList()
) : RealmObject() {

    companion object {
        const val FIELD_ID = "id"
        const val FIELD_NAME = "name"
        const val FIELD_NICK = "nick"
        const val FIELD_ENCOUNTER_RESULTS = "encounterResults"
    }
}