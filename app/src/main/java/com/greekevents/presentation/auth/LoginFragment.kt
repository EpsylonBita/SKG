package com.greekevents.presentation.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment that handles user login functionality.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {
    
    companion object {
        private const val TAG = "LoginFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: Creating placeholder view")
        
        try {
            // Simple placeholder UI until implementation is complete
            return TextView(requireContext()).apply {
                text = "Login Screen - Coming Soon"
                textSize = 18f
                gravity = android.view.Gravity.CENTER
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creating login view", e)
            // Fallback view in case of error
            return TextView(requireContext()).apply {
                text = "Unable to load login screen"
                textSize = 16f
                gravity = android.view.Gravity.CENTER
            }
        }
    }
} 