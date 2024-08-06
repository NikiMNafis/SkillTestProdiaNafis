package com.nafis.skilltestprodia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.nafis.skilltestprodia.databinding.FragmentDetailArticleBinding
import com.nafis.skilltestprodia.viewModel.ArticleViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DetailArticleFragment : Fragment() {

    private var _binding: FragmentDetailArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var articleViewModel: ArticleViewModel

    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailArticleBinding.inflate(inflater, container, false)

        articleViewModel = ViewModelProvider(this)[ArticleViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = arguments?.getInt("id")

        articleViewModel.getDetailArticle(id)
        articleViewModel.detailArticleData.observe(viewLifecycleOwner) {
            val publishedAt = it.publishedAt
            val formatterInput = DateTimeFormatter.ISO_ZONED_DATE_TIME
            val zonedDateTime = ZonedDateTime.parse(publishedAt, formatterInput)

            val formatterOutput = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", Locale("id", "ID"))
            val formattedDate = zonedDateTime.format(formatterOutput)

            val firstSentence = getFirstSentence(it.summary)

            binding.tvTitle.text = it.title
            binding.tvPublishedAt.text = formattedDate
            binding.tvSummary.text = firstSentence
            if (!it.imageUrl.isNullOrEmpty()) {
                Glide.with(view.context)
                    .load(it.imageUrl)
                    .into(binding.ivArticle)
            }
        }
    }

    private fun getFirstSentence(text: String): String {
        return text.split(".")[0] + "."
    }

    companion object {

    }
}