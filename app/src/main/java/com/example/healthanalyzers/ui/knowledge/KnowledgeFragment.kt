package com.example.healthanalyzers.ui.knowledge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.healthanalyzers.R

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
        val textView: TextView = root.findViewById(R.id.text_notifications)
        knowledgeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}