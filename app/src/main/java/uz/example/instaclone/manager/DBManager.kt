package uz.example.instaclone.manager

import com.google.firebase.firestore.FirebaseFirestore
import uz.example.instaclone.manager.handler.DBPostHandler
import uz.example.instaclone.manager.handler.DBPostsHandler
import uz.example.instaclone.manager.handler.DBUserHandler
import uz.example.instaclone.manager.handler.DBUsersHandler
import uz.example.instaclone.model.Post
import uz.example.instaclone.model.User
private var USER_PATH = "users"
private var POST_PATH = "posts"
private var FEED_PATH = "feeds"
private var FOLLOWING_PATH = "following"
private var FOLLOWERS_PATH = "followers"
object DBManager {
    private var database = FirebaseFirestore.getInstance()

    fun storeUser(user: User,handler: DBUserHandler){
        database.collection(USER_PATH).document(user.uid).set(user).addOnSuccessListener {
            handler.onSuccess()
        }.addOnFailureListener {
            handler.onError(it)
        }
    }
    fun loadUser(uid: String, handler: DBUserHandler) {
        database.collection(USER_PATH).document(uid).get().addOnSuccessListener {
            if (it.exists()) {
                val fullname: String? = it.getString("fullname")
                val email: String? = it.getString("email")
                val userImg: String? = it.getString("userImg")

                val user = User(fullname!!, email!!, userImg!!)
                user.uid = uid
                handler.onSuccess(user)
            } else {
                handler.onSuccess(null)
            }
        }.addOnFailureListener {
            handler.onError(it)
        }
    }
    fun loadUsers(handler: DBUsersHandler) {
        database.collection(USER_PATH).get().addOnCompleteListener {
            val users = ArrayList<User>()
            if (it.isSuccessful) {
                for (document in it.result!!) {
                    val uid = document.getString("uid")
                    val fullname = document.getString("fullname")
                    val email = document.getString("email")
                    val userImg = document.getString("userImg")


                    val user = User(fullname!!, email!!, userImg!!)
                    user.uid = uid!!
                    users.add(user)
                }
                handler.onSuccess(users)
            } else {
                handler.onError(it.exception!!)
            }
        }
    }
    fun updateUserImage(userImg: String) {
        val uid = AuthManager.currentUser()!!.uid
        database.collection(USER_PATH).document(uid).update("userImg", userImg)
    }

    fun storePosts(post: Post, handler: DBPostHandler) {
        val reference = database.collection(USER_PATH).document(post.uid).collection(POST_PATH)
        val id = reference.document().id
        post.id = id

        reference.document(post.id).set(post).addOnSuccessListener {
            handler.onSuccess(post)
        }.addOnFailureListener {
            handler.onError(it)
        }
    }

    fun storeFeeds(post: Post, handler: DBPostHandler) {
        val reference = database.collection(USER_PATH).document(post.uid).collection(FEED_PATH)

        reference.document(post.id).set(post).addOnSuccessListener {
            handler.onSuccess(post)
        }.addOnFailureListener {
            handler.onError(it)
        }
    }
    fun loadFeeds(uid: String, handler: DBPostsHandler) {
        val reference = database.collection(USER_PATH).document(uid).collection(FEED_PATH)
        reference.get().addOnCompleteListener {
            val posts = ArrayList<Post>()
            if (it.isSuccessful) {
                for (document in it.result!!) {
                    val id = document.getString("id")
                    val caption = document.getString("caption")
                    val postImg = document.getString("postImg")
                    val fullname = document.getString("fullname")
                    val userImg = document.getString("userImg")
                    val currentDate = document.getString("currentDate")
                    val userId = document.getString("uid")

                    val post = Post(id!!, caption!!, postImg!!)
                    post.uid = userId!!
                    post.fullname = fullname!!
                    post.userImg = userImg!!
                    post.currentDate = currentDate!!
                    posts.add(post)
                }
                handler.onSuccess(posts)
            } else {
                handler.onError(it.exception!!)
            }
        }
    }
    fun loadPosts(uid: String, handler: DBPostsHandler) {
        val reference = database.collection(USER_PATH).document(uid).collection(POST_PATH)
        reference.get().addOnCompleteListener {
            val posts = ArrayList<Post>()
            if (it.isSuccessful) {
                for (document in it.result!!) {
                    val id = document.getString("id")
                    val caption = document.getString("caption")
                    val postImg = document.getString("postImg")
                    val fullname = document.getString("fullname")
                    val userImg = document.getString("userImg")
                    val currentDate = document.getString("currentDate")
                    val userId = document.getString("uid")

                    val post = Post(id!!, caption!!, postImg!!)
                    post.uid = userId!!
                    post.fullname = fullname!!
                    post.userImg = userImg!!
                    post.currentDate = currentDate!!
                    posts.add(post)
                }
                handler.onSuccess(posts)
            } else {
                handler.onError(it.exception!!)
            }
        }
    }
}