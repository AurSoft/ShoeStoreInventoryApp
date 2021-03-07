package com.udacity.shoestore.screens.shoes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class SharedShoeViewModel: ViewModel() {
    private var _selectedShoe =  MutableLiveData<Shoe>()
    val selectedShoe : LiveData<Shoe>
        get() = _selectedShoe
    private var _newShoe =  MutableLiveData<Shoe>()
    val newShoe : LiveData<Shoe>
        get() = _newShoe
    var newUser = MutableLiveData<Boolean>()
    private var currentUser = ""

    fun selectShoe(shoe: Shoe) {
        _selectedShoe.value = shoe
    }

    fun addNewShoe(shoe: Shoe) {
        _newShoe.value = shoe
    }

    fun setCurrentUser(email: String) {
        if (currentUser != email) {
            currentUser = email
            newUser.value = true
        }
    }

}