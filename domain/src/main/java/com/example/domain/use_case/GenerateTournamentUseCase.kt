package com.example.domain.use_case

import com.example.domain.entities.Encounter
import com.example.domain.entities.Player
import com.example.domain.entities.Round
import com.example.domain.entities.Tournament
import com.example.domain.entities.params.GenerateTournamentParams
import com.example.domain.repositories.PlayerRepository
import com.example.domain.repositories.TournamentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GenerateTournamentUseCase(private val playerRepository: PlayerRepository,
private val tournamentRepository: TournamentRepository
) :
    BaseUseCase<GenerateTournamentParams, Tournament>() {
    override fun buildCall(params: GenerateTournamentParams): Flow<Tournament> = flow {
        playerRepository.getAllPlayers().collect { playerList ->
            val tournament = generateTournament(params.numberOfRounds, playerList)
            tournamentRepository.createTournament(tournament)
            emit(tournament)
        }
    }

    private fun generateTournament(rounds: Int, playerList: List<Player>): Tournament {
        val listOfRounds = ArrayList<Round>().apply {
            repeat(rounds) { iteration ->
                val indexList = generateRoundRobinIndex(playerList.size, iteration)
                val generateEncounters = generateEncounter(indexList, playerList)
                Round(0, generateEncounters)
            }
        }
        return Tournament(listOfRounds)
    }

    fun generateEncounter(indexList: ArrayList<Int>, playerList: List<Player>) =
        ArrayList<Encounter>().apply {
            while (indexList.size > 0) {
                if (indexList.size % 4 == 0) {
                    println("before$indexList")
                    val subList = indexList.subList(0, 4)
                    val encounter = Encounter(id = 0, playerList = ArrayList<Player>().apply {
                        subList.forEach {
                            add(playerList[it])
                        }
                    })
                    repeat(4) { indexList.removeAt(0) }
                    println("after$indexList")
                    add(encounter)
                } else {
                    println("before $indexList")
                    val subList = indexList.subList(0, 3)
                    val encounter = Encounter(id = 0, playerList = ArrayList<Player>().apply {
                        subList.forEach {
                            add(playerList[it])
                        }
                    })
                    add(encounter)
                    repeat(3) { indexList.removeAt(0) }
                    println("after $indexList")
                }
            }
        }

    fun generateRoundRobinIndex(size: Int, iteration: Int): ArrayList<Int> =
        ArrayList<Int>().apply {
            val listOfIndex = (0 until size).toList() as ArrayList
            println(((iteration * 4) % size))
            val subList = listOfIndex.subList(size - ((iteration * 3) % size), size).toMutableList()
            for (i in size - 1 downTo size - ((iteration * 3) % size)) {
                listOfIndex.removeAt(i)
            }
            listOfIndex.addAll(1, subList)
            for (i in 0 until size / 2) {
                add(listOfIndex[i])
                add(listOfIndex[size - i - 1])
            }
        }
}