package com.greekevents.presentation.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A custom ItemDecoration that adds spacing between items in a RecyclerView.
 * Works with both LinearLayoutManager and GridLayoutManager.
 *
 * @param spacing The spacing to add between items in pixels
 * @param includeEdge Whether to include spacing at the edges of the RecyclerView
 */
class SpacingItemDecoration(
    private val spacing: Int,
    private val includeEdge: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager
        val position = parent.getChildAdapterPosition(view)
        
        when (layoutManager) {
            is GridLayoutManager -> applyGridSpacing(outRect, position, layoutManager)
            is LinearLayoutManager -> applyLinearSpacing(outRect, position, parent.adapter?.itemCount ?: 0, layoutManager)
            else -> applyDefaultSpacing(outRect)
        }
    }
    
    private fun applyGridSpacing(outRect: Rect, position: Int, layoutManager: GridLayoutManager) {
        val spanCount = layoutManager.spanCount
        val column = position % spanCount
        
        if (includeEdge) {
            // Add spacing to all sides
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount
            
            if (position < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            // Add spacing between items only
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            
            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }
    
    private fun applyLinearSpacing(
        outRect: Rect, 
        position: Int, 
        itemCount: Int,
        layoutManager: LinearLayoutManager
    ) {
        val isHorizontal = layoutManager.orientation == LinearLayoutManager.HORIZONTAL
        
        if (isHorizontal) {
            if (includeEdge) {
                outRect.left = if (position == 0) spacing else spacing / 2
                outRect.right = if (position == itemCount - 1) spacing else spacing / 2
            } else {
                outRect.left = 0
                outRect.right = if (position < itemCount - 1) spacing else 0
            }
        } else {
            if (includeEdge) {
                outRect.top = if (position == 0) spacing else spacing / 2
                outRect.bottom = if (position == itemCount - 1) spacing else spacing / 2
            } else {
                outRect.top = 0
                outRect.bottom = if (position < itemCount - 1) spacing else 0
            }
        }
    }
    
    private fun applyDefaultSpacing(outRect: Rect) {
        outRect.left = spacing
        outRect.right = spacing
        outRect.top = spacing
        outRect.bottom = spacing
    }
} 