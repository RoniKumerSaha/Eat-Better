package com.example.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CleanBackground
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanSurface

/**
 * Loads and parses a markdown file from the app's `assets/legal/` folder.
 * The parser is intentionally minimal — it splits headers, paragraphs,
 * bullet lists, and leaves everything else as plain text. That's all we
 * need to render the privacy policy and terms written in the repo root.
 */
private fun loadLegalMarkdown(context: Context, assetPath: String): String {
  return try {
    context.assets.open(assetPath).bufferedReader().use { it.readText() }
  } catch (_: Exception) {
    "We couldn't load this document right now. Please try again later."
  }
}

private sealed class MarkdownBlock {
  data class Heading(val level: Int, val text: String) : MarkdownBlock()
  data class Paragraph(val text: String) : MarkdownBlock()
  data class BulletList(val items: List<String>) : MarkdownBlock()
}

/** Very small markdown-to-blocks parser. Handles #/## headings, paragraphs, and "- " lists. */
private fun parseMarkdown(md: String): List<MarkdownBlock> {
  val blocks = mutableListOf<MarkdownBlock>()
  val lines = md.lines()
  var i = 0
  while (i < lines.size) {
    val line = lines[i]
    when {
      line.startsWith("# ") -> {
        blocks.add(MarkdownBlock.Heading(1, line.removePrefix("# ").trim()))
        i++
      }
      line.startsWith("## ") -> {
        blocks.add(MarkdownBlock.Heading(2, line.removePrefix("## ").trim()))
        i++
      }
      line.startsWith("- ") -> {
        val items = mutableListOf<String>()
        while (i < lines.size && lines[i].startsWith("- ")) {
          items.add(lines[i].removePrefix("- ").trim())
          i++
        }
        blocks.add(MarkdownBlock.BulletList(items))
      }
      line.isBlank() -> i++
      else -> {
        val para = StringBuilder()
        while (i < lines.size && lines[i].isNotBlank() && !lines[i].startsWith("#") && !lines[i].startsWith("- ")) {
          if (para.isNotEmpty()) para.append(' ')
          para.append(lines[i].trim())
          i++
        }
        blocks.add(MarkdownBlock.Paragraph(para.toString()))
      }
    }
  }
  return blocks
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LegalContentScreen(
  assetPath: String,
  title: String,
  onNavigateBack: () -> Unit
) {
  val context = LocalContext.current
  val rawText = remember(assetPath) { loadLegalMarkdown(context, assetPath) }
  val blocks = remember(rawText) { parseMarkdown(rawText) }

  Scaffold(
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = CleanBackground),
        title = {
          Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.SemiBold,
              letterSpacing = (-0.5).sp
            ),
            color = CleanOnSurface
          )
        },
        navigationIcon = {
          IconButton(onClick = onNavigateBack) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = stringResource(R.string.action_back),
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
      contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      items(blocks) { block ->
        when (block) {
          is MarkdownBlock.Heading -> Surface(
            shape = RoundedCornerShape(14.dp),
            color = CleanSurface,
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = block.text,
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.4).sp
              ),
              color = CleanOnSurface,
              modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp)
            )
          }
          is MarkdownBlock.Paragraph -> Text(
            text = block.text,
            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 21.sp),
            color = CleanOnSurfaceVariant,
            modifier = Modifier
              .fillMaxWidth()
              .background(CleanSurface, RoundedCornerShape(14.dp))
              .padding(horizontal = 18.dp, vertical = 14.dp)
          )
          is MarkdownBlock.BulletList -> Surface(
            shape = RoundedCornerShape(14.dp),
            color = CleanSurface,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(
              modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              block.items.forEach { item ->
                Row {
                  Text(
                    text = "•",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CleanOnSurfaceVariant,
                    modifier = Modifier.padding(end = 10.dp)
                  )
                  Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                    color = CleanOnSurfaceVariant
                  )
                }
              }
            }
          }
        }
      }
      // Trailing padding for readability above any bottom system UI
      item { Spacer(modifier = Modifier.height(40.dp)) }
    }
  }
}

@Composable
private fun Row(content: @Composable () -> Unit) {
  androidx.compose.foundation.layout.Row(
    verticalAlignment = androidx.compose.ui.Alignment.Top,
    content = { content() }
  )
}