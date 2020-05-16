package com.example.catantournament.ui.player_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catantournament.R
import com.example.catantournament.extensions.observe
import com.example.catantournament.interfaces.PlayerListMenuListener
import com.example.catantournament.ui.player_list.EnterPlayerDialogFragment.From
import com.example.catantournament.ui.player_list.EnterPlayerDialogFragment.From.ADD
import com.example.catantournament.ui.player_list.EnterPlayerDialogFragment.From.MODIFY
import com.example.domain.entities.Player
import com.example.domain.entities.Result.Success
import kotlinx.android.synthetic.main.fragment_player_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.UUID

class PlayerListFragment : Fragment(), PlayerListMenuListener.ScreenMenuListener {
    private val playerListViewModel: PlayerListViewModel by viewModel()
    private val linearLayoutManager by lazy { LinearLayoutManager(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_player_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        setPlayerList()
        observeData()
    }

    private fun setPlayerList() {
        registerForContextMenu(player_list)
        player_list.layoutManager = linearLayoutManager
    }

    private fun observeData() {
        observe(playerListViewModel.playerListLiveData) {
            when (it) {
                is Success -> managePlayerList(it.data)
            }
        }
    }

    private fun managePlayerList(playerList: List<Player>) {
        player_list.adapter = PlayerListAdapter(playerList, this)
    }

    private fun setOnClickListeners() {
        fab.setOnClickListener {
            openEnterDialog(ADD)
        }
    }

    private fun openEnterDialog(from: From, player: Player? = null) {
        EnterPlayerDialogFragment.newInstance(from, player).apply {
            setTargetFragment(this@PlayerListFragment, EnterPlayerDialogFragment.REQUEST_CODE)
        }.show(parentFragmentManager, EnterPlayerDialogFragment.TAG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            data?.run {
                when (getSerializableExtra(EnterPlayerDialogFragment.EXTRA_FROM)) {
                    ADD -> addPlayer()
                    MODIFY -> modifyPlayer()
                }
            }
        }
    }

    private fun Intent.modifyPlayer() {
        val player = Player(
            UUID.fromString(getStringExtra(EnterPlayerDialogFragment.EXTRA_ID)),
            getStringExtra(EnterPlayerDialogFragment.EXTRA_NAME)!!,
            getStringExtra(EnterPlayerDialogFragment.EXTRA_NICK)!!
        )
        playerListViewModel.modifyPlayer(player)
    }

    private fun Intent.addPlayer() {
        val player = Player(
            name = getStringExtra(EnterPlayerDialogFragment.EXTRA_NAME)!!,
            nick = getStringExtra(EnterPlayerDialogFragment.EXTRA_NICK)!!
        )
        playerListViewModel.addPlayer(player)
    }

    override fun modify(player: Player) = openEnterDialog(MODIFY, player)

    override fun delete(player: Player) = playerListViewModel.deletePlayer(player.id)

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
