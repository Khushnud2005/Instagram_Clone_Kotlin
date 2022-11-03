package uz.example.instaclone.manager.handler

import uz.example.instaclone.model.Post

interface DBPostHandler {
    fun onSuccess(post: Post)
    fun onError(e: Exception)
}