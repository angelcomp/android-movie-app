package com.angelica.myfavs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelica.myfavs.models.Favorite
import com.angelica.myfavs.services.FavoritesRepository
import kotlinx.coroutines.launch

class HomeViewModel(val favoritesRepository: FavoritesRepository) : ViewModel() {

    val listFavorites = MutableLiveData<List<Favorite>>()


    fun deleteAll() {
        viewModelScope.launch {
            favoritesRepository.deleteAllFavorites()
        }
    }

    fun getAll() {
        viewModelScope.launch {
            favoritesRepository.getAll().let {
                listFavorites.postValue(it)
            }
        }
    }
}
