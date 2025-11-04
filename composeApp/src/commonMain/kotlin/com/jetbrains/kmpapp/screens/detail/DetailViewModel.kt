package com.jetbrains.kmpapp.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.data.RickAndMortyCharacter
import com.jetbrains.kmpapp.data.RickAndMortyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the character detail screen
 * Manages the state and business logic for displaying a single character's details
 */
class DetailViewModel(private val rickAndMortyRepository: RickAndMortyRepository) : ViewModel() {
    // Private mutable state for the character - nullable because it may not be loaded yet
    private val _character = MutableStateFlow<RickAndMortyCharacter?>(null)
    // Public read-only state that UI can observe
    val character: StateFlow<RickAndMortyCharacter?> = _character.asStateFlow()

    // Private mutable state for loading indicator
    private val _isLoading = MutableStateFlow(false)
    // Public read-only state for loading status
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    /**
     * Loads a specific character by ID from the repository
     * @param characterId The ID of the character to load
     */
    fun loadCharacter(characterId: Int) {
        // Launch a coroutine in the ViewModel scope (lifecycle-aware)
        viewModelScope.launch {
            // Show loading indicator
            _isLoading.value = true

            // Fetch character from repository (suspend function, runs asynchronously)
            val result = rickAndMortyRepository.getCharacterById(characterId)

            // Update the character state with fetched data (may be null if not found)
            _character.value = result

            // Hide loading indicator
            _isLoading.value = false
        }
    }
}