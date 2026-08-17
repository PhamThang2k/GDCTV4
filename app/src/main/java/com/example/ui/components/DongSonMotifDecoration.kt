package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.cos
import kotlin.math.sin

/**
 * Custom decorative Canvas rendering authentic Dong Son bronze drum geometric sunburst & concentric motifs
 */
@Composable
fun DongSonMotifCanvas(
  modifier: Modifier = Modifier,
  tint: Color = Color(0x33FFD54F)
) {
  Canvas(modifier = modifier.fillMaxSize()) {
    val center = Offset(size.width, 0f)
    val maxRadius = size.width.coerceAtLeast(size.height) * 1.2f

    // Outer concentric circles
    for (i in 1..6) {
      val r = maxRadius * (i / 6f)
      drawCircle(
        color = tint.copy(alpha = (0.05f + i * 0.03f).coerceAtMost(0.35f)),
        radius = r,
        center = center,
        style = Stroke(width = if (i % 2 == 0) 2.5f else 1.2f)
      )
    }

    // Sun rays
    val rayCount = 14
    for (i in 0 until rayCount) {
      val angle = (i * 2 * Math.PI / rayCount)
      val startRadius = maxRadius * 0.15f
      val endRadius = maxRadius * 0.95f
      val startX = center.x + (startRadius * cos(angle)).toFloat()
      val startY = center.y + (startRadius * sin(angle)).toFloat()
      val endX = center.x + (endRadius * cos(angle)).toFloat()
      val endY = center.y + (endRadius * sin(angle)).toFloat()

      drawLine(
        color = tint.copy(alpha = 0.18f),
        start = Offset(startX, startY),
        end = Offset(endX, endY),
        strokeWidth = 1.8f
      )
    }
  }
}
