package com.example.catantournament.ui.player_list

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.catantournament.R
import com.example.domain.entities.Player
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EnterPlayerDialogFragment : DialogFragment() {

    private lateinit var playerNameHint: TextInputLayout
    private lateinit var playerNameEditText: TextInputEditText
    private lateinit var playerNickHint: TextInputLayout
    private lateinit var playerNickEditText: TextInputEditText

    private fun positiveButtonClicked(dialog: DialogInterface) {
        if (!playerNameHint.isErrorEnabled && !playerNickHint.isErrorEnabled) {
            targetFragment?.onActivityResult(
                targetRequestCode,
                AppCompatActivity.RESULT_OK,
                Intent().apply {
                    if (requireArguments().getSerializable(EXTRA_FROM) == From.MODIFY) {
                        putExtra(EXTRA_ID, requireArguments().getLong(EXTRA_ID))
                        putExtra(EXTRA_FROM, From.MODIFY)
                    } else putExtra(EXTRA_FROM, From.ADD)
                    putExtra(EXTRA_NAME, playerNameEditText.text.toString())
                    putExtra(EXTRA_NICK, playerNickEditText.text.toString())
                })
            dialog.dismiss()
        }
    }

    private fun showError(hint: TextInputLayout, @StringRes errorText: Int) {
        hint.error = getString(errorText)
        hint.isErrorEnabled = true
    }

    private fun negativeButtonClicked(dialog: DialogInterface) {
        targetFragment?.onActivityResult(REQUEST_CODE, AppCompatActivity.RESULT_CANCELED, null)
        dialog.dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setView(
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.dialog_fragment_enter_player, null).apply {
                        inflateViews()
                        setOnFocusListeners()
                        setText()
                    })
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton(android.R.string.cancel, null)
            .create().apply {
                setOnShowListener { dialogInterface ->
                    getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
                        positiveButtonClicked(
                            dialogInterface
                        )
                    }
                    getButton(Dialog.BUTTON_NEGATIVE).setOnClickListener {
                        negativeButtonClicked(
                            dialogInterface
                        )
                    }
                }
            }

    private fun setText() {
        playerNameEditText.setText(requireArguments().getString(EXTRA_NAME, ""))
        playerNickEditText.setText(requireArguments().getString(EXTRA_NICK, ""))
    }

    private fun setOnFocusListeners() {
        playerNameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) checkTextInput(
                playerNameHint,
                playerNameEditText,
                R.string.dialog_enter_player_name_error
            )
        }
        playerNickEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) checkTextInput(
                playerNickHint, playerNickEditText,
                R.string.dialog_enter_player_nick_error
            )
        }
    }

    private fun checkTextInput(
        input: TextInputLayout,
        textInput: TextInputEditText,
        @StringRes errorText: Int
    ) {
        if (textInput.text.isNullOrEmpty()) {
            showError(input, errorText)
        } else {
            input.isErrorEnabled = false
        }
    }

    private fun View.inflateViews() {
        playerNameHint = findViewById(R.id.player_name_hint)
        playerNameEditText = findViewById(R.id.player_name_edit_text)
        playerNickHint = findViewById(R.id.player_nick_hint)
        playerNickEditText = findViewById(R.id.player_nick_edit_text)
    }

    enum class From {
        ADD, MODIFY
    }

    companion object {

        fun newInstance(from: From, player: Player? = null) =
            EnterPlayerDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(EXTRA_FROM, from)
                    putLong(EXTRA_ID, player?.id ?: 0L)
                    putString(EXTRA_NAME, player?.name ?: "")
                    putString(EXTRA_NICK, player?.nick ?: "")
                }
            }

        const val TAG = "EnterPlayerDialogFragment"
        const val REQUEST_CODE = 100
        const val EXTRA_ID = "extraResultId"
        const val EXTRA_NAME = "extraResultName"
        const val EXTRA_NICK = "extraResultNick"
        const val EXTRA_FROM = "extraResultFrom"
    }
}