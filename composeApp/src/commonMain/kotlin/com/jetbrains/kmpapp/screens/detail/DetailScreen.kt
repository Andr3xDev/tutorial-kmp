package com.jetbrains.kmpapp.screens.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.jetbrains.kmpapp.data.RickAndMortyCharacter
import com.jetbrains.kmpapp.screens.EmptyScreenContent
import org.koin.compose.viewmodel.koinViewModel

/**
 * Main composable for the character detail screen
 * Displays detailed information about a single Rick & Morty character
 *
 * @param objectId The ID of the character to display
 * @param navigateBack Callback to navigate back to the previous screen
 */
@Composable
fun DetailScreen(
    objectId: Int,
    navigateBack: () -> Unit,
) {
    // Get ViewModel instance from Koin DI
    val viewModel = koinViewModel<DetailViewModel>()

    // Collect StateFlow as Compose State (automatically updates UI on changes)
    val character by viewModel.character.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    // LaunchedEffect runs when the composable enters composition or when objectId changes
    // This is the right place for side effects like loading data
    LaunchedEffect(objectId) {
        viewModel.loadCharacter(objectId)
    }

    // Box allows stacking composables (content + loading indicator)
    Box(modifier = Modifier.fillMaxSize()) {
        // AnimatedContent provides smooth transition between loading/loaded states
        AnimatedContent(character != null) { characterAvailable ->
            if (characterAvailable) {
                // Show character details when data is loaded
                // !! is safe here because we checked character != null
                CharacterDetails(character!!, onBackClick = navigateBack)
            } else if (!isLoading) {
                // Show empty state when not loading and no data
                EmptyScreenContent(Modifier.fillMaxSize())
            }
        }

        // Show loading indicator centered on top of everything when loading
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

/**
 * Detailed view of a character with all information
 * Uses Scaffold for Material Design layout with TopAppBar
 *
 * @param character The character data to display
 * @param onBackClick Callback to navigate back
 * @param modifier Optional modifier for customization
 */
@Composable
private fun CharacterDetails(
    character: RickAndMortyCharacter,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Scaffold provides Material Design structure (TopBar + Content)
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text(character.name) },
                navigationIcon = {
                    // Back button in the top bar
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        },
        // Handle system bars (status bar, navigation bar)
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars),
    ) { paddingValues ->
        // Column with vertical scroll for all content
        Column(
            Modifier
                .verticalScroll(rememberScrollState()) // Enable scrolling
                .padding(paddingValues) // Respect Scaffold padding
                .fillMaxSize()
        ) {
            // Large circular character image at the top
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(Color.LightGray), // Background while image loads
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(300.dp)
                        .clip(CircleShape) // Make image circular
                )
            }

            Spacer(Modifier.height(16.dp))

            // All character information organized in cards
            Column(Modifier.padding(16.dp)) {
                // Character name as main title
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(16.dp))

                // Status card with dynamic color based on alive/dead status
                InfoCard(
                    title = "Status",
                    value = character.status,
                    color = when (character.status.lowercase()) {
                        "alive" -> Color(0xFF4CAF50)  // Green
                        "dead" -> Color(0xFFF44336)   // Red
                        else -> Color.Gray             // Gray for unknown
                    }
                )

                Spacer(Modifier.height(12.dp))

                // Species and Gender side by side in a Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoCard(
                        title = "Species",
                        value = character.species,
                        modifier = Modifier.weight(1f) // Take equal space
                    )
                    InfoCard(
                        title = "Gender",
                        value = character.gender,
                        modifier = Modifier.weight(1f) // Take equal space
                    )
                }

                // Only show Type card if character has a type
                if (character.type.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    InfoCard(title = "Type", value = character.type)
                }

                Spacer(Modifier.height(12.dp))

                // Origin location
                InfoCard(
                    title = "Origin",
                    value = character.origin.name
                )

                Spacer(Modifier.height(12.dp))

                // Current/last known location
                InfoCard(
                    title = "Last Location",
                    value = character.location.name
                )

                Spacer(Modifier.height(12.dp))

                // Number of episode appearances
                InfoCard(
                    title = "Episodes",
                    value = "${character.episode.size} appearances"
                )
            }
        }
    }
}

/**
 * Reusable card component for displaying labeled information
 * Used throughout the detail screen for consistent styling
 *
 * @param title The label/title of the information
 * @param value The actual value to display
 * @param modifier Optional modifier for customization
 * @param color Optional color for the value text (e.g., status color)
 */
@Composable
private fun InfoCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    color: Color? = null
) {
    // Material3 Card with subtle elevation
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Title/label in gray, smaller text
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            Spacer(Modifier.height(4.dp))
            // Value in larger text, optionally colored
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                // Use provided color or default theme color
                color = color ?: MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
