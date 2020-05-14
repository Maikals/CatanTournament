package com.example.data.extensions

import io.realm.Realm

fun <T> Realm.transactionScope(fn: (Realm) -> T): T? {
    var result: T? = null
    executeTransaction {
        result = fn(this)
    }
    return result
}