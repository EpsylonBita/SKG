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
import androidx.recyclerview.widget.GridLayoutManager
import com.greekevents.R
import com.greekevents.databinding.FragmentHomeBinding
import com.greekevents.presentation.home.adapter.EventAdapter
import com.greekevents.presentation.home.adapter.EventCategoryAdapter
import com.greekevents.presentation.util.SpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.google.android.material.button.MaterialButton

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
        // Create spacing decorations
        val horizontalSpacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val itemDecoration = SpacingItemDecoration(horizontalSpacing, includeEdge = true)
        
        // Set up recommended events RecyclerView
        binding.recommendedEventsRecyclerView.apply {
            adapter = recommendedEventsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            if (itemDecorationCount == 0) {
                addItemDecoration(itemDecoration)
            }
        }
        
        // Set up popular events RecyclerView
        binding.popularEventsRecyclerView.apply {
            adapter = popularEventsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            if (itemDecorationCount == 0) {
                addItemDecoration(itemDecoration)
            }
        }
        
        // Set up nearby events RecyclerView
        binding.nearbyEventsRecyclerView.apply {
            adapter = nearbyEventsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            if (itemDecorationCount == 0) {
                addItemDecoration(itemDecoration)
            }
        }
        
        // Set up categories RecyclerView with GridLayoutManager
        binding.categoriesRecyclerView.apply {
            adapter = categoryAdapter
            // Calculate the number of columns based on screen width
            val spanCount = calculateOptimalSpanCount()
            layoutManager = GridLayoutManager(requireContext(), spanCount, GridLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            if (itemDecorationCount == 0) {
                addItemDecoration(itemDecoration)
            }
        }
        
        // Apply custom animations to RecyclerViews
        applyRecyclerViewAnimations()
    }
    
    /**
     * Applies custom animations to RecyclerViews for a more engaging UI
     */
    private fun applyRecyclerViewAnimations() {
        val recyclerViews = listOf(
            binding.recommendedEventsRecyclerView,
            binding.popularEventsRecyclerView,
            binding.nearbyEventsRecyclerView,
            binding.categoriesRecyclerView
        )
        
        recyclerViews.forEach { recyclerView ->
            recyclerView.itemAnimator?.apply {
                // Set custom durations for animations
                addDuration = 300
                removeDuration = 300
                moveDuration = 300
                changeDuration = 300
            }
        }
    }
    
    /**
     * Calculates the optimal number of columns for the grid based on screen width
     * This ensures the grid looks good on different screen sizes
     */
    private fun calculateOptimalSpanCount(): Int {
        // Get the screen width
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels / displayMetrics.density
        
        // Each category card is approximately 160dp wide plus margins
        val cardWidth = 180f // 160dp card + 20dp margins
        
        // Calculate how many cards can fit in the screen width
        val spanCount = (screenWidth / cardWidth).toInt()
        
        // Return at least 1 column, at most 3
        return spanCount.coerceIn(1, 3)
    }
    
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Collect loading state
                launch {
                    viewModel.isLoading.collectLatest { isLoading ->
                        updateLoadingState(isLoading)
                    }
                }
                
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
                
                // Collect error messages
                launch {
                    viewModel.errorMessage.collectLatest { errorMessage ->
                        errorMessage?.let {
                            // Show error message
                            binding.errorView.visibility = View.VISIBLE
                            binding.errorTextView.text = it
                            binding.contentContainer.visibility = View.GONE
                            binding.shimmerLayout.visibility = View.GONE
                            binding.shimmerLayout.stopShimmer()
                        } ?: run {
                            binding.errorView.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Updates the UI based on loading state
     */
    private fun updateLoadingState(isLoading: Boolean) {
        if (isLoading) {
            // Show shimmer effect
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmer()
            binding.contentContainer.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.errorView.visibility = View.GONE
        } else {
            // Hide shimmer effect and show content
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility = View.GONE
            binding.contentContainer.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }
    
    private fun setupListeners() {
        // Set up "See All" buttons to navigate to respective list screens
        (binding.recommendedEventsHeader.getRoot().findViewById<View>(R.id.see_all_button) as? MaterialButton)?.setOnClickListener {
            // Navigate to all recommended events
            // Implementation will depend on navigation design
        }
        
        (binding.popularEventsHeader.getRoot().findViewById<View>(R.id.see_all_button) as? MaterialButton)?.setOnClickListener {
            // Navigate to all popular events
        }
        
        (binding.nearbyEventsHeader.getRoot().findViewById<View>(R.id.see_all_button) as? MaterialButton)?.setOnClickListener {
            // Navigate to all nearby events
        }
        
        (binding.categoriesHeader.getRoot().findViewById<View>(R.id.see_all_button) as? MaterialButton)?.setOnClickListener {
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