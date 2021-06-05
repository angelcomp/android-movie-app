package com.angelica.myfavs.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelica.myfavs.models.ResultadoAPI
import com.angelica.myfavs.services.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchViewModel : ViewModel() {

    var resultAPI = MutableLiveData<ResultadoAPI>()
    var isSearchClicked = MutableLiveData<Boolean>(false)

    fun getAPI(
        search: String,
        pagina: Int,
        tipo_pesquisa: String,
        anoLancamento: String
    ) {

        try {
            viewModelScope.launch {
                val res = Repository.getLista(search, pagina, tipo_pesquisa, anoLancamento)
                res?.let {
                    resultAPI.value = it
                }
            }

        } catch (ex: Exception) {
            Log.i("TAG", "getDescriptions: $ex")
        }
    }

    fun changeVisibility(value: Boolean) {
        isSearchClicked.value = value
    }
}