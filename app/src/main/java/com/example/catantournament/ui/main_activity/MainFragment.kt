package com.example.catantournament.ui.main_activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.catantournament.R
import com.example.catantournament.databinding.FragmentMainBinding
import com.example.catantournament.ui.classification.ClassificationFragment
import com.example.catantournament.ui.matches.MatchesFragment
import com.example.catantournament.ui.player_list.PlayerListFragment

class MainFragment : Fragment(R.layout.fragment_main) {
    private var binding: FragmentMainBinding? = null
    private val views get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        views.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item_player_list -> openPlayerListFragment()
                R.id.menu_item_classification -> openClassificationFragment()
                R.id.menu_item_encounters -> openEncountersFragment()
            }
            false
        }
        views.bottomNavigationView.selectedItemId = R.id.menu_item_player_list
    }

    private fun openEncountersFragment() {
        childFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            MatchesFragment.newInstance(),
            MatchesFragment.TAG
        ).commit()
    }

    private fun openClassificationFragment() {
        childFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                ClassificationFragment.newInstance(),
                ClassificationFragment.TAG
            ).commit()
    }

    private fun openPlayerListFragment() {
        childFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                PlayerListFragment.newInstance(),
                ClassificationFragment.TAG
            ).commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }
}
