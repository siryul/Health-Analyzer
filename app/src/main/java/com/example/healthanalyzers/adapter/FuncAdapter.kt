package com.example.healthanalyzers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        return ViewHolder(view)
    }

    override fun getItemCount() = funcList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val func = funcList[position]
        holder.funcImage.setImageResource(func.imageId)
        holder.funcName.text = func.name
    }
}