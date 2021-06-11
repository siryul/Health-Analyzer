package com.example.healthanalyzers.ui.report

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.healthanalyzers.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReportFragment : Fragment() {

    private lateinit var reportViewModel: ReportViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reportViewModel =
            ViewModelProvider(this).get(ReportViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_report, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        reportViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        val daily = root.findViewById<CardView>(R.id.daily)
        val weekly = root.findViewById<CardView>(R.id.weekly)
        // val monthly= root.findViewById<CardView>(R.id.monthly)
        // val yearly= root.findViewById<CardView>(R.id.yearly)

        daily.setOnClickListener {
            // 转到日报
            val intent = Intent(context, DailyReportActivity::class.java)
            startActivity(intent)
        }

        weekly.setOnClickListener {
            // 转到周报
            val intent = Intent(context, WeeklyReportActivity::class.java)
            startActivity(intent)
        }
        getDate()
        return root
    }

    private fun getDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val beginDate = Date()
        val date = Calendar.getInstance()

        date.time = beginDate
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 7)
        var preDate:Date ?= null
        try {
            preDate = dateFormat.parse(dateFormat.format(date.time))
        } catch (e:ParseException) {
            e.printStackTrace()
        }
        Log.d("Report", "(${dateFormat.parse(dateFormat.format(Calendar.DATE))}~${dateFormat.format(preDate)})")
        return "(${dateFormat.parse(dateFormat.format(Calendar.DATE))}~${dateFormat.format(preDate)})"
    }
}