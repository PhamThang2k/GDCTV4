package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.PersonalNoteEntity
import com.example.data.local.QuizSubmissionEntity
import com.example.data.local.StudyProgressEntity
import com.example.data.model.LawDoc
import com.example.data.model.Lesson
import com.example.data.model.NewsArticle
import com.example.data.model.StudyMode
import com.example.data.model.UserProfile
import com.example.data.repository.GdctRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab(val title: String, val testTag: String) {
  HOME("Trang chủ", "tab_home"),
  NEWS("Tin tức", "tab_news"),
  STUDY("Học tập GDCT", "tab_study"),
  UTILITIES("Tiện ích", "tab_utilities"),
  PROFILE("Cá nhân", "tab_profile")
}

data class GdctUiState(
  val currentTab: AppTab = AppTab.HOME,
  val selectedLesson: Lesson? = null,
  val studyMode: StudyMode = StudyMode.SLIDE,
  val currentSlideIndex: Int = 0,
  val checkedSections: Set<Int> = emptySet(),
  val selectedNews: NewsArticle? = null,
  val selectedLaw: LawDoc? = null,
  val activeQuizLesson: Lesson? = null,
  val activeQuizAnswers: Map<Int, Int> = emptyMap(),
  val quizSubmittedResult: QuizSubmissionEntity? = null,
  val selectedNewsCategory: String = "Tất cả",
  val selectedLessonCategory: String = "Tất cả",
  val isVideoPlaying: Boolean = false,
  val videoCurrentSeconds: Int = 0,
  val videoSpeed: Float = 1.0f,
  val userProfile: UserProfile = UserProfile(),
  val showPartyNotebookDialog: Boolean = false,
  val showCommanderReportDialog: Boolean = false,
  val showDailyQuoteDialog: Boolean = false,
  val showAddNoteDialog: Boolean = false,
  val searchQuery: String = ""
)

class GdctViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: GdctRepository
  private val _uiState = MutableStateFlow(GdctUiState())
  val uiState: StateFlow<GdctUiState> = _uiState.asStateFlow()

  val allLessons: List<Lesson>
  val allNews: List<NewsArticle>
  val allLaws: List<LawDoc>

  val studyProgressMap: StateFlow<Map<String, StudyProgressEntity>>
  val quizSubmissions: StateFlow<List<QuizSubmissionEntity>>
  val personalNotes: StateFlow<List<PersonalNoteEntity>>
  val bookmarkedIds: StateFlow<Set<String>>

  init {
    val db = AppDatabase.getDatabase(application)
    repository = GdctRepository(db)
    allLessons = repository.getLessons()
    allNews = repository.getNewsArticles()
    allLaws = repository.getLawDocs()

    studyProgressMap = repository.allProgress
      .combine(MutableStateFlow(Unit)) { progressList, _ ->
        progressList.associateBy { it.lessonId }
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyMap()
      )

    quizSubmissions = repository.allQuizSubmissions
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
      )

    personalNotes = repository.allNotes
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
      )

    bookmarkedIds = repository.allBookmarks
      .combine(MutableStateFlow(Unit)) { bookmarks, _ ->
        bookmarks.map { it.articleId }.toSet()
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptySet()
      )
  }

  fun setTab(tab: AppTab) {
    _uiState.value = _uiState.value.copy(currentTab = tab)
  }

  fun openLesson(lesson: Lesson, mode: StudyMode = StudyMode.SLIDE) {
    _uiState.value = _uiState.value.copy(
      selectedLesson = lesson,
      studyMode = mode,
      currentSlideIndex = 0,
      checkedSections = emptySet(),
      isVideoPlaying = false,
      videoCurrentSeconds = 0
    )
  }

  fun closeLesson() {
    _uiState.value = _uiState.value.copy(
      selectedLesson = null,
      isVideoPlaying = false
    )
  }

  fun setStudyMode(mode: StudyMode) {
    _uiState.value = _uiState.value.copy(studyMode = mode)
    val lesson = _uiState.value.selectedLesson ?: return
    updateStudyProgress(lesson)
  }

  fun nextSlide() {
    val lesson = _uiState.value.selectedLesson ?: return
    val currentIndex = _uiState.value.currentSlideIndex
    if (currentIndex < lesson.slides.size - 1) {
      _uiState.value = _uiState.value.copy(currentSlideIndex = currentIndex + 1)
      updateStudyProgress(lesson)
    }
  }

  fun prevSlide() {
    val currentIndex = _uiState.value.currentSlideIndex
    if (currentIndex > 0) {
      _uiState.value = _uiState.value.copy(currentSlideIndex = currentIndex - 1)
    }
  }

  fun setSlideIndex(index: Int) {
    val lesson = _uiState.value.selectedLesson ?: return
    if (index in lesson.slides.indices) {
      _uiState.value = _uiState.value.copy(currentSlideIndex = index)
      updateStudyProgress(lesson)
    }
  }

  fun toggleSectionChecked(sectionNumber: Int) {
    val current = _uiState.value.checkedSections
    val updated = if (current.contains(sectionNumber)) {
      current - sectionNumber
    } else {
      current + sectionNumber
    }
    _uiState.value = _uiState.value.copy(checkedSections = updated)
    val lesson = _uiState.value.selectedLesson ?: return
    updateStudyProgress(lesson)
  }

  fun toggleVideoPlay() {
    _uiState.value = _uiState.value.copy(isVideoPlaying = !_uiState.value.isVideoPlaying)
    val lesson = _uiState.value.selectedLesson ?: return
    updateStudyProgress(lesson)
  }

  fun setVideoSpeed(speed: Float) {
    _uiState.value = _uiState.value.copy(videoSpeed = speed)
  }

  fun seekVideo(seconds: Int) {
    _uiState.value = _uiState.value.copy(videoCurrentSeconds = seconds.coerceAtLeast(0))
    val lesson = _uiState.value.selectedLesson ?: return
    updateStudyProgress(lesson)
  }

  private fun updateStudyProgress(lesson: Lesson) {
    viewModelScope.launch {
      val totalSections = lesson.sections.size
      val checkedCount = _uiState.value.checkedSections.size
      val slideProgress = ((_uiState.value.currentSlideIndex + 1) * 100) / lesson.slides.size.coerceAtLeast(1)
      val sectionProgress = if (totalSections > 0) (checkedCount * 100) / totalSections else 0
      val modeBonus = when (_uiState.value.studyMode) {
        StudyMode.SLIDE -> slideProgress
        StudyMode.DOCUMENT -> sectionProgress
        StudyMode.VIDEO -> if (_uiState.value.videoCurrentSeconds > 30 || _uiState.value.isVideoPlaying) 75 else 25
      }

      val calculatedPercent = maxOf(slideProgress, sectionProgress, modeBonus).coerceIn(10, 100)
      val isCompleted = calculatedPercent >= 100

      repository.saveStudyProgress(
        lessonId = lesson.id,
        progressPercent = calculatedPercent,
        completedSectionsCount = checkedCount,
        totalSectionsCount = totalSections,
        isCompleted = isCompleted,
        lastMode = _uiState.value.studyMode.name
      )
    }
  }

  // Quiz Handling
  fun startQuiz(lesson: Lesson) {
    _uiState.value = _uiState.value.copy(
      activeQuizLesson = lesson,
      activeQuizAnswers = emptyMap(),
      quizSubmittedResult = null
    )
  }

  fun selectQuizAnswer(questionId: Int, optionIndex: Int) {
    val currentAnswers = _uiState.value.activeQuizAnswers.toMutableMap()
    currentAnswers[questionId] = optionIndex
    _uiState.value = _uiState.value.copy(activeQuizAnswers = currentAnswers)
  }

  fun submitActiveQuiz() {
    val lesson = _uiState.value.activeQuizLesson ?: return
    val answers = _uiState.value.activeQuizAnswers

    var correctCount = 0
    lesson.quizQuestions.forEach { q ->
      val userSelected = answers[q.id]
      if (userSelected != null && userSelected == q.correctOptionIndex) {
        correctCount++
      }
    }

    viewModelScope.launch {
      val submissionId = repository.submitQuiz(
        lessonId = lesson.id,
        lessonTitle = lesson.title,
        score = correctCount,
        totalQuestions = lesson.quizQuestions.size
      )

      // Also mark study progress as 100% completed
      repository.saveStudyProgress(
        lessonId = lesson.id,
        progressPercent = 100,
        completedSectionsCount = lesson.sections.size,
        totalSectionsCount = lesson.sections.size,
        isCompleted = true,
        lastMode = "QUIZ_PASSED"
      )

      val percentage = if (lesson.quizQuestions.isNotEmpty()) (correctCount * 100) / lesson.quizQuestions.size else 0
      val passed = percentage >= 60

      _uiState.value = _uiState.value.copy(
        quizSubmittedResult = QuizSubmissionEntity(
          id = submissionId,
          lessonId = lesson.id,
          lessonTitle = lesson.title,
          score = correctCount,
          totalQuestions = lesson.quizQuestions.size,
          percentage = percentage,
          passed = passed,
          timestamp = System.currentTimeMillis()
        )
      )
    }
  }

  fun dismissQuizResult() {
    _uiState.value = _uiState.value.copy(
      activeQuizLesson = null,
      quizSubmittedResult = null,
      activeQuizAnswers = emptyMap()
    )
  }

  // News handling
  fun openNews(article: NewsArticle) {
    _uiState.value = _uiState.value.copy(selectedNews = article)
  }

  fun closeNews() {
    _uiState.value = _uiState.value.copy(selectedNews = null)
  }

  fun toggleBookmarkArticle(articleId: String) {
    val isCurrentlyBookmarked = bookmarkedIds.value.contains(articleId)
    viewModelScope.launch {
      repository.toggleBookmark(articleId, isCurrentlyBookmarked)
    }
  }

  fun setNewsCategory(category: String) {
    _uiState.value = _uiState.value.copy(selectedNewsCategory = category)
  }

  fun setLessonCategory(category: String) {
    _uiState.value = _uiState.value.copy(selectedLessonCategory = category)
  }

  fun setSearchQuery(query: String) {
    _uiState.value = _uiState.value.copy(searchQuery = query)
  }

  // Law docs
  fun openLaw(law: LawDoc) {
    _uiState.value = _uiState.value.copy(selectedLaw = law)
  }

  fun closeLaw() {
    _uiState.value = _uiState.value.copy(selectedLaw = null)
  }

  // Dialogs
  fun setPartyNotebookDialog(show: Boolean) {
    _uiState.value = _uiState.value.copy(showPartyNotebookDialog = show)
  }

  fun setCommanderReportDialog(show: Boolean) {
    _uiState.value = _uiState.value.copy(showCommanderReportDialog = show)
  }

  fun setDailyQuoteDialog(show: Boolean) {
    _uiState.value = _uiState.value.copy(showDailyQuoteDialog = show)
  }

  fun setAddNoteDialog(show: Boolean) {
    _uiState.value = _uiState.value.copy(showAddNoteDialog = show)
  }

  fun savePersonalNote(lessonId: String, title: String, content: String, category: String) {
    viewModelScope.launch {
      repository.addNote(lessonId, title, content, category)
      setAddNoteDialog(false)
    }
  }

  fun deletePersonalNote(id: Long) {
    viewModelScope.launch {
      repository.deleteNote(id)
    }
  }
}
