package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.databinding.FragmentBreakinNewsBinding
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.viewModel.NewsViewModel
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsadapter: NewsAdapter
    private var _binding: FragmentSearchNewsBinding?=null
    private val binding get()=_binding!!
    private val TAG="BreakingNews"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSearchNewsBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel=(activity as NewsActivity).newsViewModel
        setUpRecyclerView()
        requireActivity().title="Search News"
        newsadapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }
        var job:Job?=null
        binding.etSearch.addTextChangedListener {
            job?.cancel()
            job= MainScope().launch {
                delay(500L)
                it?.let {
                    if(it.toString().isNotEmpty())
                        newsViewModel.getSearchNews(it.toString()).observe(viewLifecycleOwner, Observer {response->
                            when(response)
                            {
                                is Resource.Success->{
                                    hideProgressbar()
                                    response.data?.let {
                                        newsadapter.differ.submitList(it.articles)
                                    }
                                }
                                is Resource.Error->{
                                    hideProgressbar()
                                    response.messsage?.let{
                                        Log.e(TAG,"An error ocurred $it")
                                    }
                                }
                                is Resource.Loading->{
                                    showProgressbar()
                                }
                            }
                        })
                }
            }
        }


    }

    private fun showProgressbar() {
        binding.paginationProgressBar.visibility=View.VISIBLE
    }

    private fun hideProgressbar() {
        binding.paginationProgressBar.visibility=View.GONE
    }

    private fun setUpRecyclerView() {
        newsadapter= NewsAdapter()
        binding.rvSearchNews.apply {
            adapter=newsadapter
            layoutManager=LinearLayoutManager(activity)
        }
    }

}