package com.example.news.UI


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.news.Adapter.NewsAdapter
import com.example.news.R
import com.example.news.Repository.NewsRepository
import com.example.news.ViewModel.NewsViewModel
import com.example.news.ViewModel.NewsViewModelProviderFactory
import com.example.news.databinding.FragmentArticleBinding

class ArticleFragment :Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel

//    val args: ArticleFragmentArgs by navArgs()
    lateinit var articleBinding: FragmentArticleBinding
    lateinit var newsAdapter : NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        articleBinding = FragmentArticleBinding.inflate(inflater,container,false)   // for fragments
        val view = articleBinding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsRepository= NewsRepository(/*ArticleDatabase.createDatabase(requireContext())*/)
        val viewModelProviderFactory= NewsViewModelProviderFactory(newsRepository)
        viewModel= ViewModelProvider(this,viewModelProviderFactory)[NewsViewModel::class.java]
//
//        val article = args.article
//        articleBinding.webView.apply {
//            webViewClient = WebViewClient()
//            loadUrl(article.url)
//        }

    }

}