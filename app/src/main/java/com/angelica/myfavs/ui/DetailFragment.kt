package com.angelica.myfavs.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.angelica.myfavs.R
import com.angelica.myfavs.databinding.FragmentDetailBinding
import com.angelica.myfavs.models.Description
import com.angelica.myfavs.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import java.lang.Exception

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    private val viewModel by viewModels<DetailViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DetailViewModel(args.ID) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModel.description.observe(viewLifecycleOwner, { description ->
            setLayoutValues(description)
        })

        //voltar tela
        binding.toolbarDetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.fabFavorite.setOnClickListener {
            binding.fabFavorite.setImageResource(R.drawable.ic_full_star)
            //todo: favoritar
        }

        return binding.root
    }

    private fun setLayoutValues(description: Description) {
        Glide.with(this).load(description.poster).into(binding.posterDetail)

        checkEmptyValue(description.title, binding.includeDescription.tvName)
        checkEmptyValue(description.type, binding.includeDescription.tvType)
        setTextTypeColor(description.type)
        checkEmptyValue(description.imdbRating, binding.includeDescription.tvImdbRating)
        setRatingCardBackgroundColor(description.imdbRating)
        checkEmptyValue(description.plot, binding.includeDescription.tvPlot)
        checkEmptyValue(description.released, binding.includeDescription.tvReleased)
        checkEmptyValue(description.runtime, binding.includeDescription.tvRuntime)
        checkEmptyValue(description.totalSeasons, binding.includeDescription.tvSeason)
        checkEmptyValue(description.genre, binding.includeDescription.tvGenre)
        checkEmptyValue(description.country, binding.includeDescription.tvCountry)
        checkEmptyValue(description.language, binding.includeDescription.tvLanguage)
        checkEmptyValue(description.director, binding.includeDescription.tvDirector)
        checkEmptyValue(description.actors, binding.includeDescription.tvActors)
        checkEmptyValue(description.awards, binding.includeDescription.tvAwards)

        var allRatings = ""
        description.ratings.forEach {
            allRatings = allRatings + "Source: ${it.source}" + "\nValue: ${it.value}\n\n"
        }
        binding.includeDescription.tvRatings.text = allRatings
    }

    private fun checkEmptyValue(value: String?, view: TextView) {
        if (value == null || value == "N/A") {
            view.text = getString(R.string.no_information)
        } else {
            view.text = value
        }
    }

    private fun setTextTypeColor(type: String) {
        try {
            when (type) {
                "movie" -> binding.includeDescription.tvType.setTextColor(resources.getColor(R.color.red))
                "series" -> binding.includeDescription.tvType.setTextColor(resources.getColor(R.color.green))
                "game" -> binding.includeDescription.tvType.setTextColor(resources.getColor(R.color.blue))
                else -> binding.includeDescription.tvType.setTextColor(resources.getColor(R.color.black))
            }
        } catch (ex: Exception) {
            Log.i("Exception", "setTextTypeColor: $ex")
            Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG).show()
        }
    }

    private fun setRatingCardBackgroundColor(imdbRating: String) {
        try {
            when {
                imdbRating.toDouble() <= 3.0 -> {
                    binding.includeDescription.cardRating.setCardBackgroundColor(
                        resources.getColor(
                            R.color.card_red
                        )
                    )
                }
                imdbRating.toDouble() <= 6.0 -> {
                    binding.includeDescription.cardRating.setCardBackgroundColor(
                        resources.getColor(
                            R.color.card_orange
                        )
                    )
                }
                imdbRating.toDouble() <= 8.0 -> {
                    binding.includeDescription.cardRating.setCardBackgroundColor(
                        resources.getColor(
                            R.color.card_yellow
                        )
                    )
                }
                imdbRating.toDouble() <= 10.0 -> {
                    binding.includeDescription.cardRating.setCardBackgroundColor(
                        resources.getColor(
                            R.color.card_green
                        )
                    )
                }
                else -> {
                    binding.includeDescription.cardRating.setCardBackgroundColor(
                        resources.getColor(
                            R.color.gray
                        )
                    )
                }
            }
        } catch (ex: Exception) {
            Log.i("Exception", "setRatingCardBackgroundColor: $ex")
            Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG).show()
        }
    }
}