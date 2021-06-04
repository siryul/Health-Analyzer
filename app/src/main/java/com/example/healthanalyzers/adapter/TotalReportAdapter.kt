package com.example.healthanalyzers.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.R
import com.example.healthanalyzers.bean.PieChartData
import com.example.healthanalyzers.utils.PieChartDataFormatter
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet

class TotalReportAdapter(val context: Context, private val list: ArrayList<PieChartData>) :
    RecyclerView.Adapter<TotalReportAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pieChart: PieChart = view.findViewById(R.id.pieChart)
        // val tvPieChart: TextView = view.findViewById(R.id.tv_pieChart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_total_report, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // holder.tvPieChart.text = list[position].title
        val pieDataSet = PieDataSet(list[position].dataList, "")
        // 设置每一种类型的区块颜色
        val colors = listOf(
            Color.parseColor("#339af0"),
            Color.parseColor("#37b24d"),
            Color.parseColor("#f03e3e")
        )
        pieDataSet.colors = colors
        drawChart(holder.pieChart)
        val pieData = PieData(pieDataSet)
        pieData.setValueTextSize(12f)
        // 对图上标注的数字使用自定义的格式
        pieData.setValueFormatter(PieChartDataFormatter(holder.pieChart))
        holder.pieChart.data = pieData
        // 设置环中心文字
        holder.pieChart.centerText = generateCenterSpannableText(list[position].title)
    }

    // 设置 pieChart 环中心的说明
    private fun generateCenterSpannableText(text: String): SpannableString {
        val s = SpannableString(text)
        // 设置字体大小
        s.setSpan(RelativeSizeSpan(1.0f), 0, s.length, 0)
        // 设置字体样式
        s.setSpan(StyleSpan(Typeface.NORMAL), 0, s.length, 0)
        // 设置字体颜色
        s.setSpan(ForegroundColorSpan(Color.GRAY), 0, s.length, 0)
        return s
    }

    override fun getItemCount() = list.size ?: 0

    // 绘制图表样式
    private fun drawChart(chart: PieChart) {
        // 设置环中心文字为黑色
        chart.setCenterTextColor(Color.BLACK)
        // 在图中不在显示每一项代表什么含义，已有图注
        chart.setDrawEntryLabels(false)
        // 不需要表的描述，因为已在表中心显示
        chart.description.isEnabled = false
        // 获取图表图例
        val legend = chart.legend
        // 启用对 legend 的属性设置
        legend.isEnabled = true
        legend.isWordWrapEnabled = true
        legend.textColor = Color.parseColor("#000000")
        // 设置图注位置
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.setDrawInside(false)
    }
}