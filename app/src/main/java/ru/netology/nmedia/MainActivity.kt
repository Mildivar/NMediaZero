package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostAdapter
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
        val adapter = PostAdapter {
            viewModel.likeById(it.id)
            viewModel.shareById(it.id)
        }
        binding.root.adapter = adapter
        viewModel.data.observe(this, adapter::submitList)
    }
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
