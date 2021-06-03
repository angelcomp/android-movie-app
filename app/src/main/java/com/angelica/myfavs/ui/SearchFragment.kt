package com.angelica.myfavs.ui

import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.angelica.myfavs.R
import com.angelica.myfavs.adapter.MoviesAdapter
import com.angelica.myfavs.databinding.FragmentSearchBinding
import com.angelica.myfavs.services.Repository

class SearchFragment : Fragment() {

    private var CLICK_LUPA_TOOLBAR = true
    private var NUM_PAGINA: Int = 1
    private var MAX_PAGINA: Int = 2
    private var tipo_pesquisa: String = ""

    lateinit var binding: FragmentSearchBinding
    private val recyclerView by lazy {
        binding.rvSearch
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        //voltar tela
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        //lupa toolbar
        binding.fabSearch.setOnClickListener {
            changeVisibility()
        }

        binding.includeCard.btnPesquisar.setOnClickListener {
            startSearch()
            if (!binding.includeCard.numPag.text.isNullOrEmpty()) {
                NUM_PAGINA = binding.includeCard.numPag.text.toString().toInt() // NUM_PAG recebe o valor da pág digitada na pesquisa
                binding.includeButtonPages.tvNumPaginas.text = binding.includeCard.numPag.text // o textview que representa o numero da pág atual precisa receber o valor da pagina que foi digitada na pesquisa
            } else {
                NUM_PAGINA = 1
                binding.includeButtonPages.tvNumPaginas.text = NUM_PAGINA.toString()
            }
            changeVisibility()
        }

        //avançar páginas da pesquisa
        binding.includeButtonPages.nextPage.setOnClickListener {
            nextPage()
        }

        //voltar páginas da pesquisa
        binding.includeButtonPages.previousPage.setOnClickListener {
            previousPage()
        }

        return binding.root
    }

    private fun startSearch() {
        val pesquisa = binding.includeCard.etInputSearch.text.toString()
        val anoLancamento = binding.includeCard.numYear.text.toString()
        getRadioGroupValue()

        Thread(Runnable {
            loadRecyclerView(pesquisa, tipo_pesquisa, anoLancamento)
        }).start()
    }

    private fun getRadioGroupValue() {
        val id = binding.includeCard.radioGroup.checkedRadioButtonId
        when(id) {
            binding.includeCard.rbAllTypes.id -> tipo_pesquisa = ""
            binding.includeCard.rbMovie.id -> tipo_pesquisa = "movie"
            binding.includeCard.rbGame.id -> tipo_pesquisa = "game"
            binding.includeCard.rbSerie.id -> tipo_pesquisa = "series"
        }
    }

    private fun loadRecyclerView(pesquisa: String, tipo_pesquisa: String, anoLancamento: String) {
        val resultadoAPI = Repository.getLista(pesquisa, NUM_PAGINA, tipo_pesquisa, anoLancamento)
        if (resultadoAPI?.search != null) {
            setMaxPages(resultadoAPI.totalResults)
            resultadoAPI.search.let {
                recyclerView.post {
                    recyclerView.layoutManager = GridLayoutManager(context, 2)
                    recyclerView.adapter = MoviesAdapter(it)
                }
            }
        } else {
            Looper.prepare()
            Toast.makeText(context, "Não foi possível realizar a pesquisa, tente novamente", Toast.LENGTH_LONG).show()
        }
    }

    private fun setMaxPages(totalResults: Int) {
        val restoDiv = totalResults.rem(10)
        if (restoDiv == 0) {
            MAX_PAGINA = totalResults / 10
        } else {
            MAX_PAGINA = totalResults.div(10) +1
        }
    }

    private fun nextPage() {
        if (NUM_PAGINA == MAX_PAGINA) {
            Toast.makeText(context, "Não há mais páginas para pesquisar!", Toast.LENGTH_LONG).show()
        } else {
            NUM_PAGINA += 1
            binding.includeButtonPages.tvNumPaginas.text = NUM_PAGINA.toString()
            startSearch()
        }
    }

    private fun previousPage() {
        if (NUM_PAGINA > 1) {
            NUM_PAGINA -= 1
            binding.includeButtonPages.tvNumPaginas.text = NUM_PAGINA.toString()
            startSearch()
        } else {
            Toast.makeText(context, "Você já está no início!", Toast.LENGTH_LONG).show()
        }
    }

    //controla os componentes que aparecem e somem ao clicar na lupa da toolbar
    private fun changeVisibility() {
        if (!CLICK_LUPA_TOOLBAR) {
            CLICK_LUPA_TOOLBAR = true //lupa da toolbar está "ativa" (foi clicada e card aparecerá)
            binding.includeCard.card.visibility = VISIBLE //card que contém todos os campos para filtrar a pesquisa está visivel
            binding.includeButtonPages.pageButtons.visibility = GONE // botões para avançar ou voltar paginas ocultos
            binding.fabSearch.setImageResource(R.drawable.ic_search_off)
        } else {
            CLICK_LUPA_TOOLBAR = false //lupa da toolbar está "desativada" (foi clicada e card ocultará)
            binding.includeCard.card.visibility = GONE //card que contém todos os campos para filtrar a pesquisa está oculto
            binding.includeButtonPages.pageButtons.visibility = VISIBLE // botões para avançar ou voltar paginas visiveis
            binding.fabSearch.setImageResource(R.drawable.ic_search)
        }
    }
}