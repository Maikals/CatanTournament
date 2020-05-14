package com.example.catantournament.player_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catantournament.R
import com.example.domain.entities.Player
import com.example.domain.entities.Result
import kotlinx.android.synthetic.main.fragment_player_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class PlayerListFragment : Fragment() {
    private val playerListViewModel: PlayerListViewModel by viewModel()
    private val linearLayoutManager by lazy { LinearLayoutManager(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_player_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        player_list.layoutManager = linearLayoutManager
        observeData()
    }

    private fun observeData() {
        playerListViewModel.playerListLiveData.observe(this, Observer {
            when (it) {
                is Result.Success -> Log.d(TAG, it.data.toString())
            }
        })
    }

    private fun setOnClickListeners() {
        fab.setOnClickListener {
            EnterPlayerDialogFragment().apply {
                setTargetFragment(this@PlayerListFragment, EnterPlayerDialogFragment.REQUEST_CODE)
            }.show(requireFragmentManager(), EnterPlayerDialogFragment.TAG)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            data?.also {
                val player = Player(
                    name = it.getStringExtra(EnterPlayerDialogFragment.EXTRA_RESULT_NAME)!!,
                    nick = it.getStringExtra(EnterPlayerDialogFragment.EXTRA_RESULT_NICK)!!
                )
                playerListViewModel.addPlayer(player)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.add_player -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val TAG = "PlayerListFragment"

        fun newInstance() = PlayerListFragment()
    }
}
