package com.example.healthanalyzers.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.R
import com.example.healthanalyzers.bean.Mine
import com.example.healthanalyzers.ui.mine.AlterNicknameActivity
import com.example.healthanalyzers.ui.mine.AlterUsernameActivity

class MineAdapter(val mineList: List<Mine>) : RecyclerView.Adapter<MineAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_option = view.findViewById<TextView>(R.id.tv_option)
        val tv_information = view.findViewById<TextView>(R.id.tv_information)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.me_item, parent, false)
        val viewHolder = ViewHolder(view)
        // 注册点击事件
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val mine = mineList[position]
            // Toast.makeText(parent.context, "你点击了${mine.optionName}", Toast.LENGTH_SHORT).show()
            when(position) {
                // 用户名选项
                0 -> {
                    // Toast.makeText(parent.context, "你点击了第一个", Toast.LENGTH_SHORT).show()
                    val intent =Intent(parent.context, AlterUsernameActivity::class.java)
                    parent.context.startActivity(intent)
                }
                // 昵称
                1 -> {
                    val intent =Intent(parent.context, AlterNicknameActivity::class.java)
                    parent.context.startActivity(intent)
                }
                // 性别
                2 -> {

                }
                // 年龄
                3 -> {

                }
                else -> {}
            }
        }

        return viewHolder
    }

    override fun getItemCount() = mineList.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mine = mineList[position]
        holder.tv_option.text = mine.optionName
    }
}