package com.example.healthanalyzers.ui.knowledge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.healthanalyzers.R
import com.gyf.immersionbar.ktx.immersionBar

class KnowledgeFragment : Fragment() {

    private lateinit var knowledgeViewModel: KnowledgeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        knowledgeViewModel =
            ViewModelProvider(this).get(KnowledgeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_knowledge, container, false)
//        val textView: TextView = root.findViewById(R.id.text_notifications)
//        knowledgeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        val webView = root.findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        // webView.loadUrl("http://www.kepuchina.cn/health/")
        webView.loadUrl("http://www.baidu.com")
        return root
    }

    override fun onResume() {
        super.onResume()
        immersionBar {
            statusBarColor(R.color.actionbar_color)
            fitsSystemWindows(true)
        }
    }
}