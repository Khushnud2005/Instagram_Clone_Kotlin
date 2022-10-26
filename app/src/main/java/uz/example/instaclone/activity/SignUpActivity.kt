package uz.example.instaclone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.example.instaclone.R
/**
 * In SignUpActivity, user can signup with fullname, email, password
 */
class SignUpActivity : AppCompatActivity() {
    val TAG = SignUpActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }
}