package com.example.catantournament.ui.matches

import android.os.Bundle
import androidx.fragment.app.Fragment

class RoundFragment : Fragment() {

    companion object {
        const val TAG = "RoundFragment"
        fun newInstance(roundId: Long) = RoundFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_ROUND_ID, roundId)
            }
        }

        private const val EXTRA_ROUND_ID = "extraRoundId"
    }
}