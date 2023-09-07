package com.example.news.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.Adapter.NewsAdapter
import com.example.news.R
import com.example.news.Repository.NewsRepository
import com.example.news.UTIL.Resource
import com.example.news.ViewModel.NewsViewModel
import com.example.news.ViewModel.NewsViewModelProviderFactory
import com.example.news.databinding.FragmentBreakingNewsBinding


class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var newsAdapter: NewsAdapter
    lateinit var breakingNewsBinding: FragmentBreakingNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        breakingNewsBinding = FragmentBreakingNewsBinding.inflate(inflater, container, false)   // for fragments
        val view =breakingNewsBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val newsRepository = NewsRepository()
//        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
//        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment, bundle)
        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success->{ HideProgressBar()
                    response.data?.let{newsResponse -> newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error->{ HideProgressBar()
                    response.message?.let{
                    }
                }
                is Resource.Loading->{
                    ShowProgressBar()
                }
            }
        })
    }
    private fun HideProgressBar() {
        breakingNewsBinding.paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun ShowProgressBar() {
        breakingNewsBinding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter()
        breakingNewsBinding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,true)
        }
    }


}