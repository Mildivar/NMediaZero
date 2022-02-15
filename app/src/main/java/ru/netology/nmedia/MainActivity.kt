package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import java.math.RoundingMode
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()
        with(binding) {
            viewModel.data.observe(this@MainActivity) { post ->

                author.text = post.author
                published.text = post.published
                content.text = post.content
                if (post.likedByMe) likes.setImageResource(R.drawable.ic_liked_24)
                else likes.setImageResource(R.drawable.ic_like_24)
                likesCounter.text = counter(post.likeCounter)

                sharesCounter.text = counter(post.sharesCounter)
                share.setOnClickListener {
                    viewModel.share()
                }
                looksCounter.text = post.looksCounter.toString()
            }
            likes.setOnClickListener {
                viewModel.like()
            }
        }
    }
}

private fun counter(item: Int): String {
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
