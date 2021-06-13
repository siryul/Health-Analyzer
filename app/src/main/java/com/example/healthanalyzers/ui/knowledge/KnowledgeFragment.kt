package com.example.healthanalyzers.ui.knowledge

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.healthanalyzers.R

class KnowledgeFragment : Fragment() {

    private lateinit var knowledgeViewModel: KnowledgeViewModel
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
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
        webView = root.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        var mIsRedirect: Boolean = false //用来标识链接是否重定向
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Page loading started
                // Do something
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Page loading finished
                // Enable disable back forward button
            }

        }
        webView.loadUrl("https://www.kepuchina.cn/health/")

        // 实现点击返回不是返回到首页，而是回到桌面
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })

        return root
    }

    override fun onResume() {
        super.onResume()
//        immersionBar {
//            statusBarColor(R.color.actionbar_color)
//            fitsSystemWindows(true)
//        }


    }



     fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return true
    }
}