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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.text.TextStyle
import com.example.data.model.LawDoc
import com.example.data.model.UserProfile
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
  onLoginWithCredentials: (username: String, password: String) -> Boolean,
  onQuickLogin: (com.example.data.model.UserAccount) -> Unit,
  onContinueAsGuest: () -> Unit,
  onDismiss: () -> Unit
) {
  var usernameInput by remember { mutableStateOf("") }
  var passwordInput by remember { mutableStateOf("") }
  var isPasswordVisible by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf<String?>(null) }

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
                  text = "ĐĂNG NHẬP HỆ THỐNG GDCT",
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
                  text = "Không cần đăng nhập vẫn xem được toàn bộ bài giảng GDCT công khai. Đăng nhập tài khoản quân nhân để mở khóa các chuyên đề LƯU HÀNH NỘI BỘ.",
                  color = Color(0xFF1E3A8A),
                  fontSize = 11.5.sp,
                  lineHeight = 15.sp
                )
              }
            }
          }

          // Username Input
          item {
            OutlinedTextField(
              value = usernameInput,
              onValueChange = {
                usernameInput = it
                errorMessage = null
              },
              label = { Text("Tên tài khoản (Username)", color = NavyPrimary, fontWeight = FontWeight.Bold) },
              placeholder = { Text("VD: phamtatthang_162 hoặc Mã QN", color = Color(0xFF64748B)) },
              singleLine = true,
              textStyle = TextStyle(
                color = Color(0xFF000000),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
              ),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFF000000),
                unfocusedTextColor = Color(0xFF000000),
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFF8FAFC),
                focusedBorderColor = NavyPrimary,
                unfocusedBorderColor = Color(0xFF94A3B8),
                focusedLabelColor = NavyPrimary,
                unfocusedLabelColor = Color(0xFF334155),
                cursorColor = Color(0xFF000000)
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_login_username")
            )
            Text(
              text = "Tên tài khoản theo định dạng: ten_donvi (Ví dụ: phamtatthang_162)",
              color = Color(0xFF475569),
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
          }

          // Password Input
          item {
            OutlinedTextField(
              value = passwordInput,
              onValueChange = {
                passwordInput = it
                errorMessage = null
              },
              label = { Text("Mật khẩu", color = NavyPrimary, fontWeight = FontWeight.Bold) },
              placeholder = { Text("Mật khẩu mặc định: 12345@abc", color = Color(0xFF64748B)) },
              singleLine = true,
              textStyle = TextStyle(
                color = Color(0xFF000000),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
              ),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFF000000),
                unfocusedTextColor = Color(0xFF000000),
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFF8FAFC),
                focusedBorderColor = NavyPrimary,
                unfocusedBorderColor = Color(0xFF94A3B8),
                focusedLabelColor = NavyPrimary,
                unfocusedLabelColor = Color(0xFF334155),
                cursorColor = Color(0xFF000000)
              ),
              visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
              trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                  Icon(
                    imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (isPasswordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu",
                    tint = Color(0xFF0F172A)
                  )
                }
              },
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_login_password")
            )
            Text(
              text = "Mật khẩu mặc định ban đầu do Quản trị cấp: 12345@abc",
              color = Color(0xFF166534),
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
          }

          if (errorMessage != null) {
            item {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFFEF2F2),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFECACA)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Text(
                  text = errorMessage!!,
                  color = CrimsonRed,
                  fontSize = 11.5.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(8.dp)
                )
              }
            }
          }

          item {
            Spacer(modifier = Modifier.height(4.dp))
            Button(
              onClick = {
                if (usernameInput.isBlank()) {
                  errorMessage = "Vui lòng nhập Tên tài khoản được cấp (VD: phamtatthang_162)"
                  return@Button
                }
                val success = onLoginWithCredentials(usernameInput, passwordInput)
                if (!success) {
                  errorMessage = "Tên tài khoản hoặc Mật khẩu không chính xác. Mật khẩu mặc định là 12345@abc."
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_confirm_login")
            ) {
              Text("XÁC THỰC & ĐĂNG NHẬP", fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
            }
          }

          item {
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
fun EditProfileDialog(
  userProfile: UserProfile,
  onSave: (fullName: String, rank: String, role: String, unit: String, phone: String, militaryId: String) -> Unit,
  onDismiss: () -> Unit
) {
  var fullNameInput by remember { mutableStateOf(userProfile.name) }
  var rankInput by remember { mutableStateOf(userProfile.rank) }
  var roleInput by remember { mutableStateOf(userProfile.role) }
  var unitInput by remember { mutableStateOf(userProfile.unit) }
  var phoneInput by remember { mutableStateOf(userProfile.phone) }
  var militaryIdInput by remember { mutableStateOf(userProfile.militaryId) }
  var errorText by remember { mutableStateOf<String?>(null) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(10.dp),
      modifier = Modifier
        .fillMaxWidth(0.94f)
        .fillMaxHeight(0.85f)
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Surface(color = NavyDeep, modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Person, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(24.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text("CHỈNH SỬA THÔNG TIN QUÂN NHÂN", color = Color.White, fontWeight = FontWeight.Black, fontSize = 13.5.sp)
                Text("Đồng bộ trực tiếp về Cổng Web Quản trị", color = GoldYellow, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
              }
            }
            IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
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
          // Info banner
          item {
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = Color(0xFFEFF6FF),
              border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Badge, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "Tài khoản: ${userProfile.username.ifEmpty { "phamtatthang_162" }} (STT: #${userProfile.orderNumber})",
                  color = NavyDeep,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }

          // Full Name
          item {
            OutlinedTextField(
              value = fullNameInput,
              onValueChange = { fullNameInput = it; errorText = null },
              label = { Text("Họ và tên quân nhân *", color = NavyPrimary, fontWeight = FontWeight.Bold) },
              singleLine = true,
              textStyle = TextStyle(color = Color(0xFF000000), fontSize = 14.5.sp, fontWeight = FontWeight.SemiBold),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFF000000),
                unfocusedTextColor = Color(0xFF000000),
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFF8FAFC),
                focusedBorderColor = NavyPrimary,
                unfocusedBorderColor = Color(0xFF94A3B8),
                focusedLabelColor = NavyPrimary,
                unfocusedLabelColor = Color(0xFF334155),
                cursorColor = Color(0xFF000000)
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth().testTag("input_edit_fullname")
            )
          }

          // Rank
          item {
            OutlinedTextField(
              value = rankInput,
              onValueChange = { rankInput = it; errorText = null },
              label = { Text("Cấp bậc (VD: Đại úy, Thượng úy, Thiếu tá, Hạ sĩ...) *", color = NavyPrimary, fontWeight = FontWeight.Bold) },
              singleLine = true,
              textStyle = TextStyle(color = Color(0xFF000000), fontSize = 14.5.sp, fontWeight = FontWeight.SemiBold),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFF000000),
                unfocusedTextColor = Color(0xFF000000),
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFF8FAFC),
                focusedBorderColor = NavyPrimary,
                unfocusedBorderColor = Color(0xFF94A3B8),
                focusedLabelColor = NavyPrimary,
                unfocusedLabelColor = Color(0xFF334155),
                cursorColor = Color(0xFF000000)
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth().testTag("input_edit_rank")
            )
          }

          // Role / Position
          item {
            OutlinedTextField(
              value = roleInput,
              onValueChange = { roleInput = it; errorText = null },
              label = { Text("Chức vụ / Nhiệm vụ *", color = NavyPrimary, fontWeight = FontWeight.Bold) },
              placeholder = { Text("VD: Thuyền phó Tàu 015, Chính trị viên...", color = Color(0xFF64748B)) },
              singleLine = true,
              textStyle = TextStyle(color = Color(0xFF000000), fontSize = 14.5.sp, fontWeight = FontWeight.SemiBold),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFF000000),
                unfocusedTextColor = Color(0xFF000000),
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFF8FAFC),
                focusedBorderColor = NavyPrimary,
                unfocusedBorderColor = Color(0xFF94A3B8),
                focusedLabelColor = NavyPrimary,
                unfocusedLabelColor = Color(0xFF334155),
                cursorColor = Color(0xFF000000)
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth().testTag("input_edit_role")
            )
          }

          // Unit
          item {
            OutlinedTextField(
              value = unitInput,
              onValueChange = { unitInput = it; errorText = null },
              label = { Text("Đơn vị trực thuộc *", color = NavyPrimary, fontWeight = FontWeight.Bold) },
              placeholder = { Text("VD: Lữ đoàn 162, Lữ đoàn 146, Lữ đoàn 955...", color = Color(0xFF64748B)) },
              singleLine = true,
              textStyle = TextStyle(color = Color(0xFF000000), fontSize = 14.5.sp, fontWeight = FontWeight.SemiBold),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFF000000),
                unfocusedTextColor = Color(0xFF000000),
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFF8FAFC),
                focusedBorderColor = NavyPrimary,
                unfocusedBorderColor = Color(0xFF94A3B8),
                focusedLabelColor = NavyPrimary,
                unfocusedLabelColor = Color(0xFF334155),
                cursorColor = Color(0xFF000000)
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth().testTag("input_edit_unit")
            )
          }

          // Phone
          item {
            OutlinedTextField(
              value = phoneInput,
              onValueChange = { phoneInput = it; errorText = null },
              label = { Text("Số điện thoại liên hệ", color = NavyPrimary, fontWeight = FontWeight.Bold) },
              placeholder = { Text("VD: 0988.112.233", color = Color(0xFF64748B)) },
              singleLine = true,
              textStyle = TextStyle(color = Color(0xFF000000), fontSize = 14.5.sp, fontWeight = FontWeight.SemiBold),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFF000000),
                unfocusedTextColor = Color(0xFF000000),
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFF8FAFC),
                focusedBorderColor = NavyPrimary,
                unfocusedBorderColor = Color(0xFF94A3B8),
                focusedLabelColor = NavyPrimary,
                unfocusedLabelColor = Color(0xFF334155),
                cursorColor = Color(0xFF000000)
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth().testTag("input_edit_phone")
            )
          }

          // Military ID
          item {
            OutlinedTextField(
              value = militaryIdInput,
              onValueChange = { militaryIdInput = it; errorText = null },
              label = { Text("Mã Quân nhân", color = NavyPrimary, fontWeight = FontWeight.Bold) },
              placeholder = { Text("VD: QN-16201", color = Color(0xFF64748B)) },
              singleLine = true,
              textStyle = TextStyle(color = Color(0xFF000000), fontSize = 14.5.sp, fontWeight = FontWeight.SemiBold),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFF000000),
                unfocusedTextColor = Color(0xFF000000),
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFF8FAFC),
                focusedBorderColor = NavyPrimary,
                unfocusedBorderColor = Color(0xFF94A3B8),
                focusedLabelColor = NavyPrimary,
                unfocusedLabelColor = Color(0xFF334155),
                cursorColor = Color(0xFF000000)
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth().testTag("input_edit_military_id")
            )
          }

          if (errorText != null) {
            item {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFFEF2F2),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFECACA)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Text(
                  text = errorText!!,
                  color = CrimsonRed,
                  fontSize = 11.5.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(8.dp)
                )
              }
            }
          }
        }

        // Action Buttons
        Surface(
          color = Color(0xFFF8FAFC),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = onDismiss,
              colors = ButtonDefaults.outlinedButtonColors(),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("Hủy bỏ", color = Color(0xFF64748B), fontSize = 12.5.sp)
            }

            Button(
              onClick = {
                if (fullNameInput.isBlank()) {
                  errorText = "Vui lòng nhập Họ và tên quân nhân"
                  return@Button
                }
                if (rankInput.isBlank()) {
                  errorText = "Vui lòng nhập Cấp bậc quân hàm"
                  return@Button
                }
                if (roleInput.isBlank()) {
                  errorText = "Vui lòng nhập Chức vụ / Nhiệm vụ"
                  return@Button
                }
                if (unitInput.isBlank()) {
                  errorText = "Vui lòng nhập Đơn vị trực thuộc"
                  return@Button
                }
                onSave(
                  fullNameInput.trim(),
                  rankInput.trim(),
                  roleInput.trim(),
                  unitInput.trim(),
                  phoneInput.trim(),
                  militaryIdInput.trim()
                )
                onDismiss()
              },
              colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1.5f).testTag("btn_save_profile")
            ) {
              Icon(Icons.Default.Save, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("LƯU & GỬI VỀ WEB", fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
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

