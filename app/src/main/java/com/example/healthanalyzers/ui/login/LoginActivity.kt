package com.example.healthanalyzers.ui.login

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.healthanalyzers.MainActivity
import com.example.healthanalyzers.R
import com.example.healthanalyzers.databinding.ActivityLoginBinding
import com.example.healthanalyzers.ui.forgotPassword.ForgotPasswordActivity
import com.example.healthanalyzers.ui.register.RegisterActivity
import com.gyf.immersionbar.ktx.immersionBar

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // setContentView(R.layout.activity_login)

        // 利用sharedPreferences实现记住密码功能
        val prefs = getPreferences(Context.MODE_PRIVATE)

        // 界面控件
//        val username = findViewById<EditText>(R.id.username)
//        val password = findViewById<EditText>(R.id.password)
//        val login = findViewById<Button>(R.id.login)
//        val loading = findViewById<ProgressBar>(R.id.loading)
//        val tv_protocol = findViewById<TextView>(R.id.tv_protocol)
//        val image_btn_qq_login = findViewById<ImageButton>(R.id.image_btn_qq_login)
//        val image_btn_weChat_login = findViewById<ImageButton>(R.id.image_btn_weChat_login)
//        val image_btn_phone_login = findViewById<ImageButton>(R.id.image_btn_phone_login)
//        val rememberPassword = findViewById<CheckBox>(R.id.rememberPassword)
//        val complyProtocol = findViewById<CheckBox>(R.id.comply_protocol)
        // val register = findViewById<Button>(R.id.register)


        // 实现记住密码功能，将账号信息利用SharedPreferences记录在本地
        val isRemember = prefs.getBoolean("remember_password", false)
        // 记录已勾选同意使用软件协议
        val isComplyProtocol = prefs.getBoolean("comply_protocol", false)
        if (isRemember) {
            // 查询账号密码是否已经被存储
            val account_local = prefs.getString("account", "")
            val password_local = prefs.getString("password", "")
            binding.username.setText(account_local)
            binding.password.setText(password_local)
            binding.rememberPassword.isChecked = true
        }
        if (isComplyProtocol) {
            binding.complyProtocol.isChecked = true
        }


        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            binding.login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                binding.username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                binding.password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            binding.loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            // 记住密码
            val account = binding.username.text.toString()
            val pass = binding.password.text.toString()
            if (account == "admin" && pass == "123456") {
                val editor = prefs.edit()
                // 检查记住密码框是否被选中
                if (binding.rememberPassword.isChecked) {
                    editor.putBoolean("remember_password", true)
                    editor.putString("account", account)
                    editor.putString("password", pass)
                } else {
                    editor.clear()
                }
                editor.apply()

                // 是否同意协议
                if (binding.complyProtocol.isChecked) {
                    editor.putBoolean("comply_protocol", true)
                    editor.apply()
                    //Complete and destroy login activity once successful
                    // 转到主界面
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "请同意使用软件相关协议", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "用户名或者密码错误！", Toast.LENGTH_SHORT).show()
            }

        })

        binding.username.afterTextChanged {
            loginViewModel.loginDataChanged(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }

        binding.password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            binding.username.text.toString(),
                            binding.password.text.toString()
                        )
                }
                false
            }

            binding.login.setOnClickListener {
                binding.loading.visibility = View.VISIBLE
                loginViewModel.login(binding.username.text.toString(), binding.password.text.toString())
            }
        }

        // 点击《协议》展示内容
        binding.tvProtocol.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("《协议》")
                setMessage("使用本软件所产生的所有数据均不会被商用。")
                setCancelable(false)
                setPositiveButton("OK") {dialog, which ->}

                show()
            }
        }

        // 测试其他登录方式按钮
        binding.imageBtnQqLogin.setOnClickListener {
            Toast.makeText(this, "you clicked qq", Toast.LENGTH_SHORT).show()
        }

        // 注册账号
        binding.register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            // finish()
        }

        // 忘记密码
        binding.forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

    } // onCreate

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        // 设置状态栏颜色
        immersionBar {
            statusBarColor(R.color.MainActivityColor)
            // navigationBarColor(R.color.MainActivityColor)
        }
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}