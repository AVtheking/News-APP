package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.databinding.FragmentBreakinNewsBinding
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    lateinit var newsViewModel: NewsViewModel

    lateinit var newsadapter: NewsAdapter
    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!
    private val TAG = "BreakingNews"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as NewsActivity).newsViewModel
        setUpRecyclerView()
        newsadapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }
        newsViewModel.getArticles().observe(viewLifecycleOwner, Observer {
            newsadapter.differ.submitList(it)
        })
        val ItemTouchHelperCallBack=object:ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val postion=viewHolder.adapterPosition
                val article=newsadapter.differ.currentList[postion]
                newsViewModel.deleteArticle(article)
                Snackbar.make(view,"Successfully deleted Article",Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        newsViewModel.upsert(article)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(ItemTouchHelperCallBack).attachToRecyclerView(binding.rvSavedNews)

    }

    private fun setUpRecyclerView() {
        newsadapter= NewsAdapter()
        binding.rvSavedNews.apply {
            adapter=newsadapter
            layoutManager= LinearLayoutManager(activity)
        }

    }
}