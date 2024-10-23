package com.skoda.launcher.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skoda.launcher.ui.adapter.SearchAdapter
import com.skoda.launcher.ui.adapter.SearchAdapter.SearchListener
import com.mapbox.bindgen.Expected
import com.mapbox.search.autocomplete.PlaceAutocomplete
import com.mapbox.search.autocomplete.PlaceAutocompleteSuggestion
import com.skoda.launcher.R
import com.skoda.launcher.model.SearchData


class SearchFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    val placeAutocomplete = PlaceAutocomplete.create()


    private lateinit var searchView: SearchView
    private lateinit var searchLisnter: SearchUiClickListener
    private var dataList: MutableList<SearchData> = mutableListOf()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchUiClickListener) {
            searchLisnter = context;
        } else if (parentFragment is SearchUiClickListener) {
            searchLisnter = parentFragment as SearchUiClickListener
        }
    }

    interface SearchUiClickListener {
        fun showNavigationUi(data: SearchData)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<ImageView>(R.id.back_button)
        recyclerView = view.findViewById(R.id.rvSearch)
        searchView = view.findViewById(R.id.search)
        backButton.setOnClickListener {
            parentFragment?.childFragmentManager?.popBackStack()
        }

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        setupAdapter()
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                dataList.clear()
                searchplace(newText!!)

                return false;
            }

        })
    }


    private fun setupAdapter() {
        recyclerView.adapter =
            SearchAdapter(dataList as ArrayList<SearchData>, object : SearchListener {
                override fun onClickItem(searchData: SearchData) {
                    lifecycleScope.launchWhenCreated {
                        val selectedSuggestion = searchData.raw;
                        val selectionResponse = placeAutocomplete.select(selectedSuggestion!!)
                        selectionResponse.onValue { result ->
                            searchData.coordinate = result.coordinate
                            searchLisnter.showNavigationUi(searchData)
                        }.onError { e ->
                            // process error
                        }
                    }
                }

            })
    }

    fun searchplace(text: String) {
        lifecycleScope.launchWhenCreated {
            val response: Expected<Exception, List<PlaceAutocompleteSuggestion>> =
                placeAutocomplete.suggestions(text)
            response.onValue { suggestions: List<PlaceAutocompleteSuggestion> ->
                processSuggestions(suggestions)
            }.onError { e ->
                e.printStackTrace()
            }

        }
    }

    private fun processSuggestions(suggestions: List<PlaceAutocompleteSuggestion>) {
        var suggestionlist = ArrayList<SearchData>()

        for (i in suggestions) {
            suggestionlist.add(SearchData(" " + i.formattedAddress, i.coordinate, i))
        }
        if (suggestions.isEmpty())
            suggestionlist.add(SearchData("No search found", null, null))

        dataList.clear()
        dataList.addAll(suggestionlist)
        recyclerView.adapter!!.notifyDataSetChanged()

    }


}