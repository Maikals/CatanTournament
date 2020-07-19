package com.example.catantournament.ui.encounter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.Encounter
import com.example.domain.entities.Result
import com.example.domain.entities.params.GetEncounterParams
import com.example.domain.use_case.GetEncounterUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EncounterViewModel(private val getEncounterUseCase: GetEncounterUseCase) : ViewModel() {
    private val _encounterLiveData = MutableLiveData<Result<Encounter>>()
    val encounterLiveData = _encounterLiveData

    fun start(id: Long) {
        viewModelScope.launch {
            getEncounterUseCase(GetEncounterParams(id)).collect {
                _encounterLiveData.postValue(it)
            }
        }
    }

    fun saveResult() {
    }
}
