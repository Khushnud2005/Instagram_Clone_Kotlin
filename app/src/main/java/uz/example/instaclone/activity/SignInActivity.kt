package uz.example.instaclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import uz.example.instaclone.R
import uz.example.instaclone.manager.PrefsManager

/**
 * In SignInActivity, user can login with email and password
 */
class SignInActivity : AppCompatActivity() {
    val TAG = SignInActivity::class.java.simpleName
    lateinit var et_email: EditText
    lateinit var et_password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        initViews()
    }

    private fun initViews() {

        et_email = findViewById(R.id.et_emailSI)
        et_password = findViewById(R.id.et_passwordSI)
        val b_signin = findViewById<Button>(R.id.btn_signin)
        b_signin.setOnClickListener {
            callMainActivity()
            PrefsManager.getInstance(this)!!.saveData("login","true")
        }
        val tv_signup = findViewById<TextView>(R.id.tv_signup)
        tv_signup.setOnClickListener { callSignUpActivity() }

    }

    private fun callMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}