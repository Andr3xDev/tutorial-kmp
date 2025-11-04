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

@Composable
fun DetailScreen(
    objectId: Int,
    navigateBack: () -> Unit,
) {
    val viewModel = koinViewModel<DetailViewModel>()
    val character by viewModel.character.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(objectId) {
        viewModel.loadCharacter(objectId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(character != null) { characterAvailable ->
            if (characterAvailable) {
                CharacterDetails(character!!, onBackClick = navigateBack)
            } else if (!isLoading) {
                EmptyScreenContent(Modifier.fillMaxSize())
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun CharacterDetails(
    character: RickAndMortyCharacter,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text(character.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        },
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars),
    ) { paddingValues ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Character Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(300.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(Modifier.height(16.dp))

            // Character Info
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(16.dp))

                // Status Card
                InfoCard(
                    title = "Status",
                    value = character.status,
                    color = when (character.status.lowercase()) {
                        "alive" -> Color(0xFF4CAF50)
                        "dead" -> Color(0xFFF44336)
                        else -> Color.Gray
                    }
                )

                Spacer(Modifier.height(12.dp))

                // Species and Gender
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoCard(
                        title = "Species",
                        value = character.species,
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        title = "Gender",
                        value = character.gender,
                        modifier = Modifier.weight(1f)
                    )
                }

                if (character.type.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    InfoCard(title = "Type", value = character.type)
                }

                Spacer(Modifier.height(12.dp))

                // Origin
                InfoCard(
                    title = "Origin",
                    value = character.origin.name
                )

                Spacer(Modifier.height(12.dp))

                // Location
                InfoCard(
                    title = "Last Location",
                    value = character.location.name
                )

                Spacer(Modifier.height(12.dp))

                // Episodes count
                InfoCard(
                    title = "Episodes",
                    value = "${character.episode.size} appearances"
                )
            }
        }
    }
}

@Composable
private fun InfoCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    color: Color? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = color ?: MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
