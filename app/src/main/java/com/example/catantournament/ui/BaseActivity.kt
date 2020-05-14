package com.example.catantournament.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.catantournament.ui.player_list.PlayerListFragment

abstract class BaseActivity : AppCompatActivity() {

    protected fun setFragmentContent(fragment: Fragment) {
        val contentId = android.R.id.content
        supportFragmentManager.beginTransaction()
            .replace(contentId, fragment, PlayerListFragment.TAG).commit()
    }
}