package uz.example.instaclone.fragment

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import uz.example.instaclone.R
import uz.example.instaclone.adapter.ProfileAdapter
import uz.example.instaclone.manager.AuthManager
import uz.example.instaclone.model.Post

/**
 * In ProfileFragment, user can check his/her posts and counts and change profile photo
 */
class ProfileFragment : BaseFragment() {
    val TAG = ProfileFragment::class.java.simpleName
    lateinit var rv_profile: RecyclerView
    lateinit var tv_fullname: TextView
    lateinit var tv_email: TextView
    lateinit var tv_posts: TextView
    lateinit var tv_followers: TextView
    lateinit var tv_following: TextView
    lateinit var iv_logOut: ImageView
    lateinit var iv_profile: ShapeableImageView

    var pickedPhoto: Uri? = null
    var allPhotos = ArrayList<Uri>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
         initViews(view)
        return view
    }

    private fun initViews(view: View) {
        tv_fullname = view.findViewById(R.id.tv_fullname)
        tv_email = view.findViewById(R.id.tv_email)
        tv_posts = view.findViewById(R.id.tv_posts)
        tv_followers = view.findViewById(R.id.tv_followers)
        tv_following = view.findViewById(R.id.tv_following)
        iv_profile = view.findViewById(R.id.iv_profile)
        iv_logOut = view.findViewById(R.id.iv_logout)

        rv_profile = view.findViewById(R.id.rv_profile)
        rv_profile.setLayoutManager(GridLayoutManager(activity, 2))

        iv_profile.setOnClickListener {
            pickFishBunPhoto()
        }
        refreshAdapter(loadPosts())
        iv_logOut.setOnClickListener {
            AuthManager.signOut()
            callSignInActivity(requireActivity())
        }
    }
    private fun uploadUserPhoto() {
        if (pickedPhoto != null) {
            Log.d(TAG,pickedPhoto!!.path.toString())
        }

    }
    /**
     * Pick photo using FishBun library
     */
    private fun pickFishBunPhoto() {
        FishBun.with(this)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(1)
            .setMinCount(1)
            .setSelectedImages(allPhotos)
            .startAlbumWithActivityResultCallback(photoLauncher)
    }

    private val photoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            allPhotos = it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH) ?: arrayListOf()
            pickedPhoto = allPhotos.get(0)
            uploadUserPhoto()
        }
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = ProfileAdapter(this, items)
        rv_profile.adapter = adapter
    }

    private fun loadPosts():ArrayList<Post>{
        val items = ArrayList<Post>()
        items.add(Post("https://images.unsplash.com/photo-1657214058650-31cc8400713b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwxfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=600&q=60"))
        items.add(Post("https://images.unsplash.com/photo-1664575196044-195f135295df?ixlib=rb-4.0.3&ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwyMXx8fGVufDB8fHx8&auto=format&fit=crop&w=600&q=60"))
        items.add(Post("https://images.unsplash.com/photo-1509395286499-2d94a9e0c814?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTR8fHBob25lfGVufDB8MnwwfHw%3D&auto=format&fit=crop&w=600&q=60"))
        items.add(Post("https://images.unsplash.com/photo-1665436752144-4e9236563aff?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDEyMHw2c01WalRMU2tlUXx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=600&q=60"))
        items.add(Post("https://images.unsplash.com/photo-1657214058650-31cc8400713b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwxfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=600&q=60"))
        items.add(Post("https://images.unsplash.com/photo-1664575196044-195f135295df?ixlib=rb-4.0.3&ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwyMXx8fGVufDB8fHx8&auto=format&fit=crop&w=600&q=60"))
        items.add(Post("https://images.unsplash.com/photo-1509395286499-2d94a9e0c814?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTR8fHBob25lfGVufDB8MnwwfHw%3D&auto=format&fit=crop&w=600&q=60"))
        items.add(Post("https://images.unsplash.com/photo-1665436752144-4e9236563aff?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDEyMHw2c01WalRMU2tlUXx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=600&q=60"))
        return items
    }
}