package com.example.catantournament.ui.encounter

import android.os.Bundle
import com.example.catantournament.ui.BaseActivity

class EncounterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getLongExtra(EXTRA_ENCOUNTER_ID, 0)
        setFragmentContent(EncounterFragment.newInstance(id), EncounterFragment.TAG)
    }

    companion object {
        const val EXTRA_ENCOUNTER_ID = "extraEncounterId"
    }
}
