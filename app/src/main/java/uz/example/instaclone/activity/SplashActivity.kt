package uz.example.instaclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import uz.example.instaclone.R
import uz.example.instaclone.manager.PrefsManager

/**
 * In SplashActivity, user can visit SignInActivity or MainActivity
 */
class SplashActivity : AppCompatActivity() {
    val TAG = SplashActivity::class.java.simpleName
    var login:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        initViews()
    }

    private fun initViews() {
        if (PrefsManager.getInstance(this)!!.loadData("login")=="true"){
            login = true
        }
        countDownTimmer()

    }

    private fun countDownTimmer() {
        object : CountDownTimer(2000,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                if (login){
                    callMainActivity()
                }else{
                    callSignInActivity()
                }
            }

        }.start()
    }

    private fun callMainActivity() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callSignInActivity() {
        val intent = Intent(this,SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}