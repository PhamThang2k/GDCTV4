package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.Slideshow
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.StudyProgressEntity
import com.example.data.model.Lesson
import com.example.data.model.StudyMode
import com.example.ui.theme.BorderLight
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.NavyContainer
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.NavyLight
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun LessonDetailView(
  lesson: Lesson,
  studyMode: StudyMode,
  currentSlideIndex: Int,
  checkedSections: Set<Int>,
  progress: StudyProgressEntity?,
  isVideoPlaying: Boolean,
  videoSeconds: Int,
  videoSpeed: Float,
  onBack: () -> Unit,
  onModeChange: (StudyMode) -> Unit,
  onNextSlide: () -> Unit,
  onPrevSlide: () -> Unit,
  onSelectSlide: (Int) -> Unit,
  onToggleSectionChecked: (Int) -> Unit,
  onToggleVideoPlay: () -> Unit,
  onVideoSeek: (Int) -> Unit,
  onVideoSpeedChange: (Float) -> Unit,
  onStartQuiz: () -> Unit,
  onAddNote: () -> Unit
) {
  val currentProgressPercent = progress?.progressPercent ?: 0

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF4F7FA))
  ) {
    // Top Bar
    Surface(
      color = NavyDeep,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .statusBarsPadding()
          .padding(horizontal = 12.dp, vertical = 8.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
          ) {
            IconButton(
              onClick = onBack,
              modifier = Modifier.testTag("btn_back_lesson")
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Quay lại",
                tint = Color.White
              )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column {
              Text(
                text = lesson.code,
                color = GoldYellow,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = lesson.title,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
            }
          }

          // Quick Quiz CTA
          Button(
            onClick = onStartQuiz,
            colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.testTag("btn_start_quiz_top")
          ) {
            Icon(
              imageVector = Icons.Default.Assignment,
              contentDescription = null,
              modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Làm kiểm tra", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Progress Bar & Percentage
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Tiến độ học bài này: $currentProgressPercent%",
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
          )

          if (currentProgressPercent >= 100) {
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = SuccessGreen
            ) {
              Text(
                text = "Đã hoàn thành",
                color = Color.White,
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        LinearProgressIndicator(
          progress = { currentProgressPercent / 100f },
          modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
            .clip(RoundedCornerShape(3.dp)),
          color = GoldYellow,
          trackColor = Color.White.copy(alpha = 0.25f),
        )
      }
    }

    // Mode Switcher Tab Row
    TabRow(
      selectedTabIndex = studyMode.ordinal,
      containerColor = Color.White,
      contentColor = NavyPrimary,
      indicator = { tabPositions ->
        TabRowDefaults.SecondaryIndicator(
          Modifier.tabIndicatorOffset(tabPositions[studyMode.ordinal]),
          color = NavyPrimary,
          height = 3.dp
        )
      }
    ) {
      Tab(
        selected = studyMode == StudyMode.SLIDE,
        onClick = { onModeChange(StudyMode.SLIDE) },
        modifier = Modifier.testTag("tab_mode_slide"),
        text = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Slideshow, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Slide bài giảng", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      )
      Tab(
        selected = studyMode == StudyMode.DOCUMENT,
        onClick = { onModeChange(StudyMode.DOCUMENT) },
        modifier = Modifier.testTag("tab_mode_doc"),
        text = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Đọc tài liệu", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      )
      Tab(
        selected = studyMode == StudyMode.VIDEO,
        onClick = { onModeChange(StudyMode.VIDEO) },
        modifier = Modifier.testTag("tab_mode_video"),
        text = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Videocam, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Video (${lesson.videoDuration})", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      )
    }

    // Content Display by Mode
    Box(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
    ) {
      when (studyMode) {
        StudyMode.SLIDE -> {
          SlidePresentationViewer(
            lesson = lesson,
            currentIndex = currentSlideIndex,
            onNext = onNextSlide,
            onPrev = onPrevSlide,
            onSelect = onSelectSlide,
            onStartQuiz = onStartQuiz,
            onAddNote = onAddNote
          )
        }
        StudyMode.DOCUMENT -> {
          DocumentReaderViewer(
            lesson = lesson,
            checkedSections = checkedSections,
            onToggleChecked = onToggleSectionChecked,
            onStartQuiz = onStartQuiz,
            onAddNote = onAddNote
          )
        }
        StudyMode.VIDEO -> {
          VideoLectureViewer(
            lesson = lesson,
            isPlaying = isVideoPlaying,
            seconds = videoSeconds,
            speed = videoSpeed,
            onTogglePlay = onToggleVideoPlay,
            onSeek = onVideoSeek,
            onSpeedChange = onVideoSpeedChange,
            onStartQuiz = onStartQuiz,
            onAddNote = onAddNote
          )
        }
      }
    }
  }
}

/**
 * Slide Presentation Mode
 */
@Composable
fun SlidePresentationViewer(
  lesson: Lesson,
  currentIndex: Int,
  onNext: () -> Unit,
  onPrev: () -> Unit,
  onSelect: (Int) -> Unit,
  onStartQuiz: () -> Unit,
  onAddNote: () -> Unit
) {
  val slides = lesson.slides
  val safeIndex = currentIndex.coerceIn(0, slides.size - 1)
  val currentSlide = slides[safeIndex]

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp)
  ) {
    // Slide Canvas Box
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(3.dp),
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
      ) {
        // Slide Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = NavyPrimary
          ) {
            Text(
              text = "TRÌNH CHIẾU SLIDE ${safeIndex + 1}/${slides.size}",
              color = Color.White,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }

          IconButton(
            onClick = onAddNote,
            modifier = Modifier.size(32.dp).testTag("btn_note_slide")
          ) {
            Icon(
              imageVector = Icons.Default.EditNote,
              contentDescription = "Ghi chú bài học",
              tint = NavyPrimary
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Slide Title
        Text(
          text = currentSlide.title,
          color = NavyDeep,
          fontSize = 16.5.sp,
          fontWeight = FontWeight.Black,
          lineHeight = 23.sp
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = BorderLight)

        // Bullets List
        LazyColumn(
          modifier = Modifier.weight(1f),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(currentSlide.bullets) { bullet ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.Top
            ) {
              Box(
                modifier = Modifier
                  .padding(top = 5.dp)
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(CrimsonRed)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = bullet,
                color = Color(0xFF1E293B),
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight.Normal
              )
            }
          }

          if (currentSlide.highlightQuote != null) {
            item {
              Spacer(modifier = Modifier.height(6.dp))
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFFFF8E1),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldYellow),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  modifier = Modifier.padding(12.dp),
                  verticalAlignment = Alignment.Top
                ) {
                  Icon(
                    imageVector = Icons.Default.FormatQuote,
                    contentDescription = null,
                    tint = CrimsonRed,
                    modifier = Modifier.size(20.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = currentSlide.highlightQuote,
                    color = Color(0xFF78350F),
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 19.sp
                  )
                }
              }
            }
          }

          if (currentSlide.note != null) {
            item {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFEFF6FF),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  modifier = Modifier.padding(10.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = NavyLight,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Gợi ý liên hệ: ${currentSlide.note}",
                    color = Color(0xFF1E40AF),
                    fontSize = 11.5.sp,
                    lineHeight = 16.sp
                  )
                }
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Slide Controls Row
    Surface(
      shape = RoundedCornerShape(14.dp),
      color = Color.White,
      shadowElevation = 2.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedButton(
          onClick = onPrev,
          enabled = safeIndex > 0,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.testTag("btn_slide_prev")
        ) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Slide trước", fontSize = 12.sp)
        }

        // Slide dots
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          slides.forEachIndexed { i, _ ->
            Box(
              modifier = Modifier
                .size(if (i == safeIndex) 12.dp else 8.dp)
                .clip(CircleShape)
                .background(if (i == safeIndex) CrimsonRed else Color(0xFFCBD5E1))
                .clickable { onSelect(i) }
            )
          }
        }

        if (safeIndex < slides.size - 1) {
          Button(
            onClick = onNext,
            colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.testTag("btn_slide_next")
          ) {
            Text("Slide kế", fontSize = 12.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
          }
        } else {
          Button(
            onClick = onStartQuiz,
            colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.testTag("btn_slide_finish_quiz")
          ) {
            Icon(Icons.Default.Assignment, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Kiểm tra", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

/**
 * Rich Document Reader Mode
 */
@Composable
fun DocumentReaderViewer(
  lesson: Lesson,
  checkedSections: Set<Int>,
  onToggleChecked: (Int) -> Unit,
  onStartQuiz: () -> Unit,
  onAddNote: () -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "TỔNG QUAN BÀI GIẢNG",
              color = NavyPrimary,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onAddNote, modifier = Modifier.size(28.dp)) {
              Icon(Icons.Default.EditNote, contentDescription = "Ghi chú", tint = NavyPrimary)
            }
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = lesson.summary,
            color = Color(0xFF334155),
            fontSize = 13.5.sp,
            lineHeight = 20.sp
          )
          Spacer(modifier = Modifier.height(10.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = Color(0xFFE2E8F0)
            ) {
              Text(
                text = "Đối tượng: ${lesson.targetAudience}",
                color = Color(0xFF475569),
                fontSize = 11.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = Color(0xFFE2E8F0)
            ) {
              Text(
                text = "Thời lượng: ${lesson.durationMinutes} phút",
                color = Color(0xFF475569),
                fontSize = 11.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
          }
        }
      }
    }

    item {
      Text(
        text = "Nội dung các phần (Đánh dấu đã đọc để lưu tiến độ):",
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = NavyDeep
      )
    }

    itemsIndexed(lesson.sections) { idx, section ->
      val isChecked = checkedSections.contains(section.sectionNumber)

      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (isChecked) Color(0xFFF0FDF4) else Color.White
        ),
        border = androidx.compose.foundation.BorderStroke(
          1.dp,
          if (isChecked) Color(0xFF86EFAC) else Color(0xFFE2E8F0)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = section.heading,
              color = NavyPrimary,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.weight(1f)
            )

            IconButton(
              onClick = { onToggleChecked(section.sectionNumber) },
              modifier = Modifier.testTag("btn_check_section_${section.sectionNumber}")
            ) {
              Icon(
                imageVector = if (isChecked) Icons.Default.CheckBox else Icons.Default.CheckBoxOutlineBlank,
                contentDescription = "Đã đọc",
                tint = if (isChecked) SuccessGreen else Color(0xFF94A3B8)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = section.content,
            color = Color(0xFF1E293B),
            fontSize = 14.sp,
            lineHeight = 22.sp
          )

          Spacer(modifier = Modifier.height(10.dp))

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFFEF3C7),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(10.dp),
              verticalAlignment = Alignment.Top
            ) {
              Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = null,
                tint = Color(0xFFD97706),
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Cốt lõi: ${section.keyTakeaway}",
                color = Color(0xFF92400E),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 17.sp
              )
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(8.dp))
      Button(
        onClick = onStartQuiz,
        colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("btn_doc_finish_quiz")
      ) {
        Icon(Icons.Default.Assignment, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Làm bài kiểm tra trắc nghiệm", fontSize = 14.sp, fontWeight = FontWeight.Bold)
      }
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

/**
 * Video Lecture Mode Viewer
 */
@Composable
fun VideoLectureViewer(
  lesson: Lesson,
  isPlaying: Boolean,
  seconds: Int,
  speed: Float,
  onTogglePlay: () -> Unit,
  onSeek: (Int) -> Unit,
  onSpeedChange: (Float) -> Unit,
  onStartQuiz: () -> Unit,
  onAddNote: () -> Unit
) {
  val totalSecs = 18 * 60 + 40 // 18m40s
  val currentMinute = seconds / 60
  val currentSecond = seconds % 60
  val timeDisplay = String.format("%02d:%02d / %s", currentMinute, currentSecond, lesson.videoDuration)

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Video Player Box
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NavyDeep),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column {
          // Video Canvas Visualizer
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(190.dp)
              .background(
                Brush.verticalGradient(
                  colors = listOf(
                    Color(0xFF071B36),
                    NavyPrimary
                  )
                )
              ),
            contentAlignment = Alignment.Center
          ) {
            DongSonMotifCanvas(modifier = Modifier.fillMaxSize(), tint = GoldYellow.copy(alpha = 0.3f))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Surface(
                shape = CircleShape,
                color = CrimsonRed,
                modifier = Modifier
                  .size(56.dp)
                  .clickable { onTogglePlay() }
                  .testTag("btn_video_play_center")
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Tạm dừng" else "Phát",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(8.dp))

              Text(
                text = if (isPlaying) "Đang phát video bài giảng..." else "Nhấn để xem video bài giảng",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
              )
            }

            // Live tag
            Surface(
              shape = RoundedCornerShape(4.dp),
              color = CrimsonRed,
              modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
            ) {
              Text(
                text = "HD 1080p • GDCT VÙNG 4",
                color = Color.White,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          // Player Controls
          Column(modifier = Modifier.padding(12.dp)) {
            Slider(
              value = seconds.toFloat(),
              onValueChange = { onSeek(it.toInt()) },
              valueRange = 0f..totalSecs.toFloat(),
              colors = SliderDefaults.colors(
                thumbColor = GoldYellow,
                activeTrackColor = GoldYellow,
                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
              ),
              modifier = Modifier.fillMaxWidth()
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = timeDisplay,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
              )

              // Speed selector
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                listOf(1.0f, 1.25f, 1.5f).forEach { s ->
                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (speed == s) GoldYellow else Color.White.copy(alpha = 0.15f),
                    modifier = Modifier.clickable { onSpeedChange(s) }
                  ) {
                    Text(
                      text = "${s}x",
                      color = if (speed == s) NavyDeep else Color.White,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                  }
                }
              }
            }
          }
        }
      }
    }

    // Video Chapters / Notes
    item {
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "CÁC MỐC THỜI GIAN TRỌNG TÂM",
              color = NavyPrimary,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onAddNote, modifier = Modifier.size(28.dp)) {
              Icon(Icons.Default.EditNote, contentDescription = "Ghi chú", tint = NavyPrimary)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          listOf(
            "00:00" to "Lời mở đầu & Giới thiệu Chuyên đề giáo dục chính trị",
            "03:45" to "Phân tích bối cảnh an ninh biển đảo và tình hình thế giới",
            "08:20" to "Vị trí, vai trò của bản lĩnh chính trị người chiến sĩ Hải quân",
            "14:10" to "Nhiệm vụ, giải pháp rèn luyện tại tàu và đảo Vùng 4"
          ).forEach { (time, desc) ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable {
                  val parts = time.split(":")
                  val seek = parts[0].toInt() * 60 + parts[1].toInt()
                  onSeek(seek)
                }
                .padding(vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = NavyContainer
              ) {
                Text(
                  text = time,
                  color = NavyPrimary,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = desc,
                color = Color(0xFF334155),
                fontSize = 12.5.sp,
                modifier = Modifier.weight(1f)
              )
            }
          }
        }
      }
    }

    item {
      Button(
        onClick = onStartQuiz,
        colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("btn_video_finish_quiz")
      ) {
        Icon(Icons.Default.Assignment, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Làm bài kiểm tra đánh giá", fontSize = 14.sp, fontWeight = FontWeight.Bold)
      }
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}
