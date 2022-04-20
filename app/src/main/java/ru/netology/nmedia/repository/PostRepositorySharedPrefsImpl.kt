package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.Post

class PostRepositorySharedPrefsImpl(context: Context) : PostRepository {
    private var gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val key = "posts"
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    private var nextID = 1L

    private var posts = emptyList<Post>()

    private val data = MutableLiveData(posts)

    init {
        prefs.getString(key, null)?.let {
            posts = gson.fromJson(it, type)
            nextID = posts.maxOfOrNull { post -> post.id }?.inc()?:1L
            data.value = posts
        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                if (post.likedByMe) {
                    post.copy(
                        likeCounter = post.likeCounter - 1,
                        likedByMe = false
                    )
                } else {
                    post.copy(
                        likeCounter = post.likeCounter + 1,
                        likedByMe = true
                    )
                }
            } else post
        }
        data.value = posts
        sync()
    }

    override fun lookById(id: Long) {
        posts = posts.map { posts ->
            if (posts.id == id) {
                posts.copy(
                    looksCounter = posts.looksCounter + 1
                )
            } else posts
        }
        data.value = posts
        sync()
    }

    override fun shareById(id: Long) {
        posts = posts.map { posts ->
            if (posts.id == id) {
                posts.copy(
                    sharesCounter = posts.sharesCounter + 1
                )
            } else posts

//        val post = data.value ?: return
//        data.value = data.value?.copy(
//            sharesCounter = post.sharesCounter + counter
//        )
        }
        data.value = posts
        sync()
    }

    override fun removeByID(id: Long) {
        posts = posts.filter {
            it.id != id
        }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            val newID = posts.firstOrNull()?.id ?: post.id
            posts = listOf(
                post.copy(
                    id = newID + 1,
                    author = "Vldmr",
                    likedByMe = false,
                    published = "Right now"
                )
            ) + posts
            data.value = posts
            sync()
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }

    private fun sync() {
        with(prefs.edit()) {
            putString(key, gson.toJson(posts))
            apply()
        }
    }
}

