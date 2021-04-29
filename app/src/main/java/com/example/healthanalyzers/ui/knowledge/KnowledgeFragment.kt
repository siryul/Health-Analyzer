package com.example.healthanalyzers.ui.knowledge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.healthanalyzers.R

class KnowledgeFragment : Fragment() {

    private lateinit var knowledgeViewModel: KnowledgeViewModel
    private lateinit var webView: WebView

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
        webView = root.findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://www.kepuchina.cn/health/")
        // webView.loadUrl("http://www.baidu.com")

        return root
    }

    override fun onResume() {
        super.onResume()
//        immersionBar {
//            statusBarColor(R.color.actionbar_color)
//            fitsSystemWindows(true)
//        }


    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
//            webView.goBack()
//            return true
//        }
//        return false
//    }



}