package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Slideshow
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.data.local.QuizSubmissionEntity
import com.example.data.local.StudyProgressEntity
import com.example.data.model.Lesson
import com.example.data.model.StudyMode
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.NavyContainer
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun StudyScreen(
  lessons: List<Lesson>,
  selectedCategory: String,
  searchQuery: String,
  progressMap: Map<String, StudyProgressEntity>,
  quizSubmissions: List<QuizSubmissionEntity>,
  onCategorySelected: (String) -> Unit,
  onSearchChange: (String) -> Unit,
  onOpenLesson: (Lesson, StudyMode) -> Unit,
  onStartQuiz: (Lesson) -> Unit,
  modifier: Modifier = Modifier
) {
  val categories = listOf(
    "Tất cả",
    "Chuyên đề Sĩ quan & QNCN",
    "Lịch sử & Truyền thống",
    "Pháp luật & Kỷ luật",
    "Tư tưởng Hồ Chí Minh"
  )

  val filteredLessons = lessons.filter { lesson ->
    val matchesCategory = selectedCategory == "Tất cả" || lesson.category == selectedCategory
    val matchesSearch = searchQuery.isBlank() ||
      lesson.title.contains(searchQuery, ignoreCase = true) ||
      lesson.code.contains(searchQuery, ignoreCase = true) ||
      lesson.summary.contains(searchQuery, ignoreCase = true)
    matchesCategory && matchesSearch
  }

  val totalCount = lessons.size
  val completedCount = progressMap.values.count { it.isCompleted || it.progressPercent >= 100 }
  val overallPct = if (totalCount > 0) (completedCount * 100) / totalCount else 0

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF4F7FA))
  ) {
    // Header & Filter Section
    Surface(
      color = Color.White,
      shadowElevation = 2.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "CHƯƠNG TRÌNH GIÁO DỤC CHÍNH TRỊ",
            color = NavyDeep,
            fontSize = 14.5.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 0.4.sp
          )

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (overallPct >= 100) Color(0xFFDCFCE7) else NavyContainer
          ) {
            Text(
              text = "Đã học: $completedCount/$totalCount bài ($overallPct%)",
              color = if (overallPct >= 100) SuccessGreen else NavyPrimary,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Search Bar
        OutlinedTextField(
          value = searchQuery,
          onValueChange = onSearchChange,
          placeholder = { Text("Tìm kiếm bài giảng, chuyên đề, nghị quyết...", fontSize = 12.5.sp) },
          leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(20.dp))
          },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = NavyPrimary,
            unfocusedBorderColor = Color(0xFFE2E8F0),
            focusedContainerColor = Color(0xFFF8FAFC),
            unfocusedContainerColor = Color(0xFFF8FAFC)
          ),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("input_search_lessons")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Categories filter row
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(categories) { cat ->
            val isSelected = cat == selectedCategory
            FilterChip(
              selected = isSelected,
              onClick = { onCategorySelected(cat) },
              label = {
                Text(
                  text = cat,
                  fontSize = 11.5.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
              },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = NavyPrimary,
                selectedLabelColor = Color.White,
                containerColor = Color(0xFFF1F5F9),
                labelColor = Color(0xFF475569)
              ),
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier.testTag("filter_lesson_$cat")
            )
          }
        }
      }
    }

    // Lessons List
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(14.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      items(filteredLessons, key = { it.id }) { lesson ->
        val progress = progressMap[lesson.id]
        val progressPercent = progress?.progressPercent ?: 0
        val isCompleted = progress?.isCompleted == true || progressPercent >= 100

        val latestQuiz = quizSubmissions.firstOrNull { it.lessonId == lesson.id }

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenLesson(lesson, StudyMode.DOCUMENT) }
            .testTag("card_study_lesson_${lesson.id}")
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            // Header Row (Code & Category & Internal Badge)
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = if (lesson.isInternal) Color(0xFFFEF2F2) else NavyContainer
                ) {
                  Text(
                    text = lesson.code,
                    color = if (lesson.isInternal) CrimsonRed else NavyPrimary,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }

                Spacer(modifier = Modifier.width(6.dp))

                if (lesson.isInternal) {
                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = CrimsonRed
                  ) {
                    Text(
                      text = "NỘI BỘ",
                      color = Color.White,
                      fontSize = 9.5.sp,
                      fontWeight = FontWeight.Black,
                      modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                  }
                  Spacer(modifier = Modifier.width(6.dp))
                }

                Text(
                  text = lesson.category,
                  color = Color(0xFF64748B),
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium
                )
              }

              // Status Tag
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = when {
                  isCompleted -> Color(0xFFDCFCE7)
                  progressPercent > 0 -> Color(0xFFEFF6FF)
                  else -> Color(0xFFF1F5F9)
                }
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = if (isCompleted) SuccessGreen else if (progressPercent > 0) NavyPrimary else Color(0xFF94A3B8),
                    modifier = Modifier.size(12.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = when {
                      isCompleted -> "Đã hoàn thành"
                      progressPercent > 0 -> "Đang học ($progressPercent%)"
                      else -> "Chưa học"
                    },
                    color = if (isCompleted) SuccessGreen else if (progressPercent > 0) NavyPrimary else Color(0xFF64748B),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Lesson Title
            Text(
              text = lesson.title,
              color = NavyDeep,
              fontSize = 15.sp,
              fontWeight = FontWeight.Black,
              lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Summary
            Text(
              text = lesson.summary,
              color = Color(0xFF475569),
              fontSize = 12.5.sp,
              maxLines = 2,
              overflow = TextOverflow.Ellipsis,
              lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Progress Bar
            Column {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "Tiến độ học tập:",
                  color = Color(0xFF64748B),
                  fontSize = 11.sp
                )
                Text(
                  text = "$progressPercent%",
                  color = if (isCompleted) SuccessGreen else NavyPrimary,
                  fontSize = 11.5.sp,
                  fontWeight = FontWeight.Bold
                )
              }

              Spacer(modifier = Modifier.height(4.dp))

              LinearProgressIndicator(
                progress = { progressPercent / 100f },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(6.dp)
                  .clip(RoundedCornerShape(3.dp)),
                color = if (isCompleted) SuccessGreen else NavyPrimary,
                trackColor = Color(0xFFE2E8F0)
              )
            }

            // Quiz score banner if submitted
            if (latestQuiz != null) {
              Spacer(modifier = Modifier.height(10.dp))
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (latestQuiz.passed) Color(0xFFF0FDF4) else Color(0xFFFEF2F2),
                border = androidx.compose.foundation.BorderStroke(1.dp, if (latestQuiz.passed) Color(0xFF86EFAC) else Color(0xFFFECACA)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  modifier = Modifier.padding(8.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.MilitaryTech,
                      contentDescription = null,
                      tint = if (latestQuiz.passed) SuccessGreen else CrimsonRed,
                      modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "Điểm kiểm tra: ${latestQuiz.score}/${latestQuiz.totalQuestions} (${latestQuiz.percentage}%)",
                      color = if (latestQuiz.passed) Color(0xFF166534) else Color(0xFF991B1B),
                      fontSize = 11.5.sp,
                      fontWeight = FontWeight.Bold
                    )
                  }
                  Text(
                    text = if (latestQuiz.passed) "Đạt yêu cầu" else "Cần ôn lại",
                    color = if (latestQuiz.passed) SuccessGreen else CrimsonRed,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons Row (Slide / Document / Video / Audio / Quiz)
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
              OutlinedButton(
                onClick = { onOpenLesson(lesson, StudyMode.SLIDE) },
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 4.dp),
                modifier = Modifier.weight(1f).testTag("btn_slide_${lesson.id}")
              ) {
                Icon(Icons.Default.Slideshow, contentDescription = null, modifier = Modifier.size(13.dp), tint = NavyPrimary)
                Spacer(modifier = Modifier.width(3.dp))
                Text("Slide", fontSize = 10.5.sp, color = NavyPrimary, fontWeight = FontWeight.SemiBold)
              }

              OutlinedButton(
                onClick = { onOpenLesson(lesson, StudyMode.DOCUMENT) },
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 4.dp),
                modifier = Modifier.weight(1f).testTag("btn_doc_${lesson.id}")
              ) {
                Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(13.dp), tint = NavyPrimary)
                Spacer(modifier = Modifier.width(3.dp))
                Text("Đọc", fontSize = 10.5.sp, color = NavyPrimary)
              }

              OutlinedButton(
                onClick = { onOpenLesson(lesson, StudyMode.VIDEO) },
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 4.dp),
                modifier = Modifier.weight(1f).testTag("btn_video_${lesson.id}")
              ) {
                Icon(Icons.Default.Videocam, contentDescription = null, modifier = Modifier.size(13.dp), tint = NavyPrimary)
                Spacer(modifier = Modifier.width(3.dp))
                Text("Video", fontSize = 10.5.sp, color = NavyPrimary)
              }

              OutlinedButton(
                onClick = { onOpenLesson(lesson, StudyMode.AUDIO) },
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 4.dp),
                modifier = Modifier.weight(1f).testTag("btn_audio_${lesson.id}")
              ) {
                Icon(Icons.Default.Headphones, contentDescription = null, modifier = Modifier.size(13.dp), tint = NavyPrimary)
                Spacer(modifier = Modifier.width(3.dp))
                Text("Audio", fontSize = 10.5.sp, color = NavyPrimary)
              }

              Button(
                onClick = { onStartQuiz(lesson) },
                colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 4.dp),
                modifier = Modifier.weight(1.15f).testTag("btn_quiz_start_${lesson.id}")
              ) {
                Icon(Icons.Default.Assignment, contentDescription = null, modifier = Modifier.size(13.dp))
                Spacer(modifier = Modifier.width(2.dp))
                Text("Thi", fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }
  }
}
