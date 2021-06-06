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
import java.lang.Exception

class DetailViewModel(val repository: APIRepository, val favoritesRepository: FavoritesRepository) : ViewModel() {

    var description = MutableLiveData<Description>()

    fun getDescriptions(id: String) {
        try {
            viewModelScope.launch {
                description.postValue(repository.getAllDescription(id))
            }
        } catch (ex: Exception) {
            Log.i("TAG", "getDescriptions: $ex")
        }
    }

    fun saveFavorite(id: String) {
        viewModelScope.launch {
            val fav = Favorite(id)
            favoritesRepository.addFavorito(fav)
        }
    }

    //todo funcao para inserir fav
}