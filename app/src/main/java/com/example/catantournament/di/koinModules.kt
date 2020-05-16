package com.example.catantournament.di

import com.example.catantournament.ui.classification.ClassificationViewModel
import com.example.catantournament.ui.matches.MatchesViewModel
import com.example.catantournament.ui.matches.RoundViewModel
import com.example.catantournament.ui.player_list.PlayerListViewModel
import com.example.data.data_source.PlayerDataSource
import com.example.data.data_source.TournamentDataSource
import com.example.data.data_source.local.PlayerLocalDataSource
import com.example.data.data_source.local.TournamentLocalDataSource
import com.example.data.repositories.PlayerRepositoryImpl
import com.example.data.repositories.TournamentRepositoryImpl
import com.example.domain.repositories.PlayerRepository
import com.example.domain.repositories.TournamentRepository
import com.example.domain.use_case.AddPlayerUseCase
import com.example.domain.use_case.DeletePlayerUseCase
import com.example.domain.use_case.GenerateTournamentUseCase
import com.example.domain.use_case.GetAllPlayersSortedUseCase
import com.example.domain.use_case.GetAllPlayersUseCase
import com.example.domain.use_case.GetRoundUseCase
import com.example.domain.use_case.GetTournamentUseCase
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
    viewModel { MatchesViewModel(generateTournamentUseCase = get(), getTournamentUseCase = get()) }
    viewModel { RoundViewModel(getRoundUseCase = get()) }
}

private val useCasesModule = module {
    factory { AddPlayerUseCase(playerRepository = get()) }
    factory { SubscribeToPlayerUseCase(playerRepository = get()) }
    factory { GetAllPlayersUseCase(playerRepository = get()) }
    factory { GetAllPlayersSortedUseCase(playerRepository = get()) }
    factory { ModifyPlayerUseCase(playerRepository = get()) }
    factory { DeletePlayerUseCase(playerRepository = get()) }
    factory { GenerateTournamentUseCase(playerRepository = get(), tournamentRepository = get()) }
    factory { GetTournamentUseCase(tournamentRepository = get()) }
    factory { GetRoundUseCase(tournamentRepository = get()) }
}

private val repositoriesModule = module {
    factory<PlayerRepository> { PlayerRepositoryImpl(playerLocalDataSource = get()) }
    factory<TournamentRepository> { TournamentRepositoryImpl(tournamentCloudDataSource = get()) }
}

private val dataSourceModule = module {
    single<PlayerDataSource> { PlayerLocalDataSource() }
    single<TournamentDataSource> { TournamentLocalDataSource() }
}

val modulesList = listOf(viewModelModule, useCasesModule, repositoriesModule, dataSourceModule)