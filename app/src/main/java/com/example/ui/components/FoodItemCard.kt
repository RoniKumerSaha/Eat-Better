package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Liquor
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RiceBowl
import androidx.compose.material.icons.filled.SoupKitchen
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.WaterDrop
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FoodItem
import com.example.ui.theme.CleanBorder
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPillAccent
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanSurface
import com.example.ui.theme.CleanSurfaceLow
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

  Surface(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(24.dp))
      .clickable { onFoodClick() }
      .border(1.dp, CleanBorder, RoundedCornerShape(24.dp))
      .testTag("food_card_${food.id}"),
    shape = RoundedCornerShape(24.dp),
    color = CleanSurface
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      // Visual Container + Favorite icon
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(1.2f)
          .clip(RoundedCornerShape(18.dp))
          .background(CleanSurfaceLow),
        contentAlignment = Alignment.Center
      ) {
        // Food icon
        Icon(
          imageVector = getFoodIcon(food.iconSymbol, food.categoryId),
          contentDescription = food.name,
          tint = CleanPrimary,
          modifier = Modifier.size(48.dp)
        )

        // Favorite button
        IconButton(
          onClick = onFavoriteToggle,
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(4.dp)
            .size(32.dp)
            .clip(CircleShape)
            .background(CleanSurface)
            .testTag("favorite_btn_${food.id}")
        ) {
          Icon(
            imageVector = if (food.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (food.isFavorite) CleanPrimary else CleanOnSurfaceVariant,
            modifier = Modifier.size(18.dp)
          )
        }

        // Region Tag (e.g. Bangladesh)
        if (food.region == "Bangladesh") {
          Box(
            modifier = Modifier
              .align(Alignment.BottomStart)
              .padding(6.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(CleanPillAccent)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "Local",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = CleanPrimary,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Food Name
      Text(
        text = food.name,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 15.sp
        ),
        color = CleanOnSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )

      if (!food.bengaliName.isNullOrBlank()) {
        Text(
          text = food.bengaliName,
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
          color = CleanOnSurfaceVariant,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Score Badge Row
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(24.dp)
              .clip(CircleShape)
              .background(CleanPillAccent),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${food.baseScore}",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
              color = CleanPrimary
            )
          }

          Spacer(modifier = Modifier.size(4.dp))

          Text(
            text = "/ 10",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
            color = CleanOnSurfaceVariant
          )
        }
      }
    }
  }
}

fun getFoodIcon(symbol: String, category: String): ImageVector {
  return when (symbol) {
    "apple" -> Icons.Default.Spa
    "water_drop" -> Icons.Default.WaterDrop
    "egg" -> Icons.Default.Egg
    "rice_bowl" -> Icons.Default.RiceBowl
    "soup_kitchen" -> Icons.Default.SoupKitchen
    "coffee" -> Icons.Default.Coffee
    "liquor" -> Icons.Default.Liquor
    "lunch_dining" -> Icons.Default.LunchDining
    "eco" -> Icons.Default.Eco
    else -> when (category) {
      "fruits" -> Icons.Default.Spa
      "drinks" -> Icons.Default.WaterDrop
      "vegetables" -> Icons.Default.Eco
      "meals" -> Icons.Default.Restaurant
      else -> Icons.Default.Spa
    }
  }
}

