package com.example.healthanalyzers.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthanalyzers.R
import com.example.healthanalyzers.bean.Devices
import com.example.healthanalyzers.utils.DBUtils
import com.example.healthanalyzers.utils.MyItemTouchCallback
import kotlin.concurrent.thread

class DevicesAdapter(
    val context: Context,
    private var devicesList: ArrayList<Devices>,
    val account: Int
) :
    RecyclerView.Adapter<DevicesAdapter.ViewHolder>(), MyItemTouchCallback.ItemTouchResultCallback {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageDevice: ImageView = view.findViewById(R.id.image_device)
        val tvDeviceName: TextView = view.findViewById(R.id.tv_device_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.device_item, parent, false)
        val viewHolder = ViewHolder(view)
        // 测试点击事件是否准确
        viewHolder.itemView.setOnClickListener {
            Log.d(
                "DevicesAdapter",
                "position at recyclerView is ${viewHolder.adapterPosition}, and the content is ${devicesList[viewHolder.adapterPosition].name}"
            )
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = devicesList[position]
        holder.tvDeviceName.text = device.name
        Glide.with(context).load(device.imageId).into(holder.imageDevice)
    }

    override fun getItemCount() = devicesList.size ?: 0

    // 移动 item 所需要实现的方法
    override fun onItemMove(startPosition: Int, endPosition: Int) {
        TODO("Not yet implemented")
    }

    // 删除 item 所需要实现的方法
    override fun onItemDelete(position: Int) {
        val sql =
            "DELETE FROM devices WHERE userName = $account && id = ${devicesList[position].id}"
        devicesList.removeAt(position)
        // 提醒recyclerView有item被删除，需要对视图进行更新
        notifyItemRemoved(position)
        // 更新数据库
        updateDateBase(sql)
    }

    // 在删除对应的设备之后，需要对数据库进行更新
    fun updateDateBase(sql: String) {
        thread {
            val connection = DBUtils.getConnection()
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            DBUtils.close(connection, statement)
        }
    }
}