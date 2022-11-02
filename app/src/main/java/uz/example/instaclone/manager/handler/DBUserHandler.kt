package uz.example.instaclone.manager.handler

import uz.example.instaclone.model.User
import java.lang.Exception

interface DBUserHandler {

    fun onSuccess(user: User? = null)
    fun onError(exception: Exception)
}