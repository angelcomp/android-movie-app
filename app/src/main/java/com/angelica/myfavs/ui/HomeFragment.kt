package com.angelica.myfavs.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.angelica.myfavs.databinding.FragmentHomeBinding
import com.angelica.myfavs.services.Repository

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        Thread(Runnable {
            val repository = Repository.lista("sun")
            Log.i("TAG", "onCreateView: $repository")
        }).start()

        return binding.root
    }

}