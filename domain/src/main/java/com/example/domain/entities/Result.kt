package com.example.domain.entities

sealed class Result<T> {
    class Success<T>(val data: T) : Result<T>()
    object Failure : Result<Unit>()
}