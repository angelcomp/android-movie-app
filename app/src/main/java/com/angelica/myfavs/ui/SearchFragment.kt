package com.angelica.myfavs.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.angelica.myfavs.R
import com.angelica.myfavs.adapter.SearchAdapter
import com.angelica.myfavs.databinding.FragmentSearchBinding
import com.angelica.myfavs.models.Search
import com.angelica.myfavs.utils.Snackbar
import com.angelica.myfavs.viewmodel.SearchViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), SearchAdapter.OnItemClickListener {

    private var CLICK_LUPA_TOOLBAR = false
    private var NUM_PAGINA: Int = 1
    private var MAX_PAGINA: Int = 2
    private var searchType: String = ""
    private var list: List<Search> = listOf()
    private var totalResults: Int = 1
    private var isAdapterCreated = false

    private lateinit var binding: FragmentSearchBinding

    private lateinit var adapter: SearchAdapter

    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isAdapterCreated) {
            adapter = SearchAdapter(this)
            isAdapterCreated = true
        }

        val recyclerView = binding.rvSearch
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        viewModel.resultAPI.observe(viewLifecycleOwner, {
            if (it.response) {
                list = it.searches
                totalResults = it.totalResults
                loadRecyclerView()
            } else {
                val msg = getString(R.string.snackbar, it.error)
                val snackbar = Snackbar(view, msg)
                snackbar.showSnackbar()
            }
        })

        //voltar tela
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        //lupa toolbar
        binding.fabSearch.setOnClickListener {
            viewModel.changeVisibility(!CLICK_LUPA_TOOLBAR)
            changeVisibility()
        }

        binding.includeCard.btnSearch.setOnClickListener {
            hideKeyboard()
            if (isNetworkAvailable()) {
                startSearch()
                //valor do text view que informa a página de pesquisa atual
                setPageValue()
                //esconder o card de pesquisa
                viewModel.changeVisibility(!CLICK_LUPA_TOOLBAR)
                changeVisibility()
            } else {
                alertDialog()
            }
        }

        binding.includeButtonPages.tvNumPages.text = NUM_PAGINA.toString()

        //avançar páginas da pesquisa
        binding.includeButtonPages.nextPage.setOnClickListener {
            nextPage()
        }

        //voltar páginas da pesquisa
        binding.includeButtonPages.previousPage.setOnClickListener {
            previousPage()
        }

        viewModel.isSearchClicked.observe(viewLifecycleOwner, {
            CLICK_LUPA_TOOLBAR = it
        })
    }

    private fun alertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.alert)
        builder.setMessage(R.string.alert_text)
        builder.setIcon(R.drawable.ic_warning)

        builder.setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
            findNavController().popBackStack()
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.setCancelable(false)
        alert.show()
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    override fun onResume() {
        changeVisibility()
        loadRecyclerView()
        super.onResume()
    }

    private fun startSearch() {
        val pesquisa = binding.includeCard.etInputSearch.text.toString()
        val anoLancamento = binding.includeCard.numYear.text.toString()
        getRadioGroupValue()

        viewModel.getAPI(pesquisa, NUM_PAGINA, searchType, anoLancamento)
    }

    private fun loadRecyclerView() {
        setMaxPages(totalResults)
        adapter.updateList(list)
    }

    override fun movieClick(position: Int) {
        if (isNetworkAvailable()) {
            val item = SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                viewModel.resultAPI.value!!.searches[position].imdbID
            )
            findNavController().navigate(item)
        } else {
            alertDialog()
        }
    }

    private fun setPageValue() {
        NUM_PAGINA = if (!binding.includeCard.numPages.text.isNullOrEmpty()) {
            //se user informar algum valor no campo 'Year' da pesquisa
            binding.includeCard.numPages.text.toString()
                .toInt() // NUM_PAG recebe o valor da pág digitada na pesquisa
        } else {
            //default
            1
        }
        // o textview que representa o numero da pág atual precisa receber o valor da pagina que foi digitada na pesquisa
        binding.includeButtonPages.tvNumPages.text = NUM_PAGINA.toString()
    }

    private fun getRadioGroupValue() {
        when (binding.includeCard.radioGroup.checkedRadioButtonId) {
            binding.includeCard.rbAllTypes.id -> searchType = ""
            binding.includeCard.rbMovie.id -> searchType = "movie"
            binding.includeCard.rbGame.id -> searchType = "game"
            binding.includeCard.rbSeries.id -> searchType = "series"
        }
    }

    private fun setMaxPages(totalResults: Int) {
        val restoDiv = totalResults.rem(10)
        MAX_PAGINA = if (restoDiv == 0) {
            totalResults / 10
        } else {
            totalResults.div(10) + 1
        }
    }

    private fun nextPage() {
        if (NUM_PAGINA == MAX_PAGINA) {
            Toast.makeText(context, getString(R.string.final_page), Toast.LENGTH_SHORT).show()
        } else {
            NUM_PAGINA += 1
            binding.includeButtonPages.tvNumPages.text = NUM_PAGINA.toString()
            startSearch()
        }
    }

    private fun previousPage() {
        if (NUM_PAGINA > 1) {
            NUM_PAGINA -= 1
            binding.includeButtonPages.tvNumPages.text = NUM_PAGINA.toString()
            startSearch()
        } else {
            Toast.makeText(context, getString(R.string.first_page), Toast.LENGTH_SHORT).show()
        }
    }

    //controla os componentes que aparecem e somem ao clicar na lupa da toolbar
    private fun changeVisibility() {
        if (!CLICK_LUPA_TOOLBAR) {
            //card que contém todos os campos para filtrar a pesquisa está visivel
            binding.includeCard.card.visibility = VISIBLE
            // botões para avançar ou voltar paginas ocultos
            binding.includeButtonPages.pageButtons.visibility = GONE
            binding.fabSearch.setImageResource(R.drawable.ic_search_off)
        } else {
            //card que contém todos os campos para filtrar a pesquisa está oculto
            binding.includeCard.card.visibility = GONE
            // botões para avançar ou voltar paginas visiveis
            binding.includeButtonPages.pageButtons.visibility = VISIBLE
            binding.fabSearch.setImageResource(R.drawable.ic_search)
        }
    }

    private fun hideKeyboard() {
        val inputManager =
            view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val binder = requireView().windowToken
        inputManager.hideSoftInputFromWindow(
            binder,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}
