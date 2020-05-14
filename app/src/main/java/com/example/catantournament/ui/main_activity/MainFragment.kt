package com.example.catantournament.ui.main_activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.catantournament.R
import com.example.catantournament.ui.classification.ClassificationFragment
import com.example.catantournament.ui.player_list.PlayerListFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        bottom_navigation_View.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item_player_list -> openPlayerListFragment()
                R.id.menu_item_classification -> openClassificationFragment()
            }
            false
        }
        bottom_navigation_View.selectedItemId = R.id.menu_item_player_list
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

    companion object {
        const val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }
}