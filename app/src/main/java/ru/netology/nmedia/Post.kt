package ru.netology.nmedia


data class Post(
    val id: Long = 0L,
    val author: String,
    val published: String,
    val content: String,
    var likedByMe: Boolean = false,
    var likeCounter: Int,
    var sharesCounter: Int,
    var looksCounter: Int
)
