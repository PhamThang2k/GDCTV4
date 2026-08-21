package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AddNoteDialog
import com.example.ui.components.DailyQuoteDialog
import com.example.ui.components.GdctBottomNavigation
import com.example.ui.components.GdctTopBar
import com.example.ui.components.LawDetailDialog
import com.example.ui.components.LessonDetailView
import com.example.ui.components.QuizEngineDialog
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileAdminScreen
import com.example.ui.screens.StudyScreen
import com.example.ui.screens.UtilitiesScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.GdctViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        GdctApp()
      }
    }
  }
}

@Composable
fun GdctApp(viewModel: GdctViewModel = viewModel()) {
  val context = LocalContext.current
  val uiState by viewModel.uiState.collectAsState()
  val progressMap by viewModel.studyProgressMap.collectAsState()
  val quizSubmissions by viewModel.quizSubmissions.collectAsState()
  val personalNotes by viewModel.personalNotes.collectAsState()

  // Toast listener for Web CMS & downloads
  LaunchedEffect(uiState.toastMessage) {
    uiState.toastMessage?.let { msg ->
      Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
      viewModel.clearToast()
    }
  }

  // Full Screen Lesson Viewer
  if (uiState.selectedLesson != null) {
    val currentLesson = uiState.selectedLesson!!
    val currentProgress = progressMap[currentLesson.id]

    LessonDetailView(
      lesson = currentLesson,
      studyMode = uiState.studyMode,
      currentSlideIndex = uiState.currentSlideIndex,
      checkedSections = uiState.checkedSections,
      progress = currentProgress,
      isVideoPlaying = uiState.isVideoPlaying,
      videoSeconds = uiState.videoCurrentSeconds,
      videoSpeed = uiState.videoSpeed,
      isAudioPlaying = uiState.isAudioPlaying,
      audioSeconds = uiState.audioCurrentSeconds,
      audioSpeed = uiState.audioSpeed,
      downloadedDocIds = uiState.downloadedDocIds,
      onBack = { viewModel.closeLesson() },
      onModeChange = { viewModel.setStudyMode(it) },
      onNextSlide = { viewModel.nextSlide() },
      onPrevSlide = { viewModel.prevSlide() },
      onSelectSlide = { viewModel.setSlideIndex(it) },
      onToggleSectionChecked = { viewModel.toggleSectionChecked(it) },
      onToggleVideoPlay = { viewModel.toggleVideoPlay() },
      onVideoSeek = { viewModel.seekVideo(it) },
      onVideoSpeedChange = { viewModel.setVideoSpeed(it) },
      onToggleAudioPlay = { viewModel.toggleAudioPlay() },
      onAudioSeek = { viewModel.seekAudio(it) },
      onAudioSpeedChange = { viewModel.setAudioSpeed(it) },
      onDownloadDoc = { viewModel.downloadDoc(it) },
      onStartQuiz = { viewModel.startQuiz(currentLesson) },
      onAddNote = { viewModel.setAddNoteDialog(true) }
    )
  } else {
    // Standard Tab Scaffold
    Scaffold(
      modifier = Modifier.fillMaxSize(),
      topBar = {
        GdctTopBar(
          userProfile = uiState.userProfile,
          onOpenQuoteDialog = { viewModel.setDailyQuoteDialog(true) },
          onOpenCommanderReport = { viewModel.setTab(AppTab.PROFILE) }
        )
      },
      bottomBar = {
        GdctBottomNavigation(
          currentTab = uiState.currentTab,
          onTabSelected = { viewModel.setTab(it) }
        )
      }
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        when (uiState.currentTab) {
          AppTab.HOME -> HomeScreen(
            lessons = viewModel.allLessons,
            progressMap = progressMap,
            quizSubmissions = quizSubmissions,
            onNavigateTab = { viewModel.setTab(it) },
            onOpenLesson = { viewModel.openLesson(it) },
            onStartQuiz = { viewModel.startQuiz(it) },
            onOpenQuoteDialog = { viewModel.setDailyQuoteDialog(true) },
            onOpenPartyNotebook = { viewModel.setTab(AppTab.UTILITIES) },
            onOpenCommanderReport = { viewModel.setTab(AppTab.PROFILE) }
          )

          AppTab.STUDY -> StudyScreen(
            lessons = viewModel.allLessons,
            selectedCategory = uiState.selectedLessonCategory,
            searchQuery = uiState.searchQuery,
            progressMap = progressMap,
            quizSubmissions = quizSubmissions,
            onCategorySelected = { viewModel.setLessonCategory(it) },
            onSearchChange = { viewModel.setSearchQuery(it) },
            onOpenLesson = { lesson, mode -> viewModel.openLesson(lesson, mode) },
            onStartQuiz = { viewModel.startQuiz(it) }
          )

          AppTab.UTILITIES -> UtilitiesScreen(
            lessons = viewModel.allLessons,
            lawDocs = viewModel.allLaws,
            notes = personalNotes,
            onOpenLaw = { viewModel.openLaw(it) },
            onStartQuiz = { viewModel.startQuiz(it) },
            onAddNewNote = { viewModel.setAddNoteDialog(true) },
            onDeleteNote = { viewModel.deletePersonalNote(it) },
            onOpenCommanderReport = { viewModel.setTab(AppTab.PROFILE) }
          )

          AppTab.PROFILE -> ProfileAdminScreen(
            userProfile = uiState.userProfile,
            lessons = viewModel.allLessons,
            progressMap = progressMap,
            quizSubmissions = quizSubmissions,
            onOpenLesson = { viewModel.openLesson(it) }
          )
        }
      }
    }
  }

  // Modals & Dialogs
  if (uiState.activeQuizLesson != null) {
    QuizEngineDialog(
      lesson = uiState.activeQuizLesson!!,
      answers = uiState.activeQuizAnswers,
      submittedResult = uiState.quizSubmittedResult,
      onAnswerSelected = { qId, optIdx -> viewModel.selectQuizAnswer(qId, optIdx) },
      onSubmitQuiz = { viewModel.submitActiveQuiz() },
      onDismiss = { viewModel.dismissQuizResult() }
    )
  }

  if (uiState.showDailyQuoteDialog) {
    DailyQuoteDialog(
      onDismiss = { viewModel.setDailyQuoteDialog(false) }
    )
  }

  if (uiState.selectedLaw != null) {
    LawDetailDialog(
      law = uiState.selectedLaw!!,
      onDismiss = { viewModel.closeLaw() }
    )
  }

  if (uiState.showAddNoteDialog) {
    AddNoteDialog(
      lessonTitle = uiState.selectedLesson?.title ?: "Chuyên đề GDCT Vùng 4",
      onSave = { title, content, category ->
        val lessonId = uiState.selectedLesson?.id ?: "general"
        viewModel.savePersonalNote(lessonId, title, content, category)
      },
      onDismiss = { viewModel.setAddNoteDialog(false) }
    )
  }
}
