package uz.example.instaclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.example.instaclone.R

/**
 * In ProfileFragment, user can check his/her posts and counts and change profile photo
 */
class ProfileFragment : BaseFragment() {
    val TAG = ProfileFragment::class.java.simpleName
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
         initViews(view)
        return view
    }

    private fun initViews(view: View) {

    }


}