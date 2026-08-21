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
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MilitaryTech
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.data.model.DocAttachment
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
  isAudioPlaying: Boolean,
  audioSeconds: Int,
  audioSpeed: Float,
  downloadedDocIds: Set<String>,
  onBack: () -> Unit,
  onModeChange: (StudyMode) -> Unit,
  onNextSlide: () -> Unit,
  onPrevSlide: () -> Unit,
  onSelectSlide: (Int) -> Unit,
  onToggleSectionChecked: (Int) -> Unit,
  onToggleVideoPlay: () -> Unit,
  onVideoSeek: (Int) -> Unit,
  onVideoSpeedChange: (Float) -> Unit,
  onToggleAudioPlay: () -> Unit,
  onAudioSeek: (Int) -> Unit,
  onAudioSpeedChange: (Float) -> Unit,
  onDownloadDoc: (DocAttachment) -> Unit,
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

    // 4-Mode Switcher Tab Row: Slide, Đọc DOCX/PDF, Video, Audio
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
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Slideshow, contentDescription = null, modifier = Modifier.size(15.dp))
            Text("Slide", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      )
      Tab(
        selected = studyMode == StudyMode.DOCUMENT,
        onClick = { onModeChange(StudyMode.DOCUMENT) },
        modifier = Modifier.testTag("tab_mode_doc"),
        text = {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(15.dp))
            Text("Tài liệu & Tải", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      )
      Tab(
        selected = studyMode == StudyMode.VIDEO,
        onClick = { onModeChange(StudyMode.VIDEO) },
        modifier = Modifier.testTag("tab_mode_video"),
        text = {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Videocam, contentDescription = null, modifier = Modifier.size(15.dp))
            Text("Video", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      )
      Tab(
        selected = studyMode == StudyMode.AUDIO,
        onClick = { onModeChange(StudyMode.AUDIO) },
        modifier = Modifier.testTag("tab_mode_audio"),
        text = {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Audiotrack, contentDescription = null, modifier = Modifier.size(15.dp))
            Text("Audio", fontSize = 11.sp, fontWeight = FontWeight.Bold)
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
            downloadedDocIds = downloadedDocIds,
            onDownloadDoc = onDownloadDoc,
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
        StudyMode.AUDIO -> {
          AudioLectureViewer(
            lesson = lesson,
            isPlaying = isAudioPlaying,
            seconds = audioSeconds,
            speed = audioSpeed,
            onTogglePlay = onToggleAudioPlay,
            onSeek = onAudioSeek,
            onSpeedChange = onAudioSpeedChange,
            onStartQuiz = onStartQuiz,
            onAddNote = onAddNote
          )
        }
      }
    }
  }
}

/**
 * Slide Presentation Viewer
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
  val safeIndex = currentIndex.coerceIn(0, (slides.size - 1).coerceAtLeast(0))
  val currentSlide = slides.getOrNull(safeIndex) ?: return

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    // Top Slide Indicator & Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = NavyContainer
      ) {
        Text(
          text = "SLIDE ${safeIndex + 1} / ${slides.size}",
          color = NavyPrimary,
          fontSize = 11.5.sp,
          fontWeight = FontWeight.Black,
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onAddNote, modifier = Modifier.size(32.dp)) {
          Icon(Icons.Default.EditNote, contentDescription = "Ghi chú", tint = NavyPrimary)
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Slide Presentation Canvas Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(3.dp),
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(18.dp),
        verticalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          // Slide Heading
          Text(
            text = currentSlide.title,
            color = NavyDeep,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 22.sp
          )

          Spacer(modifier = Modifier.height(14.dp))

          // Bullets
          currentSlide.bullets.forEachIndexed { idx, bullet ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.Top
            ) {
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .clip(CircleShape)
                  .background(CrimsonRed)
                  .padding(top = 6.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = bullet,
                color = Color(0xFF1E293B),
                fontSize = 13.sp,
                lineHeight = 19.sp
              )
            }
          }
        }

        // Highlight quote or note if available
        if (currentSlide.highlightQuote != null) {
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFFFEF3C7),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.FormatQuote,
                contentDescription = null,
                tint = Color(0xFFD97706),
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = currentSlide.highlightQuote!!,
                color = Color(0xFF92400E),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Slide Navigation Toolbar
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
 * Rich Document Reader Mode & Downloadable DOCX/PDF
 */
