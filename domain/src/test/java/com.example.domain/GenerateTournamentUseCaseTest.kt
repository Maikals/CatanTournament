package com.example.domain

import com.example.domain.entities.Player
import com.example.domain.repositories.PlayerRepository
import com.example.domain.repositories.TournamentRepository
import com.example.domain.use_case.GenerateTournamentUseCase
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test

class GenerateTournamentUseCaseTest {

    private val playerRepository: PlayerRepository = mock()
    private val tournamentRepository: TournamentRepository = mock()
    private val generateTournamentUseCase =
        GenerateTournamentUseCase(playerRepository, tournamentRepository)

    @Test(timeout=1000)
    fun `should create 3 rounds of a tournament`() {
        val players = ArrayList<Player>().apply {
            repeat(9) {
                add(Player(it.toLong(), "name$it", "nick$it"))
            }
        }
        repeat(4) {
            println(generateTournamentUseCase.generateRoundRobinIndex(9, it))
            println(generateTournamentUseCase.generateEncounter(generateTournamentUseCase.generateRoundRobinIndex(players.size, it), players))
        }

    }

}