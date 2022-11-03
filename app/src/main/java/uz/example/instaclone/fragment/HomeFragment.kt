package uz.example.instaclone.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.example.instaclone.R
import uz.example.instaclone.adapter.HomeAdapter
import uz.example.instaclone.manager.AuthManager
import uz.example.instaclone.manager.DBManager
import uz.example.instaclone.manager.handler.DBPostsHandler
import uz.example.instaclone.model.Post

/**
 * In HomeFragment, user can check his/her posts and friends posts
 */
class HomeFragment : BaseFragment() {
    val TAG = HomeFragment::class.java.simpleName
    private var listener: HomeListener? = null
    lateinit var rv_home:RecyclerView
    var feeds = ArrayList<Post>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
         initViews(view)
        return view
    }
    /**
     * onAttach is for communication of Fragments
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is HomeListener) {
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
        rv_home = view.findViewById(R.id.rv_home)
        val iv_camera = view.findViewById<ImageView>(R.id.iv_camera)
        iv_camera.setOnClickListener {
            listener!!.scrollToUpload()
        }
        rv_home.layoutManager = GridLayoutManager(activity,1)
        loadMyFeeds()
    }

    private fun loadMyFeeds() {
        showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DBManager.loadFeeds(uid, object : DBPostsHandler {
            override fun onSuccess(posts: ArrayList<Post>) {
                dismissLoading()
                feeds.clear()
                feeds.addAll(posts)
                refreshAdapter(feeds)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }
        })
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = HomeAdapter(this, items)
        rv_home.adapter = adapter
    }


    /**
     * This interface is created for communication with UploadFragment
     */
    interface HomeListener {
        fun scrollToUpload()
    }


}