@Composable
fun DocumentReaderViewer(
  lesson: Lesson,
  checkedSections: Set<Int>,
  downloadedDocIds: Set<String>,
  onDownloadDoc: (DocAttachment) -> Unit,
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
    // 1. Downloadable DOCX / PDF Attachments Section
    if (lesson.docAttachments.isNotEmpty()) {
      item {
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight),
          elevation = CardDefaults.cardElevation(2.dp)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Download,
                  contentDescription = null,
                  tint = NavyPrimary,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "TÀI LIỆU BÀI GIẢNG ĐÍNH KÈM (DOCX / PDF)",
                  color = NavyDeep,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            lesson.docAttachments.forEach { doc ->
              val isDownloaded = downloadedDocIds.contains(doc.id)

              Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isDownloaded) Color(0xFFF0FDF4) else Color(0xFFF8FAFC),
                border = androidx.compose.foundation.BorderStroke(
                  1.dp,
                  if (isDownloaded) Color(0xFF86EFAC) else Color(0xFFE2E8F0)
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp)
              ) {
                Row(
                  modifier = Modifier.padding(10.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                  ) {
                    Surface(
                      shape = RoundedCornerShape(6.dp),
                      color = if (doc.fileType == "PDF") CrimsonRed.copy(alpha = 0.15f) else Color(0xFF0284C7).copy(alpha = 0.15f),
                      modifier = Modifier.size(36.dp)
                    ) {
                      Box(contentAlignment = Alignment.Center) {
                        Text(
                          text = doc.fileType,
                          color = if (doc.fileType == "PDF") CrimsonRed else Color(0xFF0284C7),
                          fontWeight = FontWeight.Black,
                          fontSize = 10.5.sp
                        )
                      }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                      Text(
                        text = doc.fileName,
                        color = NavyDeep,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                      )
                      Text(
                        text = "Dung lượng: ${doc.fileSize} • ${doc.pageCount} trang",
                        color = Color(0xFF64748B),
                        fontSize = 10.5.sp
                      )
                    }
                  }

                  Button(
                    onClick = { onDownloadDoc(doc) },
                    colors = ButtonDefaults.buttonColors(
                      containerColor = if (isDownloaded) SuccessGreen else NavyPrimary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp).testTag("btn_download_${doc.id}")
                  ) {
                    Icon(
                      imageVector = if (isDownloaded) Icons.Default.Check else Icons.Default.Download,
                      contentDescription = null,
                      modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = if (isDownloaded) "Đã tải" else "Tải về",
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold
                    )
                  }
                }
              }
            }
          }
        }
      }
    }

    // 2. Summary Card
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
  val totalSecs = 18 * 60 + 40
  val currentMin = seconds / 60
  val currentSec = seconds % 60
  val timeDisplay = String.format("%02d:%02d / %s", currentMin, currentSec, lesson.videoDuration)

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
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(200.dp)
              .background(
                Brush.verticalGradient(
                  colors = listOf(NavyDeep, Color(0xFF020617))
                )
              ),
            contentAlignment = Alignment.Center
          ) {
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

/**
 * Audio Lecture Mode Viewer (Nghe giảng chính trị)
 */
@Composable
fun AudioLectureViewer(
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
  val totalSecs = 18 * 60 + 40
  val currentMin = seconds / 60
  val currentSec = seconds % 60
  val timeDisplay = String.format("%02d:%02d / %s", currentMin, currentSec, lesson.audioDuration)

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Audio Player Card
    item {
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = NavyDeep),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Disc / Waveform Visualizer Avatar
          Surface(
            shape = CircleShape,
            color = NavyPrimary,
            border = androidx.compose.foundation.BorderStroke(3.dp, GoldYellow),
            modifier = Modifier.size(90.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.Headphones,
                contentDescription = null,
                tint = GoldYellow,
                modifier = Modifier.size(44.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = lesson.title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "Người thuyết giảng: ${lesson.audioSpeaker}",
            color = Color(0xFFCBD5E1),
            fontSize = 11.5.sp
          )

          Spacer(modifier = Modifier.height(16.dp))

          // Slider
          Slider(
            value = seconds.toFloat(),
            onValueChange = { onSeek(it.toInt()) },
            valueRange = 0f..totalSecs.toFloat(),
            colors = SliderDefaults.colors(
              thumbColor = GoldYellow,
              activeTrackColor = GoldYellow,
              inactiveTrackColor = Color.White.copy(alpha = 0.2f)
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = timeDisplay, color = Color(0xFFCBD5E1), fontSize = 11.sp)
            Text(text = "Tốc độ: ${speed}x", color = GoldYellow, fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Controls Row: -10s, Play/Pause, +10s, Speed
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
          ) {
            IconButton(
              onClick = { onSeek((seconds - 10).coerceAtLeast(0)) },
              modifier = Modifier.size(44.dp)
            ) {
              Icon(Icons.Default.Replay10, contentDescription = "Lùi 10s", tint = Color.White, modifier = Modifier.size(28.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Surface(
              shape = CircleShape,
              color = CrimsonRed,
              modifier = Modifier
                .size(60.dp)
                .clickable { onTogglePlay() }
                .testTag("btn_audio_play_center")
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                  contentDescription = if (isPlaying) "Tạm dừng" else "Phát",
                  tint = Color.White,
                  modifier = Modifier.size(36.dp)
                )
              }
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(
              onClick = { onSeek((seconds + 10).coerceAtMost(totalSecs)) },
              modifier = Modifier.size(44.dp)
            ) {
              Icon(Icons.Default.Forward10, contentDescription = "Tới 10s", tint = Color.White, modifier = Modifier.size(28.dp))
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Speed selector chips
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(0.75f, 1.0f, 1.25f, 1.5f, 2.0f).forEach { s ->
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (speed == s) GoldYellow else Color.White.copy(alpha = 0.15f),
                modifier = Modifier.clickable { onSpeedChange(s) }
              ) {
                Text(
                  text = "${s}x",
                  color = if (speed == s) NavyDeep else Color.White,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }
        }
      }
    }

    // Audio notes & transcript overview
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
              text = "TÓM LƯỢC NỘI DUNG PHÁT THANH GDCT",
              color = NavyPrimary,
              fontSize = 12.5.sp,
              fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onAddNote, modifier = Modifier.size(28.dp)) {
              Icon(Icons.Default.EditNote, contentDescription = "Ghi chú", tint = NavyPrimary)
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Bài nghe giảng chính trị được biên soạn công phu từ nguồn tài liệu của Ban Tuyên huấn Vùng 4 Hải quân, giúp cán bộ, chiến sĩ có thể tự học, tự bồi dưỡng nhận thức chính trị trong giờ sinh hoạt hoặc giờ nghỉ, ngày nghỉ.",
            color = Color(0xFF334155),
            fontSize = 13.sp,
            lineHeight = 20.sp
          )
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
          .testTag("btn_audio_finish_quiz")
      ) {
        Icon(Icons.Default.Assignment, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Làm bài kiểm tra trắc nghiệm", fontSize = 14.sp, fontWeight = FontWeight.Bold)
      }
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}
