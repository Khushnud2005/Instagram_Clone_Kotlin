package uz.example.instaclone.fragment

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import uz.example.instaclone.R
import uz.example.instaclone.manager.AuthManager
import uz.example.instaclone.manager.DBManager
import uz.example.instaclone.manager.StorageManager
import uz.example.instaclone.manager.handler.DBPostHandler
import uz.example.instaclone.manager.handler.DBUserHandler
import uz.example.instaclone.manager.handler.StorageHandler
import uz.example.instaclone.model.Post
import uz.example.instaclone.model.User
import uz.example.instaclone.utils.Utils

/**
 * In UploadFragment, user can upload
 * a post with photo and caption
 */
class UploadFragment : BaseFragment() {
    val TAG = UploadFragment::class.java.simpleName
    lateinit var fl_photo: FrameLayout
    lateinit var iv_photo: ImageView
    lateinit var et_caption: EditText
    private var listener: UploadListener? = null
    var pickedPhoto: Uri? = null
    var allPhotos = ArrayList<Uri>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upload, container, false)
         initViews(view)
        return view
    }
    /**
     * onAttach is for communication of Fragments
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is UploadListener) {
            context
        } else {
            throw RuntimeException("$context must implement FirstListener")
        }
    }

    /**
     * onDetach is for communication of Fragments
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    private fun initViews(view: View) {
        val fl_view = view.findViewById<FrameLayout>(R.id.fl_view)
        setViewHeight(fl_view)
        et_caption = view.findViewById(R.id.et_caption)
        fl_photo = view.findViewById(R.id.fl_photo)
        iv_photo = view.findViewById(R.id.iv_photo)
        val iv_close = view.findViewById<ImageView>(R.id.iv_close)
        val iv_pick = view.findViewById<ImageView>(R.id.iv_pick)
        val iv_upload = view.findViewById<ImageView>(R.id.iv_upload)

        iv_pick.setOnClickListener {
            pickFishBunPhoto()
        }
        iv_close.setOnClickListener {
            hidePickedPhoto()
        }
        iv_upload.setOnClickListener {
            uploadNewPost()
        }
    }
    /**
     * Set view height as screen width
     */
    private fun setViewHeight(flView: FrameLayout) {
        val params: ViewGroup.LayoutParams = flView.getLayoutParams()
        params.height = Utils.screenSize(requireActivity().application).width
        flView.setLayoutParams(params)
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

    private val photoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                allPhotos =
                    it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH) ?: arrayListOf()
                pickedPhoto = allPhotos.get(0)
                showPickedPhoto()
            }
        }

    private fun showPickedPhoto() {
        fl_photo.visibility = View.VISIBLE
        iv_photo.setImageURI(pickedPhoto)
    }

    private fun hidePickedPhoto() {
        pickedPhoto = null
        allPhotos.clear()
        fl_photo.visibility = View.GONE
    }
    private fun uploadNewPost() {
        showLoading(requireActivity())
        val caption = et_caption.text.toString().trim()
        if (caption.isNotEmpty() && pickedPhoto != null) {
            uploadPostPhoto(pickedPhoto!!,caption)
        }else if (caption.isEmpty() || pickedPhoto == null){
            Utils.toast(this.requireContext(),"Please pick Photo && write Caption !!!")
        }

    }

    private fun uploadPostPhoto(uri:Uri,caption: String) {
        StorageManager.uploadPostPhoto(uri, object : StorageHandler {
            override fun onSuccess(imgUrl: String) {
                val post = Post(caption, imgUrl)
                post.setCurrentTime()
                val uid = AuthManager.currentUser()!!.uid

                DBManager.loadUser(uid, object : DBUserHandler {
                    override fun onSuccess(user: User?) {
                        post.uid = uid
                        post.fullname = user!!.fullname
                        post.userImg = user.userImg
                        storePostToDB(post)
                    }

                    override fun onError(e: Exception) {
                    }
                })
            }

            override fun onError(exception: Exception?) {

            }
        })
    }

    private fun storePostToDB(post: Post) {
        DBManager.storePosts(post, object : DBPostHandler{
            override fun onSuccess(post: Post) {
                storeFeedToDB(post)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }
        })
    }

    private fun storeFeedToDB(post: Post) {
        DBManager.storeFeeds(post, object : DBPostHandler {
            override fun onSuccess(post: Post) {
                dismissLoading()
                resetAll()
                listener!!.scrollToHome()
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }
        })
    }
    private fun resetAll() {
        allPhotos.clear()
        et_caption.text.clear()
        pickedPhoto = null
        fl_photo.visibility = View.GONE
    }
    /**
     * This interface is created for communication with HomeFragment
     */
    interface UploadListener {
        fun scrollToHome()
    }




}