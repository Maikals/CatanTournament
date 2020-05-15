package com.example.catantournament.ui.matches

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.domain.entities.Tournament

class TournamentAdapter(fa: FragmentActivity, private val tournament: Tournament) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = tournament.roundList.size

    override fun createFragment(position: Int): RoundFragment =
        RoundFragment.newInstance(tournament.roundList[position].id)
}