package com.example.healthanalyzers.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate


import com.example.healthanalyzers.R;
import com.example.healthanalyzers.adapter.MineAdapter
import com.example.healthanalyzers.bean.Func
import com.example.healthanalyzers.bean.Mine
import com.gyf.immersionbar.ktx.immersionBar


class MineFragment : androidx.fragment.app.Fragment() {
    private lateinit var mineViewModel: MineViewModel
    private val mineList = ArrayList<Mine>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var tv_information: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mineViewModel =
            ViewModelProvider(this).get(MineViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mine, container, false)
//        val textView: TextView = root.findViewById(R.id.text_mine)
//        mineViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        recyclerView = root.findViewById(R.id.recyclerView_me)
//        tv_information = root.findViewById(R.id.tv_information)
        init()
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        val adapter = MineAdapter(mineList)
        recyclerView.adapter = adapter
        // recyclerView.addItemDecoration(RecyclerView.ItemDecoration)

        return root
    }

    private fun init() {
        mineList.add(Mine("用户名"))
        mineList.add(Mine("昵称"))
        mineList.add(Mine("性别"))
        mineList.add(Mine("出生日期"))
        mineList.add(Mine("身高"))
        mineList.add(Mine("体重"))
    }

    override fun onResume() {
        super.onResume()
        immersionBar {
            statusBarColor(R.color.actionbar_color)
        }
    }
}
