package uz.example.instaclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.example.instaclone.R
import uz.example.instaclone.adapter.FavoriteAdapter
import uz.example.instaclone.adapter.HomeAdapter
import uz.example.instaclone.model.Post

/**
 * In FavoriteFragment, user can check all liked posts
 */
class FavoriteFragment : BaseFragment() {
    val TAG = FavoriteFragment::class.java.simpleName
    lateinit var rv_favorite: RecyclerView
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
         initViews(view)
        return view
    }

    private fun initViews(view: View) {
        rv_favorite = view.findViewById(R.id.rv_favorite)
        rv_favorite.layoutManager = GridLayoutManager(activity,1)
        refreshAdapter(loadPosts())
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = FavoriteAdapter(this, items)
        rv_favorite.adapter = adapter
    }
    private fun loadPosts():ArrayList<Post>{
        val items = ArrayList<Post>()
        items.add(Post("https://images.unsplash.com/photo-1664575196044-195f135295df?ixlib=rb-4.0.3&ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwyMXx8fGVufDB8fHx8&auto=format&fit=crop&w=600&q=60"))
        items.add(Post("https://images.unsplash.com/photo-1509395286499-2d94a9e0c814?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTR8fHBob25lfGVufDB8MnwwfHw%3D&auto=format&fit=crop&w=600&q=60"))
        return items
    }


}