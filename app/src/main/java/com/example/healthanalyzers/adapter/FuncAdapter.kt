package com.example.healthanalyzers.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.bean.Func
import com.example.healthanalyzers.R

class FuncAdapter(val funcList: List<Func>) :
    RecyclerView.Adapter<FuncAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val funcImage: ImageView = view.findViewById(R.id.funcImage)
        val funcName: TextView = view.findViewById(R.id.funcName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.func_item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            when (position) {
                // 心率
                0 -> {
                    Toast.makeText(parent.context, "1", Toast.LENGTH_SHORT).show()
                }
                // 睡眠
                1 -> {
                    Toast.makeText(parent.context, "2", Toast.LENGTH_SHORT).show()

                }
                // 身高
                2 -> {
                    Toast.makeText(parent.context, "3", Toast.LENGTH_SHORT).show()

                }
                // 血氧
                3 -> {
                    Toast.makeText(parent.context, "4", Toast.LENGTH_SHORT).show()

                }
                // 血压
                4 -> {
                }
                // 血糖
                5 -> {
                }
                // 体重
                6 -> {
                }
                // 体温
                7 -> {
                }
                else -> {
                    Log.d("FuncAdapter", "点击事件出错")
                }
            }
        }
        return viewHolder
    }

    override fun getItemCount() = funcList.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val func = funcList[position]
        holder.funcImage.setImageResource(func.imageId)
        holder.funcName.text = func.name
    }
}