package com.example.healthanalyzers.ui.report

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.healthanalyzers.R

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

        val daily= root.findViewById<CardView>(R.id.daily)
        val weekly= root.findViewById<CardView>(R.id.weekly)
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

        /**
         * monthly.setOnClickListener {
        // 转到月报
        val intent = Intent(context, MonthlyReportActivity::class.java)
        startActivity(intent)
        }
        yearly.setOnClickListener {
        // 转到年报
        val intent = Intent(context, YearlyReportActivity::class.java)
        startActivity(intent)
        }
         */







        return root
    }
}