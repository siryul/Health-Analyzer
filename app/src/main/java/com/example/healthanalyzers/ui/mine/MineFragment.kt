package com.example.healthanalyzers.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


import com.example.healthanalyzers.R;


class MineFragment : androidx.fragment.app.Fragment() {
    private lateinit var mineViewModel: MineViewModel

    override fun onCreateView(
            inflater:LayoutInflater,
            container:ViewGroup?,
            savedInstanceState:Bundle?
    ): View? {
        mineViewModel =
                ViewModelProvider(this).get(MineViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mine, container, false)
        val textView: TextView = root.findViewById(R.id.text_mine)
        mineViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
