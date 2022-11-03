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
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import uz.example.instaclone.R
import uz.example.instaclone.activity.MainActivity
import uz.example.instaclone.adapter.ProfileAdapter
import uz.example.instaclone.manager.AuthManager
import uz.example.instaclone.manager.DBManager
import uz.example.instaclone.manager.StorageManager
import uz.example.instaclone.manager.handler.DBPostsHandler
import uz.example.instaclone.manager.handler.DBUserHandler
import uz.example.instaclone.manager.handler.StorageHandler
import uz.example.instaclone.model.Post
import uz.example.instaclone.model.User

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

        iv_logOut.setOnClickListener {
            AuthManager.signOut()
            callSignInActivity(requireActivity())
        }
        loadUserInfo()
        loadMyPosts()
    }

    private fun loadMyPosts() {
        val uid = AuthManager.currentUser()!!.uid
        DBManager.loadPosts(uid, object: DBPostsHandler {
            override fun onSuccess(posts: ArrayList<Post>) {
                tv_posts.text = posts.size.toString()
                refreshAdapter(posts)
            }

            override fun onError(e: Exception) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun loadUserInfo() {
        DBManager.loadUser(AuthManager.currentUser()!!.uid, object : DBUserHandler {
            override fun onSuccess(user: User?) {
                if (user != null) {
                    showUserInfo(user)
                }
            }

            override fun onError(e: Exception) {

            }
        })
    }

    private fun showUserInfo(user: User){
        tv_fullname.text = user.fullname
        tv_email.text = user.email
        Glide.with(this).load(user.userImg)
            .placeholder(R.drawable.avatar)
            .error(R.drawable.avatar)
            .into(iv_profile)
    }

    private fun uploadUserPhoto() {
        (requireActivity() as MainActivity).showLoading(requireActivity())
        if (pickedPhoto == null) return
        StorageManager.uploadUserPhoto(pickedPhoto!!, object : StorageHandler {
            override fun onSuccess(imgUrl: String) {
                (requireActivity() as MainActivity).dismissLoading()
                DBManager.updateUserImage(imgUrl)
                iv_profile.setImageURI(pickedPhoto)
            }

            override fun onError(exception: Exception?) {
                (requireActivity() as MainActivity).dismissLoading()
            }
        })
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


}