package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.BookmarkAdded
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.local.ConversationLogEntity
import com.example.data.local.NpcRelationshipEntity
import com.example.domain.model.DialogueChoice
import com.example.domain.model.MeaningClue
import com.example.domain.model.NpcMood
import com.example.domain.model.SupportLevel
import com.example.ui.SceneUiState
import com.example.ui.components.NpcMoodChip
import com.example.ui.components.SampleAiNoticeBanner

@Composable
fun SceneInteractionScreen(
    uiState: SceneUiState,
    conversation: List<ConversationLogEntity>,
    npcRelationship: NpcRelationshipEntity?,
    isPlaceholderAiMode: Boolean,
    onBack: () -> Unit,
    onToggleObserveCues: () -> Unit,
    onAdvanceMeaningSupport: () -> Unit,
    onSelectChoice: (DialogueChoice) -> Unit,
    onContinueBeat: () -> Unit,
    onSendFreeFormEnglish: (String) -> Unit
) {
    BackHandler(onBack = onBack)

    val scene = uiState.scene ?: return
    var freeInput by rememberSaveable(scene.id) { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(conversation.size) {
        if (conversation.isNotEmpty()) {
            listState.animateScrollToItem(conversation.size - 1)
        }
    }

    val liveMood = NpcMood.entries.firstOrNull { it.name == npcRelationship?.lastMood }
        ?: scene.initialMood

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.background
    ) { _ ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 680.dp)
            ) {
                // 2.5D Scene Backdrop Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                ) {
                    Image(
                        painter = painterResource(id = scene.locationId.sceneRes),
                        contentDescription = scene.locationId.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.50f),
                                        Color.Black.copy(alpha = 0.18f),
                                        Color.Black.copy(alpha = 0.78f)
                                    )
                                )
                            )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.Black.copy(alpha = 0.45f)
                        ) {
                            IconButton(
                                onClick = onBack,
                                modifier = Modifier.testTag("scene_back_button")
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                    contentDescription = "Back to New Haven",
                                    tint = Color.White
                                )
                            }
                        }

                        FilterChip(
                            selected = uiState.observeCuesEnabled,
                            onClick = onToggleObserveCues,
                            modifier = Modifier.testTag("observe_cues_toggle"),
                            label = {
                                Text(
                                    text = stringResource(R.string.observe_cues_label),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = if (uiState.observeCuesEnabled) {
                                        Icons.Rounded.Visibility
                                    } else {
                                        Icons.Outlined.Visibility
                                    },
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )
                    }

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                    ) {
                        Text(
                            text = "${scene.locationId.title} · Day 0${scene.day}",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                        Spacer(Modifier.height(2.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = scene.npcName,
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White
                            )
                            NpcMoodChip(mood = liveMood)
                        }
                        Text(
                            text = npcRelationship?.let { "${scene.npcRole} · ${it.relationshipTier}" } ?: scene.npcRole,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }

                // Scrollable Scene Story, Social Cues, Meaning Discovery & Conversation
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Environment Context Card
                    item {
                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Text(
                                text = scene.environmentAction,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }

                    // Social Intelligence Lens (when toggled on)
                    item {
                        AnimatedVisibility(visible = uiState.observeCuesEnabled) {
                            Surface(
                                shape = MaterialTheme.shapes.medium,
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(
                                        text = "SOCIAL INTELLIGENCE · BODY LANGUAGE & SUBTEXT",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = scene.bodyLanguageCue,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                    scene.subtextNote?.let { subtext ->
                                        Spacer(Modifier.height(4.dp))
                                        Text(
                                            text = subtext,
                                            style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // 3-Tier Meaning Discovery Card
                    scene.highlightedClue?.let { clue ->
                        item {
                            MeaningDiscoveryCard(
                                clue = clue,
                                level = uiState.supportLevel,
                                onAdvance = onAdvanceMeaningSupport
                            )
                        }
                    }

                    // Conversation Stream
                    items(conversation, key = { it.id }) { bubble ->
                        ConversationBubbleCard(bubble = bubble)
                    }

                    if (isPlaceholderAiMode && conversation.any { it.isPlaceholderAi }) {
                        item {
                            SampleAiNoticeBanner()
                        }
                    }

                    uiState.aiError?.let { errorMsg ->
                        item {
                            Surface(
                                shape = MaterialTheme.shapes.small,
                                color = MaterialTheme.colorScheme.error.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = errorMsg,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }
                }

                // Bottom Action & Unscripted English Bar
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 3.dp,
                    shadowElevation = 6.dp,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .imePadding()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (!uiState.choiceResolved && scene.choices.isNotEmpty()) {
                            Text(
                                text = "Choose what you want to say — or type your own below:",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            scene.choices.forEachIndexed { idx, choice ->
                                Surface(
                                    onClick = { onSelectChoice(choice) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("dialogue_choice_$idx"),
                                    shape = RoundedCornerShape(14.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                                ) {
                                    Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                                        Text(
                                            text = "\"${choice.text}\"",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(Modifier.height(2.dp))
                                        Text(
                                            text = choice.communicativeAct.label,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                            }
                        } else if (uiState.pendingNextSceneId != null || uiState.readyForDaySummary) {
                            Button(
                                onClick = onContinueBeat,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("scene_continue_button"),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(
                                    text = if (uiState.pendingNextSceneId != null) {
                                        "Continue in New Haven"
                                    } else {
                                        "See Your Day in New Haven"
                                    },
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Spacer(Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        if (scene.allowsFreeInput) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = freeInput,
                                    onValueChange = { freeInput = it },
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("free_english_input"),
                                    placeholder = {
                                        Text(
                                            text = stringResource(R.string.say_your_own_hint),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    },
                                    singleLine = true,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                Button(
                                    onClick = {
                                        val msg = freeInput.trim()
                                        if (msg.isNotEmpty()) {
                                            freeInput = ""
                                            onSendFreeFormEnglish(msg)
                                        }
                                    },
                                    enabled = freeInput.isNotBlank() && !uiState.isSendingAi,
                                    modifier = Modifier
                                        .height(52.dp)
                                        .testTag("say_it_button"),
                                    shape = RoundedCornerShape(14.dp)
                                ) {
                                    if (uiState.isSendingAi) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(18.dp),
                                            strokeWidth = 2.dp,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Rounded.Send,
                                            contentDescription = stringResource(R.string.send_action),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MeaningDiscoveryCard(
    clue: MeaningClue,
    level: SupportLevel,
    onAdvance: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.35f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (level == SupportLevel.REVEAL) Icons.Rounded.BookmarkAdded else Icons.Outlined.Lightbulb,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Notice: \"${clue.phrase}\"",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
                if (level != SupportLevel.REVEAL) {
                    TextButton(
                        onClick = onAdvance,
                        modifier = Modifier.testTag("meaning_support_button")
                    ) {
                        Text(
                            text = if (level == SupportLevel.DISCOVER) {
                                stringResource(R.string.sense_clue_button)
                            } else {
                                stringResource(R.string.reveal_meaning_button)
                            },
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

            when (level) {
                SupportLevel.DISCOVER -> {
                    Text(
                        text = "Try sensing what it means from ${clue.exampleContext} first — tap 'Need a clue?' only if you want a hint.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.85f)
                    )
                }
                SupportLevel.HINT -> {
                    Text(
                        text = "Situational Clue: ${clue.visualHint}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
                SupportLevel.REVEAL -> {
                    Text(
                        text = clue.deepReveal,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "${clue.formNote} · Saved to Language Memories",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun ConversationBubbleCard(bubble: ConversationLogEntity) {
    val isPlayer = bubble.isPlayer
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isPlayer) Alignment.End else Alignment.Start
    ) {
        Surface(
            modifier = Modifier.widthIn(max = 520.dp),
            shape = RoundedCornerShape(
                topStart = 18.dp,
                topEnd = 18.dp,
                bottomStart = if (isPlayer) 18.dp else 4.dp,
                bottomEnd = if (isPlayer) 4.dp else 18.dp
            ),
            color = if (isPlayer) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surface
            },
            border = if (isPlayer) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            tonalElevation = if (isPlayer) 0.dp else 1.dp
        ) {
            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = bubble.speakerName,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = if (isPlayer) {
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    )
                    bubble.actionTag?.let { tag ->
                        Text(
                            text = "· $tag",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isPlayer) {
                                MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.78f)
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                    if (bubble.isPlaceholderAi) {
                        Icon(
                            imageVector = Icons.Rounded.AutoAwesome,
                            contentDescription = "Sample AI reply",
                            modifier = Modifier.size(13.dp),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = bubble.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isPlayer) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
                bubble.cueNote?.let { cue ->
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = cue,
                        style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
