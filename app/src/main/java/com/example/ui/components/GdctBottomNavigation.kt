package com.example.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderLight
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.NavyPrimary
import com.example.ui.viewmodel.AppTab
import kotlin.math.cos
import kotlin.math.sin

/**
 * Bottom Navigation Bar featuring the elevated Vietnam Navy / Vùng 4 Emblem Logo
 * centered in the middle of the bottom dock for quick access to the Study Hub.
 */
@Composable
fun GdctBottomNavigation(
  currentTab: AppTab,
  onTabSelected: (AppTab) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier.fillMaxWidth()
  ) {
    HorizontalDivider(thickness = 1.dp, color = BorderLight)
    Surface(
      modifier = Modifier.fillMaxWidth(),
      color = Color.White,
      shadowElevation = 10.dp
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(68.dp)
          .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Tab 1 (Left 1): Trang chủ (Home)
        GeometricNavItem(
          title = "Trang chủ",
          icon = Icons.Outlined.Home,
          selectedIcon = Icons.Filled.Home,
          isSelected = currentTab == AppTab.HOME,
          testTag = AppTab.HOME.testTag,
          onClick = { onTabSelected(AppTab.HOME) },
          modifier = Modifier.weight(1f)
        )

        // Tab 2 (Left 2): Tiện ích & Sổ tay (Utilities)
        GeometricNavItem(
          title = "Tiện ích",
          icon = Icons.Outlined.GridView,
          selectedIcon = Icons.Filled.GridView,
          isSelected = currentTab == AppTab.UTILITIES,
          testTag = AppTab.UTILITIES.testTag,
          onClick = { onTabSelected(AppTab.UTILITIES) },
          modifier = Modifier.weight(1f)
        )

        // CENTERPIECE LOGO BUTTON: HỌC TẬP GDCT VÙNG 4 HẢI QUÂN
        Box(
          modifier = Modifier
            .weight(1.2f)
            .offset(y = (-14).dp)
            .testTag(AppTab.STUDY.testTag)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) { onTabSelected(AppTab.STUDY) },
          contentAlignment = Alignment.Center
        ) {
          val isStudySelected = currentTab == AppTab.STUDY
          val scale by animateFloatAsState(
            targetValue = if (isStudySelected) 1.08f else 1.0f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
            label = "center_logo_scale"
          )

          Column(
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            // Navy Center Emblem Badge
            Box(
              modifier = Modifier
                .scale(scale)
                .size(56.dp)
                .shadow(
                  elevation = if (isStudySelected) 10.dp else 6.dp,
                  shape = CircleShape,
                  spotColor = CrimsonRed
                )
                .clip(CircleShape)
                .background(
                  Brush.linearGradient(
                    colors = if (isStudySelected) {
                      listOf(Color(0xFFDC2626), Color(0xFF991B1B), Color(0xFF7F1D1D))
                    } else {
                      listOf(NavyPrimary, NavyDeep, Color(0xFF0F172A))
                    }
                  )
                )
                .border(
                  width = 2.5.dp,
                  brush = Brush.linearGradient(
                    colors = listOf(GoldYellow, Color(0xFFFDE047), GoldYellow)
                  ),
                  shape = CircleShape
                ),
              contentAlignment = Alignment.Center
            ) {
              // Custom Vietnam Navy Anchor & Gold Star Emblem Canvas
              NavyEmblemCanvas(
                modifier = Modifier.size(36.dp),
                isStudySelected = isStudySelected
              )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
              text = "HỌC TẬP",
              fontSize = 10.sp,
              fontWeight = FontWeight.Black,
              letterSpacing = 0.4.sp,
              color = if (isStudySelected) CrimsonRed else NavyPrimary
            )
          }
        }

        // Tab 3 (Right 1): Trắc nghiệm & Văn kiện (Linked to Study / Directives)
        GeometricNavItem(
          title = "Kiểm tra",
          icon = Icons.Outlined.Assignment,
          selectedIcon = Icons.Filled.Assignment,
          isSelected = false,
          testTag = "tab_quiz_quick",
          onClick = { onTabSelected(AppTab.STUDY) },
          modifier = Modifier.weight(1f)
        )

        // Tab 4 (Right 2): Cá nhân & Quân nhân (Profile)
        GeometricNavItem(
          title = "Cá nhân",
          icon = Icons.Outlined.Person,
          selectedIcon = Icons.Filled.Person,
          isSelected = currentTab == AppTab.PROFILE,
          testTag = AppTab.PROFILE.testTag,
          onClick = { onTabSelected(AppTab.PROFILE) },
          modifier = Modifier.weight(1f)
        )
      }
    }
  }
}

