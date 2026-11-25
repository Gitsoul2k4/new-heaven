package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material.icons.rounded.WorkOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.local.WorldStateEntity
import com.example.domain.model.LifeStage
import com.example.domain.model.NpcMood

@Composable
fun WorldStatusStrip(
    world: WorldStateEntity,
    lifeStage: LifeStage,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatusPill(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.WbSunny,
                    contentDescription = "Current day",
                    modifier = Modifier.size(15.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
            },
            label = "Day ${world.currentDay}/7"
        )
        StatusPill(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.AccountBalanceWallet,
                    contentDescription = "Wallet balance",
                    modifier = Modifier.size(15.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            label = "$${world.moneyDollars}"
        )
        StatusPill(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.WorkOutline,
                    contentDescription = "Life stage",
                    modifier = Modifier.size(15.dp),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            label = lifeStage.title,
            modifier = Modifier.weight(1f, fill = false)
        )
    }
}

@Composable
private fun StatusPill(
    icon: @Composable () -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(999.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()
            Spacer(Modifier.width(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun NpcMoodChip(
    mood: NpcMood,
    modifier: Modifier = Modifier
) {
    val containerColor = when (mood) {
        NpcMood.WARM, NpcMood.IMPRESSED -> MaterialTheme.colorScheme.primaryContainer
        NpcMood.APOLOGETIC, NpcMood.BUSY -> MaterialTheme.colorScheme.tertiaryContainer
        NpcMood.GUARDED, NpcMood.SKEPTICAL, NpcMood.SARCASTIC -> MaterialTheme.colorScheme.secondaryContainer
        NpcMood.CURIOUS -> MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = when (mood) {
        NpcMood.WARM, NpcMood.IMPRESSED -> MaterialTheme.colorScheme.onPrimaryContainer
        NpcMood.APOLOGETIC, NpcMood.BUSY -> MaterialTheme.colorScheme.onTertiaryContainer
        NpcMood.GUARDED, NpcMood.SKEPTICAL, NpcMood.SARCASTIC -> MaterialTheme.colorScheme.onSecondaryContainer
        NpcMood.CURIOUS -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(999.dp),
        color = containerColor
    ) {
        Text(
            text = "${mood.label} · ${mood.emojiHint}",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            color = contentColor,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun SampleAiNoticeBanner(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.7f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.25f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.AutoAwesome,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.sample_ai_notice),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}
