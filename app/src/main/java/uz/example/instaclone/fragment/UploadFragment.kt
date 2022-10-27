package uz.example.instaclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.example.instaclone.R

/**
 * In UploadFragment, user can upload
 * a post with photo and caption
 */
class UploadFragment : BaseFragment() {
    val TAG = UploadFragment::class.java.simpleName
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upload, container, false)
         initViews(view)
        return view
    }

    private fun initViews(view: View) {

    }


}