package com.nafis.skilltestprodia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nafis.skilltestprodia.R
import com.nafis.skilltestprodia.database.SearchHistory
import com.nafis.skilltestprodia.databinding.FragmentSearchHistoryBinding
import com.nafis.skilltestprodia.response.ResultsItem
import com.nafis.skilltestprodia.viewModel.SearchHistoryViewModel
import com.nafis.skilltestprodia.viewModel.ViewModelFactory

class SearchHistoryFragment : Fragment() {

    private var _binding: FragmentSearchHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var searchHistoryViewModel: SearchHistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchHistoryBinding.inflate(inflater, container, false)

        searchHistoryViewModel = obtainViewModel(requireActivity() as AppCompatActivity)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchHistoryViewModel.getAllHistory().observe(requireActivity()) {
            if (it != null) {
                showRvArticles(it)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): SearchHistoryViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[SearchHistoryViewModel::class.java]
    }

    private fun showRvArticles(articleData: List<SearchHistory>) {
        historyAdapter = HistoryAdapter(articleData)
        binding.rvHistory.apply {
            setHasFixedSize(true)
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object {

    }
}