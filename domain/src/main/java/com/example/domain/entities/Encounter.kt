package com.example.domain.entities

data class Encounter(
    val id: Long,
    val playerList: List<Player>,
    val encounterResults: List<EncounterResult> = ArrayList()
)