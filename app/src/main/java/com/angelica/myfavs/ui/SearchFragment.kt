package com.angelica.myfavs.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.angelica.myfavs.R
import com.angelica.myfavs.adapter.MoviesAdapter
import com.angelica.myfavs.databinding.FragmentSearchBinding
import com.angelica.myfavs.services.Repository

class SearchFragment : Fragment() {

    private var clickFab = false

    lateinit var binding: FragmentSearchBinding
    private val recyclerView by lazy {
        binding.rvSearch
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.fabSearch.setOnClickListener {
            setFloatingBtnIcon()
        }

        return binding.root
    }


    private fun setFloatingBtnIcon() {
        if (!clickFab) {
            binding.searchMovie.visibility = VISIBLE
            clickFab = true
        } else {
            binding.searchMovie.visibility = GONE
            clickFab = false
            val pesquisa = binding.etInputSearch.text.toString()

            Thread(Runnable {
                loadRecyclerView(pesquisa)
            }).start()
        }
    }

    private fun loadRecyclerView(pesquisa: String) {
        val resultadoAPI = Repository.lista(pesquisa)

        resultadoAPI?.search?.let {
            recyclerView.post {
                recyclerView.layoutManager = GridLayoutManager(context, 2)
                recyclerView.adapter = MoviesAdapter(it)
            }
        }

    }
}