package com.example.healthanalyzers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthanalyzers.R
import com.example.healthanalyzers.bean.Devices

class DevicesAdapter(val context: Context, var devicesList: List<Devices>) :
    RecyclerView.Adapter<DevicesAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageDevice: ImageView = view.findViewById(R.id.image_device)
        val tvDeviceName: TextView = view.findViewById(R.id.tv_device_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.device_item, parent, false)
        val  viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {

        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = devicesList[position]
        holder.tvDeviceName.text = device.name
        Glide.with(context).load(device.imageId).into(holder.imageDevice)
    }

    override fun getItemCount() = devicesList.size ?: 0
}