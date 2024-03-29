package com.angelica.myfavs.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.angelica.myfavs.R
import com.angelica.myfavs.databinding.FragmentDetailBinding
import com.angelica.myfavs.models.Description
import com.angelica.myfavs.models.Favorite
import com.angelica.myfavs.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModel.alreadyFav(args.ID)

        viewModel.isFavorite.observe(viewLifecycleOwner, { isFav ->
            if (isFav) {
                binding.fabFavorite.setImageResource(R.drawable.ic_full_star)
            } else {
                binding.fabFavorite.setImageResource(R.drawable.ic_star)
            }
        })

        viewModel.getDescriptions(args.ID)
        viewModel.description.observe(viewLifecycleOwner, { description ->
            setLayoutValues(description)
        })

        //voltar tela
        binding.toolbarDetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.fabFavorite.setOnClickListener {
            if (viewModel.isFavorite.value!!) {
                viewModel.deleteFavorito(args.ID)
            } else {
                viewModel.saveFavorite(createFavorite())
            }
        }

        return binding.root
    }

    private fun createFavorite(): Favorite {
        var favorite: Favorite
        viewModel.description.value.let {
            favorite = Favorite(it!!.imdbID, it.title, it.plot, it.type)
        }
        return favorite
    }

    private fun setLayoutValues(description: Description) {
        var progress_bar = progressbar(this.requireContext())
        Glide.with(this).load(description.poster).placeholder(progress_bar)
            .error(R.drawable.img_error).into(binding.posterDetail)

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

    private fun progressbar(context: Context): CircularProgressDrawable {
        var prog = CircularProgressDrawable(context)
        prog.strokeWidth = 7f
        prog.centerRadius = 90f
        val accent = ContextCompat.getColor(context, R.color.purple_700)
        val white = ContextCompat.getColor(context, R.color.purple_200)
        prog.setColorSchemeColors(accent, white)
        prog.start()
        return prog
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
        if (imdbRating != "N/A") {
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
            }
        } else {
            binding.includeDescription.tvImdbRating.text = "-"
            binding.includeDescription.cardRating.setCardBackgroundColor(
                resources.getColor(
                    R.color.gray
                )
            )
        }
    }
}