package com.angelica.myfavs.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelica.myfavs.R
import com.angelica.myfavs.adapter.MoviesAdapter
import com.angelica.myfavs.databinding.FragmentSearchBinding
import com.angelica.myfavs.services.Repository

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    private val recyclerView by lazy {
        binding.rvSearch
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        Thread(Runnable {
            loadRecyclerView()
        }).start()

        return binding.root
    }

    private fun loadRecyclerView() {
        val resultadoAPI = Repository.lista("soldier")

        resultadoAPI?.search?.let {
            recyclerView.post {
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = MoviesAdapter(it)
            }
        }

    }
}