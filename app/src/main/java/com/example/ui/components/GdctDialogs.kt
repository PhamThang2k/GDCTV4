package com.example.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.LawDoc
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun DailyQuoteDialog(onDismiss: () -> Unit) {
  var isAudioPlaying by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(6.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column {
        Surface(color = CrimsonRed, modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.FormatQuote, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(24.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text("LỜI BÁC DẠY HẢI QUÂN", color = Color.White, fontWeight = FontWeight.Black, fontSize = 15.sp)
            }
            IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
              Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White)
            }
          }
        }

        Column(modifier = Modifier.padding(18.dp)) {
          Text(
            text = "\"Ngày trước ta chỉ có đêm và rừng. Ngày nay ta có ngày, có trời, có biển. Bờ biển ta dài, tươi đẹp, ta phải biết giữ gìn lấy nó.\"",
            color = NavyDeep,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            lineHeight = 22.sp
          )

          Spacer(modifier = Modifier.height(12.dp))

          Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFFF1F5F9),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text(
                text = "📍 Bối cảnh lịch sử:",
                color = NavyPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "Bác Hồ căn dặn cán bộ, chiến sĩ Hải quân nhân dân Việt Nam ngày 15/3/1961 tại vùng biển Đông Bắc.",
                color = Color(0xFF475569),
                fontSize = 12.sp,
                lineHeight = 17.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (isAudioPlaying) GoldYellow else Color(0xFFE2E8F0),
              modifier = Modifier.clickable { isAudioPlaying = !isAudioPlaying }
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.VolumeUp,
                  contentDescription = "Nghe đọc",
                  tint = if (isAudioPlaying) NavyDeep else NavyPrimary,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (isAudioPlaying) "Đang phát giọng đọc..." else "Nghe phát thanh",
                  color = if (isAudioPlaying) NavyDeep else NavyPrimary,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            Button(
              onClick = onDismiss,
              colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
            ) {
              Text("Đã tiếp thu", fontSize = 12.sp)
            }
          }
        }
      }
    }
  }
}

@Composable
fun LawDetailDialog(law: LawDoc, onDismiss: () -> Unit) {
  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.95f)
        .fillMaxHeight(0.9f)
        .clip(RoundedCornerShape(20.dp)),
      color = Color.White,
      tonalElevation = 6.dp
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        Surface(color = NavyDeep, modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Gavel, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text("TỦ SÁCH PHÁP LUẬT VÙNG 4", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            IconButton(onClick = onDismiss) {
              Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White)
            }
          }
        }

        LazyColumn(
          modifier = Modifier
            .weight(1f)
            .padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          item {
            Text(text = law.title, color = NavyPrimary, fontSize = 16.sp, fontWeight = FontWeight.Black)
            Text(text = "Cơ quan ban hành: ${law.issuedBy} • Phân loại: ${law.category}", color = Color(0xFF64748B), fontSize = 12.sp)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text(text = law.summary, color = Color(0xFF334155), fontSize = 13.5.sp, lineHeight = 20.sp)
          }

          items(law.keyArticles) { (articleNum, content) ->
            Card(
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
              border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Text(text = articleNum, color = CrimsonRed, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = content, color = Color(0xFF1E293B), fontSize = 13.sp, lineHeight = 20.sp)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun AddNoteDialog(
  lessonTitle: String,
  onSave: (String, String, String) -> Unit,
  onDismiss: () -> Unit
) {
  var noteTitle by remember { mutableStateOf("Thu hoạch: $lessonTitle") }
  var noteContent by remember { mutableStateOf("") }
  var category by remember { mutableStateOf("Liên hệ thực tiễn bản thân") }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(6.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.EditNote, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("SỔ TAY GHI CHÉP THU HOẠCH", color = NavyDeep, fontSize = 14.sp, fontWeight = FontWeight.Black)
          }
          IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Đóng")
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = noteTitle,
          onValueChange = { noteTitle = it },
          label = { Text("Tiêu đề ghi chép") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = noteContent,
          onValueChange = { noteContent = it },
          label = { Text("Nội dung thu hoạch / Liên hệ công tác tại tàu, đơn vị") },
          placeholder = { Text("Ví dụ: Nhận thức về nhiệm vụ canh trực, giữ nghiêm kỷ luật, giúp đỡ đồng đội...") },
          modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
          shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          Button(
            onClick = onDismiss,
            colors = ButtonDefaults.outlinedButtonColors()
          ) {
            Text("Hủy", color = Color(0xFF64748B))
          }
          Spacer(modifier = Modifier.width(8.dp))
          Button(
            onClick = {
              if (noteContent.isNotBlank()) {
                onSave(noteTitle, noteContent, category)
              }
            },
            enabled = noteContent.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
            modifier = Modifier.testTag("btn_save_note")
          ) {
            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Lưu vào sổ tay")
          }
        }
      }
    }
  }
}

