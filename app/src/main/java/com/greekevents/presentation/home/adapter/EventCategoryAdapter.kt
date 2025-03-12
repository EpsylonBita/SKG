package com.greekevents.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.greekevents.R
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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    /**
     * ViewHolder for category items. Responsible for binding category data to the views.
     */
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryIcon: ImageView = itemView.findViewById(R.id.categoryIcon)
        private val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        private val categoryDescription: TextView = itemView.findViewById(R.id.categoryDescription)
        
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
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
            // Set category name and description
            categoryName.text = category.name
            categoryDescription.text = category.description ?: ""
            
            // Load category image with enhanced visual effects
            if (!category.imageUrl.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(category.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade(300))
                    .apply(RequestOptions().centerCrop())
                    .into(categoryIcon)
            } else {
                // If no image URL, load a background based on category type
                loadCategoryBackground(category.name)
            }
        }
        
        /**
         * Loads an appropriate background image based on the category name
         */
        private fun loadCategoryBackground(categoryName: String) {
            val backgroundUrl = getCategoryBackgroundUrl(categoryName)
            val fallbackDrawable = getCategoryIconByName(categoryName)
            
            Glide.with(itemView.context)
                .load(backgroundUrl)
                .placeholder(fallbackDrawable)
                .error(fallbackDrawable)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .apply(RequestOptions().centerCrop())
                .into(categoryIcon)
        }
        
        /**
         * Gets a background image URL based on category name
         */
        private fun getCategoryBackgroundUrl(categoryName: String): String? {
            return when {
                categoryName.contains("Music", ignoreCase = true) -> 
                    "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=500&q=80"
                categoryName.contains("Sports", ignoreCase = true) -> 
                    "https://images.unsplash.com/photo-1461896836934-ffe607ba8211?w=500&q=80"
                categoryName.contains("Culture", ignoreCase = true) -> 
                    "https://images.unsplash.com/photo-1566127992631-137a642a90f4?w=500&q=80"
                categoryName.contains("Art", ignoreCase = true) -> 
                    "https://images.unsplash.com/photo-1460661419201-fd4cecdf8a8b?w=500&q=80"
                categoryName.contains("Food", ignoreCase = true) -> 
                    "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?w=500&q=80"
                categoryName.contains("Education", ignoreCase = true) -> 
                    "https://images.unsplash.com/photo-1503676260728-1c00da094a0b?w=500&q=80"
                else -> null
            }
        }
        
        /**
         * Gets the appropriate icon resource ID based on the category name.
         * 
         * @param categoryName The name of the category
         * @return The icon resource ID
         */
        private fun getCategoryIconByName(categoryName: String): Int {
            return when {
                categoryName.contains("Music", ignoreCase = true) -> R.drawable.ic_music
                categoryName.contains("Sports", ignoreCase = true) -> R.drawable.ic_sports
                categoryName.contains("Culture", ignoreCase = true) -> R.drawable.ic_culture
                categoryName.contains("Art", ignoreCase = true) -> R.drawable.ic_art
                categoryName.contains("Food", ignoreCase = true) -> R.drawable.ic_food
                categoryName.contains("Education", ignoreCase = true) -> R.drawable.ic_education
                else -> R.drawable.ic_category_placeholder
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