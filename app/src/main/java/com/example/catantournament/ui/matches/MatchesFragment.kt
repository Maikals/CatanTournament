package com.example.catantournament.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.catantournament.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_matches.*
import org.koin.android.viewmodel.ext.android.viewModel

class MatchesFragment : Fragment() {
    private val matchesViewModel: MatchesViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_matches, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabLayout()
    }

    private fun manageEncounters() {
    }

    private fun setUpTabLayout() {
        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = "${getString(R.string.matches_fragment_round)} ${position + 1}"
        }.attach()
    }

    companion object {
        const val TAG = "MatchesFragment"
        fun newInstance() =
            MatchesFragment()
    }
}