package com.example.healthanalyzers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.R
import com.example.healthanalyzers.bean.Mine

class MineAdapter(val mineList: List<Mine>) : RecyclerView.Adapter<MineAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tv_option = view.findViewById<TextView>(R.id.tv_option)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.me_item, parent, false)
        val viewHolder = ViewHolder(view)
        // 注册点击事件
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val mine = mineList[position]
            Toast.makeText(parent.context, "你点击了${mine.optionName}", Toast.LENGTH_SHORT).show()
        }

        return viewHolder
    }

    override fun getItemCount() = mineList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mine = mineList[position]
        holder.tv_option.text = mine.optionName
    }
}