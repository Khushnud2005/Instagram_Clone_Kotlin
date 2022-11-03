package uz.example.instaclone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import uz.example.instaclone.R
import uz.example.instaclone.fragment.ProfileFragment
import uz.example.instaclone.model.Post
import uz.example.instaclone.utils.Utils

class ProfileAdapter(var fragment: ProfileFragment, var items: ArrayList<Post>): BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_profile, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post: Post = items[position]
        if (holder is PostViewHolder) {
            val iv_post = holder.iv_post
            val tv_caption = holder.tv_caption
            tv_caption.text = post.caption
            setViewHeight(iv_post)
            Glide.with(fragment).load(post.postImg).into(iv_post)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PostViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var iv_post: ShapeableImageView
        var tv_caption: TextView

        init {
            iv_post = view.findViewById(R.id.iv_post)
            tv_caption = view.findViewById(R.id.tv_caption)
        }
    }

    /**
     * Set ShapeableImageView height as screen width
     */
    private fun setViewHeight(view: View) {
        val params: ViewGroup.LayoutParams = view.getLayoutParams()
        params.height = Utils.screenSize(fragment.requireActivity().application).width / 2
        view.setLayoutParams(params)
    }
}