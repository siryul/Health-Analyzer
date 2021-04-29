package com.example.healthanalyzers.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.Measure.Measure
import com.example.healthanalyzers.R
import com.example.healthanalyzers.bean.Func
import com.example.healthanalyzers.utils.DBUtils
import es.dmoral.toasty.Toasty
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
                    // 插入选项依次为：用户名，测量时间，动脉血，静脉血氧饱和度
                    val sql =
                        "INSERT INTO heartrate (userName, time, heartRate) VALUES (?, ?, ?)"
                    println(time)
                    // 获得数据库连接
                    Thread(
                        Runnable {
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, Measure().getHeartRate())

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
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

                    Thread(
                        Runnable {
                            // 获得数据库连接
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, Measure().getPsySleepQuality())

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
                }
                // 身高
                2 -> {
                    // 获取当前测量时间
                    val time = Timestamp(Date().time)
                    // 插入选项依次为：用户名，测量时间，身高
                    val sql =
                        "INSERT INTO high (userName, time, high) VALUES (?, ?, ?)"
                    // println(time)

                    Thread(
                        Runnable {
                            // 获得数据库连接
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, Measure().getHigh())

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
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
                    Thread(
                        Runnable {
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, Measure().getArterialBloodOxygen())
                            preparedStatement.setObject(4, Measure().getVenousBloodOxygen())

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()

                }
                // 血压
                4 -> {
                    // 获取当前测量时间
                    val time = Timestamp(Date().time)
                    // 插入选项依次为：用户名，测量时间，收缩压，舒张压
                    val sql =
                        "INSERT INTO bloodpressure (userName, time, systolicPressure, diastolicPressure) VALUES (?, ?, ?, ?)"
                    // println(time)
                    // 获得数据库连接
                    Thread(
                        Runnable {
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, Measure().getSystolicPressure())
                            preparedStatement.setObject(4, Measure().getDiastolicPressure())

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
                }
                // 血糖
                5 -> {
                    // 获取当前测量时间
                    val time = Timestamp(Date().time)
                    // 插入选项依次为：用户名，测量时间，血糖
                    val sql =
                        "INSERT INTO bloodsugar (userName, time, bloodSugar) VALUES (?, ?, ?)"
                    // println(time)
                    // 获得数据库连接
                    Thread(
                        Runnable {
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, Measure().getBloodSugar())

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
                }
                // 体重
                6 -> {
                    // 获取当前测量时间
                    val time = Timestamp(Date().time)
                    // 插入选项依次为：用户名，测量时间，体重（kg）
                    val sql =
                        "INSERT INTO weight (userName, time, weight) VALUES (?, ?, ?)"
                    // println(time)
                    // 获得数据库连接
                    Thread(
                        Runnable {
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, Measure().getWeight())

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
                }
                // 体温
                7 -> {
                    // 获取当前测量时间
                    val time = Timestamp(Date().time)
                    // 插入选项依次为：用户名，测量时间，体温
                    val sql =
                        "INSERT INTO bodytemperature (userName, time, temperature) VALUES (?, ?, ?)"
                    // println(time)

                    Thread(
                        Runnable {
                            // 获得数据库连接
                            val connection = DBUtils.getConnection()
                            val statement = connection.createStatement()
                            // 准备预编译sql代码
                            val preparedStatement = connection.prepareStatement(sql)

                            preparedStatement.setObject(1, account.toInt())
                            preparedStatement.setObject(2, time)
                            preparedStatement.setObject(3, Measure().getTemperature())

                            preparedStatement.executeUpdate()

                            DBUtils.close(connection, statement)
                        }).start()
                }
                else -> {
                    Log.d("FuncAdapter", "点击事件出错")
                }

            }// when
            // preparedStatement.executeUpdate()
            // DBUtils.close(connection, statement)
            // 给出测量完成提示
            Toasty.success(
                parent.context,
                "测量完成",
                Toast.LENGTH_SHORT,
                true
            ).show()
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