package com.greekevents.presentation.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.greekevents.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment that displays the user profile and account settings.
 */
@AndroidEntryPoint
class ProfileFragment : Fragment() {
    
    companion object {
        private const val TAG = "ProfileFragment"
    }

    private lateinit var profileImage: ShapeableImageView
    private lateinit var userName: MaterialTextView
    private lateinit var userEmail: MaterialTextView
    private lateinit var editProfileButton: MaterialButton
    private lateinit var myEventsButton: MaterialButton
    private lateinit var settingsButton: MaterialButton
    private lateinit var logoutButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: Creating profile view")
        
        try {
            val view = inflater.inflate(R.layout.fragment_profile, container, false)
            initializeViews(view)
            return view
        } catch (e: Exception) {
            Log.e(TAG, "Error creating profile view", e)
            throw e
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: Setting up profile view")
        
        setupClickListeners()
        loadUserProfile()
    }

    private fun initializeViews(view: View) {
        profileImage = view.findViewById(R.id.profileImage)
        userName = view.findViewById(R.id.userName)
        userEmail = view.findViewById(R.id.userEmail)
        editProfileButton = view.findViewById(R.id.editProfileButton)
        myEventsButton = view.findViewById(R.id.myEventsButton)
        settingsButton = view.findViewById(R.id.settingsButton)
        logoutButton = view.findViewById(R.id.logoutButton)
    }

    private fun setupClickListeners() {
        editProfileButton.setOnClickListener {
            navigateToEditProfile()
        }

        myEventsButton.setOnClickListener {
            navigateToMyEvents()
        }

        settingsButton.setOnClickListener {
            navigateToSettings()
        }

        logoutButton.setOnClickListener {
            handleLogout()
        }
    }

    private fun loadUserProfile() {
        // TODO: Load user profile data from ViewModel
        // For now, using placeholder data
        userName.text = "John Doe"
        userEmail.text = "john.doe@example.com"
    }

    private fun navigateToEditProfile() {
        val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
        findNavController().navigate(action)
    }

    private fun navigateToMyEvents() {
        val action = ProfileFragmentDirections.actionProfileFragmentToMyEventsFragment()
        findNavController().navigate(action)
    }

    private fun navigateToSettings() {
        val action = ProfileFragmentDirections.actionProfileFragmentToSettingsFragment()
        findNavController().navigate(action)
    }

    private fun handleLogout() {
        // TODO: Implement logout logic
        // For now, just navigate to login screen
        val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
        findNavController().navigate(action)
    }
} 