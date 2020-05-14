package com.example.catantournament.ui.classification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catantournament.R
import com.example.catantournament.extensions.observe
import com.example.domain.entities.Player
import com.example.domain.entities.Result.Success
import kotlinx.android.synthetic.main.fragment_classification.*
import org.koin.android.viewmodel.ext.android.viewModel

class ClassificationFragment : Fragment() {
    private val classificationViewModel: ClassificationViewModel by viewModel()
    private val linearLayoutManager by lazy { LinearLayoutManager(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_classification, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        classification_list.adapter = ClassificationListAdapter(data)
    }

    private fun setRecyclerView() {
        classification_list.layoutManager = linearLayoutManager
    }

    companion object {
        const val TAG = "ClassificationFragment"
        fun newInstance() = ClassificationFragment()
    }
}