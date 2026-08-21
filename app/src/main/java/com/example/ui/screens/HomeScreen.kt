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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Slideshow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.QuizSubmissionEntity
import com.example.data.local.StudyProgressEntity
import com.example.data.model.Lesson
import com.example.ui.components.DongSonMotifCanvas
import com.example.ui.theme.BorderLight
import com.example.ui.theme.CanvasLight
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.CrimsonRedContainer
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.NavyContainer
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.NavyLight
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.AppTab

@Composable
fun HomeScreen(
  lessons: List<Lesson>,
  progressMap: Map<String, StudyProgressEntity>,
  quizSubmissions: List<QuizSubmissionEntity>,
  onNavigateTab: (AppTab) -> Unit,
  onOpenLesson: (Lesson) -> Unit,
  onStartQuiz: (Lesson) -> Unit,
  onOpenQuoteDialog: () -> Unit,
  onOpenPartyNotebook: () -> Unit,
  onOpenCommanderReport: () -> Unit,
  modifier: Modifier = Modifier
) {
  val totalLessons = lessons.size
  val completedLessonsCount = progressMap.values.count { it.isCompleted || it.progressPercent >= 100 }
  val overallProgressPercent = if (totalLessons > 0) (completedLessonsCount * 100) / totalLessons else 0

  val avgScore = if (quizSubmissions.isNotEmpty()) {
    val totalPct = quizSubmissions.sumOf { it.percentage }
    totalPct / quizSubmissions.size
  } else 0

  val featuredLesson = lessons.firstOrNull { it.id == "cd-04" || it.id == "bai_1" } ?: lessons.firstOrNull()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(CanvasLight),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Learning Progress Card (Geometric Balance style)
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "TIẾN ĐỘ HỌC TẬP CHÍNH TRỊ",
              fontSize = 12.5.sp,
              fontWeight = FontWeight.Black,
              color = NavyPrimary,
              letterSpacing = 0.5.sp
            )

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = CrimsonRed.copy(alpha = 0.1f)
            ) {
              Text(
                text = "$overallProgressPercent% Hoàn thành",
                color = CrimsonRed,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
          }

          // Progress Bar
          LinearProgressIndicator(
            progress = { overallProgressPercent / 100f },
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(RoundedCornerShape(4.dp)),
            color = NavyPrimary,
            trackColor = Color(0xFFE2E8F0)
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Đã học: $completedLessonsCount/$totalLessons bài giảng",
              color = Color(0xFF64748B),
              fontSize = 11.5.sp,
              fontWeight = FontWeight.Medium
            )
            Text(
              text = "Điểm TB: ${if (avgScore > 0) "${avgScore}đ" else "--"}",
              color = Color(0xFF64748B),
              fontSize = 11.5.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }
      }
    }

    // 2. Featured Hero Lesson
    if (featuredLesson != null) {
      item {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = "BÀI GIẢNG TRỌNG TÂM",
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF64748B),
            letterSpacing = 0.6.sp
          )

          Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight),
            elevation = CardDefaults.cardElevation(3.dp),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onOpenLesson(featuredLesson) }
              .testTag("featured_lesson_card")
          ) {
            Column {
              // Top Banner Area
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .height(130.dp)
                  .background(Color(0xFF0F172A))
              ) {
                Image(
                  painter = painterResource(id = R.drawable.banner_vung4_navy),
                  contentDescription = null,
                  contentScale = ContentScale.Crop,
                  modifier = Modifier.fillMaxSize()
                )

                // Dark Navy Gradient
                Box(
                  modifier = Modifier
                    .fillMaxSize()
                    .background(
                      Brush.verticalGradient(
                        colors = listOf(
                          Color.Transparent,
                          NavyDeep.copy(alpha = 0.9f)
                        )
                      )
                    )
                )

                // Top Video & Audio Badge
                Surface(
                  shape = RoundedCornerShape(4.dp),
                  color = CrimsonRed,
                  modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                ) {
                  Text(
                    text = "SLIDE • DOCX • VIDEO • AUDIO",
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }

                // Center Play Button
                Box(
                  modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.25f))
                    .border(1.dp, Color.White.copy(alpha = 0.4f), CircleShape)
                    .align(Alignment.Center),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Phát bài giảng",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                  )
                }

                // Bottom Title
                Column(
                  modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                  Text(
                    text = featuredLesson.code,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.SemiBold
                  )
                  Text(
                    text = featuredLesson.title,
                    color = Color.White,
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                }
              }

              // Card Bottom Action Bar
              Surface(
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column {
                    Text(
                      text = "Đối tượng: ${featuredLesson.targetAudience}",
                      color = Color(0xFF64748B),
                      fontSize = 10.5.sp
                    )
                    val p = progressMap[featuredLesson.id]
                    val isDone = p?.isCompleted == true || (p?.progressPercent ?: 0) >= 100
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      modifier = Modifier.padding(top = 2.dp)
                    ) {
                      Box(
                        modifier = Modifier
                          .size(6.dp)
                          .clip(CircleShape)
                          .background(if (isDone) SuccessGreen else NavyPrimary)
                      )
                      Spacer(modifier = Modifier.width(4.dp))
                      Text(
                        text = if (isDone) "Đã hoàn thành" else "Đang học (${p?.progressPercent ?: 0}%)",
                        color = if (isDone) SuccessGreen else NavyPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                      )
                    }
                  }

                  Button(
                    onClick = { onStartQuiz(featuredLesson) },
                    colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("btn_featured_quiz")
                  ) {
                    Text(
                      text = "THI THỬ",
                      color = Color.White,
                      fontSize = 10.5.sp,
                      fontWeight = FontWeight.Black
                    )
                  }
                }
              }
            }
          }
        }
      }
    }

    // 4. Quick Utilities Grid (2x2 Geometric Balanced Tiles)
    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
          text = "TIỆN ÍCH & TÀI LIỆU SỐ",
          fontSize = 11.5.sp,
          fontWeight = FontWeight.Black,
          color = Color(0xFF64748B),
          letterSpacing = 0.6.sp
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          GeometricGridCard(
            title = "Ngân hàng đề",
            subtitle = "Trắc nghiệm GDCT",
            icon = Icons.Default.Quiz,
            iconBgColor = Color(0xFFE8F0F8),
            iconTint = NavyPrimary,
            testTag = "quick_btn_quiz",
            modifier = Modifier.weight(1f),
            onClick = {
              val first = lessons.firstOrNull()
              if (first != null) onStartQuiz(first)
            }
          )

          GeometricGridCard(
            title = "Tài liệu số",
            subtitle = "Luật, Sách điện tử",
            icon = Icons.Default.LibraryBooks,
            iconBgColor = Color(0xFFFDE8E7),
            iconTint = CrimsonRed,
            testTag = "quick_btn_study",
            modifier = Modifier.weight(1f),
            onClick = { onNavigateTab(AppTab.UTILITIES) }
          )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          GeometricGridCard(
            title = "Sổ tay Đảng viên",
            subtitle = "Ghi chú & Thu hoạch",
            icon = Icons.Default.EditNote,
            iconBgColor = Color(0xFFFEF3C7),
            iconTint = Color(0xFFD97706),
            testTag = "quick_btn_notebook",
            modifier = Modifier.weight(1f),
            onClick = onOpenPartyNotebook
          )

          GeometricGridCard(
            title = "Báo cáo kết quả",
            subtitle = "Tiến độ & Điểm thi",
            icon = Icons.Default.MilitaryTech,
            iconBgColor = Color(0xFFDCFCE7),
            iconTint = SuccessGreen,
            testTag = "quick_btn_report",
            modifier = Modifier.weight(1f),
            onClick = onOpenCommanderReport
          )
        }
      }
    }

    // 5. Daily Quote Card ("Lời Bác dạy")
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight),
        elevation = CardDefaults.cardElevation(1.5.dp),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onOpenQuoteDialog() }
          .testTag("card_daily_quote")
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(28.dp)
                  .clip(CircleShape)
                  .background(CrimsonRed.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.FormatQuote,
                  contentDescription = null,
                  tint = CrimsonRed,
                  modifier = Modifier.size(16.dp)
                )
              }
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "LỜI BÁC DẠY CHIẾN SĨ HẢI QUÂN",
                color = CrimsonRed,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Black
              )
            }

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = CrimsonRed.copy(alpha = 0.1f)
            ) {
              Text(
                text = "Xem chi tiết",
                color = CrimsonRed,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "\"Bờ biển ta dài, tươi đẹp, ta phải biết giữ gìn lấy nó.\"",
            color = Color(0xFF1E293B),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
          )

          Spacer(modifier = Modifier.height(3.dp))

          Text(
            text = "Lời căn dặn lịch sử nhắc nhở mỗi cán bộ, chiến sĩ Vùng 4 luôn sẵn sàng chiến đấu, bảo vệ biển đảo quê hương.",
            color = Color(0xFF64748B),
            fontSize = 11.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
          )
        }
      }
    }

    // 6. All Political Education Lessons Row
    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "CHUYÊN ĐỀ GDCT PHÂN THEO MỤC",
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF64748B),
            letterSpacing = 0.6.sp
          )
          Text(
            text = "Xem tất cả",
            color = NavyPrimary,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
              .clickable { onNavigateTab(AppTab.STUDY) }
              .testTag("btn_see_all_lessons")
          )
        }

        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          items(lessons) { lesson ->
            val p = progressMap[lesson.id]
            val pct = p?.progressPercent ?: 0

            Card(
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(containerColor = Color.White),
              border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight),
              elevation = CardDefaults.cardElevation(2.dp),
              modifier = Modifier
                .width(260.dp)
                .clickable { onOpenLesson(lesson) }
                .testTag("card_lesson_home_${lesson.id}")
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = NavyContainer
                  ) {
                    Text(
                      text = lesson.code,
                      color = NavyPrimary,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                  }

                  if (pct >= 100) {
                    Icon(
                      imageVector = Icons.Default.CheckCircle,
                      contentDescription = "Đã hoàn thành",
                      tint = SuccessGreen,
                      modifier = Modifier.size(18.dp)
                    )
                  }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                  text = lesson.title,
                  color = NavyDeep,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  maxLines = 2,
                  overflow = TextOverflow.Ellipsis,
                  lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Multichannel tags
                Row(
                  horizontalArrangement = Arrangement.spacedBy(4.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = "${lesson.slides.size} slides",
                    color = NavyPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                  )
                  Text(text = "•", color = Color(0xFFCBD5E1), fontSize = 10.sp)
                  Text(
                    text = "${lesson.docAttachments.size} docs",
                    color = Color(0xFF0284C7),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                  )
                  Text(text = "•", color = Color(0xFFCBD5E1), fontSize = 10.sp)
                  Text(
                    text = "Audio ${lesson.audioDuration}",
                    color = Color(0xFFD97706),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                  )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                  progress = { pct / 100f },
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .clip(RoundedCornerShape(3.dp)),
                  color = if (pct >= 100) SuccessGreen else NavyPrimary,
                  trackColor = Color(0xFFE2E8F0)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = "Tiến độ: $pct%",
                    fontSize = 11.sp,
                    color = if (pct >= 100) SuccessGreen else NavyPrimary,
                    fontWeight = FontWeight.Bold
                  )

                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                      text = "Vào học",
                      fontSize = 11.5.sp,
                      color = CrimsonRed,
                      fontWeight = FontWeight.Bold
                    )
                    Icon(
                      imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                      contentDescription = null,
                      tint = CrimsonRed,
                      modifier = Modifier.size(12.dp)
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
}

@Composable
private fun GeometricGridCard(
  title: String,
  subtitle: String,
  icon: ImageVector,
  iconBgColor: Color,
  iconTint: Color,
  testTag: String,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight),
    elevation = CardDefaults.cardElevation(1.5.dp),
    modifier = modifier
      .clickable { onClick() }
      .testTag(testTag)
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(iconBgColor),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = iconTint,
          modifier = Modifier.size(22.dp)
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      Column {
        Text(
          text = title,
          color = Color(0xFF0F172A),
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          maxLines = 1
        )
        Text(
          text = subtitle,
          color = Color(0xFF64748B),
          fontSize = 10.sp,
          maxLines = 1
        )
      }
    }
  }
}
