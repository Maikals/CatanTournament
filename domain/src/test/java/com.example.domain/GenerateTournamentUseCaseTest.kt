package com.example.domain

import com.example.domain.entities.Player
import com.example.domain.entities.TournamentGenerator
import org.junit.Test
import java.util.UUID

class GenerateTournamentUseCaseTest {

    @Test(timeout = 1000)
    fun `should create 3 rounds of a tournament`() {
        val players = ArrayList<Player>().apply {
            repeat(9) {
                add(Player(UUID.randomUUID(), "name$it", "nick$it"))
            }
        }
        repeat(4) {
            println(TournamentGenerator.generateRoundRobinIndex(9, it))
            println(
                TournamentGenerator.generateEncounter(
                    TournamentGenerator.generateRoundRobinIndex(
                        players.size,
                        it
                    ),
                    players
                )
            )
        }
    }
}
