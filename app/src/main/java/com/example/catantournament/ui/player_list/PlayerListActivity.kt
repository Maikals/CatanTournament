package com.example.catantournament.ui.player_list

import android.os.Bundle
import com.example.catantournament.ui.BaseActivity

class PlayerListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentContent(PlayerListFragment.newInstance())
    }

}
