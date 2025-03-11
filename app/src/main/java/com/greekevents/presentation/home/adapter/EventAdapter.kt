package com.greekevents.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.greekevents.R
import com.greekevents.databinding.ItemEventBinding
import com.greekevents.domain.model.Event
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * RecyclerView adapter for displaying events in a list or grid.
 * 
 * @param onEventClick Callback function that is invoked when an event is clicked.
 *                    It receives the event ID as a parameter.
 */
class EventAdapter(
    private val onEventClick: (String) -> Unit
) : ListAdapter<Event, EventAdapter.EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    /**
     * ViewHolder for event items. Responsible for binding event data to the views.
     */
    inner class EventViewHolder(
        private val binding: ItemEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val event = getItem(position)
                    onEventClick(event.id)
                }
            }
        }

        /**
         * Binds event data to the views in the ViewHolder.
         * 
         * @param event The event to display
         */
        fun bind(event: Event) {
            // Bind event data to views
            binding.eventTitleTextView.text = event.title
            
            // Format and display date
            val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("el", "GR"))
            binding.eventDateTextView.text = dateFormat.format(event.startDate)
            
            // Display location
            binding.eventLocationTextView.text = event.location.city
            
            // Display category
            binding.eventCategoryTextView.text = event.categoryName
            
            // Load event image with Glide
            Glide.with(binding.eventImageView)
                .load(event.imageUrl)
                .placeholder(R.drawable.placeholder_event)
                .error(R.drawable.placeholder_event)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(binding.eventImageView)
            
            // Display price or "Free"
            if (event.isFree) {
                binding.eventPriceTextView.text = binding.root.context.getString(R.string.free)
            } else {
                event.price?.let { price ->
                    binding.eventPriceTextView.text = String.format("%.2f€", price)
                } ?: run {
                    binding.eventPriceTextView.text = ""
                }
            }
            
            // Set favorite icon state
            binding.favoriteButton.isSelected = event.isFavorite
            
            // Set up favorite button click
            binding.favoriteButton.setOnClickListener {
                // In a real app, we would toggle favorite status here
                // and notify the ViewModel to update the repository
            }
        }
    }

    /**
     * DiffUtil callback class that helps calculate the difference between two lists
     * efficiently to update only the items that changed.
     */
    class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }
}

/* Explanation for junior developers:
 * 1. ListAdapter vs. RecyclerView.Adapter:
 *    - ListAdapter is a specialized RecyclerView.Adapter that handles list updates efficiently
 *    - It uses DiffUtil to calculate and animate changes between lists
 * 
 * 2. DiffUtil:
 *    - EventDiffCallback tells the adapter how to determine if items are the same
 *    - areItemsTheSame checks if two items represent the same entity (based on ID)
 *    - areContentsTheSame checks if two items have the same content (using data class equals)
 * 
 * 3. ViewBinding:
 *    - We use view binding to access views in a type-safe way
 *    - ItemEventBinding is generated from the item_event.xml layout
 * 
 * 4. ViewHolder Pattern:
 *    - EventViewHolder holds references to views to avoid findViewById calls
 *    - bind() method updates the views with data from an Event object
 * 
 * 5. Click Listeners:
 *    - We use a lambda (onEventClick) to handle item clicks
 *    - The click listener is set in the ViewHolder's init block
 *    - bindingAdapterPosition gets the current position in the adapter
 * 
 * 6. Glide Image Loading:
 *    - Glide is a popular image loading library
 *    - It handles loading, caching, and transforming images efficiently
 *    - placeholder and error images are shown while loading or on error
 */ 