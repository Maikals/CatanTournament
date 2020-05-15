package com.example.catantournament.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.Result
import com.example.domain.entities.Tournament
import com.example.domain.entities.params.EmptyParams
import com.example.domain.entities.params.GenerateTournamentParams
import com.example.domain.use_case.GenerateTournamentUseCase
import com.example.domain.use_case.GetTournamentUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MatchesViewModel(
    private val generateTournamentUseCase: GenerateTournamentUseCase,
    private val getTournamentUseCase: GetTournamentUseCase
) :
    ViewModel() {
    private val _tournamentLiveData = MutableLiveData<MatchesStates>()
    val tournamentLiveData: LiveData<MatchesStates> = _tournamentLiveData

    fun getTournament() {
        viewModelScope.launch {
            getTournamentUseCase(EmptyParams()).collect {
                (it as? Result.Success).let { result ->
                    if (result?.data != null)
                        _tournamentLiveData.postValue(MatchesStates.Success(result.data!!))
                    else _tournamentLiveData.postValue(MatchesStates.NoTournament)
                }
            }
        }
    }

    fun generateTournament(numberOfRounds: Int) {
        _tournamentLiveData.postValue(MatchesStates.Progress)
        viewModelScope.launch {
            generateTournamentUseCase(GenerateTournamentParams(numberOfRounds)).collect {
                _tournamentLiveData.postValue(MatchesStates.Success((it as Result.Success).data))
            }
        }
    }
}

sealed class MatchesStates {
    object Progress : MatchesStates()
    class Success(val result: Tournament) : MatchesStates()
    object NoTournament : MatchesStates()
    object Error : MatchesStates()
}