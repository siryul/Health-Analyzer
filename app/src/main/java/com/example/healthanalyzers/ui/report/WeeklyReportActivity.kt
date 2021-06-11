package com.example.healthanalyzers.ui.report

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.R
import com.example.healthanalyzers.adapter.TotalReportAdapter
import com.example.healthanalyzers.bean.PieChartData
import com.example.healthanalyzers.data.UserInformation
import com.example.healthanalyzers.utils.DBUtils
import com.github.mikephil.charting.data.PieEntry
import com.thecode.aestheticdialogs.*
import kotlin.concurrent.thread

// 周报

class WeeklyReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekly_report)
        supportActionBar?.hide()

        // 初始化数据
        val list = ArrayList<PieChartData>()
        initData(list)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView_weekly)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        val adapter = TotalReportAdapter(this, list)
        recyclerView.adapter = adapter
        // 由于item的大小不会随着内容的变化而变化，因此设置此项可以加快xml的加载速度（无需对控件的大小进行多次测量）
        recyclerView.setHasFixedSize(true)

        // 点击返回上一个页面
        val iBtnBack = findViewById<ImageButton>(R.id.image_btn_back)
        iBtnBack.setOnClickListener {
            finish()
        }
    }

    fun initData(list: ArrayList<PieChartData>) {
        // 查找某个值在一个范围内的写法：heartRate BETWEEN 60 AND 90
        val userInformation = this.application as UserInformation
        val userName = userInformation.account

        // 心率
        val listOfHR = ArrayList<PieEntry>()
        // 正常
        query(listOfHR, "heartRate", "heartrate", "heartRate BETWEEN 60 AND 100", userName, "正常次数")
        // 异常
        query(listOfHR, "heartRate", "heartrate", "heartRate < 60", userName, "偏低次数")
        query(listOfHR, "heartRate", "heartrate", "heartRate > 100", userName, "偏高次数")
        list.add(PieChartData(listOfHR, "心率"))

        // 血糖
        val listOfBS = ArrayList<PieEntry>()
        // 正常
        query(
            listOfBS,
            "bloodSugar",
            "bloodsugar",
            "bloodSugar BETWEEN 70 AND 110",
            userName,
            "正常次数"
        )
        // 异常
        query(listOfBS, "bloodSugar", "bloodsugar", "bloodSugar < 70", userName, "偏低次数")
        query(listOfBS, "bloodSugar", "bloodsugar", "bloodSugar > 110", userName, "偏高次数")
        list.add(PieChartData(listOfBS, "血糖"))

        // 体温
        val listOfTM = ArrayList<PieEntry>()
        // 正常
        query(
            listOfTM,
            "temperature",
            "bodytemperature",
            "temperature BETWEEN 36.3 AND 37.5",
            userName,
            "正常次数"
        )
        // 异常
        query(listOfTM, "temperature", "bodytemperature", "temperature < 36.3", userName, "偏低次数")
        query(listOfTM, "temperature", "bodytemperature", "temperature > 37.5", userName, "偏高次数")
        list.add(PieChartData(listOfTM, "体温"))

        // 睡眠时间
        val listOfSQ = ArrayList<PieEntry>()
        // 正常
        query(listOfSQ, "quality", "sleepquality", "quality BETWEEN 7 AND 8", userName, "正常次数")
        // 异常
        query(listOfSQ, "quality", "sleepquality", "quality < 7", userName, "偏低次数")
        query(listOfSQ, "quality", "sleepquality", "quality > 8", userName, "偏高次数")
        list.add(PieChartData(listOfSQ, "睡眠时间"))

        // 血压
        val listOfBP = ArrayList<PieEntry>()
        // 正常
        query(
            listOfBP,
            "systolicPressure",
            "bloodpressure",
            "systolicPressure BETWEEN 120 AND 140 && diastolicPressure BETWEEN 80 AND 90",
            userName,
            "正常次数"
        )
        // 异常
        query(
            listOfBP,
            "systolicPressure",
            "bloodpressure",
            "systolicPressure < 120 && diastolicPressure < 80",
            userName,
            "偏低次数"
        )
        query(
            listOfBP,
            "systolicPressure",
            "bloodpressure",
            "systolicPressure > 140 && diastolicPressure > 90",
            userName,
            "偏高次数"
        )
        list.add(PieChartData(listOfBP, "血压"))
    }

    /**
     * list：查找结果
     * content：查找的主要内容
     * table：查找内容所在的表
     * range：查找的范围
     * userName：查找的用户账号
     * type：该范围归属的类型
     */
    fun query(
        list: ArrayList<PieEntry>,
        content: String,
        table: String,
        range: String,
        userName: String,
        type: String
    ) {
        thread {
            val sql =
                "SELECT COUNT($content) FROM $table WHERE userName = $userName && $range"
            val connection = DBUtils.getConnection()
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            if (resultSet.next()) {
                list.add(PieEntry(resultSet.getInt(1).toFloat(), type))
            }
            DBUtils.close(connection, statement, resultSet)
        }
    }

    // 点击 给出建议 会对当前的数据进行分析，给出建议
    fun giveAdvices(view: View) {
        AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.INFO)
            .setGravity(Gravity.CENTER)
            .setAnimation(DialogAnimation.IN_OUT)
            .setTitle("建议")
            .setMessage("要注意好好休息哦！并且是时候加强锻炼啦！💕")
            .setCancelable(true)
            .setOnClickListener(object : OnDialogClickListener {
                override fun onClick(dialog: AestheticDialog.Builder) {
                    dialog.dismiss()
                }
            }).show()
    }
}