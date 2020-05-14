package com.example.catantournament.ui.main_activity

import android.os.Bundle
import com.example.catantournament.ui.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentContent(MainFragment.newInstance(), MainFragment.TAG)
    }
}