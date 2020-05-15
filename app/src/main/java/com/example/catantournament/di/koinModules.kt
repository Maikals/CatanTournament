package com.example.catantournament.di

import com.example.catantournament.ui.classification.ClassificationViewModel
import com.example.catantournament.ui.matches.MatchesViewModel
import com.example.catantournament.ui.player_list.PlayerListViewModel
import com.example.data.data_source.PlayerDataSource
import com.example.data.data_source.local.PlayerLocalDataSource
import com.example.data.repositories.PlayerRepositoryImpl
import com.example.domain.repositories.PlayerRepository
import com.example.domain.use_case.AddPlayerUseCase
import com.example.domain.use_case.DeletePlayerUseCase
import com.example.domain.use_case.GetAllPlayersSortedUseCase
import com.example.domain.use_case.GetAllPlayersUseCase
import com.example.domain.use_case.ModifyPlayerUseCase
import com.example.domain.use_case.SubscribeToPlayerUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel {
        PlayerListViewModel(
            addPlayerUseCase = get(),
            subscribeToPlayerUseCase = get(),
            getAllPlayersUseCase = get(),
            modifyPlayerUseCase = get(),
            deletePlayerUseCase = get()
        )
    }
    viewModel { ClassificationViewModel(getAllPlayersSortedUseCase = get()) }
    viewModel { MatchesViewModel() }
}

private val useCasesModule = module {
    factory { AddPlayerUseCase(playerRepository = get()) }
    factory { SubscribeToPlayerUseCase(playerRepository = get()) }
    factory { GetAllPlayersUseCase(playerRepository = get()) }
    factory { GetAllPlayersSortedUseCase(playerRepository = get()) }
    factory { ModifyPlayerUseCase(playerRepository = get()) }
    factory { DeletePlayerUseCase(playerRepository = get()) }
}

private val repositoriesModule = module {
    factory<PlayerRepository> { PlayerRepositoryImpl(playerLocalDataSource = get()) }
}

private val dataSourceModule = module {
    single<PlayerDataSource> { PlayerLocalDataSource() }
}

val modulesList = listOf(viewModelModule, useCasesModule, repositoriesModule, dataSourceModule)