/**
 * Vector / Canvas rendering of Vietnam Navy Star & Anchor Emblem
 */
@Composable
private fun NavyEmblemCanvas(
  modifier: Modifier = Modifier,
  isStudySelected: Boolean
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f

    // 1. Draw Golden Anchor in background
    val anchorColor = GoldYellow
    val anchorStroke = Stroke(width = 2.dp.toPx())

    // Anchor vertical bar
    drawLine(
      color = anchorColor,
      start = Offset(cx, cy - h * 0.36f),
      end = Offset(cx, cy + h * 0.38f),
      strokeWidth = 2.5.dp.toPx()
    )

    // Anchor horizontal top crossbar
    drawLine(
      color = anchorColor,
      start = Offset(cx - w * 0.22f, cy - h * 0.18f),
      end = Offset(cx + w * 0.22f, cy - h * 0.18f),
      strokeWidth = 2.dp.toPx()
    )

    // Anchor top ring
    drawCircle(
      color = anchorColor,
      radius = w * 0.09f,
      center = Offset(cx, cy - h * 0.32f),
      style = anchorStroke
    )

    // Anchor curved bottom fluke
    val flukePath = Path().apply {
      moveTo(cx - w * 0.34f, cy + h * 0.18f)
      quadraticTo(
        cx, cy + h * 0.46f,
        cx + w * 0.34f, cy + h * 0.18f
      )
    }
    drawPath(path = flukePath, color = anchorColor, style = anchorStroke)

    // Anchor left & right arrows/flukes
    drawLine(
      color = anchorColor,
      start = Offset(cx - w * 0.34f, cy + h * 0.18f),
      end = Offset(cx - w * 0.28f, cy + h * 0.08f),
      strokeWidth = 2.dp.toPx()
    )
    drawLine(
      color = anchorColor,
      start = Offset(cx + w * 0.34f, cy + h * 0.18f),
      end = Offset(cx + w * 0.28f, cy + h * 0.08f),
      strokeWidth = 2.dp.toPx()
    )

    // 2. Draw Golden 5-Point Star in Center
    val starRadiusOuter = w * 0.22f
    val starRadiusInner = starRadiusOuter * 0.45f
    val starPath = Path()
    val points = 5
    var angle = -Math.PI / 2.0
    val step = Math.PI / points

    for (i in 0 until (points * 2)) {
      val r = if (i % 2 == 0) starRadiusOuter else starRadiusInner
      val x = cx + (r * cos(angle)).toFloat()
      val y = cy + (r * sin(angle)).toFloat()
      if (i == 0) starPath.moveTo(x, y) else starPath.lineTo(x, y)
      angle += step
    }
    starPath.close()

    // Fill star with brilliant gold
    drawPath(
      path = starPath,
      color = GoldYellow,
      style = Fill
    )
    drawPath(
      path = starPath,
      color = Color.White.copy(alpha = 0.8f),
      style = Stroke(width = 1.dp.toPx())
    )
  }
}

@Composable
private fun GeometricNavItem(
  title: String,
  icon: ImageVector,
  selectedIcon: ImageVector,
  isSelected: Boolean,
  testTag: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val interactionSource = remember { MutableInteractionSource() }

  Column(
    modifier = modifier
      .testTag(testTag)
      .clickable(
        interactionSource = interactionSource,
        indication = null
      ) { onClick() }
      .padding(horizontal = 2.dp, vertical = 4.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Icon(
      imageVector = if (isSelected) selectedIcon else icon,
      contentDescription = title,
      tint = if (isSelected) NavyPrimary else Color(0xFF64748B),
      modifier = Modifier.size(23.dp)
    )
    Text(
      text = title,
      fontSize = 9.5.sp,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
      color = if (isSelected) NavyPrimary else Color(0xFF64748B),
      modifier = Modifier.padding(top = 2.dp)
    )
  }
}
