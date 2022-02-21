package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class InMemoryPostRepository:PostRepository {

    private var counter = 1

    private var post = List(500) {
        Post(
            id = it.toLong(),
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "$it Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. " +
                    "Затем появились курсы по дизайну, разработке, аналитике и управлению. " +
                    "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. " +
                    "Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше," +
                    " целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку " +
                    "перемен → http://netolo.gy/fyb",
            likeCounter = 10999,
            sharesCounter = 1099999,
            looksCounter = 567
        )
    }.reversed()

    private val data = MutableLiveData(post)

    override fun likeById(id: Long) {
        post = post.map { post ->
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
            }
            else post
        }
        data.value = post
    }

    override fun shareById(id: Long) {
        post = post.map { posts ->
            if (posts.id == id) {
                posts.copy(
                    sharesCounter = posts.sharesCounter + counter
                )
            } else posts

//        val post = data.value ?: return
//        data.value = data.value?.copy(
//            sharesCounter = post.sharesCounter + counter
//        )
        }
        data.value = post
    }
        override fun getAll(): LiveData<List<Post>> = data

    }
