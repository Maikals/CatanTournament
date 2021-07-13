package com.example.catantournament.ui.matches

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catantournament.R
import com.example.catantournament.databinding.FragmentRoundBinding
import com.example.catantournament.extensions.observe
import com.example.catantournament.interfaces.AdapterOnClickListenerInterface
import com.example.catantournament.ui.encounter.EncounterActivity
import com.example.domain.entities.Encounter
import com.example.domain.entities.Result.Success
import com.example.domain.entities.Round
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoundFragment :
    Fragment(R.layout.fragment_round),
    AdapterOnClickListenerInterface.Screen<Encounter> {
    private val roundViewModel: RoundViewModel by viewModel()
    private val linearLayoutManager by lazy { LinearLayoutManager(requireContext()) }
    private var binding: FragmentRoundBinding? = null
    private val views get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRoundBinding.bind(view)
        setRecyclerView()
        observeData()
        roundViewModel.start(requireArguments().getString(EXTRA_ROUND_ID))
    }

    private fun setRecyclerView() {
        views.roundList.layoutManager = linearLayoutManager
    }

    private fun observeData() {
        observe(roundViewModel.roundLiveData) {
            when (it) {
                is Success -> manageRound(it.data)
            }
        }
    }

    private fun manageRound(data: Round) {
        views.roundList.adapter = RoundAdapter(data.encounterList, this)
    }

    override fun onClick(data: Encounter) {
        startActivity(
            Intent(requireActivity(), EncounterActivity::class.java).apply {
                putExtra(EncounterActivity.EXTRA_ENCOUNTER_ID, data.id)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TAG = "RoundFragment"
        fun newInstance(roundId: String) = RoundFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_ROUND_ID, roundId)
            }
        }

        private const val EXTRA_ROUND_ID = "extraRoundId"
    }
}
