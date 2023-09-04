package com.example.news.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
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
import com.example.news.databinding.FragmentSearchNewsBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var searchNewsBinding: FragmentSearchNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        searchNewsBinding = FragmentSearchNewsBinding.inflate(inflater,container,false)   // for fragments
        val view = searchNewsBinding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsRepository= NewsRepository()
        val viewModelProviderFactory= NewsViewModelProviderFactory(newsRepository)
        viewModel= ViewModelProvider(this,viewModelProviderFactory)[NewsViewModel::class.java]


        setupRecyclerView()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }
        var job : Job? = null
        searchNewsBinding.etSearch.addTextChangedListener{
                editable-> job?.cancel()
                job = MainScope().launch {
                    delay(500L)
                    editable?.let{
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }
        viewModel.searchNews.observe(viewLifecycleOwner,  Observer {response->
            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data?.let{newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)

                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    response.message?.let{
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }

        })


    }
    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter()
        searchNewsBinding.rvSearchNews.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,true)
        }
    }
    private fun  hideProgressBar(){
        searchNewsBinding.paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun  showProgressBar(){
        searchNewsBinding.paginationProgressBar.visibility = View.VISIBLE
    }

}