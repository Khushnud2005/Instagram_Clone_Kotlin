package uz.example.instaclone.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.example.instaclone.R
import uz.example.instaclone.adapter.FavoriteAdapter
import uz.example.instaclone.adapter.SearchAdapter
import uz.example.instaclone.manager.AuthManager
import uz.example.instaclone.manager.DBManager
import uz.example.instaclone.manager.handler.DBUsersHandler
import uz.example.instaclone.model.Post
import uz.example.instaclone.model.User

/**
 * In HomeFragment, user can search friends
 */
class SearchFragment : BaseFragment() {
    val TAG = SearchFragment::class.java.simpleName
    lateinit var rv_serch:RecyclerView
    var items = ArrayList<User>()
    var users = ArrayList<User>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
         initViews(view)
        return view
    }

    private fun initViews(view: View) {
        rv_serch = view.findViewById(R.id.rv_search)
        rv_serch.layoutManager = GridLayoutManager(activity,1)
        val et_search = view.findViewById<EditText>(R.id.et_search)
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val keyword = s.toString().trim()
                usersByKeyword(keyword)
            }
        })
        loadUsers()
    }

    private fun refreshAdapter(items: ArrayList<User>) {
        val adapter = SearchAdapter(this, items)
        rv_serch.adapter = adapter
    }
    private fun loadUsers(){
        val uid = AuthManager.currentUser()!!.uid
        DBManager.loadUsers(object : DBUsersHandler {
            override fun onSuccess(users: ArrayList<User>) {
                items.clear()
                items.addAll(users)
                refreshAdapter(items)
            }

            override fun onError(e: Exception) {
            }
        })
    }
    fun usersByKeyword(keyword: String) {
        if (keyword.isEmpty())
            refreshAdapter(items)

        users.clear()
        for (user in items)
            if (user.fullname.toLowerCase().startsWith(keyword.toLowerCase()))
                users.add(user)

        refreshAdapter(users)
    }
}