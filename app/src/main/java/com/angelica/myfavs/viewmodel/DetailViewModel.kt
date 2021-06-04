package com.angelica.myfavs.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angelica.myfavs.models.Description
import com.angelica.myfavs.services.Repository
import java.lang.Exception

class DetailViewModel(id: String) : ViewModel() {

    var description = MutableLiveData<Description>()

    init {
        Thread(Runnable {
            getDescriptions(id)
        }).start()
    }

    private fun getDescriptions(id: String) {
        try {
            description.postValue(Repository.getAllDescription(id))
        } catch (ex: Exception) {
            Log.i("TAG", "getDescriptions: $ex")
        }
    }
}