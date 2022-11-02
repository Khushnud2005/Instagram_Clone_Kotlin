package uz.example.instaclone.manager.handler

import uz.example.instaclone.model.User
import java.lang.Exception

interface DBUsersHandler {

    fun onSuccess(users:ArrayList<User>)
    fun onError(exception: Exception)
}