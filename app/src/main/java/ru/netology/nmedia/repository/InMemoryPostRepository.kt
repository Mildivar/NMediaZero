package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class InMemoryPostRepository:PostRepository {

    private var counter = 1

    val post = Post(
        id = 1,
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
        looksCounter = 567
    )

    private val _data = MutableLiveData(post)

    override fun like() {
        val post = _data.value ?: return
        _data.value = _data.value?.copy(
            likeCounter = if (post.likedByMe) post.likeCounter - counter else post.likeCounter + counter,
            likedByMe = !post.likedByMe,
        )
    }

    override fun share() {
        val post = _data.value ?: return
        _data.value = _data.value?.copy(
            sharesCounter = post.sharesCounter + counter
        )
    }


    override val data: LiveData<Post>
        get() = _data
}