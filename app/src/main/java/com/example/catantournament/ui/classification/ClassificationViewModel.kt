package com.example.catantournament.ui.classification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.domain.entities.EmptyParams
import com.example.domain.use_case.GetAllPlayersSortedUseCase

class ClassificationViewModel(private val getAllPlayersSortedUseCase: GetAllPlayersSortedUseCase) :
    ViewModel() {
    val allPlayersLiveData = getAllPlayersSortedUseCase(EmptyParams()).asLiveData()
}