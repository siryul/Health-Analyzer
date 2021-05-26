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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.*
import kotlin.concurrent.thread


class ReportAdapter(
    val context: Context,
    private val lineDataList: ArrayList<CustomLineData>,
    val account: String
) :
    RecyclerView.Adapter<ReportAdapter.ViewHolder>() {


    private lateinit var dpd: DatePickerDialog

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
                    val mDate = "${year}/${month + 1}/${dayOfMonth}"
                    viewHolder.selectDate.text = mDate
                },
                mYear, mMonth, mDay
            )
            datePickerDialog.show()
            // Toasty.success(context, "Success", Toasty.LENGTH_SHORT, true).show()

            // 若更换好时间与原时间不一样，对所画的图表进行更新
            if (!oldDate.equals(viewHolder.selectDate.text.toString())) {
                val list = ArrayList<Entry>()
                when (viewHolder.adapterPosition) {
                    // heartrate
                    0 -> {
                        query(list, "heartrate", "heartrate", viewHolder.selectDate.text as String, viewHolder.chart)
                    }
                    // sleepquality
                    1 -> {
                        query(list, "quality", "sleepquality", viewHolder.selectDate.text as String, viewHolder.chart)
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
        val time = Calendar.getInstance()
        val date =
            "${time.get(Calendar.YEAR)}-${time.get(Calendar.MONTH) + 1}-${time.get(Calendar.DAY_OF_MONTH)}"
        holder.selectDate.text = date
        oldDate = date

        val lineData = lineDataList[position]
        // 设置标题
        holder.tv_barChat_title.text = lineData.type

        // 设置图表内容
        // val dataset = LineDataSet(lineData.list, "")
        val dataset: LineDataSet
        if (holder.chart.data != null && holder.chart.data.dataSetCount > 0) {
            // 避免多次重新加载
            dataset = holder.chart.data.getDataSetByIndex(0) as LineDataSet
            dataset.values = lineData.list
            // 刷新数据
            holder.chart.data.notifyDataChanged()
            holder.chart.notifyDataSetChanged()
        } else {
            dataset = LineDataSet(lineData.list, "")
            // 设置曲线填充
            dataset.setDrawFilled(true)
            dataset.setDrawCircles(true)
            // 设置曲线为平滑曲线
            dataset.mode = LineDataSet.Mode.CUBIC_BEZIER
            // 设置填充色
            dataset.fillColor = Color.rgb(244, 117, 117)
            dataset.fillAlpha = 100
        }

        val data = LineData(dataset)
        // 设置坐标系
        setAxis(holder.chart)
        holder.chart.data = data

    }

    override fun getItemCount() = lineDataList.size ?: 0

    // 设置图表外观
    private fun setAxis(chart: LineChart) {
        // val legend = chart.legend
        // legend.form = Legend.LegendForm.LINE

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
        chart.description.text = ""
        // 添加动画
        // chart.animateY(300)
    }


    private fun query(
        list: ArrayList<Entry>,
        mainContent: String,
        table: String,
        date: String,
        chart: LineChart
    ) {
        val sql =
            "SELECT HOUR(time), MINUTE(time), $mainContent FROM $table WHERE DATE(time) = '$date' AND userName = $account;"
        var timeOnAxis: Float
        thread {
            val connection = DBUtils.getConnection()
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            while (resultSet.next()) {
                timeOnAxis = resultSet.getInt(1) + resultSet.getInt(2) / 60.0F
                list.add(BarEntry(timeOnAxis, resultSet.getInt(3).toFloat()))
            }
            DBUtils.close(connection, statement, resultSet)
            val dataSet = LineDataSet(list, "")
            val data = LineData(dataSet)
            chart.data = data
        }
    }

}