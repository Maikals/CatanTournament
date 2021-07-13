package com.example.catantournament.ui.matches

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import com.example.catantournament.R
import com.example.catantournament.databinding.FragmentMatchesBinding
import com.example.catantournament.extensions.observe
import com.example.catantournament.ui.matches.MatchesStates.Error
import com.example.catantournament.ui.matches.MatchesStates.NoTournament
import com.example.catantournament.ui.matches.MatchesStates.Progress
import com.example.catantournament.ui.matches.MatchesStates.Success
import com.example.domain.entities.Tournament
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchesFragment : Fragment(R.layout.fragment_matches) {
    private val matchesViewModel: MatchesViewModel by viewModel()
    private var binding: FragmentMatchesBinding? = null
    private val views get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMatchesBinding.bind(view)
        observeData()
        setOnClickListeners()
        matchesViewModel.getTournament()
        setMenu()
    }

    private fun setMenu() {
        views.toolbar.inflateMenu(R.menu.matches_menu)
        views.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.reset_tournament -> resetTournament()
            }
            true
        }
    }

    private fun resetTournament() {
        showGenerateTournamentDialog()
    }

    private fun setOnClickListeners() {
        views.generateTournamentButton.setOnClickListener {
            showGenerateTournamentDialog()
        }
    }

    private fun showGenerateTournamentDialog() {
        val dialog = CreateTournamentDialogFragment.newInstance().apply {
            setTargetFragment(this@MatchesFragment, CreateTournamentDialogFragment.REQUEST_CODE)
        }
        dialog.show(parentFragmentManager, CreateTournamentDialogFragment.TAG)
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
        views.emptyView.visibility = VISIBLE
    }

    private fun showError() {
    }

    private fun showProgress() {
    }

    private fun manageEncounters(tournament: Tournament) {
        views.emptyView.visibility = GONE
        views.pager.adapter = TournamentAdapter(requireActivity(), tournament)
        setUpTabLayout()
        views.pager.visibility = VISIBLE
        views.tabLayout.visibility = VISIBLE
    }

    private fun setUpTabLayout() {
        TabLayoutMediator(views.tabLayout, views.pager) { tab, position ->
            tab.text = "${getString(R.string.matches_fragment_round)} ${position + 1}"
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TAG = "MatchesFragment"
        fun newInstance() =
            MatchesFragment()
    }
}
