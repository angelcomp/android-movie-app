package com.angelica.myfavs.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelica.myfavs.models.Favorite
import com.angelica.myfavs.services.APIRepository
import com.angelica.myfavs.services.FavoritesRepository
import com.angelica.myfavs.utils.ResponseWrapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoritosViewModel(val repository: APIRepository, val favoritesRepository: FavoritesRepository) : ViewModel() {

    val loading = MutableLiveData<Int>()

    val listFavorites = MutableLiveData<List<Favorite>>()

    fun deleteFavorito(favorito: Favorite) {
        viewModelScope.launch {
            favoritesRepository.deleteFavorito(favorito)
        }
    }

    fun getAll() {
        viewModelScope.launch {
            favoritesRepository.getAll()
                .collect { fav ->
                    when (fav.status) {
                        ResponseWrapper.Status.LOADING -> loading.value = View.VISIBLE
                        else -> {
                            fav.data?.let {
                                listFavorites.value = it
                            }
                        }
                    }
                }
        }
    }
}
