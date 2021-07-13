package com.example.domain.entities

object TournamentGenerator {
    fun generateTournament(rounds: Int, playerList: List<Player>): Tournament {
        val listOfRounds = ArrayList<Round>().apply {
            repeat(rounds) { iteration ->
                val indexList = generateRoundRobinIndex(playerList.size, iteration)
                val generateEncounters = generateEncounter(indexList, playerList)
                add(Round(encounterList = generateEncounters))
            }
        }
        return Tournament(listOfRounds)
    }

    fun generateEncounter(indexList: ArrayList<Int>, playerList: List<Player>) =
        ArrayList<Encounter>().apply {
            while (indexList.size > 0) {
                if (indexList.size % 4 == 0) {
                    addEncounter(indexList, playerList, 4)
                } else {
                    addEncounter(indexList, playerList, 3)
                }
            }
        }

    private fun ArrayList<Encounter>.addEncounter(
        indexList: ArrayList<Int>,
        playerList: List<Player>,
        sublistSize: Int
    ) {
        println("before$indexList")
        val subList = indexList.subList(0, sublistSize)
        val encounter = Encounter(
            playerList = ArrayList<Player>().apply {
                subList.forEach {
                    add(playerList[it])
                }
            }
        )
        repeat(sublistSize) { indexList.removeAt(0) }
        println("after$indexList")
        add(encounter)
    }

    fun generateRoundRobinIndex(size: Int, iteration: Int): ArrayList<Int> =
        ArrayList<Int>().apply {
            val listOfIndex = (0 until size).toList() as ArrayList
            println(((iteration * 3) % size))
            println("RoundRobinBefore: $listOfIndex")
            val subList =
                listOfIndex.subList(size - ((iteration * 3) % size), size).toMutableList()
            println("subListToRemove: $subList")
            for (i in size - 1 downTo size - ((iteration * 3) % size)) {
                listOfIndex.removeAt(i)
            }
            println("listOfIndexAfterRemove: $listOfIndex")
            listOfIndex.addAll(1, subList)
            for (i in 0 until size / 2) {
                add(listOfIndex[i])
                add(listOfIndex[size - i - 1])
            }
            if (size % 2 != 0) add(listOfIndex[size / 2])
            println("RoundRobinAfter: $listOfIndex")
        }
}
