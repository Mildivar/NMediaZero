package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.ActionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()

        val newPostContract = registerForActivityResult(NewPostActivity.Contract()) { result ->
            result?.let {
                viewModel.changeContent(it)
                viewModel.save()
            }

        }

        val adapter = PostAdapter(
            object : ActionListener {
                override fun onLikeClick(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShareClick(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, post.content)
                    }
                    val shareIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(shareIntent)
                }

                override fun onRemoveClick(post: Post) {
                    viewModel.removeByID(post.id)
                }

                override fun onEditClick(post: Post) {
                    newPostContract.launch(post.content)
                    viewModel.edit(post)

                }

                override fun onLookClick(post: Post) {
                    viewModel.lookById(post.id)
                }

            }
//            likeClickListener = { viewModel.likeById(it.id) },
//            shareClickListener = { viewModel.shareById(it.id) },
//            removeListener = { (viewModel.removeByID(it.id)) }
        )
        binding.list.adapter = adapter
        viewModel.data.observe(this, adapter::submitList)

        binding.add.setOnClickListener {
            newPostContract.launch("")
        }

    }
}


