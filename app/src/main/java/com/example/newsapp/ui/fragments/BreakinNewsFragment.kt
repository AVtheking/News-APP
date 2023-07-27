package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.databinding.FragmentBreakinNewsBinding
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.viewModel.NewsViewModel
import com.example.newsapp.utils.Resource

class BreakinNewsFragment : Fragment(R.layout.fragment_breakin_news) {
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsadapter: NewsAdapter
    private var _binding:FragmentBreakinNewsBinding?=null
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
        _binding = FragmentBreakinNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel=(activity as NewsActivity).newsViewModel
        setUpRecyclerView()
        requireActivity().title="News"
        newsadapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
           findNavController().navigate(
                R.id.action_breakinNewsFragment_to_articleFragment,
                bundle
            )
        }
        newsViewModel.getBreakingNews("in").observe(viewLifecycleOwner, Observer{response->
            when(response)
            {
                is Resource.Success->{
                    hideProgreesBar()
                    response.data?.let {
                        newsadapter.differ.submitList(it.articles.toList())


                    }
                }
                is Resource.Error->{
                    hideProgreesBar()
                    response.messsage?.let {
                        Log.e(TAG,"An error occured $it")
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility=View.VISIBLE

    }

    private fun hideProgreesBar() {
        binding.paginationProgressBar.visibility=View.INVISIBLE

    }


    private fun setUpRecyclerView() {
        newsadapter= NewsAdapter()
        binding.rvBreakingNews.apply {
           adapter=newsadapter
            layoutManager=LinearLayoutManager(activity)

        }

    }

}