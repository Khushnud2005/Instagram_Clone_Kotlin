package uz.example.instaclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.example.instaclone.R

/**
 * In FavoriteFragment, user can check all liked posts
 */
class FavoriteFragment : BaseFragment() {
    val TAG = FavoriteFragment::class.java.simpleName
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
         initViews(view)
        return view
    }

    private fun initViews(view: View) {

    }


}