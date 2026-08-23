package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.AppNotification
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun NotificationDialog(
  notifications: List<AppNotification>,
  onMarkAllAsRead: () -> Unit,
  onMarkAsRead: (String) -> Unit,
  onDeleteNotification: (String) -> Unit,
  onClearAll: () -> Unit,
  onOpenLesson: (String) -> Unit,
  onDismiss: () -> Unit
) {
  val unreadCount = notifications.count { !it.isRead }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(8.dp),
      modifier = Modifier
        .fillMaxWidth(0.94f)
        .padding(vertical = 24.dp)
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        // 1. Header with Military Banner
        Surface(
          color = CrimsonRed,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(GoldYellow),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.NotificationsActive,
                  contentDescription = null,
                  tint = NavyDeep,
                  modifier = Modifier.size(20.dp)
                )
              }

              Spacer(modifier = Modifier.width(10.dp))

              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "THÔNG BÁO GDCT",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 14.5.sp,
                    letterSpacing = 0.5.sp
                  )
                  if (unreadCount > 0) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                      shape = RoundedCornerShape(10.dp),
                      color = GoldYellow
                    ) {
                      Text(
                        text = "$unreadCount mới",
                        color = NavyDeep,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                      )
                    }
                  }
                }
                Text(
                  text = "Cổng Quản trị & Đồng bộ Vùng 4",
                  color = Color.White.copy(alpha = 0.85f),
                  fontSize = 11.sp
                )
              }
            }

            IconButton(
              onClick = onDismiss,
              modifier = Modifier
                .size(32.dp)
                .testTag("btn_close_notifications")
            ) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Đóng",
                tint = Color.White
              )
            }
          }
        }

        // 2. Action Bar: Mark all read & Clear all
        if (notifications.isNotEmpty()) {
          Surface(
            color = Color(0xFFF8FAFC),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 6.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              if (unreadCount > 0) {
                TextButton(
                  onClick = onMarkAllAsRead,
                  contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                  modifier = Modifier.testTag("btn_mark_all_read")
                ) {
                  Icon(
                    imageVector = Icons.Default.DoneAll,
                    contentDescription = null,
                    tint = NavyPrimary,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "Đánh dấu tất cả đã xem",
                    color = NavyPrimary,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              } else {
                Text(
                  text = "Tất cả thông báo đã đọc",
                  color = Color(0xFF64748B),
                  fontSize = 11.5.sp,
                  fontWeight = FontWeight.Medium,
                  modifier = Modifier.padding(start = 8.dp)
                )
              }

              TextButton(
                onClick = onClearAll,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                modifier = Modifier.testTag("btn_clear_all_notifs")
              ) {
                Icon(
                  imageVector = Icons.Default.ClearAll,
                  contentDescription = null,
                  tint = Color(0xFF64748B),
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Xóa tất cả",
                  color = Color(0xFF64748B),
                  fontSize = 11.5.sp
                )
              }
            }
          }
        }

        HorizontalDivider(color = Color(0xFFE2E8F0))

        // 3. Notification List
        if (notifications.isEmpty()) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Default.Notifications,
              contentDescription = null,
              tint = Color(0xFFCBD5E1),
              modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = "Chưa có thông báo mới nào",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = Color(0xFF475569)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Khi Quản trị viên thêm chuyên đề hoặc chỉ thị mới trên Web Quản trị, thông báo sẽ tự động xuất hiện tại đây.",
              color = Color(0xFF94A3B8),
              fontSize = 11.5.sp,
              textAlign = androidx.compose.ui.text.style.TextAlign.Center,
              lineHeight = 16.sp
            )
          }
        } else {
          LazyColumn(
            modifier = Modifier
              .fillMaxWidth()
              .heightIn(max = 420.dp)
              .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(notifications, key = { it.id }) { notif ->
              val isUnread = !notif.isRead

              Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                  containerColor = if (isUnread) Color(0xFFFFFBEB) else Color.White
                ),
                border = androidx.compose.foundation.BorderStroke(
                  1.dp,
                  if (isUnread) GoldYellow.copy(alpha = 0.8f) else Color(0xFFE2E8F0)
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable {
                    if (isUnread) onMarkAsRead(notif.id)
                    if (notif.lessonId != null) {
                      onOpenLesson(notif.lessonId)
                    }
                  }
                  .testTag("notif_item_${notif.id}")
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                  verticalAlignment = Alignment.Top
                ) {
                  // Icon
                  Box(
                    modifier = Modifier
                      .size(36.dp)
                      .clip(CircleShape)
                      .background(
                        if (isUnread) CrimsonRed.copy(alpha = 0.15f) else Color(0xFFF1F5F9)
                      ),
                    contentAlignment = Alignment.Center
                  ) {
                    Icon(
                      imageVector = when (notif.type) {
                        "NEW_LESSON" -> Icons.Default.MenuBook
                        "COMMANDER_DIRECTIVE" -> Icons.Default.MilitaryTech
                        else -> Icons.Default.Notifications
                      },
                      contentDescription = null,
                      tint = if (isUnread) CrimsonRed else Color(0xFF64748B),
                      modifier = Modifier.size(18.dp)
                    )
                  }

                  Spacer(modifier = Modifier.width(10.dp))

                  // Content
                  Column(modifier = Modifier.weight(1f)) {
                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.SpaceBetween,
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      Row(verticalAlignment = Alignment.CenterVertically) {
                        if (isUnread) {
                          Box(
                            modifier = Modifier
                              .size(7.dp)
                              .clip(CircleShape)
                              .background(CrimsonRed)
                          )
                          Spacer(modifier = Modifier.width(5.dp))
                        }
                        Text(
                          text = notif.title,
                          color = if (isUnread) NavyDeep else Color(0xFF334155),
                          fontSize = 12.5.sp,
                          fontWeight = if (isUnread) FontWeight.Bold else FontWeight.Medium
                        )
                      }

                      Text(
                        text = notif.timeFormatted,
                        color = Color(0xFF94A3B8),
                        fontSize = 10.sp
                      )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                      text = notif.message,
                      color = if (isUnread) NavyPrimary else Color(0xFF64748B),
                      fontSize = 12.sp,
                      lineHeight = 17.sp,
                      fontWeight = if (isUnread) FontWeight.SemiBold else FontWeight.Normal
                    )

                    if (notif.lessonId != null) {
                      Spacer(modifier = Modifier.height(8.dp))
                      Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                      ) {
                        Surface(
                          shape = RoundedCornerShape(4.dp),
                          color = if (isUnread) CrimsonRed else NavyPrimary
                        ) {
                          Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                          ) {
                            Icon(
                              imageVector = Icons.Default.School,
                              contentDescription = null,
                              tint = Color.White,
                              modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                              text = "Vào học ngay",
                              color = Color.White,
                              fontSize = 10.5.sp,
                              fontWeight = FontWeight.Bold
                            )
                          }
                        }

                        IconButton(
                          onClick = { onDeleteNotification(notif.id) },
                          modifier = Modifier.size(24.dp)
                        ) {
                          Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Xóa thông báo",
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(14.dp)
                          )
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }

        HorizontalDivider(color = Color(0xFFE2E8F0))

        // 4. Bottom Button
        Surface(
          color = Color(0xFFF8FAFC),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            horizontalArrangement = Arrangement.End
          ) {
            Button(
              onClick = onDismiss,
              colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.height(36.dp)
            ) {
              Text("Đóng", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}