@Composable
fun LoginDialog(
  userAccounts: List<com.example.data.model.UserAccount>,
  onLoginWithCredentials: (militaryId: String, pin: String) -> Boolean,
  onQuickLogin: (com.example.data.model.UserAccount) -> Unit,
  onContinueAsGuest: () -> Unit,
  onDismiss: () -> Unit
) {
  var militaryIdInput by remember { mutableStateOf("") }
  var pinInput by remember { mutableStateOf("") }
  var errorMessage by remember { mutableStateOf<String?>(null) }
  var isQuickSelectOpen by remember { mutableStateOf(true) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Card(
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(8.dp),
      modifier = Modifier
        .fillMaxWidth(0.94f)
        .padding(vertical = 16.dp)
    ) {
      Column {
        // Dialog Header
        Surface(
          color = NavyDeep,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(34.dp)
                  .clip(CircleShape)
                  .background(GoldYellow),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.MilitaryTech,
                  contentDescription = null,
                  tint = NavyDeep,
                  modifier = Modifier.size(20.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "ĐĂNG NHẬP QUÂN NHÂN",
                  color = Color.White,
                  fontSize = 14.5.sp,
                  fontWeight = FontWeight.Black,
                  letterSpacing = 0.5.sp
                )
                Text(
                  text = "Tài khoản do Cổng Web Quản trị cấp",
                  color = GoldYellow,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium
                )
              }
            }
            IconButton(onClick = onDismiss, modifier = Modifier.size(30.dp)) {
              Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White)
            }
          }
        }

        // Dialog Body
        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          item {
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = Color(0xFFEFF6FF),
              border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.LibraryBooks,
                  contentDescription = null,
                  tint = NavyPrimary,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "Không cần đăng nhập vẫn xem được toàn bộ bài giảng GDCT công khai. Đăng nhập để mở khóa chuyên đề LƯU HÀNH NỘI BỘ.",
                  color = Color(0xFF1E3A8A),
                  fontSize = 11.5.sp,
                  lineHeight = 15.sp
                )
              }
            }
          }

          // Manual Login Inputs
          item {
            OutlinedTextField(
              value = militaryIdInput,
              onValueChange = {
                militaryIdInput = it
                errorMessage = null
              },
              label = { Text("Số hiệu QN / Mã cán bộ") },
              placeholder = { Text("VD: HQ-V4-2026") },
              singleLine = true,
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_login_military_id")
            )
          }

          item {
            OutlinedTextField(
              value = pinInput,
              onValueChange = {
                pinInput = it
                errorMessage = null
              },
              label = { Text("Mã PIN bảo mật") },
              placeholder = { Text("Mặc định: 123456") },
              singleLine = true,
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_login_pin")
            )
          }

          if (errorMessage != null) {
            item {
              Text(
                text = errorMessage!!,
                color = CrimsonRed,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          item {
            Button(
              onClick = {
                if (militaryIdInput.isBlank()) {
                  errorMessage = "Vui lòng nhập Số hiệu quân nhân"
                  return@Button
                }
                val success = onLoginWithCredentials(militaryIdInput, pinInput)
                if (!success) {
                  errorMessage = "Số hiệu QN hoặc Mã PIN không đúng. Bạn có thể chọn nhanh tài khoản mẫu bên dưới."
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .testTag("btn_confirm_login")
            ) {
              Text("XÁC THỰC & ĐĂNG NHẬP", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
          }

          // Quick 1-click login section
          item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Color(0xFFE2E8F0))
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable { isQuickSelectOpen = !isQuickSelectOpen },
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "⚡ Đăng nhập nhanh tài khoản mẫu được cấp:",
                color = Color(0xFF475569),
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = if (isQuickSelectOpen) "Thu gọn" else "Mở rộng",
                color = NavyPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }

          if (isQuickSelectOpen) {
            items(userAccounts) { acc ->
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFF8FAFC),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable { onQuickLogin(acc) }
                  .testTag("quick_login_${acc.id}")
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      Text(
                        text = acc.fullName,
                        color = NavyDeep,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold
                      )
                      Spacer(modifier = Modifier.width(6.dp))
                      Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color(0xFFDCFCE7)
                      ) {
                        Text(
                          text = "Nội bộ",
                          color = SuccessGreen,
                          fontSize = 9.sp,
                          fontWeight = FontWeight.Bold,
                          modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                      }
                    }
                    Text(
                      text = "${acc.rank} • ${acc.role} (${acc.unit})",
                      color = Color(0xFF64748B),
                      fontSize = 10.5.sp
                    )
                  }

                  Button(
                    onClick = { onQuickLogin(acc) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A)),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.height(30.dp)
                  ) {
                    Text("Chọn", fontSize = 11.sp)
                  }
                }
              }
            }
          }

          item {
            Spacer(modifier = Modifier.height(4.dp))
            Button(
              onClick = onContinueAsGuest,
              colors = ButtonDefaults.outlinedButtonColors(),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .testTag("btn_guest_mode")
            ) {
              Text("Tiếp tục xem ở Chế độ Khách (Không cần tài khoản)", color = Color(0xFF64748B), fontSize = 11.5.sp)
            }
          }
        }
      }
    }
  }
}

