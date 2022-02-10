package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likesCounter.text = counter(post.likeCounter)
            likes.setOnClickListener {
                post.likedByMe = !post.likedByMe
                val image = if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                post.likeCounter = if (post.likedByMe) {
                    ++post.likeCounter
                } else {
                    --post.likeCounter
                }
                likesCounter.text = counter(post.likeCounter)
                likes.setImageResource(image)
            }
            sharesCounter.text = counter(post.sharesCounter)
            share.setOnClickListener {
                val share = ++post.sharesCounter
                sharesCounter.text = counter(share)
            }
            looksCounter.text = post.looksCounter.toString()
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
}