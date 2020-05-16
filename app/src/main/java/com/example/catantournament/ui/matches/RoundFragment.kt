package com.example.catantournament.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catantournament.R
import com.example.catantournament.extensions.observe
import com.example.catantournament.interfaces.AdapterOnClickListenerInterface
import com.example.domain.entities.Encounter
import com.example.domain.entities.Result.Success
import com.example.domain.entities.Round
import kotlinx.android.synthetic.main.fragment_round.*
import org.koin.android.viewmodel.ext.android.viewModel

class RoundFragment : Fragment(), AdapterOnClickListenerInterface.Screen<Encounter> {
    private val roundViewModel: RoundViewModel by viewModel()
    private val linearLayoutManager by lazy { LinearLayoutManager(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_round, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeData()
        roundViewModel.start(requireArguments().getLong(EXTRA_ROUND_ID))
    }

    private fun setRecyclerView() {
        round_list.layoutManager = linearLayoutManager
    }

    private fun observeData() {
        observe(roundViewModel.roundLiveData) {
            when (it) {
                is Success -> manageRound(it.data)
            }
        }
    }

    private fun manageRound(data: Round) {
        round_list.adapter = RoundAdapter(data.encounterList, this)
    }

    companion object {
        const val TAG = "RoundFragment"
        fun newInstance(roundId: Long) = RoundFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_ROUND_ID, roundId)
            }
        }

        private const val EXTRA_ROUND_ID = "extraRoundId"
    }

    override fun onClick(data: Encounter) {
    }
}