@Composable
fun InternalRestrictedDialog(
  lessonTitle: String,
  onLoginClick: () -> Unit,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(8.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column {
        Surface(color = CrimsonRed, modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.MilitaryTech, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(24.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text("TÀI LIỆU LƯU HÀNH NỘI BỘ", color = Color.White, fontWeight = FontWeight.Black, fontSize = 14.5.sp)
            }
            IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
              Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White)
            }
          }
        }

        Column(modifier = Modifier.padding(18.dp)) {
          Text(
            text = "Yêu cầu đăng nhập tài khoản quân nhân:",
            color = NavyDeep,
            fontSize = 14.sp,
            fontWeight = FontWeight.Black
          )

          Spacer(modifier = Modifier.height(6.dp))

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFFEF2F2),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFECACA)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = "📌 $lessonTitle",
              color = Color(0xFF991B1B),
              fontSize = 12.5.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(10.dp)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Chuyên đề này được phân loại LƯU HÀNH NỘI BỘ dành riêng cho Sĩ quan, QNCN có tài khoản được cấp phát và quản lý qua Cổng Web Quản trị.\n\nCác nội dung GDCT công khai khác bạn vẫn có thể xem bình thường mà không cần đăng nhập.",
            color = Color(0xFF475569),
            fontSize = 12.sp,
            lineHeight = 17.sp
          )

          Spacer(modifier = Modifier.height(16.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = onDismiss,
              colors = ButtonDefaults.outlinedButtonColors(),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("Đóng", color = Color(0xFF64748B), fontSize = 12.sp)
            }

            Button(
              onClick = onLoginClick,
              colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .weight(1.5f)
                .testTag("btn_internal_dialog_login")
            ) {
              Text("Đăng nhập ngay", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
          }
        }
      }
    }
  }
}

