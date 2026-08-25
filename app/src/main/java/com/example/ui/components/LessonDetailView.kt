package com.example.ui.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Slideshow
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.ViewCarousel
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
  var viewingDoc by remember { mutableStateOf<DocAttachment?>(null) }

  if (viewingDoc != null) {
    DocumentReaderViewerDialog(
      doc = viewingDoc!!,
      lesson = lesson,
      checkedSections = checkedSections,
      onToggleChecked = onToggleSectionChecked,
      onDismiss = { viewingDoc = null }
    )
  }

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
            onOpenDoc = { doc -> viewingDoc = doc },
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
 * Slide Presentation Viewer - PowerPoint Presentation Mode
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

  var isFullscreenShow by remember { mutableStateOf(false) }
  var showSpeakerNotes by remember { mutableStateOf(false) }
  var slideTheme by remember { mutableStateOf("navy") } // "navy", "classic", "dark"
  var isAutoPlaying by remember { mutableStateOf(false) }

  if (isFullscreenShow) {
    FullscreenSlideShowDialog(
      slides = slides,
      initialIndex = safeIndex,
      lessonTitle = lesson.title,
      lessonCode = lesson.code,
      onSelectSlide = onSelect,
      onDismiss = { isFullscreenShow = false }
    )
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 12.dp, vertical = 10.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    // 1. PowerPoint Presentation Toolbar & Controls
    Surface(
      shape = RoundedCornerShape(12.dp),
      color = Color.White,
      shadowElevation = 1.5.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = CrimsonRed
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Slideshow,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(13.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "POWERPOINT PPTx",
                color = Color.White,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Black
              )
            }
          }

          Spacer(modifier = Modifier.width(8.dp))

          Text(
            text = "Slide ${safeIndex + 1}/${slides.size}",
            color = NavyDeep,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          // Speaker Notes Toggle
          IconButton(
            onClick = { showSpeakerNotes = !showSpeakerNotes },
            modifier = Modifier.size(32.dp).testTag("btn_toggle_speaker_notes")
          ) {
            Icon(
              imageVector = Icons.Default.EditNote,
              contentDescription = "Ghi chú thuyết minh",
              tint = if (showSpeakerNotes) CrimsonRed else NavyPrimary,
              modifier = Modifier.size(20.dp)
            )
          }

          // Fullscreen PPT Presentation Mode Button
          Button(
            onClick = { isFullscreenShow = true },
            colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
            shape = RoundedCornerShape(8.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            modifier = Modifier.height(30.dp).testTag("btn_ppt_fullscreen")
          ) {
            Icon(Icons.Default.Fullscreen, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(3.dp))
            Text("Trình chiếu", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // 2. Main 16:9 Presentation Canvas Card
    val hasSlideImage = currentSlide.imageUrl.isNotBlank() || currentSlide.imageData.isNotBlank()
    val slideImgSrc = if (currentSlide.imageUrl.isNotBlank()) currentSlide.imageUrl else currentSlide.imageData

    Card(
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(
        containerColor = if (hasSlideImage) Color.Black else when (slideTheme) {
          "dark" -> Color(0xFF0F172A)
          "classic" -> Color(0xFFFAF8F5)
          else -> Color.White
        }
      ),
      border = androidx.compose.foundation.BorderStroke(
        1.5.dp,
        if (hasSlideImage) NavyPrimary else when (slideTheme) {
          "dark" -> Color(0xFF334155)
          "classic" -> Color(0xFFE2E8F0)
          else -> NavyPrimary.copy(alpha = 0.2f)
        }
      ),
      elevation = CardDefaults.cardElevation(4.dp),
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
    ) {
      if (hasSlideImage) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
          contentAlignment = Alignment.Center
        ) {
          SubcomposeAsyncImage(
            model = slideImgSrc,
            contentDescription = currentSlide.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
            loading = {
              Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  LinearProgressIndicator(modifier = Modifier.width(120.dp), color = CrimsonRed)
                  Spacer(modifier = Modifier.height(6.dp))
                  Text("Đang tải slide PPT...", color = Color.White, fontSize = 11.sp)
                }
              }
            },
            error = {
              // Fallback to text presentation
              Column(
                modifier = Modifier
                  .fillMaxSize()
                  .background(Color.White)
                  .padding(16.dp)
                  .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
              ) {
                Column {
                  Text(
                    text = currentSlide.title,
                    color = NavyDeep,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                  )
                  Spacer(modifier = Modifier.height(8.dp))
                  currentSlide.bullets.forEach { b ->
                    Text(text = "• $b", color = Color(0xFF1E293B), fontSize = 13.sp, modifier = Modifier.padding(vertical = 3.dp))
                  }
                }
              }
            }
          )

          // Floating Badge: Slide Number & Status
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = Color.Black.copy(alpha = 0.7f),
            modifier = Modifier
              .align(Alignment.TopEnd)
              .padding(8.dp)
          ) {
            Text(
              text = "Slide ${safeIndex + 1}/${slides.size}",
              color = GoldYellow,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
            )
          }
        }
      } else {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
          verticalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            // Slide Header Bar (PowerPoint Master Slide Banner)
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.MilitaryTech,
                  contentDescription = null,
                  tint = if (slideTheme == "dark") GoldYellow else CrimsonRed,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "VÙNG 4 HẢI QUÂN • ${lesson.code}",
                  color = if (slideTheme == "dark") GoldYellow else NavyPrimary,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  letterSpacing = 0.5.sp
                )
              }

              Surface(
                shape = RoundedCornerShape(4.dp),
                color = if (slideTheme == "dark") Color(0xFF1E293B) else Color(0xFFF1F5F9)
              ) {
                Text(
                  text = "TRANG ${safeIndex + 1}",
                  color = if (slideTheme == "dark") Color(0xFF94A3B8) else Color(0xFF64748B),
                  fontSize = 9.5.sp,
                  fontWeight = FontWeight.Black,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Slide Title
            Text(
              text = currentSlide.title,
              color = if (slideTheme == "dark") Color.White else NavyDeep,
              fontSize = 16.5.sp,
              fontWeight = FontWeight.Black,
              lineHeight = 23.sp
            )

            // Decorative Gold Accent Line
            Box(
              modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(0.35f)
                .height(3.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(if (slideTheme == "dark") GoldYellow else CrimsonRed)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Structured Bullets Points
            currentSlide.bullets.forEachIndexed { idx, bullet ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 5.dp),
                verticalAlignment = Alignment.Top
              ) {
                Box(
                  modifier = Modifier
                    .padding(top = 4.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(if (slideTheme == "dark") GoldYellow else NavyPrimary)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                  text = bullet,
                  color = if (slideTheme == "dark") Color(0xFFE2E8F0) else Color(0xFF1E293B),
                  fontSize = 13.5.sp,
                  lineHeight = 20.sp,
                  fontWeight = FontWeight.Normal
                )
              }
            }
          }

          // Highlight Quote / Core Directive
          if (currentSlide.highlightQuote != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = if (slideTheme == "dark") Color(0xFF1E293B) else Color(0xFFFEF3C7),
              border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (slideTheme == "dark") GoldYellow.copy(alpha = 0.5f) else Color(0xFFFDE68A)
              ),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.FormatQuote,
                  contentDescription = null,
                  tint = if (slideTheme == "dark") GoldYellow else Color(0xFFD97706),
                  modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = currentSlide.highlightQuote!!,
                  color = if (slideTheme == "dark") GoldYellow else Color(0xFF92400E),
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  fontStyle = FontStyle.Italic,
                  lineHeight = 17.sp
                )
              }
            }
          }
        }
      }
    }

    // 3. Collapsible Speaker Notes (Ghi chú thuyết minh bài giảng)
    AnimatedVisibility(visible = showSpeakerNotes) {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFEFF6FF),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(10.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.EditNote, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "GHI CHÚ THUYẾT MINH BÁO CÁO VIÊN",
                color = NavyPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
            IconButton(onClick = { showSpeakerNotes = false }, modifier = Modifier.size(20.dp)) {
              Icon(Icons.Default.Close, contentDescription = "Đóng", tint = NavyPrimary, modifier = Modifier.size(14.dp))
            }
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = currentSlide.note ?: "Nhấn mạnh liên hệ thực tiễn tại đơn vị, phân tích sâu nội dung trọng tâm cho cán bộ, chiến sĩ nắm vững và thực hành.",
            color = Color(0xFF1E3A8A),
            fontSize = 12.sp,
            lineHeight = 17.sp
          )
        }
      }
    }

    // 4. Slide Thumbnails Carousel Strip (Dải hình thu nhỏ slide chuyển nhanh)
    LazyRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      itemsIndexed(slides) { idx, s ->
        val isSelected = idx == safeIndex
        val hasThumbImage = s.imageUrl.isNotBlank() || s.imageData.isNotBlank()
        val thumbSrc = if (s.imageUrl.isNotBlank()) s.imageUrl else s.imageData

        Surface(
          shape = RoundedCornerShape(8.dp),
          color = if (isSelected) NavyContainer else Color.White,
          border = androidx.compose.foundation.BorderStroke(
            if (isSelected) 2.dp else 1.dp,
            if (isSelected) CrimsonRed else Color(0xFFCBD5E1)
          ),
          modifier = Modifier
            .width(86.dp)
            .height(54.dp)
            .clickable { onSelect(idx) }
            .testTag("thumbnail_slide_$idx")
        ) {
          if (hasThumbImage) {
            Box(modifier = Modifier.fillMaxSize()) {
              AsyncImage(
                model = thumbSrc,
                contentDescription = s.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
              Box(
                modifier = Modifier
                  .fillMaxSize()
                  .background(
                    Brush.verticalGradient(
                      listOf(Color.Black.copy(alpha = 0.5f), Color.Transparent, Color.Black.copy(alpha = 0.75f))
                    )
                  )
              )
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 4.dp, vertical = 2.dp)
                  .align(Alignment.TopStart),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "${idx + 1}",
                  fontSize = 9.5.sp,
                  fontWeight = FontWeight.Black,
                  color = if (isSelected) GoldYellow else Color.White
                )
                if (isSelected) {
                  Box(
                    modifier = Modifier
                      .size(6.dp)
                      .clip(CircleShape)
                      .background(CrimsonRed)
                  )
                }
              }
              Text(
                text = s.title,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                  .padding(3.dp)
                  .align(Alignment.BottomStart)
              )
            }
          } else {
            Column(
              modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
              verticalArrangement = Arrangement.SpaceBetween
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "${idx + 1}",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Black,
                  color = if (isSelected) NavyPrimary else Color(0xFF64748B)
                )
                if (isSelected) {
                  Box(
                    modifier = Modifier
                      .size(5.dp)
                      .clip(CircleShape)
                      .background(CrimsonRed)
                  )
                }
              }
              Text(
                text = s.title,
                fontSize = 8.5.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) NavyDeep else Color(0xFF475569),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
            }
          }
        }
      }
    }

    // 5. PowerPoint Navigation Toolbar
    Surface(
      shape = RoundedCornerShape(12.dp),
      color = Color.White,
      shadowElevation = 2.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedButton(
          onClick = onPrev,
          enabled = safeIndex > 0,
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.testTag("btn_slide_prev")
        ) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(15.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Slide trước", fontSize = 11.5.sp)
        }

        // Quick Indicator Dots
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
          slides.take(8).forEachIndexed { i, _ ->
            Box(
              modifier = Modifier
                .size(if (i == safeIndex) 10.dp else 6.dp)
                .clip(CircleShape)
                .background(if (i == safeIndex) CrimsonRed else Color(0xFFCBD5E1))
                .clickable { onSelect(i) }
            )
          }
          if (slides.size > 8) {
            Text(
              text = "+${slides.size - 8}",
              fontSize = 9.sp,
              color = Color(0xFF64748B),
              fontWeight = FontWeight.Bold
            )
          }
        }

        if (safeIndex < slides.size - 1) {
          Button(
            onClick = onNext,
            colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.testTag("btn_slide_next")
          ) {
            Text("Slide kế", fontSize = 11.5.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(15.dp))
          }
        } else {
          Button(
            onClick = onStartQuiz,
            colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.testTag("btn_slide_finish_quiz")
          ) {
            Icon(Icons.Default.Assignment, contentDescription = null, modifier = Modifier.size(15.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Làm kiểm tra", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

/**
 * Fullscreen PowerPoint Presentation Modal Dialog
 */
@Composable
fun FullscreenSlideShowDialog(
  slides: List<com.example.data.model.SlideItem>,
  initialIndex: Int,
  lessonTitle: String,
  lessonCode: String,
  onSelectSlide: (Int) -> Unit,
  onDismiss: () -> Unit
) {
  var currentIndex by remember { mutableStateOf(initialIndex) }
  var isLaserOn by remember { mutableStateOf(false) }
  val safeIndex = currentIndex.coerceIn(0, (slides.size - 1).coerceAtLeast(0))
  val currentSlide = slides.getOrNull(safeIndex) ?: return

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.Black),
      color = Color(0xFF090D16)
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .statusBarsPadding()
          .padding(14.dp),
        verticalArrangement = Arrangement.SpaceBetween
      ) {
        // Top Presentation Controls Bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = CrimsonRed
            ) {
              Text(
                text = "TRÌNH CHIẾU PPT",
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "$lessonCode • Slide ${safeIndex + 1}/${slides.size}",
              color = GoldYellow,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(
              onClick = { isLaserOn = !isLaserOn },
              modifier = Modifier.size(32.dp)
            ) {
              Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "Chỉ điểm laser",
                tint = if (isLaserOn) CrimsonRed else Color.White
              )
            }

            IconButton(
              onClick = onDismiss,
              modifier = Modifier.size(32.dp)
            ) {
              Icon(
                imageVector = Icons.Default.FullscreenExit,
                contentDescription = "Thoát trình chiếu",
                tint = Color.White
              )
            }
          }
        }

        // Center 16:9 PowerPoint Slide Stage
        val hasFullscreenImage = currentSlide.imageUrl.isNotBlank() || currentSlide.imageData.isNotBlank()
        val fullscreenImgSrc = if (currentSlide.imageUrl.isNotBlank()) currentSlide.imageUrl else currentSlide.imageData

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = if (hasFullscreenImage) Color.Black else Color.White),
          elevation = CardDefaults.cardElevation(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(vertical = 10.dp)
        ) {
          if (hasFullscreenImage) {
            Box(
              modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
              contentAlignment = Alignment.Center
            ) {
              SubcomposeAsyncImage(
                model = fullscreenImgSrc,
                contentDescription = currentSlide.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
                loading = {
                  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LinearProgressIndicator(modifier = Modifier.width(120.dp), color = CrimsonRed)
                  }
                }
              )
            }
          } else {
            Column(
              modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
              verticalArrangement = Arrangement.SpaceBetween
            ) {
              Column {
                // Master Slide Top
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.MilitaryTech, contentDescription = null, tint = CrimsonRed, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "BỘ TƯ LỆNH VÙNG 4 HẢI QUÂN",
                      color = NavyDeep,
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Black
                    )
                  }
                  Text(
                    text = "Slide ${safeIndex + 1} / ${slides.size}",
                    color = Color(0xFF64748B),
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Bold
                  )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                  text = currentSlide.title,
                  color = NavyDeep,
                  fontSize = 18.sp,
                  fontWeight = FontWeight.Black,
                  lineHeight = 24.sp
                )

                Box(
                  modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(0.3f)
                    .height(3.5.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(CrimsonRed)
                )

                Spacer(modifier = Modifier.height(6.dp))

                currentSlide.bullets.forEach { bullet ->
                  Row(
                    modifier = Modifier
                      .fillMaxWidth()
                      .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.Top
                  ) {
                    Box(
                      modifier = Modifier
                        .padding(top = 4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(NavyPrimary)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                      text = bullet,
                      color = Color(0xFF1E293B),
                      fontSize = 14.sp,
                      lineHeight = 21.sp
                    )
                  }
                }
              }

              // Highlight Quote at bottom
              if (currentSlide.highlightQuote != null) {
                Surface(
                  shape = RoundedCornerShape(10.dp),
                  color = Color(0xFFFEF3C7),
                  border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Icon(
                      imageVector = Icons.Default.FormatQuote,
                      contentDescription = null,
                      tint = Color(0xFFD97706),
                      modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                      text = currentSlide.highlightQuote!!,
                      color = Color(0xFF92400E),
                      fontSize = 13.sp,
                      fontWeight = FontWeight.Bold,
                      fontStyle = FontStyle.Italic
                    )
                  }
                }
              }
            }
          }
        }

        // Bottom Navigation in Fullscreen Show
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Button(
            onClick = {
              if (safeIndex > 0) {
                currentIndex = safeIndex - 1
                onSelectSlide(safeIndex - 1)
              }
            },
            enabled = safeIndex > 0,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B)),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Trang trước", fontSize = 12.sp)
          }

          Text(
            text = "${safeIndex + 1} / ${slides.size}",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )

          Button(
            onClick = {
              if (safeIndex < slides.size - 1) {
                currentIndex = safeIndex + 1
                onSelectSlide(safeIndex + 1)
              } else {
                onDismiss()
              }
            },
            colors = ButtonDefaults.buttonColors(
              containerColor = if (safeIndex < slides.size - 1) NavyPrimary else CrimsonRed
            ),
            shape = RoundedCornerShape(10.dp)
          ) {
            Text(if (safeIndex < slides.size - 1) "Trang sau" else "Hoàn tất", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(6.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
          }
        }
      }
    }
  }
}

