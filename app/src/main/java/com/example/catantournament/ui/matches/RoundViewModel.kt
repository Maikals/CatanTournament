package com.example.catantournament.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.Result
import com.example.domain.entities.Round
import com.example.domain.entities.params.GetRoundParams
import com.example.domain.use_case.GetRoundUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RoundViewModel(private val getRoundUseCase: GetRoundUseCase) : ViewModel() {
    private val _roundLiveData = MutableLiveData<Result<Round>>()
    val roundLiveData: LiveData<Result<Round>> = _roundLiveData

    fun start(id: String?) {
        viewModelScope.launch {
            getRoundUseCase(GetRoundParams(id)).collect {
                _roundLiveData.postValue(it)
            }
        }
    }
}
