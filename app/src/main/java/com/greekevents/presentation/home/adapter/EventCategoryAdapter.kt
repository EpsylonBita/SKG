package com.greekevents.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.greekevents.R
import com.greekevents.databinding.ItemCategoryBinding
import com.greekevents.domain.model.Category

/**
 * RecyclerView adapter for displaying event categories.
 * 
 * @param onCategoryClick Callback function that is invoked when a category is clicked.
 *                       It receives the category ID and name as parameters.
 */
class EventCategoryAdapter(
    private val onCategoryClick: (String, String) -> Unit
) : ListAdapter<Category, EventCategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    /**
     * ViewHolder for category items. Responsible for binding category data to the views.
     */
    inner class CategoryViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val category = getItem(position)
                    onCategoryClick(category.id, category.name)
                }
            }
        }

        /**
         * Binds category data to the views in the ViewHolder.
         * 
         * @param category The category to display
         */
        fun bind(category: Category) {
            // Set category name
            binding.categoryNameTextView.text = category.name
            
            // Load category image if available, or use a placeholder
            Glide.with(binding.categoryImageView)
                .load(category.imageUrl)
                .placeholder(R.drawable.placeholder_category)
                .error(R.drawable.placeholder_category)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(binding.categoryImageView)
            
            // Set category background color if available
            category.colorResId?.let { colorResId ->
                try {
                    val color = ContextCompat.getColor(binding.root.context, colorResId)
                    binding.categoryBackground.setBackgroundColor(color)
                } catch (e: Exception) {
                    // Fallback to default color if resource not found
                    binding.categoryBackground.setBackgroundResource(R.color.category_other)
                }
            } ?: run {
                // Use default category colors based on category name
                val colorResId = getCategoryColorByName(category.name)
                binding.categoryBackground.setBackgroundResource(colorResId)
            }
        }
        
        /**
         * Gets the appropriate color resource ID based on the category name.
         * 
         * @param categoryName The name of the category
         * @return The color resource ID
         */
        private fun getCategoryColorByName(categoryName: String): Int {
            return when {
                categoryName.contains("Μουσική", ignoreCase = true) -> R.color.category_music
                categoryName.contains("Αθλητικά", ignoreCase = true) -> R.color.category_sports
                categoryName.contains("Πολιτισμός", ignoreCase = true) -> R.color.category_culture
                categoryName.contains("Τέχνη", ignoreCase = true) -> R.color.category_art
                categoryName.contains("Γαστρονομία", ignoreCase = true) -> R.color.category_food
                categoryName.contains("Θέατρο", ignoreCase = true) -> R.color.category_theater
                categoryName.contains("Σινεμά", ignoreCase = true) -> R.color.category_cinema
                categoryName.contains("Εκπαίδευση", ignoreCase = true) -> R.color.category_education
                categoryName.contains("Τεχνολογία", ignoreCase = true) -> R.color.category_technology
                else -> R.color.category_other
            }
        }
    }

    /**
     * DiffUtil callback class that helps calculate the difference between two lists
     * efficiently to update only the items that changed.
     */
    class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}

/* Explanation for junior developers:
 * 1. ListAdapter:
 *    - This adapter extends ListAdapter, which handles list updates efficiently
 *    - It uses DiffUtil to calculate differences between old and new lists
 * 
 * 2. ViewBinding:
 *    - ItemCategoryBinding is generated from item_category.xml layout
 *    - It provides type-safe access to views without findViewById()
 * 
 * 3. Lambda callback:
 *    - onCategoryClick is a lambda function passed to the adapter's constructor
 *    - It allows the Fragment to handle click events without coupling to the adapter
 *    - It passes both ID and name to avoid another lookup when navigating
 * 
 * 4. ViewHolder Pattern:
 *    - CategoryViewHolder caches view references for better performance
 *    - The ViewHolder's init block sets up click listeners
 *    - The bind() method updates views with category data
 * 
 * 5. Glide for image loading:
 *    - Handles asynchronous image loading, caching, and transformations
 *    - Shows placeholders during loading and on errors
 * 
 * 6. Dynamic styling:
 *    - Categories can have their own colors defined in colorResId
 *    - If not provided, we determine color based on category name
 *    - ContextCompat is used for backward compatibility when getting colors
 */ 