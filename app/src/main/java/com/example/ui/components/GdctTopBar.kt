package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.UserProfile
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.CrimsonRedDark
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.NavyDeep

@Composable
fun GdctTopBar(
  userProfile: UserProfile,
  unreadNotificationCount: Int = 0,
  onOpenNotifications: () -> Unit = {},
  onOpenQuoteDialog: () -> Unit,
  onOpenCommanderReport: () -> Unit,
  onOpenLoginDialog: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .shadow(elevation = 6.dp, shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
    color = CrimsonRed
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.verticalGradient(
            colors = listOf(
              CrimsonRed,
              CrimsonRedDark
            )
          )
        )
    ) {
      // Geometric Radial / Dong Son motif canvas
      DongSonMotifCanvas(
        modifier = Modifier.matchParentSize(),
        tint = Color.White.copy(alpha = 0.15f)
      )

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .statusBarsPadding()
          .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // App Header: V4 Badge, Titles, Notification & Quote buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
          ) {
            // White circular container with Navy V4 emblem
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color.White)
                .padding(2.dp),
              contentAlignment = Alignment.Center
            ) {
              Box(
                modifier = Modifier
                  .size(38.dp)
                  .clip(CircleShape)
                  .background(NavyDeep),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "V4",
                  color = Color.White,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Black
                )
              }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "VÙNG 4 HẢI QUÂN",
                  color = Color.White,
                  fontSize = 13.5.sp,
                  fontWeight = FontWeight.Black,
                  letterSpacing = 0.6.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                  imageVector = Icons.Default.Star,
                  contentDescription = null,
                  tint = GoldYellow,
                  modifier = Modifier.size(12.dp)
                )
              }
              Text(
                text = "Giáo dục chính trị trực tuyến",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }

          // Top right actions: Quote & Notification
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            // Daily Quote Button
            Surface(
              shape = CircleShape,
              color = Color.White.copy(alpha = 0.2f),
              modifier = Modifier
                .size(38.dp)
                .clickable { onOpenQuoteDialog() }
                .testTag("btn_daily_quote_top")
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.FormatQuote,
                  contentDescription = "Lời Bác dạy",
                  tint = Color.White,
                  modifier = Modifier.size(20.dp)
                )
              }
            }

            // Real-time Notification Badge from Web Admin
            Surface(
              shape = CircleShape,
              color = Color.White.copy(alpha = 0.2f),
              modifier = Modifier
                .size(38.dp)
                .clickable { onOpenNotifications() }
                .testTag("btn_top_notifications")
            ) {
              Box(contentAlignment = Alignment.Center) {
                if (unreadNotificationCount > 0) {
                  BadgedBox(
                    badge = {
                      Badge(
                        containerColor = GoldYellow,
                        contentColor = NavyDeep
                      ) {
                        Text(
                          text = if (unreadNotificationCount > 99) "99+" else "$unreadNotificationCount",
                          fontSize = 9.sp,
                          fontWeight = FontWeight.Black
                        )
                      }
                    }
                  ) {
                    Icon(
                      imageVector = Icons.Default.Notifications,
                      contentDescription = "Thông báo",
                      tint = Color.White,
                      modifier = Modifier.size(20.dp)
                    )
                  }
                } else {
                  Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Thông báo",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                  )
                }
              }
            }
          }
        }

        // Quick Soldier Status & Auth Pill
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = Color.White.copy(alpha = 0.18f),
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenLoginDialog() }
            .testTag("bar_soldier_profile_status")
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 12.dp, vertical = 7.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                tint = if (userProfile.isLoggedIn) GoldYellow else Color.White.copy(alpha = 0.8f),
                modifier = Modifier.size(15.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (userProfile.isLoggedIn) "${userProfile.rank} ${userProfile.name}" else "Chế độ Khách (Xem tự do)",
                color = Color.White,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
              Text(
                text = if (userProfile.isLoggedIn) " • ${userProfile.unit}" else " • Nhấn để đăng nhập",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 10.5.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
            }

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (userProfile.isLoggedIn) GoldYellow else Color.White.copy(alpha = 0.25f)
            ) {
              Text(
                text = if (userProfile.isLoggedIn) "Nội bộ" else "Đăng nhập",
                color = if (userProfile.isLoggedIn) NavyDeep else Color.White,
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
          }
        }
      }
    }
  }
}

