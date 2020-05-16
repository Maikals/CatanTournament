package com.example.catantournament.ui.player_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.Player
import com.example.domain.entities.Result
import com.example.domain.entities.params.AddPlayerParams
import com.example.domain.entities.params.DeletePlayerParams
import com.example.domain.entities.params.EmptyParams
import com.example.domain.entities.params.ModifyPlayerParams
import com.example.domain.use_case.AddPlayerUseCase
import com.example.domain.use_case.DeletePlayerUseCase
import com.example.domain.use_case.GetAllPlayersUseCase
import com.example.domain.use_case.ModifyPlayerUseCase
import com.example.domain.use_case.SubscribeToPlayerUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.UUID

class PlayerListViewModel(
    private val addPlayerUseCase: AddPlayerUseCase,
    private val subscribeToPlayerUseCase: SubscribeToPlayerUseCase,
    private val getAllPlayersUseCase: GetAllPlayersUseCase,
    private val modifyPlayerUseCase: ModifyPlayerUseCase,
    private val deletePlayerUseCase: DeletePlayerUseCase
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
            addPlayerUseCase(
                AddPlayerParams(
                    player
                )
            ).collect {
                getAllPlayersUseCase(EmptyParams()).collect {
                    _playerListLiveData.postValue(it)
                }
            }
        }
    }

    fun modifyPlayer(player: Player) {
        viewModelScope.launch {
            modifyPlayerUseCase(
                ModifyPlayerParams(
                    player
                )
            ).collect {
                getAllPlayersUseCase(EmptyParams()).collect {
                    _playerListLiveData.postValue(it)
                }
            }
        }
    }

    fun deletePlayer(uuid: UUID) {
        viewModelScope.launch {
            deletePlayerUseCase(
                DeletePlayerParams(
                    uuid
                )
            ).collect {
                getAllPlayersUseCase(EmptyParams()).collect {
                    _playerListLiveData.postValue(it)
                }
            }
        }
    }
}