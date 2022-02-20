package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun getAll():LiveData<List<Post>>
//    val data:LiveData<Post>
}