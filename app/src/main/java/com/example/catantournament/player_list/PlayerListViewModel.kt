package com.example.catantournament.player_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.AddPlayerParams
import com.example.domain.entities.EmptyParams
import com.example.domain.entities.Player
import com.example.domain.entities.Result
import com.example.domain.use_case.AddPlayerUseCase
import com.example.domain.use_case.GetAllPlayersUseCase
import com.example.domain.use_case.SubscribeToPlayerUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlayerListViewModel(
    private val addPlayerUseCase: AddPlayerUseCase,
    private val subscribeToPlayerUseCase: SubscribeToPlayerUseCase,
    private val getAllPlayersUseCase: GetAllPlayersUseCase
) : ViewModel() {
    private val _playerListLiveData: MutableLiveData<Result<List<Player>>> =
        (subscribeToPlayerUseCase(EmptyParams()).asLiveData() as MutableLiveData<Result<List<Player>>>).apply {
            viewModelScope.launch {
                getAllPlayersUseCase(EmptyParams()).collect {
                    postValue(it)
                }
            }
        }
    val playerListLiveData: LiveData<Result<List<Player>>>
        get() = _playerListLiveData

    fun addPlayer(player: Player) {
        viewModelScope.launch {
            addPlayerUseCase(AddPlayerParams(player)).collect { }
        }
    }
}