package ru.netology.nmedia


data class Post(
    val id: Long = 0L,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean = false,
    val likeCounter: Int,
    val sharesCounter: Int,
    val looksCounter: Int,
    val video:String
)
