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
  onOpenQuoteDialog: () -> Unit,
  onOpenCommanderReport: () -> Unit,
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

            // Commander / Notification Badge
            Surface(
              shape = CircleShape,
              color = Color.White.copy(alpha = 0.2f),
              modifier = Modifier
                .size(38.dp)
                .clickable { onOpenCommanderReport() }
                .testTag("btn_top_report")
            ) {
              Box(contentAlignment = Alignment.Center) {
                BadgedBox(
                  badge = {
                    Badge(
                      containerColor = GoldYellow,
                      contentColor = NavyDeep
                    ) {
                      Text("1", fontSize = 9.sp, fontWeight = FontWeight.Black)
                    }
                  }
                ) {
                  Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Báo cáo Chỉ huy",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                  )
                }
              }
            }
          }
        }

        // Quick Soldier Status & Search Bar (Geometric Balance translucent pill)
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = Color.White.copy(alpha = 0.18f),
          modifier = Modifier.fillMaxWidth()
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
                tint = GoldYellow,
                modifier = Modifier.size(15.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "${userProfile.rank} ${userProfile.name}",
                color = Color.White,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
              Text(
                text = " • ${userProfile.unit}",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 10.5.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
            }

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color.White.copy(alpha = 0.25f)
            ) {
              Text(
                text = "Trực tuyến 2026",
                color = Color.White,
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }
        }
      }
    }
  }
}

