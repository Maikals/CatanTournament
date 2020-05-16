package com.example.catantournament.ui.encounter

import android.os.Bundle
import androidx.fragment.app.Fragment

class EncounterFragment : Fragment() {

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
