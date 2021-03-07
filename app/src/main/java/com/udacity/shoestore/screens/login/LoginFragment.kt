package com.udacity.shoestore.screens.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.LoginFragmentBinding
import com.udacity.shoestore.screens.shoes.SharedShoeViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var sharedViewModel: SharedShoeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.login_fragment, container, false)
        val sharedViewModel: SharedShoeViewModel = ViewModelProvider(requireActivity()).get(SharedShoeViewModel::class.java)

        binding.registerTextView.setOnClickListener {
            binding.loginOrRegister.setText(R.string.register_text)
            binding.registerTextView.visibility = View.GONE
        }

        binding.loginOrRegister.setOnClickListener {
            if (binding.editEmail.text.toString().isEmpty() || binding.editPassword.text.toString().isEmpty()) {
                Toast.makeText(context, R.string.fill_login, Toast.LENGTH_LONG).show()
            } else {
                sharedViewModel.setCurrentUser(binding.editEmail.text.toString())
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
            }
        }
        return binding.root
    }
}