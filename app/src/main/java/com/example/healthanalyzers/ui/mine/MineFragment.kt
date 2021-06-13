package com.example.healthanalyzers.ui.mine;


import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.R
import com.example.healthanalyzers.adapter.MineAdapter
import com.example.healthanalyzers.bean.Mine
import com.example.healthanalyzers.data.UserInformation
import com.example.healthanalyzers.utils.DBUtils
import com.gyf.immersionbar.ktx.immersionBar
import java.io.IOException
import kotlin.concurrent.thread


class MineFragment : androidx.fragment.app.Fragment() {
    private lateinit var mineViewModel: MineViewModel
    private val mineList = ArrayList<Mine>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var tv_information: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mineViewModel =
            ViewModelProvider(this).get(MineViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mine, container, false)
//        val textView: TextView = root.findViewById(R.id.text_mine)
//        mineViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        recyclerView = root.findViewById(R.id.recyclerView_me)
//        tv_information = root.findViewById(R.id.tv_information)
        init()
        val layoutManager = LinearLayoutManager(context)
        layoutManager.isAutoMeasureEnabled = true
        recyclerView.layoutManager = layoutManager
        val adapter = MineAdapter(mineList)
        recyclerView.adapter = adapter
        // recyclerView.addItemDecoration(RecyclerView.ItemDecoration)

        // 修改密码
        val tv_alter_password = root.findViewById<TextView>(R.id.tv_alter_password)
        tv_alter_password.setOnClickListener {
            val intent = Intent(context, AlterPasswordActivity::class.java)
            startActivity(intent)
        }

        // 注销操作
        val tv_logout = root.findViewById<TextView>(R.id.tv_logout)
        tv_logout.setOnClickListener {
            val popwindow_logout =
                LayoutInflater.from(context).inflate(R.layout.popwindow_logout, null)
            val popupWindow = PopupWindow(
                popwindow_logout,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 80)
            popupWindow.isTouchable = true

            // 处理弹窗中按钮事件
            val tv_make_sure_logout =
                popwindow_logout.findViewById<TextView>(R.id.tv_make_sure_logout)
            val tv_cancel = popwindow_logout.findViewById<TextView>(R.id.tv_cancel)

            tv_cancel.setOnClickListener {
                popupWindow.dismiss()
            }

            tv_make_sure_logout.setOnClickListener {
                // 点击确认注销之后，注销数据库中账号信息，并跳转至登录页面
                val userInformation = activity?.application as UserInformation
                val account = userInformation.account
                val sql = "DELETE FROM `user` WHERE userName = $account"
                thread {
                    val connection = DBUtils.getConnection()
                    val statement = connection.createStatement()
                    statement.executeUpdate(sql)
                    DBUtils.close(connection, statement)
                }
            }
        }

        // 实现点击返回不是返回到首页，而是回到桌面
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })

        return root
    }

    private fun init() {
        val userInformation = activity?.getApplication() as UserInformation
        val account = userInformation.account
        mineList.add(Mine("用户名", account))
        var sql =
            "SELECT userName, nickName, sex, age FROM user WHERE userName = $account"
        Thread(
            Runnable {
                try {
                    // Class.forName("com.mysql.jdbc.Driver")
                    // val connection = DriverManager.getConnection(
                    //     "jdbc:mysql://192.168.220.1:3306/health?useSSL=false&allowPublicKeyRetrieval=true",
                    //     "root", "666666"
                    // )
                    // val statement = connection.createStatement()
                    // val resultSet = statement.executeQuery(sql)

                    val connection = DBUtils.getConnection()
                    val statement = connection.createStatement()
                    var resultSet = statement.executeQuery(sql)

                    while (resultSet.next()) {
                        mineList.add(Mine("昵称", resultSet.getString("nickName")))
                        if (resultSet.getInt("sex") == 0) {
                            mineList.add(Mine("性别", "女"))
                        } else {
                            mineList.add(Mine("性别", "男"))
                        }
                        if (resultSet.getInt("age") != 0) {
                            mineList.add(Mine("年龄", resultSet.getInt("age").toString()))
                        } else {
                            mineList.add(Mine("年龄", ""))
                        }
                    }
                    sql =
                        "SELECT high FROM high WHERE userName = $account && time = (SELECT time FROM high ORDER BY time ASC LIMIT 1)"
                    resultSet = statement.executeQuery(sql)
                    if (resultSet.next() && resultSet.getFloat("high") != 0.0f) {
                        mineList.add(Mine("身高", resultSet.getFloat("high").toString() + " cm"))
                    } else {
                        mineList.add(Mine("身高", ""))
                    }
                    sql =
                        "SELECT weight FROM weight WHERE userName = $account && time = (SELECT time FROM weight ORDER BY time ASC LIMIT 1)"
                    resultSet = statement.executeQuery(sql)
                    if (resultSet.next() && resultSet.getDouble("weight") != 0.0) {
                        mineList.add(Mine("体重", resultSet.getDouble("weight").toString() + " kg"))
                    } else {
                        mineList.add(Mine("体重", ""))
                    }
                    DBUtils.close(connection, statement, resultSet)
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }).start()
    }

    override fun onResume() {
        super.onResume()
        immersionBar {
            statusBarColor(R.color.actionbar_color)
        }
    }
}
