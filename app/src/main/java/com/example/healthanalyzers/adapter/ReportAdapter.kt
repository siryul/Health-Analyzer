package com.example.healthanalyzers.adapter


import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.R
import com.example.healthanalyzers.bean.CustomLineData
import com.example.healthanalyzers.utils.DBUtils
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


class ReportAdapter(
    val context: Context,
    private var lineDataList: ArrayList<CustomLineData>,
    val account: String
) : RecyclerView.Adapter<ReportAdapter.ViewHolder>() {


    private var dateList: ArrayList<String> = ArrayList()

    private lateinit var oldDate: String

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_barChat_title: TextView = view.findViewById(R.id.tv_barChat_title)
        val chart: LineChart = view.findViewById(R.id.chart)
        val selectDate: TextView = view.findViewById(R.id.selectDate)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.report_item, parent, false)
        val viewHolder = ViewHolder(view)

        // 时间选择
        viewHolder.selectDate.setOnClickListener {
            val now = Calendar.getInstance()
            var mYear = now.get(Calendar.YEAR)
            var mMonth = now.get(Calendar.MONTH)
            var mDay = now.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    mYear = year
                    mMonth = month
                    mDay = dayOfMonth
                    val mDate = "${year}-${month + 1}-${dayOfMonth}"
                    viewHolder.selectDate.text = mDate
                    // 更新对应的时间
                    lineDataList[viewHolder.adapterPosition].date = mDate
                },
                mYear, mMonth, mDay
            )
            datePickerDialog.show()
            // Toasty.success(context, "Success", Toasty.LENGTH_SHORT, true).show()

            // 若更换好时间与原时间不一样，对所画的图表进行更新
            val position = viewHolder.adapterPosition
            if (!dateList[position].equals(viewHolder.selectDate.text.toString())) {
                when (position) {
                    // heartrate
                    0 -> {
                        query(
                            lineDataList[position].list,
                            "heartrate",
                            "heartrate",
                            viewHolder.selectDate.text as String,
                            viewHolder.chart,
                            position

                        )
                        // lineDataList[viewHolder.adapterPosition].date =
                        //     viewHolder.selectDate.text.toString()
                        dateList[position] = lineDataList[position].date
                    }
                    // sleepquality
                    1 -> {
                        query(
                            lineDataList[position].list,
                            "quality",
                            "sleepquality",
                            viewHolder.selectDate.text as String,
                            viewHolder.chart,
                            position
                        )
                    }
                    // high
                    2 -> {
                    }
                    // bloodoxygen
                    3 -> {
                    }
                    //
                    4 -> {
                    }
                    5 -> {
                    }
                    6 -> {
                    }
                    7 -> {
                    }
                    else -> {
                    }
                }
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 设置当前时间
        holder.selectDate.text = lineDataList[position].date
        oldDate = lineDataList[position].date

        // 记录下每一向的“当前”时间
        dateList.add(lineDataList[position].date)

        val lineData = lineDataList[position]
        // 设置标题
        holder.tv_barChat_title.text = lineData.type
        // 设置坐标系
        setAxis(holder.chart, lineData.low, lineData.high, lineData.start, lineData.end)

        // 设置图表内容
        // val dataset = LineDataSet(lineData.list, "")
        val dataSet: LineDataSet
        if (holder.chart.data != null && holder.chart.data.dataSetCount > 0) {
            // 避免多次重新加载
            dataSet = holder.chart.data.getDataSetByIndex(0) as LineDataSet
            dataSet.values = lineData.list
            // 刷新数据
            holder.chart.data.notifyDataChanged()
            holder.chart.notifyDataSetChanged()
        } else {
            dataSet = LineDataSet(lineData.list, lineData.type)
            setPoints(dataSet)
        }

        holder.chart.data = LineData(dataSet)

    }

    override fun getItemCount() = lineDataList.size ?: 0

    // 设置图表外观
    private fun setAxis(chart: LineChart, low: Float, high: Float, start: Float, end: Float) {
        // 设置显示的坐标轴有哪些
        chart.axisRight.isEnabled = false
        chart.axisLeft.isEnabled = true
        // holder.barChart.getAxisLeft().setDrawAxisLine(true)
        val x = chart.xAxis
        // 设置 x 轴显示在下方
        x.position = XAxis.XAxisPosition.BOTTOM
        // 不显示与 x 垂直的网格线
        x.setDrawGridLines(false)
        x.xOffset = 1F
        x.axisMinimum = 0F
        x.axisMaximum = 24F
        x.setAvoidFirstLastClipping(true)

        chart.description.text = ""
        // 添加动画
        chart.animateX(500)

        val y = chart.axisLeft
        y.setDrawGridLines(false)
        y.axisMinimum = start
        y.axisMaximum = end
        val limitLineTop = LimitLine(high, "偏高")
        limitLineTop.lineColor = Color.RED
        y.addLimitLine(limitLineTop)
        val limitLineBottom = LimitLine(low, "偏低")
        limitLineBottom.lineColor = Color.RED
        y.addLimitLine(limitLineBottom)
        val count = ((end - start) / 10).toInt()
        y.labelCount = if (count > 6) {
            count
        } else {
            (end - start).toInt()
        }
    }

    // 设置点的形貌等特性
    private fun setPoints(dataSet: LineDataSet) {
        // 设置曲线是否特殊画出坐标点
        dataSet.setDrawCircles(true)
        // 设置曲线为平滑曲线（水平贝塞尔曲线）
        dataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        // 设置每一个坐标点标注数据的文字大小为 0F，表示不显示
        dataSet.valueTextSize = 0F
        // 设置点的大小
        dataSet.circleRadius = 2F
        // 设置点的颜色
        dataSet.setCircleColor(Color.BLUE)
        // 设置是否点击某个点之后显示对应的辅助线
        dataSet.isHighlightEnabled = false
        // 设置线宽
        dataSet.lineWidth = 2F
        // 设置绘制的线下面是否填充颜色
        dataSet.setDrawFilled(false)
    }


    private fun query(
        list: ArrayList<Entry>,
        mainContent: String,
        table: String,
        date: String,
        chart: LineChart,
        position: Int
    ) {
        val sql =
            "SELECT HOUR(time), MINUTE(time), $mainContent FROM $table WHERE DATE(time) = '$date' AND userName = $account;"
        var timeOnAxis: Float
        thread {
            val connection = DBUtils.getConnection()
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            list.clear()
            while (resultSet.next()) {
                timeOnAxis = resultSet.getInt(1) + resultSet.getInt(2) / 60.0F
                list.add(BarEntry(timeOnAxis, resultSet.getInt(3).toFloat()))
            }
            DBUtils.close(connection, statement, resultSet)
            val dataSet = LineDataSet(list, "")
            val data = LineData(dataSet)
            chart.data = data
            notifyItemChanged(position)
        }
    }

}