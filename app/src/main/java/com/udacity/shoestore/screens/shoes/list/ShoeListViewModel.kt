package com.udacity.shoestore.screens.shoes.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class ShoeListViewModel : ViewModel() {
    private val _shoeList = MutableLiveData<List<Shoe>>()
    val shoeList: LiveData<List<Shoe>>
        get() = _shoeList

    fun addShoe(shoe: Shoe) {
        if (getLastShoe() != shoe) _shoeList.addNewItem(shoe)
    }

    private fun getLastShoe() : Shoe? {
        if(_shoeList.value?.isNotEmpty() == true) return _shoeList.value?.last()
        return null
    }

    fun dropList() {
        _shoeList.value = listOf()
    }

    private fun <T> MutableLiveData<List<T>>.addNewItem(item: T) {
        val items: MutableList<T> = this.value?.toMutableList() ?: mutableListOf()
        items.add(item)
        this.value = items
    }
}