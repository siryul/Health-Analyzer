package com.example.healthanalyzers.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.bean.Func
import com.example.healthanalyzers.R
import com.example.healthanalyzers.adapter.FuncAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ktx.immersionBar

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val funcList = ArrayList<Func>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)

        recyclerView = root.findViewById(R.id.recyclerView)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            // textView.text = it
        })
        // 初始化功能列表数据
        initFunc()
        // val layoutManager = LinearLayoutManager(context)
        val layoutManager = GridLayoutManager(context, 3)
        recyclerView.layoutManager = layoutManager
        val adapter = FuncAdapter(funcList)
        recyclerView.adapter = adapter
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun initFunc() {
        funcList.add(Func("心率", R.drawable.ic_heart_rate_64))
        funcList.add(Func("睡眠", R.drawable.ic_psychological_sleep_quality_64))
        funcList.add(Func("身高", R.drawable.ic_high_64))
        funcList.add(Func("血氧饱和度", R.drawable.ic_blood_oxygen_64))
        funcList.add(Func("血压", R.drawable.ic_blood_pressure_64dp))
        funcList.add(Func("血糖", R.drawable.ic_blood_suger_64dp))
        funcList.add(Func("体重", R.drawable.ic_bodyfat_64))
        funcList.add(Func("体温", R.drawable.ic_body_temperature_64dp))
    }

    override fun onResume() {
        super.onResume()
        immersionBar {
            statusBarColor(R.color.actionbar_color)
            fitsSystemWindows(true)
        }
    }
}