package com.nafis.skilltestprodia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nafis.skilltestprodia.R
import com.nafis.skilltestprodia.database.SearchHistory
import com.nafis.skilltestprodia.databinding.FragmentHomeBinding
import com.nafis.skilltestprodia.response.ResultsItem
import com.nafis.skilltestprodia.viewModel.ArticleViewModel
import com.nafis.skilltestprodia.viewModel.SearchHistoryViewModel
import com.nafis.skilltestprodia.viewModel.ViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var homeAdapter: HomeAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var searchHistoryViewModel: SearchHistoryViewModel

    private var selectedCategory: String = ""
//    private var searchHistory: SearchHistory? = null
    private lateinit var searchHistory: SearchHistory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        articleViewModel = ViewModelProvider(this)[ArticleViewModel::class.java]
        searchHistoryViewModel = obtainViewModel(requireActivity() as AppCompatActivity)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleViewModel.getArticles()
        articleViewModel.articlesData.observe(viewLifecycleOwner) {articleData ->
            showRvArticles(articleData)
        }

        articleViewModel.categoryData.observe(viewLifecycleOwner) {
            setupSpinner(it)
        }

        val svArticle = binding.svArticle
        svArticle.queryHint = "Search"
        svArticle.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                articleViewModel.findArticle(query.toString())
                articleViewModel.articleFindData.observe(viewLifecycleOwner) {
                    showRvArticles(it)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                articleViewModel.findArticle(query.toString())
                articleViewModel.articleFindData.observe(viewLifecycleOwner) {
                    showRvArticles(it)
                }

                if (query != null) {
                    saveHistory(query)
                }
                return true
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): SearchHistoryViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[SearchHistoryViewModel::class.java]
    }

    private fun saveHistory(query: String) {
        if (query.isNotEmpty()) {
            searchHistory = SearchHistory(name = query)
            searchHistoryViewModel.insert(searchHistory)
        }
    }

    private fun setupSpinner(categories: List<String>) {
        val allCategories = listOf("All Categories") + categories
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, allCategories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategory.adapter = adapter

        binding.spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCategory = if (position == 0) "" else parent?.getItemAtPosition(position).toString()
                articleViewModel.filterArticles(selectedCategory)
                articleViewModel.articleFilteredData.observe(viewLifecycleOwner) {
                    showRvArticles(it)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedCategory = ""
                articleViewModel.filterArticles(selectedCategory)
            }
        }
    }

    private fun showRvArticles(articleData: List<ResultsItem>) {
        homeAdapter = HomeAdapter(articleData)
        binding.rvHome.apply {
            setHasFixedSize(true)
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        homeAdapter.setOnItemClickCallBack(object : HomeAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ResultsItem) {
                val id = data.id

                val bundle = Bundle().apply {
                    putInt("id", id)
                }

                val detailArticleFragment = DetailArticleFragment().apply {
                    arguments = bundle
                }
                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction().apply {
                    replace(
                        R.id.main_frame_container,
                        detailArticleFragment,
                        DetailArticleFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                    commit()
                }

            }
        })
    }

    companion object {

    }
}