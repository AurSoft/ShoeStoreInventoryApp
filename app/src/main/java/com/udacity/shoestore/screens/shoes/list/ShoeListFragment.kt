package com.udacity.shoestore.screens.shoes.list

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ListRowBinding
import com.udacity.shoestore.databinding.ShoeListFragmentBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.screens.shoes.SharedShoeViewModel
import org.xmlpull.v1.XmlPullParser

class ShoeListFragment : Fragment() {
    private lateinit var viewModel: ShoeListViewModel
    private lateinit var binding: ShoeListFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.shoe_list_fragment, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(ShoeListViewModel::class.java)
        val sharedShoeViewModel: SharedShoeViewModel = ViewModelProvider(requireActivity()).get(SharedShoeViewModel::class.java)
        binding.lifecycleOwner = this

        //Navigate to new shoe fragment
        val action = ShoeListFragmentDirections.actionShoeListFragmentToNewShoeFragment()
        binding.shoeListFab.setOnClickListener {
            it.findNavController().navigate(action)
        }

        //Add new shoe to list
        sharedShoeViewModel.newShoe.observe(viewLifecycleOwner, Observer { newShoe ->
            viewModel.addShoe(newShoe)
        })

        //When user changes, drop list
        sharedShoeViewModel.newUser.observe(viewLifecycleOwner, Observer { newUser ->
            if (newUser) {
                viewModel.dropList()
                binding.listContainer.removeAllViews()
                binding.listContainer.invalidate()
                sharedShoeViewModel.newUser.value = false
            }
        })

        //if list is updated, update layout
        viewModel.shoeList.observe(viewLifecycleOwner, Observer { shoeList ->
            shoeList.forEach { shoe ->
                val shoeItemBinding = DataBindingUtil.inflate<ListRowBinding>(inflater, R.layout.list_row, container, false)
                shoeItemBinding.shoe = shoe
                if (shoe.images.isNotEmpty()) {
                    val imageUri: Uri = Uri.parse(shoe.images.first())
                    shoeItemBinding.shoeRowImage.setImageURI(imageUri)
                } else {
                    shoeItemBinding.shoeRowImage.setImageResource(R.drawable.ic_sneakers)
                }
                shoeItemBinding.root.setOnClickListener {
                    sharedShoeViewModel.selectShoe(shoe)
                    val toShoeDetailsFragment = ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailsFragment()
                    it.findNavController().navigate(toShoeDetailsFragment)
                }
                binding.listContainer.addView(shoeItemBinding.root)
            }
        })
        return binding.root
    }
}