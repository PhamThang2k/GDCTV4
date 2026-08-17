package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AddNoteDialog
import com.example.ui.components.DailyQuoteDialog
import com.example.ui.components.GdctBottomNavigation
import com.example.ui.components.GdctTopBar
import com.example.ui.components.LawDetailDialog
import com.example.ui.components.LessonDetailView
import com.example.ui.components.NewsDetailDialog
import com.example.ui.components.QuizEngineDialog
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NewsScreen
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
  val uiState by viewModel.uiState.collectAsState()
  val progressMap by viewModel.studyProgressMap.collectAsState()
  val quizSubmissions by viewModel.quizSubmissions.collectAsState()
  val personalNotes by viewModel.personalNotes.collectAsState()
  val bookmarkedIds by viewModel.bookmarkedIds.collectAsState()

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
      onBack = { viewModel.closeLesson() },
      onModeChange = { viewModel.setStudyMode(it) },
      onNextSlide = { viewModel.nextSlide() },
      onPrevSlide = { viewModel.prevSlide() },
      onSelectSlide = { viewModel.setSlideIndex(it) },
      onToggleSectionChecked = { viewModel.toggleSectionChecked(it) },
      onToggleVideoPlay = { viewModel.toggleVideoPlay() },
      onVideoSeek = { viewModel.seekVideo(it) },
      onVideoSpeedChange = { viewModel.setVideoSpeed(it) },
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
            news = viewModel.allNews,
            progressMap = progressMap,
            quizSubmissions = quizSubmissions,
            onNavigateTab = { viewModel.setTab(it) },
            onOpenLesson = { viewModel.openLesson(it) },
            onOpenNews = { viewModel.openNews(it) },
            onStartQuiz = { viewModel.startQuiz(it) },
            onOpenQuoteDialog = { viewModel.setDailyQuoteDialog(true) },
            onOpenPartyNotebook = { viewModel.setTab(AppTab.UTILITIES) },
            onOpenCommanderReport = { viewModel.setTab(AppTab.PROFILE) }
          )

          AppTab.NEWS -> NewsScreen(
            newsList = viewModel.allNews,
            selectedCategory = uiState.selectedNewsCategory,
            searchQuery = uiState.searchQuery,
            bookmarkedIds = bookmarkedIds,
            onCategorySelected = { viewModel.setNewsCategory(it) },
            onSearchChange = { viewModel.setSearchQuery(it) },
            onOpenArticle = { viewModel.openNews(it) },
            onToggleBookmark = { viewModel.toggleBookmarkArticle(it) }
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

  if (uiState.selectedNews != null) {
    val article = uiState.selectedNews!!
    val isBookmarked = bookmarkedIds.contains(article.id)
    NewsDetailDialog(
      article = article,
      isBookmarked = isBookmarked,
      onToggleBookmark = { viewModel.toggleBookmarkArticle(article.id) },
      onDismiss = { viewModel.closeNews() }
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
