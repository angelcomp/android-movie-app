package com.angelica.myfavs.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.angelica.myfavs.R
import com.angelica.myfavs.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        var item = args

        binding.fabFavorite.setOnClickListener {
            binding.fabFavorite.setImageResource(R.drawable.ic_full_star)
        }

        //Glide.with(this).load(item.poster).into(binding.posterDetail)

        return binding.root
    }

}