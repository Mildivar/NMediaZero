package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class InMemoryPostRepository : PostRepository {

    private var nextID = 1L

    private var posts =  listOf(
        Post(
            id = nextID++,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. " +
                    "Затем появились курсы по дизайну, разработке, аналитике и управлению. " +
                    "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. " +
                    "Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше," +
                    " целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку " +
                    "перемен → http://netolo.gy/fyb",
            likeCounter = 10999,
            sharesCounter = 1099999,
            looksCounter = 10999,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        ),

        Post(
            id = nextID++,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. " +
                    "Затем появились курсы по дизайну, разработке, аналитике и управлению. " +
                    "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. " +
                    "Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше," +
                    " целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку " +
                    "перемен → http://netolo.gy/fyb",
            likeCounter = 10999,
            sharesCounter = 1099999,
            looksCounter = 10999,
            video = ""
        ),

        Post(
            id = nextID++,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. " +
                    "Затем появились курсы по дизайну, разработке, аналитике и управлению. " +
                    "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. " +
                    "Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше," +
                    " целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку " +
                    "перемен → http://netolo.gy/fyb",
            likeCounter = 10999,
            sharesCounter = 1099999,
            looksCounter = 10999,
            video = ""
        ),

        Post(
            id = nextID++,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. " +
                    "Затем появились курсы по дизайну, разработке, аналитике и управлению. " +
                    "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. " +
                    "Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше," +
                    " целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку " +
                    "перемен → http://netolo.gy/fyb",
            likeCounter = 10999,
            sharesCounter = 1099999,
            looksCounter = 10999,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        )
    )

    private val data = MutableLiveData(posts)

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
        }

        override fun getAll(): LiveData<List<Post>> = data
        override fun removeByID(id: Long) {
            posts = posts.filter {
                it.id != id
            }
            data.value = posts
        }

        override fun save(post: Post) {
            if (post.id == 0L) {
                val newID = posts.firstOrNull()?.id ?: post.id
                posts = listOf(post.copy(id = newID + 1)) + posts
                data.value = posts
                return
            }

            posts = posts.map {
                if (it.id != post.id) it else it.copy(content = post.content)
            }
            data.value = posts
        }
    }

