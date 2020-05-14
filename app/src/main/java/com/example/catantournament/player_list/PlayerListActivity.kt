package com.example.catantournament.player_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PlayerListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentContent()
    }

    private fun setFragmentContent() {
        val contentId = android.R.id.content
        val fragment = PlayerListFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(contentId, fragment, PlayerListFragment.TAG).commit()
    }
}
