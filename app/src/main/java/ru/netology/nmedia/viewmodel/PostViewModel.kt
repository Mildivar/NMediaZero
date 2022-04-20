package ru.netology.nmedia.viewmodel

//ViewModel отвечает за хранение данных, относящихся к UI, и за
//связывание UI с бизнес-логикой. В нашем случае, вся бизнес-логика
//хранится в Repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryFileImpl

val empty = Post(
    id = 0L,
    author = "",
    published = "",
    content = "",
    likedByMe = false,
    likeCounter = 0,
    sharesCounter = 0,
    looksCounter = 0,
    video = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryFileImpl(application)
    fun likeById(id: Long) {
        repository.likeById(id)
    }

    fun lookById(id:Long) {
        repository.lookById(id)
    }

//    fun shareById(id: Long) {
//        repository.shareById(id)
//    }

    val data = repository.getAll()
    fun removeByID(id: Long) = repository.removeByID(id)

    private val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
            edited.value = empty
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        edited.value?.let {
            if (it.content == content) {
                return
            }
            edited.value = it.copy(content = content)
        }
    }

}