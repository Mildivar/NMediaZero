package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import java.math.RoundingMode
import java.text.DecimalFormat

interface ActionListener{
    fun onLikeClick(post:Post){}
    fun onShareClick(post:Post){}
    fun onRemoveClick(post:Post){}
    fun onEditClick(post:Post){}
    fun onLookClick(post:Post){}
}

class PostAdapter(
    private val actionListener: ActionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            binding = CardPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            actionListener
        )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

/*
ViewHolder — класс, содержащий информацию о визуальном
отображении конкретного элемента списка.
*/
class PostViewHolder(
    private val binding: CardPostBinding,
    private val actionListener: ActionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.isChecked = post.likedByMe
            likes.text = counter(post.likeCounter).toString()
//            if (post.likedByMe) likes.setImageResource(R.drawable.ic_liked_24)
//            else likes.setImageResource(R.drawable.ic_like_24)
//            likesCounter.text = counter(post.likeCounter)
            share.text = counter(post.sharesCounter)
            looks.text = post.looksCounter.toString()
            looks.setOnClickListener{actionListener.onLookClick(post)}
            likes.setOnClickListener { actionListener.onLikeClick(post) }
            share.setOnClickListener { actionListener.onShareClick(post) }
            menu.setOnClickListener {
                PopupMenu(binding.root.context, binding.menu).apply {
                    inflate(R.menu.post_menu)
                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.remove -> {
                                actionListener.onRemoveClick(post)
                                true
                            }
                            R.id.edit -> {
                                actionListener.onEditClick(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}

class PostDiffItemCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem == newItem
}
fun counter(item: Int): String {
    return when (item) {
        in 1000..9999 -> {
            val num = roundOffDecimal(item / 1000.0)
            (num + "K")
        }
        in 10_000..999_999 -> {
            ((item / 1000).toString() + "K")
        }
        in 1_000_000..1_000_000_000 -> {
            val num = roundOffDecimal(item / 1_000_000.0)
            (num + "M")
        }
        else -> item.toString()
    }

}

private fun roundOffDecimal(number: Double): String {
    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(number).toString()
}