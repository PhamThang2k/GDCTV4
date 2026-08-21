package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderLight
import com.example.ui.theme.CanvasLight
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.NavyPrimary
import com.example.ui.viewmodel.AppTab

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
      shadowElevation = 8.dp
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(68.dp)
          .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Tab 1: Trang chủ
        GeometricNavItem(
          title = "Trang chủ",
          icon = Icons.Outlined.Home,
          selectedIcon = Icons.Filled.Home,
          isSelected = currentTab == AppTab.HOME,
          testTag = AppTab.HOME.testTag,
          onClick = { onTabSelected(AppTab.HOME) }
        )

        // Tab 2 (Centerpiece Floating Action): Học tập GDCT
        Box(
          modifier = Modifier
            .offset(y = (-8).dp)
            .testTag(AppTab.STUDY.testTag)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) { onTabSelected(AppTab.STUDY) },
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(
              modifier = Modifier
                .size(50.dp)
                .shadow(elevation = 6.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(CrimsonRed)
                .border(3.dp, CanvasLight, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Filled.MenuBook,
                contentDescription = "Học tập GDCT",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
              )
            }

            Text(
              text = "HỌC TẬP",
              fontSize = 9.5.sp,
              fontWeight = FontWeight.Black,
              color = if (currentTab == AppTab.STUDY) CrimsonRed else Color(0xFF64748B),
              modifier = Modifier.padding(top = 2.dp)
            )
          }
        }

        // Tab 3: Tiện ích & Sổ tay
        GeometricNavItem(
          title = "Tiện ích",
          icon = Icons.Outlined.GridView,
          selectedIcon = Icons.Filled.GridView,
          isSelected = currentTab == AppTab.UTILITIES,
          testTag = AppTab.UTILITIES.testTag,
          onClick = { onTabSelected(AppTab.UTILITIES) }
        )

        // Tab 4: Cá nhân & Báo cáo
        GeometricNavItem(
          title = "Cá nhân",
          icon = Icons.Outlined.Person,
          selectedIcon = Icons.Filled.Person,
          isSelected = currentTab == AppTab.PROFILE,
          testTag = AppTab.PROFILE.testTag,
          onClick = { onTabSelected(AppTab.PROFILE) }
        )
      }
    }
  }
}

@Composable
private fun GeometricNavItem(
  title: String,
  icon: ImageVector,
  selectedIcon: ImageVector,
  isSelected: Boolean,
  testTag: String,
  onClick: () -> Unit
) {
  val interactionSource = remember { MutableInteractionSource() }

  Column(
    modifier = Modifier
      .testTag(testTag)
      .clickable(
        interactionSource = interactionSource,
        indication = null
      ) { onClick() }
      .padding(horizontal = 4.dp, vertical = 4.dp),
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