/**
 * Rich Document Reader Mode & Downloadable DOCX/PDF with Offline Storage
 */
@Composable
fun DocumentReaderViewer(
  lesson: Lesson,
  checkedSections: Set<Int>,
  downloadedDocIds: Set<String>,
  onDownloadDoc: (DocAttachment) -> Unit,
  onOpenDoc: (DocAttachment) -> Unit,
  onToggleChecked: (Int) -> Unit,
  onStartQuiz: () -> Unit,
  onAddNote: () -> Unit
) {
  val totalSections = lesson.sections.size
  val completedSectionsCount = checkedSections.count { secNum ->
    lesson.sections.any { it.sectionNumber == secNum }
  }
  val sectionPct = if (totalSections > 0) (completedSectionsCount * 100) / totalSections else 0

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Offline Storage Status Banner
    item {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF0FDF4),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF86EFAC)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.CloudDone,
            contentDescription = null,
            tint = SuccessGreen,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = "BỘ NHỚ ỨNG DỤNG ĐÃ SẴN SÀNG OFFLINE",
              color = Color(0xFF166534),
              fontSize = 11.sp,
              fontWeight = FontWeight.Black
            )
            Text(
              text = "Tất cả tài liệu Word DOCX và PDF có thể mở đọc trơn tru ngay trên ứng dụng kể cả khi không có mạng internet.",
              color = Color(0xFF15803D),
              fontSize = 10.5.sp,
              lineHeight = 15.sp
            )
          }
        }
      }
    }

    // 2. Downloadable DOCX / PDF Attachments Section
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
                  imageVector = Icons.Default.Description,
                  contentDescription = null,
                  tint = NavyPrimary,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "TÀI LIỆU VĂN KIỆN ĐÍNH KÈM (DOCX / PDF)",
                  color = NavyDeep,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            lesson.docAttachments.forEach { doc ->
              val isDownloaded = downloadedDocIds.contains(doc.id) || doc.isDownloaded

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
                  .clickable { onOpenDoc(doc) }
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
                      modifier = Modifier.size(38.dp)
                    ) {
                      Box(contentAlignment = Alignment.Center) {
                        Text(
                          text = doc.fileType,
                          color = if (doc.fileType == "PDF") CrimsonRed else Color(0xFF0284C7),
                          fontWeight = FontWeight.Black,
                          fontSize = 11.sp
                        )
                      }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                      Text(
                        text = doc.fileName,
                        color = NavyDeep,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                      )
                      Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                          text = "${doc.fileType} • ${doc.fileSize} • ${doc.pageCount} trang",
                          color = Color(0xFF64748B),
                          fontSize = 10.5.sp
                        )
                        if (isDownloaded) {
                          Spacer(modifier = Modifier.width(4.dp))
                          Text(
                            text = "• Đã lưu máy",
                            color = SuccessGreen,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold
                          )
                        }
                      }
                    }
                  }

                  Spacer(modifier = Modifier.width(8.dp))

                  Button(
                    onClick = {
                      if (isDownloaded) {
                        onOpenDoc(doc)
                      } else {
                        onDownloadDoc(doc)
                        onOpenDoc(doc)
                      }
                    },
                    colors = ButtonDefaults.buttonColors(
                      containerColor = if (isDownloaded) SuccessGreen else NavyPrimary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    modifier = Modifier.height(34.dp).testTag("btn_doc_action_${doc.id}")
                  ) {
                    Icon(
                      imageVector = if (isDownloaded) Icons.Default.MenuBook else Icons.Default.Download,
                      contentDescription = null,
                      modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                      text = if (isDownloaded) "Đọc ngay" else "Tải về",
                      fontSize = 11.5.sp,
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

    // 3. Summary Card
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

    // 4. Section Tracker Header & Progress Card
    item {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = if (sectionPct >= 100) Color(0xFFF0FDF4) else Color.White),
        border = androidx.compose.foundation.BorderStroke(
          1.dp,
          if (sectionPct >= 100) Color(0xFF86EFAC) else Color(0xFFCBD5E1)
        )
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.CheckBox,
                contentDescription = null,
                tint = if (sectionPct >= 100) SuccessGreen else NavyPrimary,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "NỘI DUNG TỪNG PHẦN HỌC TẬP",
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Bold,
                color = NavyDeep
              )
            }

            Surface(
              shape = RoundedCornerShape(6.dp),
              color = if (sectionPct >= 100) SuccessGreen else NavyPrimary
            ) {
              Text(
                text = "Đã học: $completedSectionsCount/$totalSections phần ($sectionPct%)",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          LinearProgressIndicator(
            progress = { if (totalSections > 0) completedSectionsCount.toFloat() / totalSections.toFloat() else 0f },
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = if (sectionPct >= 100) SuccessGreen else GoldYellow,
            trackColor = Color(0xFFE2E8F0)
          )

          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Tích chọn vào từng phần bên dưới sau khi đọc xong để cập nhật tiến độ học tập.",
            color = Color(0xFF64748B),
            fontSize = 11.sp
          )
        }
      }
    }

    // 5. Granular Section Cards
    itemsIndexed(lesson.sections) { idx, section ->
      val isChecked = checkedSections.contains(section.sectionNumber)

      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (isChecked) Color(0xFFF0FDF4) else Color.White
        ),
        border = androidx.compose.foundation.BorderStroke(
          1.5.dp,
          if (isChecked) Color(0xFF86EFAC) else Color(0xFFE2E8F0)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          // Section header with badge and checkbox
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = if (isChecked) Color(0xFFDCFCE7) else NavyContainer
            ) {
              Text(
                text = "PHẦN ${section.sectionNumber}",
                color = if (isChecked) Color(0xFF15803D) else NavyPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (isChecked) Color(0xFFDCFCE7) else Color(0xFFF1F5F9),
              border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (isChecked) Color(0xFF86EFAC) else Color(0xFFCBD5E1)
              ),
              modifier = Modifier
                .clickable { onToggleChecked(section.sectionNumber) }
                .testTag("btn_toggle_section_${section.sectionNumber}")
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
              ) {
                Icon(
                  imageVector = if (isChecked) Icons.Default.CheckCircle else Icons.Default.CheckBoxOutlineBlank,
                  contentDescription = null,
                  tint = if (isChecked) SuccessGreen else Color(0xFF64748B),
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                  text = if (isChecked) "Đã học xong" else "Tích đã học",
                  color = if (isChecked) Color(0xFF15803D) else Color(0xFF475569),
                  fontSize = 11.5.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = section.heading,
            color = NavyPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 21.sp
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = section.content,
            color = Color(0xFF1E293B),
            fontSize = 13.5.sp,
            lineHeight = 21.sp
          )

          Spacer(modifier = Modifier.height(12.dp))

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
                text = "Ghi nhớ cốt lõi: ${section.keyTakeaway}",
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
 * Fullscreen / Modal Document Reader Viewer Dialog supporting DOCX and PDF natively
 */
@Composable
fun DocumentReaderViewerDialog(
  doc: DocAttachment,
  lesson: Lesson,
  checkedSections: Set<Int>,
  onToggleChecked: (Int) -> Unit,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current
  var fontSizeSp by remember { mutableStateOf(14) }
  var readingTheme by remember { mutableStateOf("light") } // "light", "sepia", "dark"
  var currentPdfPage by remember { mutableStateOf(1) }
  val totalPdfPages = doc.pageCount.coerceAtLeast(1)
  var isBookmarked by remember { mutableStateOf(false) }
  var pdfSearchQuery by remember { mutableStateOf("") }
  var showSearchBar by remember { mutableStateOf(false) }

  val isPdf = doc.fileType.equals("PDF", ignoreCase = true)

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .background(
          when (readingTheme) {
            "sepia" -> Color(0xFFFDFBF7)
            "dark" -> Color(0xFF0F172A)
            else -> Color(0xFFF1F5F9)
          }
        ),
      color = when (readingTheme) {
        "sepia" -> Color(0xFFFDFBF7)
        "dark" -> Color(0xFF0F172A)
        else -> Color(0xFFF1F5F9)
      }
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        // 1. Official Top Banner
        Surface(
          color = NavyDeep,
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .statusBarsPadding()
              .padding(horizontal = 14.dp, vertical = 10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Icon(
                  imageVector = Icons.Default.MilitaryTech,
                  contentDescription = null,
                  tint = GoldYellow,
                  modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                  Text(
                    text = "BỘ TƯ LỆNH VÙNG 4 HẢI QUÂN",
                    color = GoldYellow,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                  )
                  Text(
                    text = doc.fileName,
                    color = Color.White,
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                }
              }

              IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White)
              }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Sub toolbar with format badge, zoom controls, theme switcher
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Surface(
                  shape = RoundedCornerShape(4.dp),
                  color = if (isPdf) CrimsonRed else Color(0xFF0284C7)
                ) {
                  Text(
                    text = if (isPdf) "PDF CHUẨN" else "DOCX WORD",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }

                Surface(
                  shape = RoundedCornerShape(4.dp),
                  color = Color.White.copy(alpha = 0.15f)
                ) {
                  Text(
                    text = if (isPdf) "Trang $currentPdfPage/$totalPdfPages" else "${doc.fileSize} • Offline",
                    color = Color(0xFFE2E8F0),
                    fontSize = 10.5.sp,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
              }

              // Text Zoom & Reading Theme Controls
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                // Theme Toggle: Light -> Sepia -> Dark
                IconButton(
                  onClick = {
                    readingTheme = when (readingTheme) {
                      "light" -> "sepia"
                      "sepia" -> "dark"
                      else -> "light"
                    }
                  },
                  modifier = Modifier.size(28.dp)
                ) {
                  Icon(
                    imageVector = when (readingTheme) {
                      "sepia" -> Icons.Default.Palette
                      "dark" -> Icons.Default.DarkMode
                      else -> Icons.Default.LightMode
                    },
                    contentDescription = "Chế độ đọc",
                    tint = GoldYellow,
                    modifier = Modifier.size(16.dp)
                  )
                }

                // Bookmark toggle
                IconButton(
                  onClick = {
                    isBookmarked = !isBookmarked
                    Toast.makeText(context, if (isBookmarked) "Đã đánh dấu trang này" else "Đã gỡ đánh dấu", Toast.LENGTH_SHORT).show()
                  },
                  modifier = Modifier.size(28.dp)
                ) {
                  Icon(
                    imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Đánh dấu trang",
                    tint = if (isBookmarked) CrimsonRed else Color.White,
                    modifier = Modifier.size(16.dp)
                  )
                }

                // Zoom controls
                IconButton(
                  onClick = { if (fontSizeSp > 12) fontSizeSp -= 2 },
                  modifier = Modifier.size(26.dp)
                ) {
                  Icon(Icons.Default.ZoomOut, contentDescription = "Nhỏ lại", tint = Color.White, modifier = Modifier.size(16.dp))
                }
                Text("${fontSizeSp}pt", color = GoldYellow, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                IconButton(
                  onClick = { if (fontSizeSp < 22) fontSizeSp += 2 },
                  modifier = Modifier.size(26.dp)
                ) {
                  Icon(Icons.Default.ZoomIn, contentDescription = "Lớn hơn", tint = Color.White, modifier = Modifier.size(16.dp))
                }
              }
            }
          }
        }

        // 2. Document Render Canvas (DOCX vs PDF Mode)
        LazyColumn(
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          if (isPdf) {
            // PDF Paginated Mode Layout
            item {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFFEF2F2),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFECACA)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  modifier = Modifier.padding(10.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Description, contentDescription = null, tint = CrimsonRed, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "TRÌNH ĐỌC PDF PHÂN TRANG (OFFLINE)",
                      color = CrimsonRed,
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Black
                    )
                  }

                  Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(
                      onClick = { if (currentPdfPage > 1) currentPdfPage-- },
                      enabled = currentPdfPage > 1,
                      shape = RoundedCornerShape(6.dp),
                      contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                      modifier = Modifier.height(28.dp)
                    ) {
                      Text("Trang trước", fontSize = 10.5.sp)
                    }
                    OutlinedButton(
                      onClick = { if (currentPdfPage < totalPdfPages) currentPdfPage++ },
                      enabled = currentPdfPage < totalPdfPages,
                      shape = RoundedCornerShape(6.dp),
                      contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                      modifier = Modifier.height(28.dp)
                    ) {
                      Text("Trang sau", fontSize = 10.5.sp)
                    }
                  }
                }
              }
            }
          }

          // Document Header Letterhead (Military Word / PDF format)
          item {
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(
                containerColor = when (readingTheme) {
                  "sepia" -> Color(0xFFFFFDF9)
                  "dark" -> Color(0xFF1E293B)
                  else -> Color.White
                }
              ),
              elevation = CardDefaults.cardElevation(2.dp)
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Text(
                  text = "CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM\nĐộc lập - Tự do - Hạnh phúc",
                  fontSize = 11.5.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (readingTheme == "dark") GoldYellow else NavyDeep,
                  textAlign = TextAlign.Center
                )
                Text(
                  text = "-------------------",
                  fontSize = 10.sp,
                  color = Color.Gray,
                  textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = "QUÂN CHỦNG HẢI QUÂN - BỘ TƯ LỆNH VÙNG 4",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Black,
                  color = if (readingTheme == "dark") Color.White else NavyPrimary,
                  textAlign = TextAlign.Center
                )
                Text(
                  text = "Số: 128/CT-TH • Năm 2026",
                  fontSize = 10.5.sp,
                  color = Color(0xFF64748B),
                  textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = "TÀI LIỆU HỌC TẬP GIÁO DỤC CHÍNH TRỊ NĂM 2026",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Black,
                  color = CrimsonRed,
                  textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "Chuyên đề: ${lesson.title}",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (readingTheme == "dark") GoldYellow else NavyPrimary,
                  textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "Giảng viên biên soạn: ${lesson.audioSpeaker} • Đối tượng: ${lesson.targetAudience}",
                  fontSize = 11.sp,
                  color = Color(0xFF64748B),
                  textAlign = TextAlign.Center
                )
              }
            }
          }

          // Document Summary Overview
          item {
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(
                containerColor = when (readingTheme) {
                  "sepia" -> Color(0xFFFFFDF9)
                  "dark" -> Color(0xFF1E293B)
                  else -> Color.White
                }
              )
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Text(
                  text = "I. MỤC ĐÍCH, YÊU CẦU VÀ NỘI DUNG TỔNG QUAN",
                  fontSize = (fontSizeSp).sp,
                  fontWeight = FontWeight.Bold,
                  color = if (readingTheme == "dark") GoldYellow else NavyPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = lesson.summary,
                  fontSize = fontSizeSp.sp,
                  color = if (readingTheme == "dark") Color(0xFFE2E8F0) else Color(0xFF1E293B),
                  lineHeight = (fontSizeSp + 7).sp
                )
              }
            }
          }

          // Full Document Content (DOCX / PDF rich text from Web Admin)
          val docText = doc.fullContent.ifBlank { lesson.docFullContent.ifBlank { lesson.fullText } }
          if (docText.isNotBlank()) {
            item {
              Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                  containerColor = when (readingTheme) {
                    "sepia" -> Color(0xFFFFFDF9)
                    "dark" -> Color(0xFF1E293B)
                    else -> Color.White
                  }
                ),
                border = androidx.compose.foundation.BorderStroke(
                  1.dp,
                  if (readingTheme == "dark") GoldYellow.copy(alpha = 0.4f) else NavyPrimary.copy(alpha = 0.2f)
                )
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = if (readingTheme == "dark") GoldYellow else NavyPrimary,
                        modifier = Modifier.size(18.dp)
                      )
                      Spacer(modifier = Modifier.width(6.dp))
                      Text(
                        text = "NỘI DUNG VĂN KIỆN TOÀN VĂN (${if (isPdf) "PDF" else "DOCX"})",
                        fontSize = (fontSizeSp + 0.5f).sp,
                        fontWeight = FontWeight.Black,
                        color = if (readingTheme == "dark") GoldYellow else NavyPrimary
                      )
                    }

                    Surface(
                      shape = RoundedCornerShape(4.dp),
                      color = if (isPdf) CrimsonRed.copy(alpha = 0.15f) else Color(0xFF0284C7).copy(alpha = 0.15f)
                    ) {
                      Text(
                        text = if (isPdf) "Chuẩn PDF" else "Chuẩn DOCX",
                        color = if (isPdf) CrimsonRed else Color(0xFF0284C7),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                      )
                    }
                  }

                  Spacer(modifier = Modifier.height(10.dp))

                  // Paragraphs rendering
                  docText.split("\n\n").forEach { paragraph ->
                    val cleanP = paragraph.trim()
                    if (cleanP.isNotBlank()) {
                      Text(
                        text = cleanP,
                        fontSize = fontSizeSp.sp,
                        color = if (readingTheme == "dark") Color(0xFFE2E8F0) else Color(0xFF1E293B),
                        lineHeight = (fontSizeSp + 7).sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                      )
                    }
                  }
                }
              }
            }
          }

          // Render Sections in Document Format
          itemsIndexed(lesson.sections) { idx, sec ->
            val isChecked = checkedSections.contains(sec.sectionNumber)

            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(
                containerColor = when {
                  isChecked -> if (readingTheme == "dark") Color(0xFF064E3B) else Color(0xFFF0FDF4)
                  readingTheme == "sepia" -> Color(0xFFFFFDF9)
                  readingTheme == "dark" -> Color(0xFF1E293B)
                  else -> Color.White
                }
              ),
              border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (isChecked) Color(0xFF86EFAC) else if (readingTheme == "dark") Color(0xFF334155) else Color(0xFFE2E8F0)
              )
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = sec.heading,
                    fontSize = (fontSizeSp + 1).sp,
                    fontWeight = FontWeight.Bold,
                    color = if (readingTheme == "dark") GoldYellow else NavyPrimary,
                    modifier = Modifier.weight(1f)
                  )

                  IconButton(onClick = { onToggleChecked(sec.sectionNumber) }) {
                    Icon(
                      imageVector = if (isChecked) Icons.Default.CheckCircle else Icons.Default.CheckBoxOutlineBlank,
                      contentDescription = "Đã học",
                      tint = if (isChecked) SuccessGreen else Color(0xFF94A3B8)
                    )
                  }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                  text = sec.content,
                  fontSize = fontSizeSp.sp,
                  color = if (readingTheme == "dark") Color(0xFFE2E8F0) else Color(0xFF334155),
                  lineHeight = (fontSizeSp + 7).sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = if (readingTheme == "dark") Color(0xFF2E1065) else Color(0xFFFEF3C7),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      Icons.Default.Lightbulb,
                      contentDescription = null,
                      tint = if (readingTheme == "dark") GoldYellow else Color(0xFFD97706),
                      modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "Ý nghĩa cốt lõi: ${sec.keyTakeaway}",
                      color = if (readingTheme == "dark") Color(0xFFF3E8FF) else Color(0xFF92400E),
                      fontSize = (fontSizeSp - 2).coerceAtLeast(10).sp,
                      fontWeight = FontWeight.Medium
                    )
                  }
                }
              }
            }
          }

          // Signatory block
          item {
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(
                containerColor = when (readingTheme) {
                  "sepia" -> Color(0xFFFFFDF9)
                  "dark" -> Color(0xFF1E293B)
                  else -> Color.White
                }
              )
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(14.dp),
                horizontalAlignment = Alignment.End
              ) {
                Text(
                  text = "TM. ĐẢNG ỦY BỘ TƯ LỆNH VÙNG 4",
                  fontSize = 11.5.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (readingTheme == "dark") Color.White else NavyDeep
                )
                Text(
                  text = "CHÍNH ỦY",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Black,
                  color = CrimsonRed
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                  text = "(Đã ký và đóng dấu lưu hành nội bộ)",
                  fontSize = 10.5.sp,
                  color = Color(0xFF64748B),
                  fontStyle = FontStyle.Italic
                )
              }
            }
          }
        }

        // 3. Bottom Action Bar
        Surface(
          color = Color.White,
          shadowElevation = 8.dp,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .padding(12.dp)
              .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            OutlinedButton(
              onClick = {
                try {
                  val intent = Intent(Intent.ACTION_VIEW).apply {
                    val mimeType = if (isPdf) "application/pdf" else "application/msword"
                    setDataAndType(Uri.parse("file:///android_asset/documents/${doc.fileName}"), mimeType)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                  }
                  context.startActivity(Intent.createChooser(intent, "Mở tài liệu bằng:"))
                } catch (e: Exception) {
                  Toast.makeText(context, "Đã lưu tài liệu trên bộ nhớ máy và hiển thị đầy đủ trên app!", Toast.LENGTH_SHORT).show()
                }
              },
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f).height(44.dp)
            ) {
              Icon(Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Mở bằng app ngoài", fontSize = 12.sp)
            }

            Button(
              onClick = onDismiss,
              colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f).height(44.dp)
            ) {
              Text("Đóng tài liệu", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
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
