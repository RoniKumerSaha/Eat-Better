package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.FoodItem
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPillAccent
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanSurface
import com.example.ui.theme.PremiumGradients
import com.example.ui.theme.PremiumShadows
import com.example.ui.theme.PremiumShapes
import com.example.ui.theme.premiumShadow
import com.example.ui.theme.ScoreGentleWarm
import com.example.ui.theme.ScoreMidOat

@Composable
fun FoodItemCard(
  food: FoodItem,
  onFoodClick: () -> Unit,
  onFavoriteToggle: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scoreColor = when {
    food.baseScore >= 8 -> CleanPrimary
    food.baseScore >= 6 -> ScoreMidOat
    else -> ScoreGentleWarm
  }
  val tileBrush = tileBrushFor(food.categoryId)

  // Press scale animation for that premium "tactile" feel
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val pressScale by animateFloatAsState(
    targetValue = if (isPressed) 0.97f else 1f,
    animationSpec = tween(120, easing = FastOutSlowInEasing),
    label = "FoodCardPress"
  )

  Surface(
    modifier = modifier
      .fillMaxWidth()
      .scale(pressScale)
      .premiumShadow(PremiumShadows.CardResting, PremiumShapes.medium)
      .clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = onFoodClick
      )
      .testTag("food_card_${food.id}"),
    shape = PremiumShapes.medium,
    color = CleanSurface
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      // Premium gradient tile with icon
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(1.2f)
          .clip(RoundedCornerShape(18.dp))
          .background(tileBrush),
        contentAlignment = Alignment.Center
      ) {
        // Subtle inner ring for depth
        Box(
          modifier = Modifier
            .size(86.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.55f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = getFoodIcon(food),
            contentDescription = food.name,
            tint = CleanPrimary,
            modifier = Modifier.size(44.dp)
          )
        }

        // Favorite button
        IconButton(
          onClick = onFavoriteToggle,
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(6.dp)
            .size(32.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.85f))
            .testTag("favorite_btn_${food.id}")
        ) {
          Icon(
            imageVector = if (food.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = stringResource(R.string.food_card_favorite_content_description),
            tint = if (food.isFavorite) CleanPrimary else CleanOnSurfaceVariant,
            modifier = Modifier.size(16.dp)
          )
        }

        // Region Tag (e.g. Bangladesh) — pill, glassmorphic
        if (food.region == "Bangladesh") {
          Box(
            modifier = Modifier
              .align(Alignment.BottomStart)
              .padding(8.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(Color.White.copy(alpha = 0.85f))
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = stringResource(R.string.food_card_local),
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
              ),
              color = CleanPrimary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Food Name
      Text(
        text = food.name,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 14.5.sp,
          letterSpacing = (-0.2).sp
        ),
        color = CleanOnSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )

      if (!food.bengaliName.isNullOrBlank()) {
        Text(
          text = food.bengaliName,
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
          color = CleanOnSurfaceVariant,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Score badge (left) + tiny serving hint
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(22.dp)
              .clip(CircleShape)
              .background(scoreColor.copy(alpha = 0.14f)),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${food.baseScore}",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
              ),
              color = scoreColor
            )
          }

          Spacer(modifier = Modifier.size(6.dp))

          Text(
            text = "/ 10",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 10.sp,
              fontWeight = FontWeight.Medium
            ),
            color = CleanOnSurfaceVariant
          )
        }
      }
    }
  }
}

/** Premium tinted tile background per category — calmer than per-food logic. */
private fun tileBrushFor(category: String): Brush = when (category) {
  "fruits" -> PremiumGradients.TileSage
  "vegetables" -> PremiumGradients.TileSage
  "meals" -> PremiumGradients.TileWarm
  "bangladesh" -> PremiumGradients.TileWarm
  "snacks" -> PremiumGradients.TileWarm
  "drinks" -> PremiumGradients.TileCool
  else -> PremiumGradients.TileNeutral
}