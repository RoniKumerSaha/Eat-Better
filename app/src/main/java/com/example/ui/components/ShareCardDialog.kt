package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.SagePrimary
import com.example.ui.theme.SecondaryContainer
import java.io.File
import java.io.FileOutputStream

@Composable
fun ShareCardDialog(
  displayName: String,
  score: Int,
  streakDays: Int,
  nutritiousPercentage: Int,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(28.dp),
      color = MaterialTheme.colorScheme.surface,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .testTag("share_card_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Share Score Card",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
          )
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Visual Share Card Preview
        Card(
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
          elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                  colors = listOf(
                    Color(0xFFF9F6F0),
                    Color(0xFFE8F2EC)
                  )
                )
              )
              .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = Icons.Default.Spa,
                contentDescription = null,
                tint = SagePrimary,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Eat Better",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = SagePrimary
              )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = if (displayName.isNotBlank()) "$displayName's Daily Nutrition" else "My Daily Nutrition",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Score Display
            Box(
              modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(Color.White)
                .padding(8.dp),
              contentAlignment = Alignment.Center
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                  text = "$score",
                  style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold
                  ),
                  color = SagePrimary
                )
                Text(
                  text = "/100",
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Highlights
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceEvenly
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    Icons.Default.LocalFireDepartment,
                    contentDescription = null,
                    tint = SagePrimary,
                    modifier = Modifier.size(18.dp)
                  )
                  Text(
                    text = " $streakDays Days",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                  )
                }
                Text(
                  text = "Mindful Streak",
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }

              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    Icons.Default.Eco,
                    contentDescription = null,
                    tint = SagePrimary,
                    modifier = Modifier.size(18.dp)
                  )
                  Text(
                    text = " $nutritiousPercentage%",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                  )
                }
                Text(
                  text = "Nutritious Food",
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
              text = "Small choices • Lasting wellness",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
          onClick = {
            shareScoreCard(context, displayName, score, streakDays, nutritiousPercentage)
            onDismiss()
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("share_to_apps_button"),
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = SagePrimary)
        ) {
          Icon(Icons.Default.Share, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Share with Friends & Family", style = MaterialTheme.typography.labelLarge)
        }
      }
    }
  }
}

private fun shareScoreCard(
  context: Context,
  displayName: String,
  score: Int,
  streak: Int,
  nutritiousPercentage: Int
) {
  val title = if (displayName.isNotBlank()) "$displayName's Eat Better Score" else "My Eat Better Nutrition Score"
  val text = "$title: $score/100 ($nutritiousPercentage% nutritious choices, $streak-day mindful streak!). Understanding what I eat with Eat Better."

  val sendIntent = Intent().apply {
    action = Intent.ACTION_SEND
    putExtra(Intent.EXTRA_TEXT, text)
    type = "text/plain"
  }
  val shareIntent = Intent.createChooser(sendIntent, "Share your Nutrition Score")
  context.startActivity(shareIntent)
}
