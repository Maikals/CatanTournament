package com.example.catantournament.utls

import com.example.domain.use_case.MainContextInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MainContext : MainContextInterface {
    override fun mainContext(): CoroutineDispatcher = Dispatchers.Main
}