package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.counter
import ru.netology.nmedia.databinding.CardPostBinding

typealias LikeClickListener = (Post) -> Unit
typealias ShareClickListener = (Post) -> Unit

class PostAdapter(
    private val likeClickListener: LikeClickListener,
    private val shareClickListener: ShareClickListener
) : ListAdapter<Post, PostViewHolder>(PostDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            binding = CardPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            likeClickListener = likeClickListener,
            shareClickListener = shareClickListener,
        )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val likeClickListener: LikeClickListener,
    private val shareClickListener: ShareClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe) likes.setImageResource(R.drawable.ic_liked_24)
            else likes.setImageResource(R.drawable.ic_like_24)
            likesCounter.text = counter(post.likeCounter)
            sharesCounter.text = counter(post.sharesCounter)
            looksCounter.text = post.looksCounter.toString()

            likes.setOnClickListener { likeClickListener(post) }
            share.setOnClickListener { shareClickListener(post) }
        }
    }
}

class PostDiffItemCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem == newItem
}