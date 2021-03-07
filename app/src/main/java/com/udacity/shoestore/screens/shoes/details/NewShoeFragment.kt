package com.udacity.shoestore.screens.shoes.details

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.NewShoeFragmentBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.screens.shoes.SharedShoeViewModel


class NewShoeFragment: Fragment() {
    private lateinit var binding: NewShoeFragmentBinding
    private val RESULT_LOAD_IMAGE: Int = 1
    private val imageUris: MutableList<String> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.new_shoe_fragment,
                container, false)
        val sharedShoeViewModel: SharedShoeViewModel = ViewModelProvider(requireActivity()).get(SharedShoeViewModel::class.java)
        binding.lifecycleOwner = this

        val action = NewShoeFragmentDirections.actionNewShoeFragmentToShoeListFragment()

        binding.fabClose.setOnClickListener {
            it.findNavController().navigate(action)
        }

        binding.fabSaveShoe.setOnClickListener {
            val shoe = newShoe()
            if (allNecessaryFieldsFilled(shoe)) {
                sharedShoeViewModel.addNewShoe(shoe)
                it.findNavController().navigate(action)
            } else {
                Toast.makeText(context, R.string.fill_all_text, Toast.LENGTH_LONG).show()
            }
        }

        binding.addImageButton.setOnClickListener {
            val i = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(i, RESULT_LOAD_IMAGE)
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            val selectedImage: Uri = data!!.data!!
            imageUris.add(selectedImage.toString())
            binding.addImageButton.text = resources.getText(R.string.add_another_image_text)
        }
    }

    private fun newShoe() : Shoe {
        var size = 0.0
        if (binding.editTextSize.text.isNotEmpty()) size = binding.editTextSize.text.toString().toDouble()
        return Shoe(binding.editTextShoeName.text.toString(),
                size,
                binding.editTextCompany.text.toString(),
                binding.editTextDescription.text.toString(),
                imageUris)
    }

    private fun allNecessaryFieldsFilled(shoe: Shoe): Boolean {
        if (shoe.company.isEmpty()) return false
        if (shoe.name.isEmpty()) return false
        if (shoe.size == 0.0) return false
        return true
    }
}