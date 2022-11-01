package uz.example.instaclone.fragment

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import uz.example.instaclone.activity.SignInActivity

/**
 * BaseFragment is parent for all Fragments
 */
open class BaseFragment :Fragment() {

    fun callSignInActivity(activity: Activity) {
        val intent = Intent(context, SignInActivity::class.java)
        startActivity(intent)
        activity.finish()
    }
}