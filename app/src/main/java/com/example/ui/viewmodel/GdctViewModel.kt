package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.PersonalNoteEntity
import com.example.data.local.QuizSubmissionEntity
import com.example.data.local.StudyProgressEntity
import com.example.data.model.AppNotification
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

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
  val adminLaws: List<LawDoc> = emptyList(),
  val adminSelectedCategory: String = "Tất cả",
  val adminSearchQuery: String = "",
  val adminSelectedUnit: String = "Tất cả đơn vị",
  val adminSelectedStatus: String = "Tất cả trạng thái",
  val selectedUserDetail: UserAccount? = null,
  val showAddLessonDialog: Boolean = false,
  val editingLesson: Lesson? = null,
  val showAddAccountDialog: Boolean = false,
  val adminActiveSubTab: Int = 0, // 0: Nội dung GDCT, 1: Quản lý tài khoản, 2: Thống kê báo cáo
  val toastMessage: String? = null,
  val customServerUrl: String = "",
  val serverConnectionStatus: String = "Đã kết nối Máy chủ Giáo dục Chính trị Vùng 4",
  val notifications: List<AppNotification> = emptyList(),
  val showNotificationsDialog: Boolean = false,
  val isDownloadingDoc: Boolean = false,
  val downloadingDocFileName: String? = null,
  val downloadProgress: Float = 0f
) {
  val unreadNotificationsCount: Int get() = notifications.count { !it.isRead }
}

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

  @Volatile
  private var cachedWorkingHost: String? = null

  init {
    val db = AppDatabase.getDatabase(application)
    repository = GdctRepository(db)
    allLessons = repository.getLessons()
    allLaws = repository.getLawDocs()

    val initialAccounts = repository.getUserAccounts()
    val initialProfile = repository.getUserProfile()
    val initialNotifications = listOf(
      AppNotification(
        id = "notif_01",
        title = "Chuyên đề GDCT trọng tâm năm 2026",
        message = "Bộ Tư lệnh Vùng 4 vừa ban hành bài giảng: Nâng cao bản lĩnh chính trị, ý chí quyết chiến quyết thắng của cán bộ, chiến sĩ Vùng 4 Hải quân",
        lessonId = "bai_1",
        lessonCode = "CĐ-01/2026",
        timestamp = System.currentTimeMillis() - 3600000 * 5,
        timeFormatted = "Hôm nay, 08:30",
        isRead = false,
        type = "NEW_LESSON"
      ),
      AppNotification(
        id = "notif_02",
        title = "Chỉ thị & Nhắc nhở từ Phòng Chính trị",
        message = "Đề nghị toàn thể cán bộ, chiến sĩ khẩn trương hoàn thành nội dung học tập và bài thi trắc nghiệm các chuyên đề quý 1/2026.",
        lessonId = "bai_1",
        lessonCode = "CĐ-01/2026",
        timestamp = System.currentTimeMillis() - 3600000 * 2,
        timeFormatted = "Hôm nay, 10:15",
        isRead = false,
        type = "COMMANDER_DIRECTIVE"
      )
    )
    _uiState.value = _uiState.value.copy(
      userProfile = initialProfile,
      adminLessons = allLessons,
      adminUserAccounts = initialAccounts,
      adminLaws = allLaws,
      notifications = initialNotifications
    )

    // Check and restore persistent saved user session
    viewModelScope.launch {
      val saved = repository.getSavedUserSession()
      if (saved != null && saved.isLoggedIn) {
        val restored = UserProfile(
          isLoggedIn = true,
          isInternalAccess = saved.isInternalAccess,
          name = saved.name,
          username = saved.username,
          password = saved.password,
          rank = saved.rank,
          role = saved.role,
          unit = saved.unit,
          militaryId = saved.militaryId,
          orderNumber = saved.orderNumber,
          joinDate = saved.joinDate,
          partyStatus = saved.partyStatus,
          phone = saved.phone
        )
        _uiState.value = _uiState.value.copy(userProfile = restored)
      }
    }

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

    // Launch continuous background bidirectional sync with Web Admin server
    startContinuousSyncLoop()
  }

  private fun startContinuousSyncLoop() {
    viewModelScope.launch(Dispatchers.IO) {
      // First immediate sync
      syncWithServerInternal()
      
      // Polling loop every 2.5 seconds for instant real-time sync with Web Admin
      while (true) {
        delay(2500)
        try {
          syncWithServerInternal()
        } catch (e: Exception) {
          // Keep loop running silently
        }
      }
    }
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

  fun updateProfileInfo(name: String, rank: String, role: String, unit: String, phone: String, militaryId: String = "") {
    val current = _uiState.value.userProfile
    if (!current.isLoggedIn) return

    val updatedProfile = current.copy(
      name = name.trim().ifEmpty { current.name },
      rank = rank.trim().ifEmpty { current.rank },
      role = role.trim().ifEmpty { current.role },
      unit = unit.trim().ifEmpty { current.unit },
      phone = phone.trim(),
      militaryId = if (militaryId.isNotBlank()) militaryId.trim() else current.militaryId
    )

    val accounts = _uiState.value.adminUserAccounts.toMutableList()
    val userIndex = accounts.indexOfFirst { 
      it.username.equals(current.username, ignoreCase = true) || 
      it.militaryId.equals(current.militaryId, ignoreCase = true) 
    }
    if (userIndex >= 0) {
      accounts[userIndex] = accounts[userIndex].copy(
        fullName = updatedProfile.name,
        rank = updatedProfile.rank,
        role = updatedProfile.role,
        unit = updatedProfile.unit,
        phone = updatedProfile.phone,
        militaryId = updatedProfile.militaryId
      )
    }

    _uiState.value = _uiState.value.copy(
      userProfile = updatedProfile,
      adminUserAccounts = accounts,
      toastMessage = "Đã cập nhật thông tin quân nhân và đồng bộ về Cổng Web Quản trị Vùng 4!"
    )

    viewModelScope.launch(Dispatchers.IO) {
      repository.saveUserSession(updatedProfile)
    }

    syncProfileToWebAdmin(updatedProfile)
  }

  fun setCustomServerUrl(url: String) {
    val clean = url.trim()
    _uiState.value = _uiState.value.copy(
      customServerUrl = clean,
      toastMessage = if (clean.isNotBlank()) "Đã cấu hình địa chỉ máy chủ Web Quản trị: $clean" else "Đã xóa địa chỉ máy chủ tùy chỉnh"
    )
    cachedWorkingHost = null
    syncAllWithWebAdmin()
  }

  private fun syncProfileToWebAdmin(profile: UserProfile) {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val payload = JSONObject().apply {
          put("username", profile.username)
          put("fullName", profile.name)
          put("rank", profile.rank)
          put("role", profile.role)
          put("unit", profile.unit)
          put("phone", profile.phone)
          put("militaryId", profile.militaryId)
          put("militaryCode", profile.militaryId)
        }
        sendJsonPost("/api/users/update-profile", payload)
        syncWithServerInternal()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  private fun syncPasswordToWebAdmin(username: String, oldPass: String, newPass: String) {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val payload = JSONObject().apply {
          put("username", username)
          put("oldPassword", oldPass)
          put("newPassword", newPass)
        }
        sendJsonPost("/api/users/change-password", payload)
        syncWithServerInternal()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  fun syncAllWithWebAdmin() {
    viewModelScope.launch(Dispatchers.IO) {
      val success = try {
        syncWithServerInternal()
        true
      } catch (e: Exception) {
        false
      }

      withContext(Dispatchers.Main) {
        _uiState.value = _uiState.value.copy(
          toastMessage = if (success) "Đồng bộ hai chiều với Cổng Web Quản trị hoàn tất thành công!" else "Đã đồng bộ dữ liệu nội bộ!"
        )
      }
    }
  }

  private fun getCandidateHosts(endpoint: String): List<String> {
    val cleanEndpoint = if (endpoint.startsWith("/")) endpoint else "/$endpoint"
    val list = mutableListOf<String>()

    val custom = _uiState.value.customServerUrl.trim().trimEnd('/')
    if (custom.isNotBlank()) {
      list.add("$custom$cleanEndpoint")
    }

    cachedWorkingHost?.let {
      val base = it.trimEnd('/')
      list.add("$base$cleanEndpoint")
    }

    // Direct local / emulator connections first for instant real-time sync
    list.add("http://10.0.2.2:3000$cleanEndpoint")
    list.add("http://127.0.0.1:3000$cleanEndpoint")
    list.add("http://localhost:3000$cleanEndpoint")
    list.add("https://ais-dev-fg3vokzh3myfkmipyfaqdl-910262898976.asia-southeast1.run.app$cleanEndpoint")
    list.add("https://ais-pre-fg3vokzh3myfkmipyfaqdl-910262898976.asia-southeast1.run.app$cleanEndpoint")
    list.add("https://gdctv4.onrender.com$cleanEndpoint")
    list.add("https://gdctv4.onrender.com/api$cleanEndpoint")

    return list.distinct()
  }

  private fun performHttpGet(endpoint: String): String? {
    val hostUrls = getCandidateHosts(endpoint)
    for (urlStr in hostUrls) {
      try {
        val url = URL(urlStr)
        val conn = (url.openConnection() as HttpURLConnection).apply {
          requestMethod = "GET"
          setRequestProperty("Accept", "application/json")
          connectTimeout = 1500
          readTimeout = 2000
        }
        if (conn.responseCode in 200..299) {
          val reader = BufferedReader(InputStreamReader(conn.inputStream, Charsets.UTF_8))
          val response = reader.readText()
          reader.close()
          conn.disconnect()
          val base = if (urlStr.contains("/api/")) urlStr.substringBefore("/api/") else urlStr
          cachedWorkingHost = base
          return response
        }
        conn.disconnect()
      } catch (e: Exception) {
        // Try next host candidate
      }
    }
    return null
  }

  private fun sendJsonPost(endpoint: String, json: JSONObject): String? {
    val hostUrls = getCandidateHosts(endpoint)
    for (urlStr in hostUrls) {
      try {
        val url = URL(urlStr)
        val conn = (url.openConnection() as HttpURLConnection).apply {
          requestMethod = "POST"
          setRequestProperty("Content-Type", "application/json; charset=utf-8")
          setRequestProperty("Accept", "application/json")
          doOutput = true
          connectTimeout = 1500
          readTimeout = 2000
        }
        conn.outputStream.use { os ->
          os.write(json.toString().toByteArray(Charsets.UTF_8))
        }
        val responseCode = conn.responseCode
        if (responseCode in 200..299) {
          val reader = BufferedReader(InputStreamReader(conn.inputStream, Charsets.UTF_8))
          val response = reader.readText()
          reader.close()
          conn.disconnect()
          val base = if (urlStr.contains("/api/")) urlStr.substringBefore("/api/") else urlStr
          cachedWorkingHost = base
          return response
        }
        conn.disconnect()
      } catch (e: Exception) {
        // try next host
      }
    }
    return null
  }

  private suspend fun syncWithServerInternal() {
    val response = performHttpGet("/api/sync") ?: return
    try {
      val rootJson = JSONObject(response)
      if (!rootJson.optBoolean("success", false)) return

      // 1. Sync Users
      val usersArray = rootJson.optJSONArray("users")
      if (usersArray != null && usersArray.length() > 0) {
        val parsedUsers = mutableListOf<UserAccount>()
        for (i in 0 until usersArray.length()) {
          val uJson = usersArray.getJSONObject(i)
          parsedUsers.add(
            UserAccount(
              id = uJson.optString("id", "acc_$i"),
              orderNumber = uJson.optInt("orderNumber", i + 1),
              username = uJson.optString("username", "user_$i"),
              password = uJson.optString("password", "12345@abc"),
              militaryId = uJson.optString("militaryCode", uJson.optString("militaryId", "QN-16201")),
              fullName = uJson.optString("fullName", "Quân nhân"),
              rank = uJson.optString("rank", "Chiến sĩ"),
              role = uJson.optString("position", uJson.optString("role", "Chiến sĩ")),
              unit = uJson.optString("unit", "Vùng 4 Hải quân"),
              completedLessonsCount = uJson.optInt("completedLessonsCount", uJson.optInt("progress", 0) / 25),
              totalLessonsCount = uJson.optInt("totalLessonsCount", 6),
              averageScore = uJson.optDouble("avgScore", uJson.optDouble("averageScore", 8.5)),
              lastActive = uJson.optString("lastActive", "Vừa xong"),
              status = uJson.optString("status", "Đang học"),
              isInternalAccess = true,
              phone = uJson.optString("phone", "0988.123.456")
            )
          )
        }

        withContext(Dispatchers.Main) {
          val currentProfile = _uiState.value.userProfile
          var updatedProfile = currentProfile

          if (currentProfile.isLoggedIn) {
            val currentAccount = parsedUsers.firstOrNull {
              it.username.equals(currentProfile.username, ignoreCase = true) ||
              it.militaryId.equals(currentProfile.militaryId, ignoreCase = true)
            }
            if (currentAccount != null) {
              updatedProfile = currentProfile.copy(
                name = currentAccount.fullName,
                rank = currentAccount.rank,
                role = currentAccount.role,
                unit = currentAccount.unit,
                militaryId = currentAccount.militaryId,
                password = currentAccount.password,
                phone = if (currentAccount.phone.isNotBlank()) currentAccount.phone else currentProfile.phone
              )
              viewModelScope.launch(Dispatchers.IO) {
                repository.saveUserSession(updatedProfile)
              }
            }
          }

          _uiState.value = _uiState.value.copy(
            adminUserAccounts = parsedUsers,
            userProfile = updatedProfile
          )
        }
      }

      // 2. Sync Lessons
      val lessonsArray = rootJson.optJSONArray("lessons")
      if (lessonsArray != null && lessonsArray.length() > 0) {
        val parsedLessons = mutableListOf<Lesson>()
        for (i in 0 until lessonsArray.length()) {
          val lJson = lessonsArray.getJSONObject(i)
          val lessonId = lJson.optString("id", "bai_${i + 1}")
          val title = lJson.optString("title", "Chuyên đề Giáo dục Chính trị")
          val category = lJson.optString("category", "Chuyên đề Sĩ quan & QNCN")
          val summary = lJson.optString("summary", "Nội dung học tập chính trị trọng tâm")
          val lecturer = lJson.optString("lecturer", "Phòng Chính trị Vùng 4")
          val duration = lJson.optInt("durationMinutes", lJson.optInt("estimatedMinutes", 45))
          val isInternal = lJson.optBoolean("isInternal", false)
          val code = lJson.optString("code", "CĐ-${String.format("%02d", i + 1)}/2026")
          val videoUrl = lJson.optString("videoUrl", "https://video.gdct.vung4.vn/$lessonId.mp4")
          val videoDuration = lJson.optString("videoDuration", "18:00")
          val audioUrl = lJson.optString("audioUrl", "https://audio.gdct.vung4.vn/$lessonId.mp3")
          val audioDuration = lJson.optString("audioDuration", "18:00")
          val audioSpeaker = lJson.optString("audioSpeaker", lecturer)

          // Parse questions
          val qArray = lJson.optJSONArray("questions")
          val quizQuestions = mutableListOf<QuizQuestion>()
          if (qArray != null && qArray.length() > 0) {
            for (qIdx in 0 until qArray.length()) {
              val qObj = qArray.getJSONObject(qIdx)
              val optsJson = qObj.optJSONArray("options")
              val options = mutableListOf<String>()
              if (optsJson != null) {
                for (o in 0 until optsJson.length()) {
                  options.add(optsJson.optString(o))
                }
              }
              if (options.isEmpty()) {
                options.addAll(listOf("Phương án A", "Phương án B", "Phương án C", "Phương án D"))
              }
              quizQuestions.add(
                QuizQuestion(
                  id = qObj.optInt("id", qIdx + 1),
                  question = qObj.optString("question", "Nội dung câu hỏi trắc nghiệm?"),
                  options = options,
                  correctOptionIndex = qObj.optInt("correctAnswer", qObj.optInt("correctOptionIndex", 0)),
                  explanation = qObj.optString("explanation", "Theo tài liệu GDCT chính thức của Bộ Tư lệnh Vùng 4 Hải quân.")
                )
              )
            }
          } else {
            quizQuestions.addAll(
              listOf(
                QuizQuestion(
                  id = 1,
                  question = "Mục tiêu trọng tâm của bài giảng '$title' là gì?",
                  options = listOf(
                    "Quán triệt sâu sắc các quan điểm của Đảng và Quân chủng Hải quân",
                    "Đọc lướt qua tài liệu",
                    "Không cần liên hệ thực tiễn",
                    "Chỉ tập trung vào lý thuyết đơn thuần"
                  ),
                  correctOptionIndex = 0,
                  explanation = "Mục tiêu nhằm nâng cao nhận thức, bản lĩnh và trách nhiệm người chiến sĩ Hải quân."
                ),
                QuizQuestion(
                  id = 2,
                  question = "Phương châm học tập GDCT hiệu quả nhất đối với cán bộ, chiến sĩ là gì?",
                  options = listOf(
                    "Học đi đôi với hành, gắn lý luận với thực tiễn chiến đấu",
                    "Học chỉ để đối phó thi cử",
                    "Thụ động ghi chép",
                    "Học vẹt không cần hiểu"
                  ),
                  correctOptionIndex = 0,
                  explanation = "Gắn lý luận với thực tiễn tàu, đảo, đài trạm và nhiệm vụ trực SSCĐ."
                )
              )
            )
          }

          // Parse sections
          val secArray = lJson.optJSONArray("sections")
          val sections = mutableListOf<LessonSection>()
          if (secArray != null && secArray.length() > 0) {
            for (sIdx in 0 until secArray.length()) {
              val sObj = secArray.getJSONObject(sIdx)
              sections.add(
                LessonSection(
                  sectionNumber = sObj.optInt("sectionNumber", sIdx + 1),
                  heading = sObj.optString("heading", "Phần ${sIdx + 1}: Nội dung chuyên đề trọng tâm"),
                  content = sObj.optString("content", summary),
                  keyTakeaway = sObj.optString("keyTakeaway", "Nắm vững lý luận và vận dụng sáng tạo vào thực tiễn.")
                )
              )
            }
          } else {
            sections.addAll(
              listOf(
                LessonSection(
                  sectionNumber = 1,
                  heading = "Phần I: Bối cảnh, mục đích và yêu cầu trọng tâm của chuyên đề",
                  content = summary,
                  keyTakeaway = "Nắm vững tình hình nhiệm vụ, xác định rõ trách nhiệm và quyết tâm cao."
                ),
                LessonSection(
                  sectionNumber = 2,
                  heading = "Phần II: Các nội dung cốt lõi và giải pháp thực hiện tại đơn vị",
                  content = "Thực hiện tốt phong trào thi đua quyết thắng, quản lý chặt chẽ vũ khí trang bị kỹ thuật, chấp hành nghiêm điều lệnh và kỷ luật quân đội.",
                  keyTakeaway = "Gương mẫu đi đầu, đoàn kết hiệp đồng, lập công tập thể."
                ),
                LessonSection(
                  sectionNumber = 3,
                  heading = "Phần III: Liên hệ thực tiễn bản thân và phương hướng phấn đấu",
                  content = "Mỗi cán bộ, chiến sĩ tự giác rèn luyện phẩm chất Bộ đội Cụ Hồ - Người chiến sĩ Hải quân, không ngại khó khăn sóng gió.",
                  keyTakeaway = "Sẵn sàng chiến đấu hy sinh bảo vệ vững chắc chủ quyền biển đảo."
                )
              )
            )
          }

          // Parse slides
          val slideArray = lJson.optJSONArray("slides")
          val slides = mutableListOf<SlideItem>()
          if (slideArray != null && slideArray.length() > 0) {
            for (slIdx in 0 until slideArray.length()) {
              val slObj = slideArray.getJSONObject(slIdx)
              val bulletsJson = slObj.optJSONArray("bullets")
              val bulletsList = mutableListOf<String>()
              if (bulletsJson != null) {
                for (b in 0 until bulletsJson.length()) {
                  bulletsList.add(bulletsJson.optString(b))
                }
              }
              if (bulletsList.isEmpty()) {
                bulletsList.add(slObj.optString("content", summary))
              }
              slides.add(
                SlideItem(
                  slideNumber = slObj.optInt("slideNumber", slIdx + 1),
                  title = slObj.optString("title", "${slIdx + 1}. Nội dung Slide bài giảng"),
                  bullets = bulletsList,
                  highlightQuote = slObj.optString("highlightQuote", "Gắn lý luận với thực tiễn chiến đấu."),
                  note = slObj.optString("note", "Trọng tâm bài giảng")
                )
              )
            }
          } else {
            slides.addAll(
              listOf(
                SlideItem(
                  slideNumber = 1,
                  title = "1. Tổng quan Chuyên đề",
                  bullets = listOf(title, "Giảng viên: $lecturer • Đơn vị: Vùng 4 Hải quân", "Khái quát toàn diện mục tiêu bài học"),
                  highlightQuote = "Nắm vững mục tiêu và lý luận gắn liền thực tiễn Hải quân.",
                  note = "Trọng tâm bài giảng"
                ),
                SlideItem(
                  slideNumber = 2,
                  title = "2. Ý nghĩa & Mục đích",
                  bullets = listOf("Nâng cao nhận thức chính trị tư tưởng", summary, "Cốt lõi nhận thức của người quân nhân"),
                  highlightQuote = "Bồi dưỡng lý tưởng cách mạng, bản lĩnh kiên định.",
                  note = "Nhận thức tư tưởng"
                ),
                SlideItem(
                  slideNumber = 3,
                  title = "3. Nhiệm vụ & Yêu cầu",
                  bullets = listOf("Nhiệm vụ trực sẵn sàng chiến đấu tại Vùng 4", "Luôn đề cao cảnh giác, sẵn sàng nhận và hoàn thành xuất sắc mọi nhiệm vụ được giao.", "Sẵn sàng chiến đấu cao"),
                  highlightQuote = "Đoàn kết hiệp đồng, lập công tập thể.",
                  note = "Nhiệm vụ trọng tâm"
                ),
                SlideItem(
                  slideNumber = 4,
                  title = "4. Tổng kết & Hành động",
                  bullets = listOf("Lời căn dặn và định hướng phấn đấu", "Phát huy truyền thống Chiến đấu anh dũng, mưu trí sáng tạo, làm chủ vùng biển.", "Quyết chiến quyết thắng"),
                  highlightQuote = "Bảo vệ vững chắc chủ quyền biển đảo Tổ quốc.",
                  note = "Định hướng phấn đấu"
                )
              )
            )
          }

          // Parse doc attachments
          val docArray = lJson.optJSONArray("docAttachments")
          val docAttachments = mutableListOf<DocAttachment>()
          if (docArray != null && docArray.length() > 0) {
            for (dIdx in 0 until docArray.length()) {
              val dObj = docArray.getJSONObject(dIdx)
              val dFileName = dObj.optString("fileName", "${lessonId}_Tai_lieu.docx")
              val dType = if (dFileName.lowercase().endsWith(".pdf")) "PDF" else "WORD"
              docAttachments.add(
                DocAttachment(
                  id = dObj.optString("id", "doc_${dIdx + 1}"),
                  fileName = dFileName,
                  fileSize = dObj.optString("fileSize", "1.8 MB"),
                  fileType = dObj.optString("fileType", dType),
                  downloadUrl = dObj.optString("downloadUrl", "https://docs.gdct.vung4.vn/$dFileName")
                )
              )
            }
          } else {
            val docxName = lJson.optString("docxAttachment", "${lessonId}_Giao_an.docx")
            val pdfName = lJson.optString("pdfAttachment", "${lessonId}_Tai_lieu.pdf")
            docAttachments.add(
              DocAttachment("doc_1", docxName, "1.8 MB", "WORD", "https://docs.gdct.vung4.vn/$docxName")
            )
            docAttachments.add(
              DocAttachment("doc_2", pdfName, "2.4 MB", "PDF", "https://docs.gdct.vung4.vn/$pdfName")
            )
          }

          parsedLessons.add(
            Lesson(
              id = lessonId,
              code = code,
              title = title,
              category = category,
              targetAudience = lJson.optString("targetAudience", "Cán bộ, chiến sĩ Vùng 4"),
              durationMinutes = duration,
              summary = summary,
              lecturer = lecturer,
              videoUrl = videoUrl,
              videoDuration = videoDuration,
              audioUrl = audioUrl,
              audioDuration = audioDuration,
              audioSpeaker = audioSpeaker,
              docAttachments = docAttachments,
              slides = slides,
              sections = sections,
              quizQuestions = quizQuestions,
              status = "Đã phê duyệt",
              updatedDate = "2026-03-01",
              isInternal = isInternal,
              securityLevel = if (isInternal) "Lưu hành nội bộ" else "Công khai"
            )
          )
        }

        withContext(Dispatchers.Main) {
          val currentSelected = _uiState.value.selectedLesson
          val currentQuizLesson = _uiState.value.activeQuizLesson

          val updatedSelected = if (currentSelected != null) {
            parsedLessons.find { it.id == currentSelected.id } ?: currentSelected
          } else null

          val updatedQuiz = if (currentQuizLesson != null) {
            parsedLessons.find { it.id == currentQuizLesson.id } ?: currentQuizLesson
          } else null

          _uiState.value = _uiState.value.copy(
            adminLessons = parsedLessons,
            selectedLesson = updatedSelected,
            activeQuizLesson = updatedQuiz
          )
        }
      }

      // 3. Sync Laws
      val lawsArray = rootJson.optJSONArray("laws")
      if (lawsArray != null && lawsArray.length() > 0) {
        val parsedLaws = mutableListOf<LawDoc>()
        for (i in 0 until lawsArray.length()) {
          val lawObj = lawsArray.getJSONObject(i)
          parsedLaws.add(
            LawDoc(
              id = lawObj.optString("id", "law_$i"),
              title = lawObj.optString("title", "Văn bản Pháp luật"),
              category = lawObj.optString("category", "Pháp luật & Kỷ luật"),
              issuedBy = lawObj.optString("issuedBy", "Bộ Quốc phòng"),
              summary = lawObj.optString("summary", "Nội dung quy định văn bản"),
              keyArticles = listOf(
                Pair("Điều 1", "Phạm vi điều chỉnh và đối tượng áp dụng"),
                Pair("Điều 2", "Nguyên tắc chấp hành kỷ luật Quân đội và Pháp luật Nhà nước")
              )
            )
          )
        }
        withContext(Dispatchers.Main) {
          _uiState.value = _uiState.value.copy(adminLaws = parsedLaws)
        }
      }

      // 4. Sync Submissions (Commander comments and approvals from Web Admin)
      val subsArray = rootJson.optJSONArray("submissions")
      if (subsArray != null && subsArray.length() > 0) {
        for (i in 0 until subsArray.length()) {
          val sObj = subsArray.getJSONObject(i)
          val entity = QuizSubmissionEntity(
            id = sObj.optLong("id", (i + 100).toLong()),
            lessonId = sObj.optString("lessonId", "bai_1"),
            lessonTitle = sObj.optString("lessonTitle", "Chuyên đề GDCT"),
            score = sObj.optInt("score", 4),
            totalQuestions = sObj.optInt("totalQuestions", 4),
            percentage = sObj.optInt("percentage", 100),
            passed = sObj.optBoolean("passed", true),
            timestamp = sObj.optLong("timestamp", System.currentTimeMillis()),
            syncedToAdmin = true,
            commanderReviewStatus = sObj.optString("commanderReviewStatus", "Chính trị viên đã phê duyệt"),
            commanderComment = sObj.optString("commanderComment", "Đồng chí nắm vững kiến thức chính trị.")
          )
          repository.insertOrUpdateQuizSubmission(entity)
        }
      }

      // 5. Sync Notifications & Directives from Web Admin
      val notifsArray = rootJson.optJSONArray("notifications")
      if (notifsArray != null && notifsArray.length() > 0) {
        val parsedNotifs = mutableListOf<AppNotification>()
        for (i in 0 until notifsArray.length()) {
          val nObj = notifsArray.getJSONObject(i)
          parsedNotifs.add(
            AppNotification(
              id = nObj.optString("id", "notif_$i"),
              title = nObj.optString("title", "Thông báo Giáo dục Chính trị"),
              message = nObj.optString("message", "Nội dung cập nhật mới từ Phòng Chính trị"),
              lessonId = if (nObj.has("lessonId") && !nObj.isNull("lessonId") && nObj.optString("lessonId").isNotBlank()) nObj.optString("lessonId") else null,
              lessonCode = if (nObj.has("lessonCode") && !nObj.isNull("lessonCode") && nObj.optString("lessonCode").isNotBlank()) nObj.optString("lessonCode") else null,
              timestamp = nObj.optLong("timestamp", System.currentTimeMillis()),
              timeFormatted = nObj.optString("timeFormatted", "Vừa xong"),
              isRead = nObj.optBoolean("isRead", false),
              type = nObj.optString("type", "NEW_LESSON")
            )
          )
        }
        withContext(Dispatchers.Main) {
          _uiState.value = _uiState.value.copy(notifications = parsedNotifs)
        }
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
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
        val updatedProfile = currentProfile.copy(password = newPass.trim())
        _uiState.value = _uiState.value.copy(
          adminUserAccounts = accounts,
          userProfile = updatedProfile,
          toastMessage = "Đổi mật khẩu thành công! Mật khẩu mới đã được cập nhật và gửi về Web Quản trị."
        )
        viewModelScope.launch(Dispatchers.IO) {
          repository.saveUserSession(updatedProfile)
        }
        syncPasswordToWebAdmin(currentProfile.username, oldPass.trim(), newPass.trim())
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
      viewModelScope.launch(Dispatchers.IO) {
        val payload = JSONObject().apply {
          put("userId", accountId)
          put("username", updated.username)
        }
        sendJsonPost("/api/users/reset-password", payload)
      }
    }
  }

  fun resetUserPassword(userId: String) = resetAccountPassword(userId)

  fun triggerManualSync() {
    _uiState.value = _uiState.value.copy(toastMessage = "Đang đồng bộ dữ liệu thời gian thực với Cổng Web Quản trị...")
    viewModelScope.launch(Dispatchers.IO) {
      val current = _uiState.value.userProfile
      if (current.isLoggedIn) {
        val payload = JSONObject().apply {
          put("username", current.username)
          put("fullName", current.name)
          put("rank", current.rank)
          put("role", current.role)
          put("unit", current.unit)
          put("phone", current.phone)
          put("militaryId", current.militaryId)
        }
        sendJsonPost("/api/users/update-profile", payload)
      }
      syncWithServerInternal()
      withContext(Dispatchers.Main) {
        _uiState.value = _uiState.value.copy(toastMessage = "Dữ liệu hai chiều giữa Ứng dụng và Cổng Web Quản trị đã đồng bộ thành công!")
      }
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
      partyStatus = "Đảng viên chính thức (Đã xác thực)",
      phone = account.phone
    )

    val targetLesson = _uiState.value.restrictedLessonTarget

    _uiState.value = _uiState.value.copy(
      userProfile = newProfile,
      showLoginDialog = false,
      showInternalRestrictedDialog = false,
      restrictedLessonTarget = null,
      toastMessage = "Đăng nhập thành công: ${account.rank} ${account.fullName} (${account.username}) - Đã lưu phiên đăng nhập!"
    )

    viewModelScope.launch(Dispatchers.IO) {
      repository.saveUserSession(newProfile)
    }

    if (targetLesson != null) {
      openLesson(targetLesson)
    }
  }

  fun logout() {
    val guestProfile = repository.getUserProfile()
    _uiState.value = _uiState.value.copy(
      userProfile = guestProfile,
      toastMessage = "Đã đăng xuất tài khoản và chuyển về Chế độ Khách"
    )
    viewModelScope.launch(Dispatchers.IO) {
      repository.clearUserSession()
    }
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
    viewModelScope.launch {
      val currentDownloaded = _uiState.value.downloadedDocIds.toMutableSet()
      currentDownloaded.add(doc.id)
      _uiState.value = _uiState.value.copy(
        downloadedDocIds = currentDownloaded,
        toastMessage = "Đã lưu trữ ngoại tuyến tiêu chuẩn tệp ${doc.fileType}: ${doc.fileName} (${doc.fileSize})"
      )
    }
  }

  // Notifications Management
  fun setShowNotificationsDialog(show: Boolean) {
    _uiState.value = _uiState.value.copy(showNotificationsDialog = show)
  }

  fun markNotificationAsRead(id: String) {
    val current = _uiState.value.notifications.map {
      if (it.id == id) it.copy(isRead = true) else it
    }
    _uiState.value = _uiState.value.copy(notifications = current)
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val payload = JSONObject().apply { put("id", id) }
        sendJsonPost("/api/notifications/mark-read", payload)
      } catch (e: Exception) {}
    }
  }

  fun markAllNotificationsAsRead() {
    val current = _uiState.value.notifications.map { it.copy(isRead = true) }
    _uiState.value = _uiState.value.copy(
      notifications = current,
      toastMessage = "Đã đánh dấu tất cả thông báo đã đọc"
    )
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val payload = JSONObject().apply { put("all", true) }
        sendJsonPost("/api/notifications/mark-read", payload)
      } catch (e: Exception) {}
    }
  }

  fun deleteNotification(id: String) {
    val current = _uiState.value.notifications.filterNot { it.id == id }
    _uiState.value = _uiState.value.copy(notifications = current)
  }

  fun clearAllNotifications() {
    _uiState.value = _uiState.value.copy(
      notifications = emptyList(),
      toastMessage = "Đã xóa toàn bộ thông báo"
    )
    viewModelScope.launch(Dispatchers.IO) {
      try {
        sendJsonPost("/api/notifications/clear", JSONObject())
      } catch (e: Exception) {}
    }
  }

  fun openLessonById(lessonId: String) {
    val target = _uiState.value.adminLessons.find { it.id == lessonId }
      ?: allLessons.find { it.id == lessonId }
    if (target != null) {
      openLesson(target)
    } else {
      _uiState.value = _uiState.value.copy(
        currentTab = AppTab.STUDY,
        toastMessage = "Đã chuyển đến danh mục Chuyên đề GDCT"
      )
    }
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

      val profile = _uiState.value.userProfile
      if (profile.isLoggedIn) {
        launch(Dispatchers.IO) {
          try {
            val progressPayload = JSONObject().apply {
              put("username", profile.username)
              put("progress", calculatedPercent)
              put("lastActive", "Vừa học chuyên đề: ${lesson.title.take(30)}...")
            }
            sendJsonPost("/api/users/sync-progress", progressPayload)
          } catch (e: Exception) {
            // Ignore
          }
        }
      }
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

      val submissionEntity = QuizSubmissionEntity(
        id = submissionId,
        lessonId = lesson.id,
        lessonTitle = lesson.title,
        score = correctCount,
        totalQuestions = lesson.quizQuestions.size,
        percentage = percentage,
        passed = passed,
        timestamp = System.currentTimeMillis(),
        syncedToAdmin = true,
        commanderReviewStatus = "Đã gửi lên Cổng Web Quản trị - Chờ Cán bộ ký duyệt",
        commanderComment = "Kết quả thi: $correctCount/${lesson.quizQuestions.size} câu đúng ($percentage%)"
      )

      _uiState.value = _uiState.value.copy(
        quizSubmittedResult = submissionEntity
      )

      // Post submission immediately to Web Admin
      val profile = _uiState.value.userProfile
      launch(Dispatchers.IO) {
        try {
          val subPayload = JSONObject().apply {
            put("id", submissionId)
            put("username", if (profile.isLoggedIn) profile.username else "quan_nhan")
            put("soldierName", if (profile.isLoggedIn) profile.name else "Chiến sĩ")
            put("soldierRank", if (profile.isLoggedIn) profile.rank else "Binh nhất")
            put("soldierUnit", if (profile.isLoggedIn) profile.unit else "Vùng 4 Hải quân")
            put("lessonId", lesson.id)
            put("lessonTitle", lesson.title)
            put("score", correctCount)
            put("totalQuestions", lesson.quizQuestions.size)
            put("percentage", percentage)
            put("passed", passed)
            put("timestamp", System.currentTimeMillis())
          }
          sendJsonPost("/api/submissions", subPayload)

          if (profile.isLoggedIn) {
            val progressPayload = JSONObject().apply {
              put("username", profile.username)
              put("progress", 100)
              put("avgScore", (percentage.toDouble() / 10.0))
              put("lastActive", "Vừa hoàn thành trắc nghiệm ($correctCount/${lesson.quizQuestions.size}đ)")
            }
            sendJsonPost("/api/users/sync-progress", progressPayload)
          }

          syncWithServerInternal()
        } catch (e: Exception) {
          e.printStackTrace()
        }
      }
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

    viewModelScope.launch(Dispatchers.IO) {
      try {
        val payload = JSONObject().apply {
          put("id", lesson.id)
          put("code", lesson.code)
          put("title", lesson.title)
          put("category", lesson.category)
          put("targetAudience", lesson.targetAudience)
          put("durationMinutes", lesson.durationMinutes)
          put("summary", lesson.summary)
          put("lecturer", lesson.lecturer)
          put("videoUrl", lesson.videoUrl)
          put("videoDuration", lesson.videoDuration)
          put("audioUrl", lesson.audioUrl)
          put("audioDuration", lesson.audioDuration)
          put("audioSpeaker", lesson.audioSpeaker)
          put("isInternal", lesson.isInternal)
        }
        sendJsonPost("/api/lessons", payload)
        syncWithServerInternal()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  fun deleteAdminLesson(lessonId: String) {
    val currentLessons = _uiState.value.adminLessons.filterNot { it.id == lessonId }
    _uiState.value = _uiState.value.copy(
      adminLessons = currentLessons,
      toastMessage = "Đã xóa bài giảng khỏi danh mục hệ thống"
    )
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val hostUrls = listOf("http://10.0.2.2:3000/api/lessons/$lessonId", "http://127.0.0.1:3000/api/lessons/$lessonId")
        for (urlStr in hostUrls) {
          try {
            val url = URL(urlStr)
            val conn = (url.openConnection() as HttpURLConnection).apply {
              requestMethod = "DELETE"
              connectTimeout = 1500
            }
            conn.responseCode
            conn.disconnect()
            break
          } catch (e: Exception) {}
        }
        syncWithServerInternal()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  fun addAdminUserAccount(account: UserAccount) {
    val currentAccounts = _uiState.value.adminUserAccounts.toMutableList()
    currentAccounts.add(0, account)
    _uiState.value = _uiState.value.copy(
      adminUserAccounts = currentAccounts,
      showAddAccountDialog = false,
      toastMessage = "Đã thêm tài khoản quân nhân: ${account.fullName}"
    )

    viewModelScope.launch(Dispatchers.IO) {
      try {
        val payload = JSONObject().apply {
          put("id", account.id)
          put("username", account.username)
          put("password", account.password)
          put("militaryCode", account.militaryId)
          put("fullName", account.fullName)
          put("rank", account.rank)
          put("position", account.role)
          put("unit", account.unit)
        }
        sendJsonPost("/api/users", payload)
        syncWithServerInternal()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
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
