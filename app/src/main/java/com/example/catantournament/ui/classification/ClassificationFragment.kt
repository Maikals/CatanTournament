package com.example.catantournament.ui.classification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catantournament.R
import com.example.catantournament.databinding.FragmentClassificationBinding
import com.example.catantournament.extensions.observe
import com.example.domain.entities.Player
import com.example.domain.entities.Result.Success
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassificationFragment : Fragment(R.layout.fragment_classification) {
    private val classificationViewModel: ClassificationViewModel by viewModel()
    private val linearLayoutManager by lazy { LinearLayoutManager(requireContext()) }
    private var binding: FragmentClassificationBinding? = null
    private val views get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClassificationBinding.bind(view)
        setRecyclerView()
        observeData()
    }

    private fun observeData() {
        observe(classificationViewModel.allPlayersLiveData) {
            when (it) {
                is Success -> manageClassificationList(it.data)
            }
        }
    }

    private fun manageClassificationList(data: List<Player>) {
        views.classificationList.adapter = ClassificationListAdapter(data)
    }

    private fun setRecyclerView() {
        views.classificationList.layoutManager = linearLayoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TAG = "ClassificationFragment"
        fun newInstance() = ClassificationFragment()
    }
}
