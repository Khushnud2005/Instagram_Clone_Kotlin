package uz.example.instaclone.manager.handler

import uz.example.instaclone.model.Post

interface DBPostsHandler {
    fun onSuccess(posts: ArrayList<Post>)
    fun onError(e: Exception)
}