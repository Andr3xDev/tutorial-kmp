package com.jetbrains.kmpapp.screens.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
 * Main composable for the character list screen
 * Displays a list of Rick & Morty characters with loading state
 *
 * @param navigateToDetails Callback to navigate to detail screen with character ID
 */
@Composable
fun ListScreen(
    navigateToDetails: (objectId: Int) -> Unit
) {
    // Get ViewModel instance from Koin DI
    val viewModel = koinViewModel<ListViewModel>()

    // Collect StateFlow as Compose State (automatically updates UI on changes)
    // 'by' delegate converts StateFlow to State for direct property access
    val characters by viewModel.characters.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    // Box allows stacking composables (list + loading indicator)
    Box(modifier = Modifier.fillMaxSize()) {
        // AnimatedContent provides smooth transition between empty/loaded states
        AnimatedContent(characters.isNotEmpty()) { charactersAvailable ->
            if (charactersAvailable) {
                // Show character list when data is available
                CharacterList(
                    characters = characters,
                    onCharacterClick = navigateToDetails,
                )
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
 * Scrollable list of characters using LazyColumn (like RecyclerView)
 * Only renders visible items for performance
 *
 * @param characters List of characters to display
 * @param onCharacterClick Callback when a character is clicked
 * @param modifier Optional modifier for customization
 */
@Composable
private fun CharacterList(
    characters: List<RickAndMortyCharacter>,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    // LazyColumn is efficient - only composes visible items
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        // Add padding for safe areas (notches, navigation bars)
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
    ) {
        // Single header item at the top
        item {
            Text(
                text = "Rick & Morty Characters",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Generate items from the characters list
        // key = { it.id } helps LazyColumn optimize recomposition
        items(characters, key = { it.id }) { character ->
            CharacterCard(
                character = character,
                onClick = { onCharacterClick(character.id) },
            )
        }
    }
}

/**
 * Individual character card displaying character info
 * Uses Material3 Card with elevation and rounded corners
 *
 * @param character The character data to display
 * @param onClick Callback when card is clicked
 * @param modifier Optional modifier for customization
 */
@Composable
private fun CharacterCard(
    character: RickAndMortyCharacter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Material3 Card provides elevation and shape
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() }, // Make entire card clickable
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        // Row layout: image on left, text on right
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // AsyncImage loads image from URL asynchronously using Coil
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Crop, // Fill the space, crop if needed
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape) // Make image circular
                    .background(Color.LightGray), // Placeholder background while loading
            )

            Spacer(Modifier.width(16.dp))

            // Column for text content (takes remaining space with weight)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Character name - bold and large
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(4.dp))

                // Status and species with dynamic color based on status
                Text(
                    text = "${character.status} - ${character.species}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = when (character.status.lowercase()) {
                        "alive" -> Color(0xFF4CAF50)  // Green for alive
                        "dead" -> Color(0xFFF44336)   // Red for dead
                        else -> Color.Gray             // Gray for unknown
                    }
                )

                Spacer(Modifier.height(2.dp))

                // Current location in gray
                Text(
                    text = "Location: ${character.location.name}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}
