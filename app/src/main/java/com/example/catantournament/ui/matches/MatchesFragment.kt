package com.example.catantournament.ui.matches

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.catantournament.R
import com.example.catantournament.extensions.observe
import com.example.catantournament.ui.matches.MatchesStates.*
import com.example.domain.entities.Tournament
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
        observeData()
        setOnClickListeners()
        matchesViewModel.getTournament()
    }

    private fun setOnClickListeners() {
        generate_tournament_button.setOnClickListener {
            val dialog = CreateTournamentDialogFragment.newInstance().apply {
                setTargetFragment(this, CreateTournamentDialogFragment.REQUEST_CODE)
            }
            dialog.show(parentFragmentManager, CreateTournamentDialogFragment.TAG)
        }
    }

    private fun observeData() {
        observe(matchesViewModel.tournamentLiveData) {
            when (it) {
                is Success -> manageEncounters(it.result)
                is Progress -> showProgress()
                is Error -> showError()
                is NoTournament -> showEmptyView()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CreateTournamentDialogFragment.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                data?.run {
                    val numberOfRounds =
                        getIntExtra(CreateTournamentDialogFragment.EXTRA_NUMBER_OF_ROUNDS, 1)
                    matchesViewModel.generateTournament(numberOfRounds)
                }
            }
        }
    }

    private fun showEmptyView() {
        empty_view.visibility = VISIBLE
    }

    private fun showError() {

    }

    private fun showProgress() {

    }

    private fun manageEncounters(tournament: Tournament) {
        empty_view.visibility = GONE
        pager.adapter = TournamentAdapter(requireActivity(), tournament)
        setUpTabLayout()
        pager.visibility = VISIBLE
        tab_layout.visibility = VISIBLE
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