package com.example.data.entities.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PlayerORM(
    @PrimaryKey var id: Long = 0,
    var name: String = "",
    var nick: String = ""
) : RealmObject() {

    companion object {
        const val FIELD_ID = "id"
        const val FIELD_NAME = "name"
        const val FIELD_NICK = "nick"
    }
}