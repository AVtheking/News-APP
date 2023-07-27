package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.databinding.FragmentBreakinNewsBinding
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.viewModel.NewsViewModel
import com.example.newsapp.utils.model.Article


class ArticleFragment : Fragment(R.layout.fragment_article) {
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsadapter: NewsAdapter
    private var _binding: FragmentArticleBinding?=null

    private val binding get()=_binding!!
    val args:ArticleFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? NewsActivity)?.hideBottomNavigationView()
        newsViewModel=(activity as NewsActivity).newsViewModel


        var article=args.article
        binding.webView.apply {
            webViewClient=WebViewClient()
            loadUrl(article.url)
        }
        binding.fab.setOnClickListener {
            newsViewModel.upsert(article)
            Toast.makeText(requireContext(),"Article Saved Succesfully",Toast.LENGTH_LONG
            ).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as NewsActivity)?.showBottomNavigationView()
    }


}