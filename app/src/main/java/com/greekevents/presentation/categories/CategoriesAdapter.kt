package com.greekevents.presentation.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greekevents.R

class CategoriesAdapter(
    private val categories: List<Category>,
    private val onCategoryClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryIcon: ImageView = itemView.findViewById(R.id.categoryIcon)
        private val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        private val categoryDescription: TextView = itemView.findViewById(R.id.categoryDescription)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onCategoryClick(categories[position])
                }
            }
        }

        fun bind(category: Category) {
            categoryIcon.setImageResource(category.iconResId)
            categoryName.text = category.name
            categoryDescription.text = category.description
        }
    }
} 