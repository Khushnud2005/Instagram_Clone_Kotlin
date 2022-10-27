package uz.example.instaclone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import uz.example.instaclone.R
/**
 * In SignUpActivity, user can signup with fullname, email, password
 */
class SignUpActivity : AppCompatActivity() {
    val TAG = SignUpActivity::class.java.simpleName
    lateinit var et_fullname: EditText
    lateinit var et_password: EditText
    lateinit var et_email: EditText
    lateinit var et_cpassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initViews()
    }

    private fun initViews() {
        et_fullname = findViewById(R.id.et_fullnameSU)
        et_email = findViewById(R.id.et_emailSU)
        et_password = findViewById(R.id.et_passwordSU)
        et_cpassword = findViewById(R.id.et_cpasswordSU)

        val btn_signup = findViewById<Button>(R.id.btn_signup)
        btn_signup.setOnClickListener {
            finish()
        }
        val tv_signin = findViewById<TextView>(R.id.tv_signin)
        tv_signin.setOnClickListener { finish() }
    }
}