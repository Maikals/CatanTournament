package com.example.catantournament.ui.matches

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.catantournament.R
import com.google.android.material.textfield.TextInputLayout

class CreateTournamentDialogFragment : DialogFragment() {
    private lateinit var roundSpinner: Spinner

    private fun positiveButtonClicked(dialog: DialogInterface) {
        targetFragment?.onActivityResult(
            targetRequestCode,
            AppCompatActivity.RESULT_OK,
            Intent().apply {
                putExtra(EXTRA_NUMBER_OF_ROUNDS, (roundSpinner.selectedItem as String).toInt())
            })
        dialog.dismiss()
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
                    .inflate(R.layout.dialog_fragment_create_tournament, null).apply {
                        inflateViews()
                        setSpinner()
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

    private fun setSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.round_number_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            roundSpinner.adapter = adapter
        }
    }

    private fun View.inflateViews() {
        roundSpinner = findViewById(R.id.round_number_spinner)
    }

    companion object {

        fun newInstance() = CreateTournamentDialogFragment()

        const val TAG = "CreateTournamentDialogFragment"
        const val EXTRA_NUMBER_OF_ROUNDS = "extraNumberOfRounds"
        const val REQUEST_CODE = 100
    }
}