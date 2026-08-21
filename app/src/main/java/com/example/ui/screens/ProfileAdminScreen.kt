package com.example.ui.screens

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.QuizSubmissionEntity
import com.example.data.local.StudyProgressEntity
import com.example.data.model.Lesson
import com.example.data.model.UserProfile
import com.example.ui.components.DongSonMotifCanvas
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
  onLogout: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedSubTab by remember { mutableIntStateOf(0) } // 0: Hồ sơ học tập, 1: Báo cáo Quản trị & Chỉ huy

  val totalLessons = lessons.size
  val completedLessons = progressMap.values.count { it.isCompleted || it.progressPercent >= 100 }
  val inProgressLessons = progressMap.values.count { !it.isCompleted && it.progressPercent > 0 }
  val avgScore = if (quizSubmissions.isNotEmpty()) {
    quizSubmissions.sumOf { it.percentage } / quizSubmissions.size
  } else 0

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF4F7FA)),
    contentPadding = PaddingValues(bottom = 24.dp),
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
                  text = if (userProfile.isLoggedIn) "Số hiệu QN: ${userProfile.militaryId} • ${userProfile.partyStatus}" else "Đăng nhập để mở khóa bài học nội bộ",
                  color = Color.White.copy(alpha = 0.7f),
                  fontSize = 10.5.sp
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Auth Button Row
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              if (userProfile.isLoggedIn) {
                Button(
                  onClick = onOpenLoginDialog,
                  colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f)),
                  shape = RoundedCornerShape(8.dp),
                  contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                  modifier = Modifier.weight(1f)
                ) {
                  Text("Đổi tài khoản", color = Color.White, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                  onClick = onLogout,
                  colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed.copy(alpha = 0.8f)),
                  shape = RoundedCornerShape(8.dp),
                  contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                  modifier = Modifier.weight(1f)
                ) {
                  Text("Đăng xuất", color = Color.White, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
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
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4 Mini Metric Cards
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              MetricBox(title = "Đã hoàn thành", value = "$completedLessons/$totalLessons", subtitle = "Bài giảng", modifier = Modifier.weight(1f))
              MetricBox(title = "Điểm TB thi", value = if (avgScore > 0) "$avgScore%" else "--", subtitle = "Trắc nghiệm", modifier = Modifier.weight(1f))
              MetricBox(title = "Đánh giá", value = if (avgScore >= 80) "GIỎI" else "KHÁ", subtitle = "Chính trị viên", modifier = Modifier.weight(1f))
              MetricBox(title = "Chuỗi học", value = "12", subtitle = "Ngày liên tục", modifier = Modifier.weight(1f))
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
              Text("Báo cáo Quản trị & Chỉ huy", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
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
      // Admin & Commander Report View
      item {
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE)),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(22.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text("HỆ THỐNG QUẢN TRỊ & ĐỒNG BỘ DỮ LIỆU", color = NavyPrimary, fontSize = 13.5.sp, fontWeight = FontWeight.Black)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Mọi kết quả học tập và bài thi trắc nghiệm của quân nhân được tự động ký số, mã hóa và gửi về cơ sở dữ liệu của Phòng Chính trị Vùng 4 và Chỉ huy Lữ đoàn để theo dõi.",
              color = Color(0xFF1E3A8A),
              fontSize = 11.5.sp,
              lineHeight = 16.sp
            )
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
      Text(text = value, color = GoldYellow, fontSize = 13.5.sp, fontWeight = FontWeight.Black)
      Text(text = subtitle, color = Color.White.copy(alpha = 0.6f), fontSize = 8.5.sp, maxLines = 1)
    }
  }
}
