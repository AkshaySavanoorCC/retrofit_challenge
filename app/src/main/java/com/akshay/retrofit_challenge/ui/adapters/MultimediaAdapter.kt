package com.akshay.retrofit_challenge.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.akshay.retrofit_challenge.databinding.MultimediaCardBinding
import com.akshay.retrofit_challenge.model.MultiMediaModel
import com.squareup.picasso.Picasso

class MultimediaAdapter(private val thumbnailClicked:(MultiMediaModel)->Unit) : androidx.recyclerview.widget.ListAdapter<MultiMediaModel, MultimediaAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val binding: MultimediaCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val thumbnailImage : ImageView = binding.thumbnailImageView

        fun bind(video: MultiMediaModel) {
            binding.apply {
                Picasso.get().load(video.thumbnailUrl).into(thumbnailImageView)
            durationTextView.text = video.duration
            titleTextView.text = video.title
            authorsTextView.text = video.author
            viewTextView.text = "${video.views} views"
            uploadTextView.text = video.uploadTime

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: MultimediaCardBinding = MultimediaCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.thumbnailImage.setOnClickListener{
            thumbnailClicked(current)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<MultiMediaModel>() {
            override fun areItemsTheSame(oldItem: MultiMediaModel, newItem: MultiMediaModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MultiMediaModel, newItem: MultiMediaModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}