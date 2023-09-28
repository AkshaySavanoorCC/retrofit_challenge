package com.akshay.retrofit_challenge.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akshay.retrofit_challenge.R
import com.akshay.retrofit_challenge.database.MultimediaDatabase
import com.akshay.retrofit_challenge.databinding.ActivityRetrofitTaskBinding
import com.akshay.retrofit_challenge.databinding.FragmentVideoBinding
import com.akshay.retrofit_challenge.factory.MediaViewModelFactory
import com.akshay.retrofit_challenge.model.MultiMediaModel
import com.akshay.retrofit_challenge.repository.MainRepository
import com.akshay.retrofit_challenge.ui.adapters.MultimediaAdapter
import com.akshay.retrofit_challenge.ui.viewmodels.MultimediaViewModel
import kotlinx.coroutines.launch

class VideoFragment : Fragment() {
    private lateinit var binding : FragmentVideoBinding
    private lateinit var videoViewModel: MultimediaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = MultimediaDatabase.getDataBaseDetails(requireContext())
        val repository = MainRepository(database.mediaDao())
        val mediaViewModelFactory = MediaViewModelFactory(repository)
        videoViewModel = ViewModelProvider(this, mediaViewModelFactory)[MultimediaViewModel::class.java]

        videoViewModel.addDataToDb()

        val thumbnailClicked : (MultiMediaModel) -> Unit = {
                selectedMedia ->
            val action = VideoFragmentDirections.actionVideoFragmentToDetailedVideoFragment(mediaId = selectedMedia.id)
            findNavController().navigate(action)


        }

        val adapter =  MultimediaAdapter(thumbnailClicked)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        videoViewModel.allMediaFiles.observe(viewLifecycleOwner){
        adapter.submitList(it)
        }


    }


}