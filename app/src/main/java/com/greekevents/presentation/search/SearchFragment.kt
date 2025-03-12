package com.greekevents.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greekevents.R
import com.greekevents.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * SearchFragment provides the search interface for users to find events.
 * It allows searching by name, location, and date.
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: SearchViewModel by viewModels()
    
    companion object {
        private const val TAG = "SearchFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: Initializing view")
        try {
            _binding = FragmentSearchBinding.inflate(inflater, container, false)
            return binding.root
        } catch (e: Exception) {
            Log.e(TAG, "Error inflating search fragment", e)
            // Create a fallback View if the binding fails
            return TextView(requireContext()).apply {
                text = "Search functionality is being developed"
                textSize = 16f
                gravity = android.view.Gravity.CENTER
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: Setting up UI elements")
        
        try {
            setupSearchButton()
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up search UI", e)
            Toast.makeText(context, "Search is temporarily unavailable", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupSearchButton() {
        binding.searchButton.setOnClickListener {
            val searchQuery = binding.searchEditText.text.toString().trim()
            
            if (searchQuery.isEmpty()) {
                Toast.makeText(context, "Please enter a search term", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // Log the search attempt
            Log.d(TAG, "Search button clicked with query: $searchQuery")
            
            try {
                // Navigate to search results
                val action = SearchFragmentDirections.actionSearchFragmentToSearchResultsFragment(
                    searchQuery = searchQuery,
                    searchLocation = null,
                    searchStartDate = null,
                    searchEndDate = null
                )
                
                // Use viewModel for any additional processing
                viewModel.search(searchQuery)
                
                findNavController().navigate(action)
            } catch (e: Exception) {
                Log.e(TAG, "Error navigating to search results", e)
                Toast.makeText(context, "Unable to perform search at this time", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 