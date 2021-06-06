package com.angelica.myfavs.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelica.myfavs.models.Description
import com.angelica.myfavs.models.Favorite
import com.angelica.myfavs.services.APIRepository
import com.angelica.myfavs.services.FavoritesRepository
import kotlinx.coroutines.launch

class DetailViewModel(val repository: APIRepository, val favoritesRepository: FavoritesRepository) :
    ViewModel() {

    var description = MutableLiveData<Description>()
    var isFavorite = MutableLiveData<Boolean>()

    fun getDescriptions(id: String) {
        try {
            viewModelScope.launch {
                description.postValue(repository.getAllDescription(id))
            }
        } catch (ex: Exception) {
            Log.i("TAG", "getDescriptions: $ex")
        }
    }

    fun alreadyFav(id: String) {
        viewModelScope.launch {
            val fav = favoritesRepository.getFavorite(id)
            if (fav == null) {
                isFavorite.postValue(false)
            } else {
                isFavorite.postValue(true)
            }
        }
    }

    fun deleteFavorito(id: String) {
        viewModelScope.launch {
            favoritesRepository.deleteFavorite(id)
        }
        isFavorite.postValue(false)
    }

    fun saveFavorite(fav: Favorite) {
        viewModelScope.launch {
            favoritesRepository.addFavorite(fav)
        }
        isFavorite.postValue(true)
    }
}