package com.angelica.myfavs.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelica.myfavs.models.Description
import com.angelica.myfavs.services.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel(id: String) : ViewModel() {

    var description = MutableLiveData<Description>()

    init {
        viewModelScope.launch {
            getDescriptions(id)
        }
    }

    private suspend fun getDescriptions(id: String) {
        try {
            description.postValue(Repository.getAllDescription(id))
        } catch (ex: Exception) {
            Log.i("TAG", "getDescriptions: $ex")
        }
    }
}