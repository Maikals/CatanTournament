package com.example.data.db

import com.example.data.extensions.transactionScope
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmModel

object RealmInstance {
    private const val realmName = "CatanDatabase"
    private const val encryptedKey =
        "4u7x!A%D*F-JaNdRgUkXp2s5v8y/B?E(H+KbPeShVmYq3t6w9z\$C&F)J@NcQfTjW"
    private const val schemaVersion = 1L
    private val realmConfig = RealmConfiguration.Builder()
        .name(realmName)
        .encryptionKey(encryptedKey.toByteArray())
        .schemaVersion(schemaVersion)
        .build()

    fun <T> queryScope(fn: (Realm) -> T): T = fn(getRealmInstance())

    fun <T> transactionScope(fn: (Realm) -> T): T? =
        getRealmInstance().transactionScope {
            fn(it)
        }

    fun <S : RealmModel> deleteEntity(fieldName: String, id: String, type: Class<S>) {
        val objectToDelete = getRealmInstance().where(type).equalTo(fieldName, id).findAll()
        transactionScope {
            objectToDelete.deleteAllFromRealm()
        }
    }

    @Synchronized
    fun getRealmInstance() = Realm.getInstance(realmConfig)
}