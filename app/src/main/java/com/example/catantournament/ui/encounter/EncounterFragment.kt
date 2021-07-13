package com.example.catantournament.ui.encounter

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.catantournament.R
import com.example.catantournament.databinding.FragmentEncounterBinding
import com.example.catantournament.databinding.IncludeEncounterResultBinding
import com.example.catantournament.extensions.observe
import com.example.domain.entities.Encounter
import com.example.domain.entities.EncounterResult
import com.example.domain.entities.Player
import com.example.domain.entities.Result
import org.koin.androidx.viewmodel.ext.android.viewModel

class EncounterFragment : Fragment(R.layout.fragment_encounter) {
    private val encounterId by lazy { requireArguments().getString(EXTRA_ENCOUNTER_ID)!! }
    private val encounterViewModel: EncounterViewModel by viewModel()
    private var binding: FragmentEncounterBinding? = null
    private val views get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEncounterBinding.bind(view)
        encounterViewModel.start(encounterId)
        setToolbar()
        observeData()
    }

    private fun setToolbar() {
        views.toolbar.inflateMenu(R.menu.encounter_menu)
        views.toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel)
        views.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        views.toolbar.setOnMenuItemClickListener {
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
                else -> {
                }
            }
        }
    }

    private fun manageEncounter(data: Encounter) {
        data.playerList.forEachIndexed { i, player ->
            when (i) {
                0 -> manageUser(player, views.firstPlayer.playerName)
                1 -> manageUser(player, views.secondPlayer.playerName)
                2 -> manageUser(player, views.thirdPlayer.playerName)
                3 -> manageUser(player, views.fourthPlayer.playerName)
            }
        }
        data.encounterResults.forEachIndexed { i, encounterResult ->
            when (i) {
                0 -> manageEncounter(encounterResult, views.firstPlayer)
                1 -> manageEncounter(encounterResult, views.secondPlayer)
                2 -> manageEncounter(encounterResult, views.thirdPlayer)
                3 -> manageEncounter(encounterResult, views.fourthPlayer)
            }
        }
    }

    private fun manageUser(
        player: Player,
        playerNameTextView: TextView
    ) {
        playerNameTextView.text = player.name
    }

    private fun manageEncounter(
        encounter: EncounterResult,
        encounterLayout: IncludeEncounterResultBinding
    ) {
        encounterLayout.matchPointsText.setText(encounter.matchPoints)
        encounterLayout.victoryPointsText.setText(encounter.victoryPoints)
        encounterLayout.bigCavalryArmyPointsText.setText(encounter.cavalryArmyPoints)
        encounterLayout.bigTradingRoutePointsText.setText(encounter.bigTradeRoutePoints)
        encounterLayout.numberOfCitiesText.setText(encounter.numberOfCities)
    }

    private fun saveResult() =
        encounterViewModel.saveResult(
            Encounter(
                id = encounterId,
                encounterResults = createEncounters(),
                playerList = listOf()
            )
        )

    private fun createEncounters(): List<EncounterResult> =
        ArrayList<EncounterResult>().apply {
            (1..4).forEach { i ->
                add(
                    when (i) {
                        1 -> createEncounterResult(views.firstPlayer)
                        2 -> createEncounterResult(views.secondPlayer)
                        3 -> createEncounterResult(views.thirdPlayer)
                        4 -> createEncounterResult(views.fourthPlayer)
                        else -> EncounterResult()
                    }
                )
            }
        }

    private fun createEncounterResult(encounterResultBinding: IncludeEncounterResultBinding) =
        EncounterResult(
            matchPoints = encounterResultBinding.matchPointsText.text.toString().toInt(),
            victoryPoints = encounterResultBinding.victoryPointsText.text.toString().toInt(),
            cavalryArmyPoints = encounterResultBinding.bigCavalryArmyPointsText.text.toString()
                .toInt(),
            bigTradeRoutePoints = encounterResultBinding.bigTradingRoutePointsText.text.toString()
                .toInt(),
            numberOfCities = encounterResultBinding.numberOfCitiesText.text.toString().toInt()
        )

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TAG = "EncounterFragment"
        private const val EXTRA_ENCOUNTER_ID = "extraEncounterId"

        fun newInstance(id: String) =
            EncounterFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_ENCOUNTER_ID, id)
                }
            }
    }
}
