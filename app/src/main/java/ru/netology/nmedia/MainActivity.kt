package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
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
        val adapter = PostAdapter(
            object :ActionListener{
                override fun onLikeClick(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShareClick(post: Post) {
                    viewModel.shareById(post.id)
                }

                override fun onRemoveClick(post: Post) {
                    viewModel.removeByID(post.id)
                }

                override fun onEditClick(post: Post) {
                    viewModel.edit(post)
                }

            }
//            likeClickListener = { viewModel.likeById(it.id) },
//            shareClickListener = { viewModel.shareById(it.id) },
//            removeListener = { (viewModel.removeByID(it.id)) }
        )
        binding.list.adapter = adapter
        viewModel.data.observe(this, adapter::submitList)

        viewModel.edited.observe(this){
            if (it.id ==0L){
                return@observe
            }

            with (binding.content){
                requestFocus()
                setText(it.content)
            }
        }

        binding.save.setOnClickListener{
            val text = binding.content.text.toString()
            if (binding.content.text.isNullOrBlank()){
                Toast.makeText(this,"Content is empty",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.changeContent(text)
            viewModel.save()

            binding.content.clearFocus()
            binding.content.setText("")

            AndroidUtils.hideKeyboard(binding.content)
        }
    }
}


