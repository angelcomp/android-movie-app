package com.angelica.myfavs.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.angelica.myfavs.adapter.MoviesAdapter
import com.angelica.myfavs.databinding.FragmentSearchBinding
import com.angelica.myfavs.services.Repository

class SearchFragment : Fragment() {

    private var clickLupaToolbar = true
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
            clickLupaToolbar()
        }
        
        binding.btnPesquisar.setOnClickListener {
            startSearch()
            binding.card.visibility = GONE //card que contém todos os campos para filtrar a pesquisa está oculto
            binding.pageButtons.visibility = VISIBLE // botões para avançar ou voltar paginas visiveis
            clickLupaToolbar = false
        }

        //avançar páginas da pesquisa
        binding.nextPage.setOnClickListener {
            if (NUM_PAGINA == MAX_PAGINA) {
                Toast.makeText(context, "Não há mais páginas para pesquisar!", Toast.LENGTH_LONG).show()
            } else {
                NUM_PAGINA += 1
                binding.tvNumPaginas.text = NUM_PAGINA.toString()
                startSearch()
            }
        }

        //voltar páginas da pesquisa
        binding.previousPage.setOnClickListener {
            if (NUM_PAGINA > 1) {
                NUM_PAGINA -= 1
                binding.tvNumPaginas.text = NUM_PAGINA.toString()
                startSearch()
            } else {
                Toast.makeText(context, "Você já está no início!", Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    //controla os componentes que aparecem e somem ao clicar na lupa da toolbar
    private fun clickLupaToolbar() {
        if (!clickLupaToolbar) {
            clickLupaToolbar = true //lupa da toolbar está "ativa" (foi clicada e card aparecerá)
            binding.card.visibility = VISIBLE //card que contém todos os campos para filtrar a pesquisa está visivel
            binding.pageButtons.visibility = GONE // botões para avançar ou voltar paginas ocultos
        } else {
            clickLupaToolbar = false //lupa da toolbar está "desativada" (foi clicada e card ocultará)
            binding.card.visibility = GONE //card que contém todos os campos para filtrar a pesquisa está oculto
            binding.pageButtons.visibility = VISIBLE // botões para avançar ou voltar paginas visiveis
        }
    }

    private fun startSearch() {
        val pesquisa = binding.etInputSearch.text.toString()
        getRadioGroup()

        Thread(Runnable {
            loadRecyclerView(pesquisa, tipo_pesquisa)
        }).start()
    }

    private fun getRadioGroup() {
        val id = binding.radioGroup.checkedRadioButtonId
        when(id) {
            binding.rbAllTypes.id -> tipo_pesquisa = ""
            binding.rbMovie.id -> tipo_pesquisa = "movie"
            binding.rbGame.id -> tipo_pesquisa = "game"
            binding.rbSerie.id ->tipo_pesquisa = "series"
        }
    }

    private fun loadRecyclerView(pesquisa: String, tipo_pesquisa: String) {
        val resultadoAPI = Repository.getLista(pesquisa, NUM_PAGINA, tipo_pesquisa)

        resultadoAPI?.search?.let {
            recyclerView.post {
                recyclerView.layoutManager = GridLayoutManager(context, 2)
                recyclerView.adapter = MoviesAdapter(it)
            }
        }
    }
}