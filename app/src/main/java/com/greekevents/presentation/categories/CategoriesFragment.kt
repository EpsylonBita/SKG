package com.greekevents.presentation.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.greekevents.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment that displays event categories for users to browse.
 */
@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    
    companion object {
        private const val TAG = "CategoriesFragment"
    }

    private lateinit var categoriesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: Creating categories view")
        
        try {
            val view = inflater.inflate(R.layout.fragment_categories, container, false)
            categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView)
            return view
        } catch (e: Exception) {
            Log.e(TAG, "Error creating categories view", e)
            throw e
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: Setting up categories RecyclerView")
        
        setupCategoriesRecyclerView()
    }

    private fun setupCategoriesRecyclerView() {
        categoriesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        categoriesRecyclerView.adapter = CategoriesAdapter(getCategories()) { category ->
            navigateToCategoryEvents(category)
        }
    }

    private fun navigateToCategoryEvents(category: Category) {
        val action = CategoriesFragmentDirections.actionCategoriesFragmentToCategoryEventsFragment(
            categoryId = category.name.lowercase(),
            categoryName = category.name
        )
        findNavController().navigate(action)
    }

    private fun getCategories(): List<Category> {
        return listOf(
            Category("Music", "Live music events and concerts", R.drawable.ic_music),
            Category("Sports", "Sports events and tournaments", R.drawable.ic_sports),
            Category("Food", "Food festivals and culinary events", R.drawable.ic_food),
            Category("Art", "Art exhibitions and galleries", R.drawable.ic_art),
            Category("Culture", "Cultural events and festivals", R.drawable.ic_culture),
            Category("Education", "Educational workshops and seminars", R.drawable.ic_education)
        )
    }
}

data class Category(
    val name: String,
    val description: String,
    val iconResId: Int
) 