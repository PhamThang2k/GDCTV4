package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.local.QuizSubmissionEntity
import com.example.data.local.StudyProgressEntity
import com.example.data.model.Lesson
import com.example.data.model.UserAccount
import com.example.data.model.UserProfile
import com.example.ui.components.DongSonMotifCanvas
import com.example.ui.components.EditProfileDialog
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.NavyContainer
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SuccessGreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProfileAdminScreen(
  userProfile: UserProfile,
  lessons: List<Lesson>,
  progressMap: Map<String, StudyProgressEntity>,
  quizSubmissions: List<QuizSubmissionEntity>,
  onOpenLesson: (Lesson) -> Unit,
  onOpenLoginDialog: () -> Unit,
  onChangePassword: ((oldPass: String, newPass: String) -> Boolean)? = null,
  onUpdateProfile: ((fullName: String, rank: String, role: String, unit: String, phone: String, militaryId: String) -> Unit)? = null,
  onSyncWithServer: (() -> Unit)? = null,
  adminUserAccounts: List<UserAccount> = emptyList(),
  onResetAccountPassword: ((String) -> Unit)? = null,
  onLogout: () -> Unit,
  serverStatus: String = "Máy chủ Internet trực tuyến",
  customServerUrl: String = "",
  onSetCustomServerUrl: ((String) -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var selectedSubTab by remember { mutableIntStateOf(0) } // 0: Hồ sơ học tập, 1: Báo cáo Quản trị & Cổng Web CMS
  var showChangePasswordDialog by remember { mutableStateOf(false) }
  var showEditProfileDialog by remember { mutableStateOf(false) }
  var showServerConfigDialog by remember { mutableStateOf(false) }
  var serverUrlInput by remember(customServerUrl) { mutableStateOf(customServerUrl) }
  
  var oldPasswordInput by remember { mutableStateOf("") }
  var newPasswordInput by remember { mutableStateOf("") }
  var confirmPasswordInput by remember { mutableStateOf("") }
  var isOldPassVisible by remember { mutableStateOf(false) }
  var isNewPassVisible by remember { mutableStateOf(false) }
  var changePasswordError by remember { mutableStateOf<String?>(null) }
  var changePasswordSuccess by remember { mutableStateOf(false) }

  val totalLessons = lessons.size
  val completedLessons = progressMap.values.count { it.isCompleted || it.progressPercent >= 100 }
  val avgScore = if (quizSubmissions.isNotEmpty()) {
    quizSubmissions.sumOf { it.percentage } / quizSubmissions.size
  } else 0

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF4F7FA)),
    contentPadding = PaddingValues(bottom = 28.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Soldier Military ID Card Header
    item {
      Card(
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
        colors = CardDefaults.cardColors(containerColor = NavyDeep),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              Brush.verticalGradient(
                colors = listOf(
                  NavyDeep,
                  NavyPrimary
                )
              )
            )
        ) {
          DongSonMotifCanvas(modifier = Modifier.matchParentSize(), tint = GoldYellow)

          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(18.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              // Avatar with Navy Ring
              Box(
                modifier = Modifier
                  .size(64.dp)
                  .clip(CircleShape)
                  .background(Color.White.copy(alpha = 0.2f))
                  .border(2.dp, GoldYellow, CircleShape)
                  .padding(3.dp),
                contentAlignment = Alignment.Center
              ) {
                Image(
                  painter = painterResource(id = R.drawable.ic_navy_logo),
                  contentDescription = null,
                  contentScale = ContentScale.Crop,
                  modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                )
              }

              Spacer(modifier = Modifier.width(14.dp))

              Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = if (userProfile.isLoggedIn) userProfile.name else "Quân nhân / Khách vãng lai",
                    color = Color.White,
                    fontSize = 16.5.sp,
                    fontWeight = FontWeight.Black
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  if (userProfile.isLoggedIn) {
                    Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(16.dp))
                  }
                }

                Spacer(modifier = Modifier.height(2.dp))

                if (userProfile.isLoggedIn) {
                  Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = SuccessGreen.copy(alpha = 0.3f),
                    modifier = Modifier.padding(vertical = 2.dp)
                  ) {
                    Text(
                      text = "✓ ĐÃ LƯU ĐĂNG NHẬP TRÊN THIẾT BỊ",
                      color = GoldYellow,
                      fontSize = 9.5.sp,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                  }
                }

                Text(
                  text = if (userProfile.isLoggedIn) "Cấp bậc: ${userProfile.rank} • ${userProfile.role}" else "Chưa đăng nhập tài khoản quân nhân",
                  color = GoldYellow,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )

                Text(
                  text = if (userProfile.isLoggedIn) "Đơn vị: ${userProfile.unit}" else "Xem bài giảng GDCT công khai tự do",
                  color = Color.White.copy(alpha = 0.85f),
                  fontSize = 11.5.sp
                )

                Text(
                  text = if (userProfile.isLoggedIn) "Tài khoản: ${userProfile.username.ifEmpty { "phamtatthang_162" }} (Mã: ${userProfile.militaryId})" else "Đăng nhập để mở khóa bài học nội bộ",
                  color = Color.White.copy(alpha = 0.85f),
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            if (userProfile.isLoggedIn) {
              Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  // Button Edit Profile
                  Button(
                    onClick = { showEditProfileDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.weight(1f).testTag("btn_open_edit_profile")
                  ) {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = NavyDeep, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Sửa hồ sơ", color = NavyDeep, fontSize = 11.sp, fontWeight = FontWeight.Black)
                  }

                  // Button Change Password
                  Button(
                    onClick = {
                      oldPasswordInput = ""
                      newPasswordInput = ""
                      confirmPasswordInput = ""
                      changePasswordError = null
                      changePasswordSuccess = false
                      showChangePasswordDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldYellow),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.weight(1.1f).testTag("btn_open_change_pass")
                  ) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = NavyDeep, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Đổi mật khẩu", color = NavyDeep, fontSize = 11.sp, fontWeight = FontWeight.Black)
                  }
                }

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  Button(
                    onClick = onOpenLoginDialog,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.weight(1f)
                  ) {
                    Text("Đổi TK", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                  }

                  Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed.copy(alpha = 0.85f)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.weight(1f)
                  ) {
                    Text("Đăng xuất", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                  }
                }
              }
            } else {
              Button(
                onClick = onOpenLoginDialog,
                colors = ButtonDefaults.buttonColors(containerColor = GoldYellow),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("btn_profile_login")
              ) {
                Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = NavyDeep, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("ĐĂNG NHẬP TÀI KHOẢN QUÂN NHÂN", color = NavyDeep, fontSize = 12.sp, fontWeight = FontWeight.Black)
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 4 Mini Metric Cards
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              MetricBox(title = "Đã hoàn thành", value = "$completedLessons/$totalLessons", subtitle = "Bài giảng", modifier = Modifier.weight(1f))
              MetricBox(title = "Điểm TB thi", value = if (avgScore > 0) "$avgScore%" else "--", subtitle = "Trắc nghiệm", modifier = Modifier.weight(1f))
              MetricBox(title = "Đánh giá", value = if (avgScore >= 80) "GIỎI" else "KHÁ", subtitle = "Chính trị viên", modifier = Modifier.weight(1f))
              MetricBox(title = "Đồng bộ Web", value = "TỰ ĐỘNG", subtitle = "Trực tuyến", modifier = Modifier.weight(1f))
            }
          }
        }
      }
    }

    // 2. Sub-tab Selector
    item {
      Surface(
        color = Color.White,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 14.dp),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 2.dp
      ) {
        TabRow(
          selectedTabIndex = selectedSubTab,
          containerColor = Color.White,
          contentColor = NavyPrimary,
          indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
              Modifier.tabIndicatorOffset(tabPositions[selectedSubTab]),
              color = NavyPrimary,
              height = 3.dp
            )
          }
        ) {
          Tab(
            selected = selectedSubTab == 0,
            onClick = { selectedSubTab = 0 },
            modifier = Modifier.testTag("subtab_learning_profile"),
            text = {
              Text("Tiến độ tự học", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
            }
          )
          Tab(
            selected = selectedSubTab == 1,
            onClick = { selectedSubTab = 1 },
            modifier = Modifier.testTag("subtab_admin_report"),
            text = {
              Text("Cổng Web Quản trị & Báo cáo", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
            }
          )
        }
      }
    }

    // 3. Tab Contents
    if (selectedSubTab == 0) {
      // Learning Profile & Lesson Checklist
      item {
        Column(modifier = Modifier.padding(horizontal = 14.dp)) {
          Text(
            text = "CHI TIẾT TIẾN ĐỘ CÁC BÀI GIẢNG",
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = NavyDeep,
            letterSpacing = 0.5.sp
          )
        }
      }

      items(lessons) { lesson ->
        val p = progressMap[lesson.id]
        val pct = p?.progressPercent ?: 0
        val isFinished = p?.isCompleted == true || pct >= 100
        val lastQuiz = quizSubmissions.firstOrNull { it.lessonId == lesson.id }

        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
            .clickable { onOpenLesson(lesson) }
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(shape = RoundedCornerShape(6.dp), color = NavyContainer) {
                Text(
                  text = lesson.code,
                  color = NavyPrimary,
                  fontSize = 10.5.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }

              Text(
                text = if (isFinished) "Hoàn thành 100%" else "$pct% đã học",
                color = if (isFinished) SuccessGreen else NavyPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(text = lesson.title, color = NavyDeep, fontSize = 13.5.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            if (lastQuiz != null) {
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFFF0FDF4),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(Icons.Default.MilitaryTech, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(14.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "Điểm kiểm tra: ${lastQuiz.score}/${lastQuiz.totalQuestions} (${lastQuiz.percentage}%) - Xếp loại ${if (lastQuiz.percentage >= 80) "Giỏi" else "Khá"}",
                    color = Color(0xFF166534),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                  )
                }
              }
            } else {
              Text(text = "Chưa làm bài kiểm tra đánh giá", color = Color(0xFF94A3B8), fontSize = 11.sp)
            }
          }
        }
      }
    } else {
      // 3. Tab 1: Web Admin Portal & Command Report
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, NavyPrimary.copy(alpha = 0.3f)),
          elevation = CardDefaults.cardElevation(3.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Surface(shape = CircleShape, color = NavyDeep, modifier = Modifier.size(36.dp)) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(Icons.Default.Language, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(20.dp))
                }
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "CỔNG WEB QUẢN TRỊ GDCT VÙNG 4",
                  color = NavyDeep,
                  fontSize = 13.5.sp,
                  fontWeight = FontWeight.Black
                )
                Text(
                  text = "Web Admin CMS - Máy chủ đồng bộ thời gian thực",
                  color = Color(0xFF64748B),
                  fontSize = 11.sp
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
              shape = RoundedCornerShape(10.dp),
              color = Color(0xFFF8FAFC),
              border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(10.dp)
                      .background(SuccessGreen, shape = CircleShape)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Trực tuyến: Tự động đồng bộ thời gian thực 2 chiều (3.5s)",
                    color = SuccessGreen,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold
                  )
                }

                Text(
                  text = "🔑 Thông tin đăng nhập Cổng Quản trị trên Trình duyệt:",
                  color = NavyPrimary,
                  fontSize = 11.5.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = "• Tài khoản Quản trị: gdct_vung4",
                  color = Color(0xFF1E293B),
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold
                )
                Text(
                  text = "• Mật khẩu quản trị: 12345@abc",
                  color = Color(0xFF1E293B),
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold
                )
                Text(
                  text = "• Cổng Cloud Internet: https://gdctv4.onrender.com",
                  color = Color(0xFF0F766E),
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium
                )
                if (customServerUrl.isNotBlank()) {
                  Text(
                    text = "• Máy chủ tùy chỉnh: $customServerUrl",
                    color = NavyPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Button(
                onClick = {
                  val targetUrl = if (customServerUrl.isNotBlank()) customServerUrl else "https://gdctv4.onrender.com"
                  try {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(targetUrl))
                    context.startActivity(browserIntent)
                  } catch (e: Exception) {
                    onSyncWithServer?.invoke()
                  }
                },
                colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1.2f).testTag("btn_open_web_admin")
              ) {
                Icon(Icons.Default.OpenInBrowser, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("MỞ WEB CLOUD", fontWeight = FontWeight.Bold, fontSize = 11.sp)
              }

              Button(
                onClick = { onSyncWithServer?.invoke() },
                colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f).testTag("btn_sync_now")
              ) {
                Icon(Icons.Default.Sync, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("ĐỒNG BỘ", fontWeight = FontWeight.Bold, fontSize = 11.sp)
              }

              OutlinedButton(
                onClick = { showServerConfigDialog = true },
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 8.dp),
                modifier = Modifier.weight(0.8f)
              ) {
                Icon(Icons.Default.Settings, contentDescription = null, tint = NavyDeep, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(2.dp))
                Text("Cấu hình", color = NavyDeep, fontSize = 10.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }

      // Accounts Table / Quick user overview
      item {
        Column(modifier = Modifier.padding(horizontal = 14.dp)) {
          Text(
            text = "DANH SÁCH TÀI KHOẢN QUÂN NHÂN TRÊN HỆ THỐNG (${adminUserAccounts.size})",
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = NavyDeep,
            letterSpacing = 0.5.sp
          )
        }
      }

      items(adminUserAccounts) { acc ->
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                  shape = RoundedCornerShape(4.dp),
                  color = NavyContainer,
                  modifier = Modifier.padding(end = 6.dp)
                ) {
                  Text(
                    text = "#${acc.orderNumber}",
                    color = NavyPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                  )
                }
                Text(
                  text = "${acc.rank} ${acc.fullName}",
                  color = NavyDeep,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold
                )
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Tài khoản: ${acc.username} • ${acc.unit}",
                color = Color(0xFF64748B),
                fontSize = 11.5.sp
              )
              Text(
                text = "Chức vụ: ${acc.role} • Mã QN: ${acc.militaryId}",
                color = Color(0xFF475569),
                fontSize = 11.sp
              )
            }

            if (onResetAccountPassword != null) {
              Button(
                onClick = { onResetAccountPassword(acc.id) },
                colors = ButtonDefaults.outlinedButtonColors(),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Icon(Icons.Default.LockReset, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Reset MK", color = NavyPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }

      item {
        Column(modifier = Modifier.padding(horizontal = 14.dp)) {
          Text(
            text = "NHẬT KÝ BÀI THI ĐÃ NỘP & Ý KIẾN CHỈ HUY",
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = NavyDeep,
            letterSpacing = 0.5.sp
          )
        }
      }

      if (quizSubmissions.isEmpty()) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(30.dp),
            contentAlignment = Alignment.Center
          ) {
            Text("Chưa có kết quả bài thi nào được nộp.", color = Color(0xFF64748B))
          }
        }
      } else {
        items(quizSubmissions) { submission ->
          val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
          val dateStr = dateFormat.format(Date(submission.timestamp))

          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp),
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 14.dp)
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = if (submission.passed) SuccessGreen else CrimsonRed
                ) {
                  Text(
                    text = if (submission.percentage >= 80) "XẾP LOẠI GIỎI" else "XẾP LOẠI KHÁ",
                    color = Color.White,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }

                Text(text = dateStr, color = Color(0xFF94A3B8), fontSize = 10.5.sp)
              }

              Spacer(modifier = Modifier.height(6.dp))

              Text(text = submission.lessonTitle, color = NavyDeep, fontSize = 13.5.sp, fontWeight = FontWeight.Bold)

              Spacer(modifier = Modifier.height(6.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "Kết quả: ${submission.score}/${submission.totalQuestions} (${submission.percentage}%)",
                  color = Color(0xFF334155),
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Medium
                )
                Text(
                  text = "Trạng thái: Đã duyệt",
                  color = SuccessGreen,
                  fontSize = 11.5.sp,
                  fontWeight = FontWeight.Bold
                )
              }

              HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFF1F5F9))

              Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.MilitaryTech, contentDescription = null, tint = CrimsonRed, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                  Text(text = "Nhận xét của Chính trị viên / Chỉ huy:", color = NavyPrimary, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                  Spacer(modifier = Modifier.height(2.dp))
                  Text(text = submission.commanderComment, color = Color(0xFF475569), fontSize = 11.5.sp, lineHeight = 16.sp)
                }
              }
            }
          }
        }
      }
    }
  }

  // 4. Edit Profile Dialog
  if (showEditProfileDialog) {
    EditProfileDialog(
      userProfile = userProfile,
      onSave = { fullName, rank, role, unit, phone, militaryId ->
        onUpdateProfile?.invoke(fullName, rank, role, unit, phone, militaryId)
      },
      onDismiss = { showEditProfileDialog = false }
    )
  }

  // 5. Change Password Dialog (With Solid Pitch Black Text!)
  if (showChangePasswordDialog) {
    Dialog(onDismissRequest = { showChangePasswordDialog = false }) {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column {
          Surface(color = NavyDeep, modifier = Modifier.fillMaxWidth()) {
            Row(
              modifier = Modifier.padding(16.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "ĐỔI MẬT KHẨU TÀI KHOẢN",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 14.5.sp
              )
              IconButton(onClick = { showChangePasswordDialog = false }, modifier = Modifier.size(28.dp)) {
                Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White)
              }
            }
          }

          Column(
            modifier = Modifier
              .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Text(
              text = "Tài khoản: ${userProfile.username.ifEmpty { "phamtatthang_162" }} (${userProfile.name})",
              color = NavyPrimary,
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            )

            // Current Password
            OutlinedTextField(
              value = oldPasswordInput,
              onValueChange = {
                oldPasswordInput = it
                changePasswordError = null
              },
              label = { Text("Mật khẩu hiện tại (hoặc 12345@abc)", color = NavyPrimary, fontWeight = FontWeight.Bold) },
              singleLine = true,
              textStyle = TextStyle(
                color = Color(0xFF000000),
                fontSize = 14.5.sp,
                fontWeight = FontWeight.SemiBold
              ),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFF000000),
                unfocusedTextColor = Color(0xFF000000),
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFF8FAFC),
                focusedBorderColor = NavyPrimary,
                unfocusedBorderColor = Color(0xFF94A3B8),
                cursorColor = Color(0xFF000000)
              ),
              visualTransformation = if (isOldPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
              trailingIcon = {
                IconButton(onClick = { isOldPassVisible = !isOldPassVisible }) {
                  Icon(
                    imageVector = if (isOldPassVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = null,
                    tint = Color(0xFF0F172A)
                  )
                }
              },
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth().testTag("input_old_password")
            )

            // New Password
            OutlinedTextField(
              value = newPasswordInput,
              onValueChange = {
                newPasswordInput = it
                changePasswordError = null
              },
              label = { Text("Mật khẩu mới", color = NavyPrimary, fontWeight = FontWeight.Bold) },
              singleLine = true,
              textStyle = TextStyle(
                color = Color(0xFF000000),
                fontSize = 14.5.sp,
                fontWeight = FontWeight.SemiBold
              ),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFF000000),
                unfocusedTextColor = Color(0xFF000000),
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFF8FAFC),
                focusedBorderColor = NavyPrimary,
                unfocusedBorderColor = Color(0xFF94A3B8),
                cursorColor = Color(0xFF000000)
              ),
              visualTransformation = if (isNewPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
              trailingIcon = {
                IconButton(onClick = { isNewPassVisible = !isNewPassVisible }) {
                  Icon(
                    imageVector = if (isNewPassVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = null,
                    tint = Color(0xFF0F172A)
                  )
                }
              },
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth().testTag("input_new_password")
            )

            // Confirm New Password
            OutlinedTextField(
              value = confirmPasswordInput,
              onValueChange = {
                confirmPasswordInput = it
                changePasswordError = null
              },
              label = { Text("Xác nhận mật khẩu mới", color = NavyPrimary, fontWeight = FontWeight.Bold) },
              singleLine = true,
              textStyle = TextStyle(
                color = Color(0xFF000000),
                fontSize = 14.5.sp,
                fontWeight = FontWeight.SemiBold
              ),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFF000000),
                unfocusedTextColor = Color(0xFF000000),
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFF8FAFC),
                focusedBorderColor = NavyPrimary,
                unfocusedBorderColor = Color(0xFF94A3B8),
                cursorColor = Color(0xFF000000)
              ),
              visualTransformation = if (isNewPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth().testTag("input_confirm_password")
            )

            if (changePasswordError != null) {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFFEF2F2),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFECACA)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Text(
                  text = changePasswordError!!,
                  color = CrimsonRed,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(8.dp)
                )
              }
            }

            if (changePasswordSuccess) {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFF0FDF4),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBBF7D0)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Text(
                  text = "Đã cập nhật mật khẩu mới thành công và đồng bộ về Web Quản trị!",
                  color = SuccessGreen,
                  fontSize = 11.5.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(8.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Button(
                onClick = { showChangePasswordDialog = false },
                colors = ButtonDefaults.outlinedButtonColors(),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f)
              ) {
                Text("Hủy", color = Color(0xFF64748B), fontSize = 12.sp)
              }

              Button(
                onClick = {
                  if (oldPasswordInput.isBlank()) {
                    changePasswordError = "Vui lòng nhập Mật khẩu hiện tại"
                    return@Button
                  }
                  if (newPasswordInput.length < 6) {
                    changePasswordError = "Mật khẩu mới phải có ít nhất 6 ký tự"
                    return@Button
                  }
                  if (newPasswordInput != confirmPasswordInput) {
                    changePasswordError = "Xác nhận mật khẩu mới không khớp"
                    return@Button
                  }
                  val success = onChangePassword?.invoke(oldPasswordInput, newPasswordInput) ?: true
                  if (success) {
                    changePasswordSuccess = true
                    changePasswordError = null
                    showChangePasswordDialog = false
                  } else {
                    changePasswordError = "Mật khẩu hiện tại không đúng!"
                  }
                },
                colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1.5f).testTag("btn_save_password")
              ) {
                Text("LƯU MẬT KHẨU", fontWeight = FontWeight.Bold, fontSize = 12.sp)
              }
            }
          }
        }
      }
    }

    if (showServerConfigDialog) {
      Dialog(onDismissRequest = { showServerConfigDialog = false }) {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Settings, contentDescription = null, tint = NavyPrimary)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Cấu hình Máy chủ Web Quản trị",
                color = NavyDeep,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Text(
              text = "Ứng dụng tự động kết nối qua mạng Internet đến Cổng Web Cloud. Nếu bạn có địa chỉ máy chủ nội bộ hoặc Cloud riêng, bạn có thể nhập vào đây:",
              fontSize = 12.sp,
              color = Color(0xFF475569)
            )

            OutlinedTextField(
              value = serverUrlInput,
              onValueChange = { serverUrlInput = it },
              label = { Text("Địa chỉ Máy chủ (URL)", fontSize = 12.sp) },
              placeholder = { Text("https://gdctv4.onrender.com", fontSize = 10.sp) },
              singleLine = true,
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth()
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Button(
                onClick = { showServerConfigDialog = false },
                colors = ButtonDefaults.outlinedButtonColors(),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f)
              ) {
                Text("Đóng", color = Color(0xFF64748B), fontSize = 12.sp)
              }

              Button(
                onClick = {
                  onSetCustomServerUrl?.invoke(serverUrlInput)
                  showServerConfigDialog = false
                },
                colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1.3f)
              ) {
                Text("LƯU & KẾT NỐI", fontWeight = FontWeight.Bold, fontSize = 12.sp)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun MetricBox(
  title: String,
  value: String,
  subtitle: String,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(10.dp),
    color = Color.White.copy(alpha = 0.12f),
    modifier = modifier
  ) {
    Column(
      modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(text = title, color = Color.White.copy(alpha = 0.75f), fontSize = 9.sp, maxLines = 1)
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = value, color = GoldYellow, fontSize = 13.sp, fontWeight = FontWeight.Black)
      Text(text = subtitle, color = Color.White.copy(alpha = 0.6f), fontSize = 8.5.sp, maxLines = 1)
    }
  }
}
