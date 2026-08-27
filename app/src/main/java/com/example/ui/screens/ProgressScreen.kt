package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.DailyRecordEntity
import com.example.ui.theme.CleanBackground
import com.example.ui.theme.CleanBorder
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPillAccent
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanSurface
import com.example.ui.theme.CleanSurfaceLow
import com.example.ui.theme.CleanTrack
import com.example.ui.theme.ScoreGentleWarm
import com.example.ui.theme.ScoreMidOat
import com.example.ui.viewmodel.EatBetterViewModel
import com.example.ui.viewmodel.UiState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
  viewModel: EatBetterViewModel,
  uiState: UiState
) {
  val records by viewModel.allRecordsFlow.collectAsState(initial = emptyList())

  // Generate 7-day trend data
  val past7Days = remember(records) {
    val cal = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val dayLabelFormat = SimpleDateFormat("EEE", Locale.US)

    val list = mutableListOf<DayTrendData>()
    for (i in 6 downTo 0) {
      val dayCal = Calendar.getInstance()
      dayCal.add(Calendar.DAY_OF_YEAR, -i)
      val dateStr = dateFormat.format(dayCal.time)
      val label = if (i == 0) "Today" else dayLabelFormat.format(dayCal.time)
      val record = records.find { it.date == dateStr }
      val score = record?.score ?: if (i == 0) 84 else 0
      list.add(DayTrendData(date = dateStr, label = label, score = score))
    }
    list
  }

  val avgScore = remember(past7Days) {
    val nonZero = past7Days.filter { it.score > 0 }
    if (nonZero.isNotEmpty()) (nonZero.sumOf { it.score } / nonZero.size) else 0
  }

  Scaffold(
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = CleanBackground
        ),
        title = {
          Text(
            text = "Progress & Trends",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.SemiBold,
              letterSpacing = (-0.5).sp
            ),
            color = CleanOnSurface
          )
        },
        actions = {
          IconButton(onClick = { viewModel.openShareDialog() }) {
            Icon(
              imageVector = Icons.Default.Share,
              contentDescription = "Share",
              tint = CleanOnSurface
            )
          }
        }
      )
    },
    containerColor = CleanBackground
  ) { paddingValues ->

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 90.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

      // Summary Metric Cards
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          // Weekly Average Card
          Surface(
            shape = RoundedCornerShape(24.dp),
            color = CleanSurface,
            modifier = Modifier
              .weight(1f)
              .border(1.dp, CleanBorder, RoundedCornerShape(24.dp))
          ) {
            Column(modifier = Modifier.padding(18.dp)) {
              Text(
                text = "7-Day Average",
                style = MaterialTheme.typography.labelSmall,
                color = CleanOnSurfaceVariant
              )
              Spacer(modifier = Modifier.height(4.dp))
              Row(verticalAlignment = Alignment.Bottom) {
                Text(
                  text = "$avgScore",
                  style = MaterialTheme.typography.displaySmall,
                  fontWeight = FontWeight.Bold,
                  color = CleanPrimary
                )
                Text(
                  text = "/100",
                  style = MaterialTheme.typography.labelSmall,
                  color = CleanOnSurfaceVariant,
                  modifier = Modifier.padding(bottom = 4.dp, start = 2.dp)
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Balanced & steady",
                style = MaterialTheme.typography.bodySmall,
                color = CleanPrimary,
                fontWeight = FontWeight.Medium
              )
            }
          }

          // Streak Card
          Surface(
            shape = RoundedCornerShape(24.dp),
            color = CleanSurface,
            modifier = Modifier
              .weight(1f)
              .border(1.dp, CleanBorder, RoundedCornerShape(24.dp))
          ) {
            Column(modifier = Modifier.padding(18.dp)) {
              Text(
                text = "Active Streak",
                style = MaterialTheme.typography.labelSmall,
                color = CleanOnSurfaceVariant
              )
              Spacer(modifier = Modifier.height(4.dp))
              Row(verticalAlignment = Alignment.Bottom) {
                Text(
                  text = "${uiState.streakDays}",
                  style = MaterialTheme.typography.displaySmall,
                  fontWeight = FontWeight.Bold,
                  color = CleanPrimary
                )
                Text(
                  text = " days",
                  style = MaterialTheme.typography.titleMedium,
                  color = CleanPrimary,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(bottom = 4.dp, start = 2.dp)
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Mindful habit strong",
                style = MaterialTheme.typography.bodySmall,
                color = CleanPrimary,
                fontWeight = FontWeight.Medium
              )
            }
          }
        }
      }

      // 7-Day Nutrition Score Bar Chart
      item {
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = CleanSurface,
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CleanBorder, RoundedCornerShape(24.dp))
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = "Weekly Nutrition Trend",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = CleanOnSurface
                )
                Text(
                  text = "Scores across the last 7 days",
                  style = MaterialTheme.typography.bodySmall,
                  color = CleanOnSurfaceVariant
                )
              }

              Icon(
                imageVector = Icons.Default.AutoGraph,
                contentDescription = null,
                tint = CleanPrimary,
                modifier = Modifier.size(24.dp)
              )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Chart Bars
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Bottom
            ) {
              past7Days.forEach { day ->
                ChartBar(day = day)
              }
            }
          }
        }
      }

      // Habit Insights Card
      item {
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = CleanSurfaceLow,
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CleanBorder, RoundedCornerShape(24.dp))
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Spa,
                contentDescription = null,
                tint = CleanPrimary,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Mindful Insights",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = CleanOnSurface
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = "• Consistent breakfast habits fuel your morning focus without energy spikes.\n• Adding leafy greens and pure hydration gives your daily score an immediate boost.\n• You have logged 10+ wholesome Bangladesh foods this week!",
              style = MaterialTheme.typography.bodySmall,
              color = CleanOnSurfaceVariant,
              lineHeight = 20.sp
            )
          }
        }
      }
    }
  }
}

data class DayTrendData(
  val date: String,
  val label: String,
  val score: Int
)

@Composable
private fun ChartBar(day: DayTrendData) {
  val animatedHeightProgress by animateFloatAsState(
    targetValue = (day.score / 100f).coerceIn(0.05f, 1f),
    animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
    label = "ChartBarHeight"
  )

  val barColor = when {
    day.score >= 80 -> CleanPrimary
    day.score >= 60 -> ScoreMidOat
    day.score > 0 -> ScoreGentleWarm
    else -> CleanBorder
  }

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.width(36.dp)
  ) {
    if (day.score > 0) {
      Text(
        text = "${day.score}",
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold),
        color = barColor
      )
      Spacer(modifier = Modifier.height(4.dp))
    }

    Box(
      modifier = Modifier
        .width(16.dp)
        .height(110.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(CleanSurfaceLow),
      contentAlignment = Alignment.BottomCenter
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight(animatedHeightProgress)
          .clip(RoundedCornerShape(8.dp))
          .background(barColor)
      )
    }

    Spacer(modifier = Modifier.height(6.dp))

    Text(
      text = day.label,
      style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
      color = CleanOnSurfaceVariant,
      fontWeight = if (day.label == "Today") FontWeight.Bold else FontWeight.Normal
    )
  }
}
