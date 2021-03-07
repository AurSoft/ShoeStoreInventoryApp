package com.udacity.shoestore.screens.shoes.details

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ShoeDetailsFragmentBinding
import com.udacity.shoestore.screens.shoes.SharedShoeViewModel
import kotlinx.android.synthetic.main.shoe_details_fragment.*

class ShoeDetailsFragment : Fragment() {
    private lateinit var binding: ShoeDetailsFragmentBinding
    private var imageIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.shoe_details_fragment,
                container, false)
        val sharedShoeViewModel: SharedShoeViewModel = ViewModelProvider(requireActivity()).get(
            SharedShoeViewModel::class.java)
        binding.lifecycleOwner = this
        binding.shoeViewModel = sharedShoeViewModel
        if (sharedShoeViewModel.selectedShoe.value?.images?.isNotEmpty() == true) {
            val imageUri: Uri = Uri.parse(sharedShoeViewModel.selectedShoe.value?.images?.first())
            binding.shoeImages.setImageURI(imageUri)
        }

        binding.nextImageButton.setOnClickListener {
            if (sharedShoeViewModel.selectedShoe.value?.images?.isNotEmpty() == true) {
                val imagesUris = sharedShoeViewModel.selectedShoe.value?.images!!
                if (imageIndex == imagesUris.size - 1) imageIndex = 0 else imageIndex++
                val imageUri: Uri = Uri.parse(imagesUris[imageIndex])
                binding.shoeImages.setImageURI(imageUri)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}