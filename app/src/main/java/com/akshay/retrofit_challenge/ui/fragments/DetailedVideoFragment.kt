package com.akshay.retrofit_challenge.ui.fragments


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.SimpleExoPlayer

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import android.widget.MediaController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshay.retrofit_challenge.database.MultimediaDatabase
import com.akshay.retrofit_challenge.databinding.FragmentDetailedVideoBinding
import com.akshay.retrofit_challenge.factory.MediaViewModelFactory
import com.akshay.retrofit_challenge.model.MultiMediaModel
import com.akshay.retrofit_challenge.repository.MainRepository
import com.akshay.retrofit_challenge.ui.adapters.MultimediaAdapter
import com.akshay.retrofit_challenge.ui.viewmodels.MultimediaViewModel
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailedVideoFragment : Fragment() {
    private lateinit var binding: FragmentDetailedVideoBinding
    private lateinit var videoViewModel: MultimediaViewModel
    private val args: DetailedVideoFragmentArgs by navArgs()
    private lateinit var player: SimpleExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailedVideoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = MultimediaDatabase.getDataBaseDetails(requireContext())
        val repository = MainRepository(database.mediaDao())
        val mediaViewModelFactory = MediaViewModelFactory(repository)
        videoViewModel = ViewModelProvider(this, mediaViewModelFactory)[MultimediaViewModel::class.java]
        val mediaId = args.mediaId

        lifecycleScope.launch {
            videoViewModel.getCurrentMedia(mediaId)

        }
        val thumbnailClicked : (MultiMediaModel) -> Unit = {
                selectedMedia ->
            player.stop()
            videoViewModel.updateCurrentVideo(selectedMedia)
        }


        val adapter =  MultimediaAdapter(thumbnailClicked)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)
        videoViewModel.allMediaFiles.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

       videoViewModel.currentVideo.observe(viewLifecycleOwner){
         binding.apply {
         titleTextView.text = it?.title
         authorTextView.text = it?.author
         durationTextView.text = it?.uploadTime
         descriptionTextView.text = it?.description
         likesTextView.text = "${it?.views} views"
             val uri = Uri.parse(it?.videoUrl)
              player = SimpleExoPlayer.Builder(requireContext()).build()
             videoViews.player = player
             val mediaItem = MediaItem.fromUri(uri)
             player.setMediaItem(mediaItem)
             player.prepare()
             player.play()
         }
       }
        binding.uploadedBtnTextView.setOnClickListener {
            binding.apply {
                descriptionTextView.visibility = View.VISIBLE
                uploadedBtnTextView.visibility = View.GONE
            }
        }
        binding.descriptionTextView.setOnClickListener {
            binding.apply {
                descriptionTextView.visibility = View.GONE
                uploadedBtnTextView.visibility = View.VISIBLE
            }
        }
    }
}