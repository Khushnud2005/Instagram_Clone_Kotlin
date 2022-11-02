package uz.example.instaclone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import uz.example.instaclone.R
import uz.example.instaclone.manager.AuthManager
import uz.example.instaclone.manager.DBManager
import uz.example.instaclone.manager.handler.AuthHandler
import uz.example.instaclone.manager.handler.DBUserHandler
import uz.example.instaclone.model.User
import uz.example.instaclone.utils.Extensions.toast

/**
 * In SignUpActivity, user can signup with fullname, email, password
 */
class SignUpActivity : BaseActivity() {
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
            val fullname = et_fullname.text.toString().trim()
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            if(fullname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                val user = User(fullname, email, password,"")
                firebaseSignUp(user)
            }
        }
        val tv_signin = findViewById<TextView>(R.id.tv_signin)
        tv_signin.setOnClickListener { finish() }
    }
    private fun firebaseSignUp(user: User) {
        showLoading(this)
        AuthManager.signUp(user.email, user.password, object : AuthHandler {
            override fun onSuccess(uid: String) {
                user.uid = uid
                storeUserToDB(user)
            }

            override fun onError(exception: Exception?) {
                dismissLoading()
                toast(getString(R.string.str_signup_failed))
            }
        })
    }

    private fun storeUserToDB(user: User){

        DBManager.storeUser(user, object: DBUserHandler {

            override fun onSuccess(user: User?) {
                dismissLoading()
                toast(getString(R.string.str_signup_success))
                callMainActivity(context)
            }

            override fun onError(e: Exception) {

            }
        })
    }

}