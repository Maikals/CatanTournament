package com.example.catantournament.ui.encounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.catantournament.R
import com.example.catantournament.extensions.observe
import com.example.domain.entities.Encounter
import com.example.domain.entities.EncounterResult
import com.example.domain.entities.Result
import kotlinx.android.synthetic.main.fragment_encounter.*
import kotlinx.android.synthetic.main.include_encounter_result.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class EncounterFragment : Fragment() {

    private val encounterViewModel: EncounterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_encounter, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        encounterViewModel.start(requireArguments().getLong(EXTRA_ENCOUNTER_ID))
        setToolbar()
        observeData()
    }

    private fun setToolbar() {
        toolbar.inflateMenu(R.menu.encounter_menu)
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.encounter_save -> saveResult()
            }
            true
        }
    }

    private fun observeData() {
        observe(encounterViewModel.encounterLiveData) {
            when (it) {
                is Result.Success -> manageEncounter(it.data)
            }
        }
    }

    private fun manageEncounter(data: Encounter) {
        data.encounterResults.forEachIndexed { i, encounter ->
            when (i) {
                0 -> manageUser(data.playerList[i].name, encounter, first_player)
                1 -> manageUser(data.playerList[i].name, encounter, second_player)
                2 -> manageUser(data.playerList[i].name, encounter, third_player)
                3 -> manageUser(data.playerList[i].name, encounter, fourth_player)
            }
        }
    }

    private fun manageUser(
        playerName: String,
        encounter: EncounterResult,
        playerLayout: View
    ) {
        playerLayout.player_name.text = playerName
        playerLayout.match_points_text.setText(encounter.matchPoints)
        playerLayout.victory_points_text.setText(encounter.victoryPoints)
        playerLayout.big_cavalry_army_points_text.setText(encounter.cavalryArmyPoints)
        playerLayout.big_trading_route_points_text.setText(encounter.bigTradeRoutePoints)
        playerLayout.number_of_cities_text.setText(encounter.numberOfCities)
    }

    private fun saveResult() {
        encounterViewModel.saveResult()
    }

    companion object {
        const val TAG = "EncounterFragment"
        private const val EXTRA_ENCOUNTER_ID = "extraEncounterId"

        fun newInstance(id: Long) =
            EncounterFragment().apply {
                arguments = Bundle().apply {
                    putLong(EXTRA_ENCOUNTER_ID, id)
                }
            }
    }
}
