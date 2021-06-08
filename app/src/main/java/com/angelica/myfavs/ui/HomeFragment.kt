package com.angelica.myfavs.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelica.myfavs.R
import com.angelica.myfavs.adapter.HomeAdapter
import com.angelica.myfavs.databinding.FragmentHomeBinding
import com.angelica.myfavs.models.Favorite
import com.angelica.myfavs.models.Search
import com.angelica.myfavs.viewmodel.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), HomeAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var adapter: HomeAdapter
    private var isAdapterCreated = false
    private var list: List<Favorite> = listOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        viewModel.getAll()
        viewModel.listFavorites.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                list = it
                loadRecyclerView()
                binding.tvEmpty.visibility = GONE
            } else {
                binding.tvEmpty.visibility = VISIBLE
            }
        })
//        viewModel.deleteAll()

        if (!isAdapterCreated) {
            adapter = HomeAdapter(this)
            isAdapterCreated = true
        }

        val recyclerView = binding.rvFavMovies
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun loadRecyclerView() {
        adapter.updateList(list)
    }

    override fun favoriteClick(position: Int) {

    }
}