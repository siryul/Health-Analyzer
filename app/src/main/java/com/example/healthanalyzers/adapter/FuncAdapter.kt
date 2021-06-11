package com.example.healthanalyzers.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.R
import com.example.healthanalyzers.bean.Func
import com.example.healthanalyzers.measure.Measure
import com.example.healthanalyzers.utils.DBUtils
import com.google.android.material.internal.ContextUtils.getActivity
import com.thecode.aestheticdialogs.*
import java.sql.Timestamp
import java.util.*

/**
 * funcList: 代表所具有的功能列表
 * account：表示当前测量的数据是针对哪一个用户而言
 */

class FuncAdapter(val funcList: List<Func>, val account: String) :
    RecyclerView.Adapter<FuncAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val funcImage: ImageView = view.findViewById(R.id.funcImage)
        val funcName: TextView = view.findViewById(R.id.funcName)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.func_item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            //  实现具体的点击选项
            when (viewHolder.adapterPosition) {
                // 心率
                0 -> {
                    // 获取当前测量时间
                    val time = Timestamp(Date().time)
                    // 插入选项依次为：用户名，测量时间，心率
                    val sql =
                        "INSERT INTO heartrate (userName, time, heartRate) VALUES (?, ?, ?)"
                    println(time)
                    val value = Measure().getHeartRate()
                    // 获得数据库连接
                    Thread(
                        Runnable {
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, value)

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
                    getActivity(parent.context)?.let { it1 ->
                        AestheticDialog.Builder(it1, DialogStyle.RAINBOW, DialogType.SUCCESS)
                            .setTitle("心率检测结果")
                            .setMessage("心率：${value} cpm")
                            .setCancelable(false)
                            .setAnimation(DialogAnimation.SLIDE_DOWN)
                            .setGravity(Gravity.CENTER)
                            .setOnClickListener(object : OnDialogClickListener {
                                override fun onClick(dialog: AestheticDialog.Builder) {
                                    dialog.dismiss()
                                }
                            }).show()
                    }
                    Log.d("FuncAdapter", "测量时间为：$time")
                }
                // 睡眠
                1 -> {
                    // 获取当前测量时间
                    val time = Timestamp(Date().time)
                    // 插入选项依次为：用户名，测量时间，睡眠质量(暂时由睡眠时间代替)
                    val sql =
                        "INSERT INTO sleepquality (userName, time, quality) VALUES (?, ?, ?)"
                    // println(time)
                    val value = Measure().getPsySleepQuality()
                    Thread(
                        Runnable {
                            // 获得数据库连接
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, value)

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
                    getActivity(parent.context)?.let { it1 ->
                        AestheticDialog.Builder(it1, DialogStyle.RAINBOW, DialogType.SUCCESS)
                            .setTitle("睡眠质量检测结果")
                            .setMessage("睡眠时间：${value} h")
                            .setCancelable(false)
                            .setAnimation(DialogAnimation.SLIDE_DOWN)
                            .setGravity(Gravity.CENTER)
                            .setOnClickListener(object : OnDialogClickListener {
                                override fun onClick(dialog: AestheticDialog.Builder) {
                                    dialog.dismiss()
                                }
                            }).show()
                    }
                }
                // 身高
                2 -> {
                    // 获取当前测量时间
                    val time = Timestamp(Date().time)
                    // 插入选项依次为：用户名，测量时间，身高
                    val sql =
                        "INSERT INTO high (userName, time, high) VALUES (?, ?, ?)"
                    // println(time)
                    val value = Measure().getHigh()
                    Thread(
                        Runnable {
                            // 获得数据库连接
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, value)

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
                    getActivity(parent.context)?.let { it1 ->
                        AestheticDialog.Builder(it1, DialogStyle.RAINBOW, DialogType.SUCCESS)
                            .setTitle("身高测量结果")
                            .setMessage("身高：$value cm")
                            .setCancelable(false)
                            .setAnimation(DialogAnimation.SLIDE_DOWN)
                            .setGravity(Gravity.CENTER)
                            .setOnClickListener(object : OnDialogClickListener {
                                override fun onClick(dialog: AestheticDialog.Builder) {
                                    dialog.dismiss()
                                }
                            }).show()
                    }
                }
                // 血氧
                3 -> {
                    // 获取当前测量时间
                    val time = Timestamp(Date().time)
                    // 插入选项依次为：用户名，测量时间，动脉血，静脉血氧饱和度
                    val sql =
                        "INSERT INTO bloodoxygen (userName, time, ArterialBloodOxygen, VenousBloodOxygen) VALUES (?, ?, ?, ?)"
                    // println(time)
                    // 获得数据库连接
                    val arterial = Measure().getArterialBloodOxygen()
                    val venous = Measure().getVenousBloodOxygen()
                    Thread(
                        Runnable {
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, arterial)
                            preparedStatement.setObject(4, venous)

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
                    getActivity(parent.context)?.let { it1 ->
                        AestheticDialog.Builder(it1, DialogStyle.RAINBOW, DialogType.SUCCESS)
                            .setTitle("血氧量检测结果")
                            .setMessage("动脉血氧量：${arterial}mL/L\n静脉血氧量：${venous}mL/L")
                            .setCancelable(false)
                            .setAnimation(DialogAnimation.SLIDE_DOWN)
                            .setGravity(Gravity.CENTER)
                            .setOnClickListener(object : OnDialogClickListener {
                                override fun onClick(dialog: AestheticDialog.Builder) {
                                    dialog.dismiss()
                                }
                            }).show()
                    }
                }
                // 血压
                4 -> {
                    // 获取当前测量时间
                    val time = Timestamp(Date().time)
                    // 插入选项依次为：用户名，测量时间，收缩压，舒张压
                    val sql =
                        "INSERT INTO bloodpressure (userName, time, systolicPressure, diastolicPressure) VALUES (?, ?, ?, ?)"
                    // println(time)
                    val systolic = Measure().getSystolicPressure()
                    val diastolic = Measure().getDiastolicPressure()
                    // 获得数据库连接
                    Thread(
                        Runnable {
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, systolic)
                            preparedStatement.setObject(4, diastolic)

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
                    getActivity(parent.context)?.let { it1 ->
                        AestheticDialog.Builder(it1, DialogStyle.RAINBOW, DialogType.SUCCESS)
                            .setTitle("血压测量结果")
                            .setMessage("收缩压：$systolic mmHg\n舒张压：$diastolic mmHg")
                            .setCancelable(false)
                            .setAnimation(DialogAnimation.SLIDE_DOWN)
                            .setGravity(Gravity.CENTER)
                            .setOnClickListener(object : OnDialogClickListener {
                                override fun onClick(dialog: AestheticDialog.Builder) {
                                    dialog.dismiss()
                                }
                            }).show()
                    }
                }
                // 血糖
                5 -> {
                    // 获取当前测量时间
                    val time = Timestamp(Date().time)
                    // 插入选项依次为：用户名，测量时间，血糖
                    val sql =
                        "INSERT INTO bloodsugar (userName, time, bloodSugar) VALUES (?, ?, ?)"
                    // println(time)
                    val bloodSugar = Measure().getBloodSugar()
                    // 获得数据库连接
                    Thread(
                        Runnable {
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, bloodSugar)

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
                    getActivity(parent.context)?.let { it1 ->
                        AestheticDialog.Builder(it1, DialogStyle.RAINBOW, DialogType.SUCCESS)
                            .setTitle("血糖测量结果")
                            .setMessage("血糖：$bloodSugar mmol/L")
                            .setCancelable(false)
                            .setAnimation(DialogAnimation.SLIDE_DOWN)
                            .setGravity(Gravity.CENTER)
                            .setOnClickListener(object : OnDialogClickListener {
                                override fun onClick(dialog: AestheticDialog.Builder) {
                                    dialog.dismiss()
                                }
                            }).show()
                    }
                }
                // 体重
                6 -> {
                    // 获取当前测量时间
                    val time = Timestamp(Date().time)
                    // 插入选项依次为：用户名，测量时间，体重（kg）
                    val sql =
                        "INSERT INTO weight (userName, time, weight) VALUES (?, ?, ?)"
                    // println(time)
                    val weight = Measure().getWeight()
                    // 获得数据库连接
                    Thread(
                        Runnable {
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, weight)

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
                    getActivity(parent.context)?.let { it1 ->
                        AestheticDialog.Builder(it1, DialogStyle.RAINBOW, DialogType.SUCCESS)
                            .setTitle("体重测量结果")
                            .setMessage("体重：$weight kg")
                            .setCancelable(false)
                            .setAnimation(DialogAnimation.SLIDE_DOWN)
                            .setGravity(Gravity.CENTER)
                            .setOnClickListener(object : OnDialogClickListener {
                                override fun onClick(dialog: AestheticDialog.Builder) {
                                    dialog.dismiss()
                                }
                            }).show()
                    }
                }
                // 体温
                7 -> {
                    // 获取当前测量时间
                    val time = Timestamp(Date().time)
                    // 插入选项依次为：用户名，测量时间，体温
                    val sql =
                        "INSERT INTO bodytemperature (userName, time, temperature) VALUES (?, ?, ?)"
                    // println(time)
                    val temperature = Measure().getTemperature()
                    Thread(
                        Runnable {
                            // 获得数据库连接
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, temperature)

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
                    getActivity(parent.context)?.let { it1 ->
                        AestheticDialog.Builder(it1, DialogStyle.RAINBOW, DialogType.SUCCESS)
                            .setTitle("体温测量结果")
                            .setMessage("体温为：$temperature ℃")
                            .setCancelable(false)
                            .setAnimation(DialogAnimation.SLIDE_DOWN)
                            .setGravity(Gravity.CENTER)
                            .setOnClickListener(object : OnDialogClickListener {
                                override fun onClick(dialog: AestheticDialog.Builder) {
                                    dialog.dismiss()
                                }
                            }).show()
                    }
                }
                else -> {
                    Log.d("FuncAdapter", "点击事件出错")
                }

            }// when
            // preparedStatement.executeUpdate()
            // DBUtils.close(connection, statement)
            // 给出测量完成提示
            // Toasty.success(
            //     parent.context,
            //     "测量完成",
            //     Toast.LENGTH_SHORT,
            //     true
            // ).show()
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