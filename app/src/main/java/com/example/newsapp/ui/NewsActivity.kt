package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.database.NewsDatabase
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.Repositroy.NewsRepository
import com.example.newsapp.ui.viewModel.NewsModelFactory
import com.example.newsapp.ui.viewModel.NewsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.NavHostFragment

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
     lateinit var newsViewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Log.d("TAGY", "Binding article: ")
        val repository=NewsRepository(NewsDatabase(this))
        val viewModelFactory=NewsModelFactory(repository)
        newsViewModel= ViewModelProvider(this,viewModelFactory)[NewsViewModel::class.java]
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        val navController=this.findNavController(R.id.newsNavHostFragment)
        binding.bottomNavigationView.setupWithNavController(navController)



    }
}