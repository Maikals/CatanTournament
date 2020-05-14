package com.example.catantournament.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {

    protected fun setFragmentContent(fragment: Fragment, tag: String) {
        val contentId = android.R.id.content
        supportFragmentManager.beginTransaction()
            .replace(contentId, fragment, tag).commit()
    }
}