package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.repository.InMemoryPostRepository
import ru.netology.nmedia.repository.PostRepository

class PostViewModel: ViewModel() {
    private val repository: PostRepository = InMemoryPostRepository()
    fun like(){
        repository.like()

    }
    fun share(){
        repository.share()
    }
    val data: LiveData<Post> = repository.data
}