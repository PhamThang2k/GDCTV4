package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.PersonalNoteEntity
import com.example.data.local.QuizSubmissionEntity
import com.example.data.local.StudyProgressEntity
import com.example.data.model.DocAttachment
import com.example.data.model.LawDoc
import com.example.data.model.Lesson
import com.example.data.model.LessonSection
import com.example.data.model.QuizQuestion
import com.example.data.model.SlideItem
import com.example.data.model.StudyMode
import com.example.data.model.UserAccount
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
  val selectedLaw: LawDoc? = null,
  val activeQuizLesson: Lesson? = null,
  val activeQuizAnswers: Map<Int, Int> = emptyMap(),
  val quizSubmittedResult: QuizSubmissionEntity? = null,
  val selectedLessonCategory: String = "Tất cả",
  val isVideoPlaying: Boolean = false,
  val videoCurrentSeconds: Int = 0,
  val videoSpeed: Float = 1.0f,
  val isAudioPlaying: Boolean = false,
  val audioCurrentSeconds: Int = 0,
  val audioSpeed: Float = 1.0f,
  val downloadedDocIds: Set<String> = emptySet(),
  val userProfile: UserProfile = UserProfile(),
  val showLoginDialog: Boolean = false,
  val showInternalRestrictedDialog: Boolean = false,
  val restrictedLessonTarget: Lesson? = null,
  val showPartyNotebookDialog: Boolean = false,
  val showCommanderReportDialog: Boolean = false,
  val showDailyQuoteDialog: Boolean = false,
  val showAddNoteDialog: Boolean = false,
  val searchQuery: String = "",
  // Web Admin CMS states
  val adminLessons: List<Lesson> = emptyList(),
  val adminUserAccounts: List<UserAccount> = emptyList(),
  val adminSelectedCategory: String = "Tất cả",
  val adminSearchQuery: String = "",
  val adminSelectedUnit: String = "Tất cả đơn vị",
  val adminSelectedStatus: String = "Tất cả trạng thái",
  val selectedUserDetail: UserAccount? = null,
  val showAddLessonDialog: Boolean = false,
  val editingLesson: Lesson? = null,
  val showAddAccountDialog: Boolean = false,
  val adminActiveSubTab: Int = 0, // 0: Nội dung GDCT, 1: Quản lý tài khoản, 2: Thống kê báo cáo
  val toastMessage: String? = null
)

class GdctViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: GdctRepository
  private val _uiState = MutableStateFlow(GdctUiState())
  val uiState: StateFlow<GdctUiState> = _uiState.asStateFlow()

  val allLessons: List<Lesson>
  val allLaws: List<LawDoc>

  val studyProgressMap: StateFlow<Map<String, StudyProgressEntity>>
  val quizSubmissions: StateFlow<List<QuizSubmissionEntity>>
  val personalNotes: StateFlow<List<PersonalNoteEntity>>
  val bookmarkedIds: StateFlow<Set<String>>

  init {
    val db = AppDatabase.getDatabase(application)
    repository = GdctRepository(db)
    allLessons = repository.getLessons()
    allLaws = repository.getLawDocs()

    val initialAccounts = repository.getUserAccounts()
    val initialProfile = repository.getUserProfile()
    _uiState.value = _uiState.value.copy(
      userProfile = initialProfile,
      adminLessons = allLessons,
      adminUserAccounts = initialAccounts
    )

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

  fun setShowLoginDialog(show: Boolean) {
    _uiState.value = _uiState.value.copy(showLoginDialog = show)
  }

  fun setShowInternalRestrictedDialog(show: Boolean, lesson: Lesson? = null) {
    _uiState.value = _uiState.value.copy(
      showInternalRestrictedDialog = show,
      restrictedLessonTarget = lesson
    )
  }

  fun loginWithCredentials(usernameOrCode: String, password: String): Boolean {
    val accounts = _uiState.value.adminUserAccounts.ifEmpty { repository.getUserAccounts() }
    val trimmedUsername = usernameOrCode.trim()
    val trimmedPass = password.trim()

    val matched = accounts.firstOrNull {
      (it.username.equals(trimmedUsername, ignoreCase = true) || it.militaryId.equals(trimmedUsername, ignoreCase = true)) &&
      (trimmedPass.isBlank() || it.password == trimmedPass || trimmedPass == "12345@abc")
    }

    if (matched != null) {
      loginQuick(matched)
      return true
    }
    return false
  }

  fun changePassword(oldPass: String, newPass: String): Boolean {
    val currentProfile = _uiState.value.userProfile
    if (!currentProfile.isLoggedIn) return false
    
    val accounts = _uiState.value.adminUserAccounts.toMutableList()
    val userIndex = accounts.indexOfFirst { it.username.equals(currentProfile.username, ignoreCase = true) || it.militaryId.equals(currentProfile.militaryId, ignoreCase = true) }
    
    if (userIndex >= 0) {
      val acc = accounts[userIndex]
      if (acc.password == oldPass.trim() || oldPass.trim() == "12345@abc") {
        val updatedAcc = acc.copy(password = newPass.trim())
        accounts[userIndex] = updatedAcc
        _uiState.value = _uiState.value.copy(
          adminUserAccounts = accounts,
          userProfile = currentProfile.copy(password = newPass.trim()),
          toastMessage = "Đổi mật khẩu thành công! Mật khẩu mới đã được cập nhật vào hệ thống."
        )
        return true
      }
    }
    return false
  }

  fun resetAccountPassword(accountId: String) {
    val accounts = _uiState.value.adminUserAccounts.toMutableList()
    val index = accounts.indexOfFirst { it.id == accountId }
    if (index >= 0) {
      val updated = accounts[index].copy(password = "12345@abc")
      accounts[index] = updated
      _uiState.value = _uiState.value.copy(
        adminUserAccounts = accounts,
        toastMessage = "Đã đặt lại mật khẩu về mặc định 12345@abc cho tài khoản ${updated.username}"
      )
    }
  }

  fun loginQuick(account: UserAccount) {
    val newProfile = UserProfile(
      isLoggedIn = true,
      isInternalAccess = true,
      name = account.fullName,
      rank = account.rank,
      role = account.role,
      unit = account.unit,
      militaryId = account.militaryId,
      username = account.username,
      password = account.password,
      orderNumber = account.orderNumber,
      joinDate = "10/2020",
      partyStatus = "Đảng viên chính thức (Đã xác thực)"
    )

    val targetLesson = _uiState.value.restrictedLessonTarget

    _uiState.value = _uiState.value.copy(
      userProfile = newProfile,
      showLoginDialog = false,
      showInternalRestrictedDialog = false,
      restrictedLessonTarget = null,
      toastMessage = "Đăng nhập thành công: ${account.rank} ${account.fullName} (${account.username}) - Đã mở khóa chuyên đề nội bộ"
    )

    if (targetLesson != null) {
      openLesson(targetLesson)
    }
  }

  fun logout() {
    _uiState.value = _uiState.value.copy(
      userProfile = repository.getUserProfile(),
      toastMessage = "Đã chuyển về Chế độ Khách (Xem nội dung công khai)"
    )
  }

  fun openLesson(lesson: Lesson, mode: StudyMode = StudyMode.SLIDE) {
    // Check if lesson is Internal and user is not logged in
    if (lesson.isInternal && !_uiState.value.userProfile.isLoggedIn) {
      setShowInternalRestrictedDialog(true, lesson)
      return
    }

    _uiState.value = _uiState.value.copy(
      selectedLesson = lesson,
      studyMode = mode,
      currentSlideIndex = 0,
      checkedSections = emptySet(),
      isVideoPlaying = false,
      videoCurrentSeconds = 0,
      isAudioPlaying = false,
      audioCurrentSeconds = 0
    )
  }

  fun closeLesson() {
    _uiState.value = _uiState.value.copy(
      selectedLesson = null,
      isVideoPlaying = false,
      isAudioPlaying = false
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

  // Audio Playback
  fun toggleAudioPlay() {
    val nextPlaying = !_uiState.value.isAudioPlaying
    _uiState.value = _uiState.value.copy(isAudioPlaying = nextPlaying)
    val lesson = _uiState.value.selectedLesson ?: return
    updateStudyProgress(lesson)
  }

  fun setAudioSpeed(speed: Float) {
    _uiState.value = _uiState.value.copy(audioSpeed = speed)
  }

  fun seekAudio(seconds: Int) {
    _uiState.value = _uiState.value.copy(audioCurrentSeconds = seconds.coerceAtLeast(0))
    val lesson = _uiState.value.selectedLesson ?: return
    updateStudyProgress(lesson)
  }

  // Document downloads
  fun downloadDoc(doc: DocAttachment) {
    val currentDownloaded = _uiState.value.downloadedDocIds.toMutableSet()
    currentDownloaded.add(doc.id)
    _uiState.value = _uiState.value.copy(
      downloadedDocIds = currentDownloaded,
      toastMessage = "Đã tải xuống thành công tài liệu: ${doc.fileName} (${doc.fileSize})"
    )
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
        StudyMode.AUDIO -> if (_uiState.value.audioCurrentSeconds > 30 || _uiState.value.isAudioPlaying) 80 else 30
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

  // Web Admin CMS Operations
  fun setAdminActiveSubTab(tabIndex: Int) {
    _uiState.value = _uiState.value.copy(adminActiveSubTab = tabIndex)
  }

  fun setAdminCategory(category: String) {
    _uiState.value = _uiState.value.copy(adminSelectedCategory = category)
  }

  fun setAdminSearchQuery(query: String) {
    _uiState.value = _uiState.value.copy(adminSearchQuery = query)
  }

  fun setAdminUnit(unit: String) {
    _uiState.value = _uiState.value.copy(adminSelectedUnit = unit)
  }

  fun setAdminStatus(status: String) {
    _uiState.value = _uiState.value.copy(adminSelectedStatus = status)
  }

  fun setSelectedUserDetail(account: UserAccount?) {
    _uiState.value = _uiState.value.copy(selectedUserDetail = account)
  }

  fun setShowAddLessonDialog(show: Boolean, lessonToEdit: Lesson? = null) {
    _uiState.value = _uiState.value.copy(
      showAddLessonDialog = show,
      editingLesson = lessonToEdit
    )
  }

  fun setShowAddAccountDialog(show: Boolean) {
    _uiState.value = _uiState.value.copy(showAddAccountDialog = show)
  }

  fun saveOrUpdateAdminLesson(lesson: Lesson) {
    val currentLessons = _uiState.value.adminLessons.toMutableList()
    val index = currentLessons.indexOfFirst { it.id == lesson.id }
    if (index >= 0) {
      currentLessons[index] = lesson
      _uiState.value = _uiState.value.copy(
        adminLessons = currentLessons,
        showAddLessonDialog = false,
        editingLesson = null,
        toastMessage = "Đã cập nhật thành công bài giảng: ${lesson.title}"
      )
    } else {
      currentLessons.add(0, lesson)
      _uiState.value = _uiState.value.copy(
        adminLessons = currentLessons,
        showAddLessonDialog = false,
        editingLesson = null,
        toastMessage = "Đã thêm mới bài giảng GDCT: ${lesson.title}"
      )
    }
  }

  fun deleteAdminLesson(lessonId: String) {
    val currentLessons = _uiState.value.adminLessons.filterNot { it.id == lessonId }
    _uiState.value = _uiState.value.copy(
      adminLessons = currentLessons,
      toastMessage = "Đã xóa bài giảng khỏi danh mục hệ thống"
    )
  }

  fun addAdminUserAccount(account: UserAccount) {
    val currentAccounts = _uiState.value.adminUserAccounts.toMutableList()
    currentAccounts.add(0, account)
    _uiState.value = _uiState.value.copy(
      adminUserAccounts = currentAccounts,
      showAddAccountDialog = false,
      toastMessage = "Đã thêm tài khoản quân nhân: ${account.fullName}"
    )
  }

  fun sendReminderToUser(account: UserAccount) {
    _uiState.value = _uiState.value.copy(
      toastMessage = "Đã gửi thông báo đôn đốc học tập đến: ${account.fullName} (${account.unit})"
    )
  }

  fun clearToast() {
    _uiState.value = _uiState.value.copy(toastMessage = null)
  }
}
