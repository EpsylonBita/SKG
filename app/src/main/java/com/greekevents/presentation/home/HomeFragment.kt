package com.greekevents.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.greekevents.R
import com.greekevents.databinding.FragmentHomeBinding
import com.greekevents.presentation.home.adapter.EventAdapter
import com.greekevents.presentation.home.adapter.EventCategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * HomeFragment displays the main screen of the application with personalized event 
 * recommendations, popular events, and event categories.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: HomeViewModel by viewModels()
    
    private val recommendedEventsAdapter = EventAdapter { eventId ->
        navigateToEventDetails(eventId)
    }
    
    private val popularEventsAdapter = EventAdapter { eventId ->
        navigateToEventDetails(eventId)
    }
    
    private val nearbyEventsAdapter = EventAdapter { eventId ->
        navigateToEventDetails(eventId)
    }
    
    private val categoryAdapter = EventCategoryAdapter { categoryId, categoryName ->
        navigateToCategoryEvents(categoryId, categoryName)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerViews()
        observeViewModel()
        setupListeners()
    }
    
    private fun setupRecyclerViews() {
        // Set up recommended events RecyclerView
        binding.recommendedEventsRecyclerView.apply {
            adapter = recommendedEventsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        
        // Set up popular events RecyclerView
        binding.popularEventsRecyclerView.apply {
            adapter = popularEventsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        
        // Set up nearby events RecyclerView
        binding.nearbyEventsRecyclerView.apply {
            adapter = nearbyEventsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        
        // Set up categories RecyclerView
        binding.categoriesRecyclerView.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
    
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Collect recommended events
                launch {
                    viewModel.recommendedEvents.collectLatest { events ->
                        recommendedEventsAdapter.submitList(events)
                        binding.recommendedEventsEmptyView.visibility = 
                            if (events.isEmpty()) View.VISIBLE else View.GONE
                    }
                }
                
                // Collect popular events
                launch {
                    viewModel.popularEvents.collectLatest { events ->
                        popularEventsAdapter.submitList(events)
                        binding.popularEventsEmptyView.visibility = 
                            if (events.isEmpty()) View.VISIBLE else View.GONE
                    }
                }
                
                // Collect nearby events
                launch {
                    viewModel.nearbyEvents.collectLatest { events ->
                        nearbyEventsAdapter.submitList(events)
                        binding.nearbyEventsEmptyView.visibility = 
                            if (events.isEmpty()) View.VISIBLE else View.GONE
                    }
                }
                
                // Collect categories
                launch {
                    viewModel.categories.collectLatest { categories ->
                        categoryAdapter.submitList(categories)
                    }
                }
                
                // Collect loading state
                launch {
                    viewModel.isLoading.collectLatest { isLoading ->
                        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                    }
                }
                
                // Collect error messages
                launch {
                    viewModel.errorMessage.collectLatest { errorMessage ->
                        errorMessage?.let {
                            // Show error message (could use a Snackbar or custom view)
                            binding.errorView.visibility = View.VISIBLE
                            binding.errorTextView.text = it
                        } ?: run {
                            binding.errorView.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
    
    private fun setupListeners() {
        // Set up "See All" buttons to navigate to respective list screens
        binding.seeAllRecommendedButton.setOnClickListener {
            // Navigate to all recommended events
            // Implementation will depend on navigation design
        }
        
        binding.seeAllPopularButton.setOnClickListener {
            // Navigate to all popular events
        }
        
        binding.seeAllNearbyButton.setOnClickListener {
            // Navigate to all nearby events
        }
        
        binding.seeAllCategoriesButton.setOnClickListener {
            // Navigate to categories screen
            findNavController().navigate(R.id.action_homeFragment_to_categoriesFragment)
        }
        
        // Set up pull-to-refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        
        // Set up retry button in error view
        binding.retryButton.setOnClickListener {
            viewModel.refreshData()
        }
    }
    
    private fun navigateToEventDetails(eventId: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToEventDetailsFragment(eventId)
        findNavController().navigate(action)
    }
    
    private fun navigateToCategoryEvents(categoryId: String, categoryName: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToCategoryEventsFragment(categoryId, categoryName)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

/* Explanation for junior developers:
 * 1. @AndroidEntryPoint annotation allows Hilt to inject dependencies into this fragment
 * 
 * 2. View Binding:
 *    - _binding holds the binding reference and can be null
 *    - The non-null binding property provides safe access to views
 *    - We set _binding to null in onDestroyView to prevent memory leaks
 * 
 * 3. ViewModel:
 *    - viewModels() delegate lazily creates the ViewModel instance
 *    - The ViewModel survives configuration changes (like screen rotation)
 *    - It holds the UI state and handles business logic
 * 
 * 4. Adapters:
 *    - Each RecyclerView has its own adapter for displaying different data
 *    - We pass click listeners to the adapters to handle item clicks
 * 
 * 5. Coroutines:
 *    - lifecycleScope.launch creates coroutines that are automatically canceled when the fragment is destroyed
 *    - repeatOnLifecycle ensures collection only happens when the fragment is at least in STARTED state
 *    - collectLatest processes only the most recent value when multiple values are emitted rapidly
 * 
 * 6. Navigation:
 *    - findNavController() gets the NavController associated with this fragment
 *    - We use SafeArgs (generated Directions classes) to create type-safe navigation actions
 */ 