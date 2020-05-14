package com.example.domain.use_case

import kotlinx.coroutines.CoroutineDispatcher

interface MainContextInterface {
    fun mainContext(): CoroutineDispatcher
}