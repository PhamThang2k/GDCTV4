package com.example.data.model

data class SlideItem(
  val slideNumber: Int,
  val title: String,
  val bullets: List<String>,
  val highlightQuote: String? = null,
  val note: String? = null
)

data class LessonSection(
  val sectionNumber: Int,
  val heading: String,
  val content: String,
  val keyTakeaway: String
)

data class QuizQuestion(
  val id: Int,
  val question: String,
  val options: List<String>,
  val correctOptionIndex: Int,
  val explanation: String
)

data class Lesson(
  val id: String,
  val code: String,
  val title: String,
  val category: String,
  val targetAudience: String,
  val durationMinutes: Int,
  val summary: String,
  val videoUrl: String,
  val videoDuration: String,
  val slides: List<SlideItem>,
  val sections: List<LessonSection>,
  val quizQuestions: List<QuizQuestion>
)

data class NewsArticle(
  val id: String,
  val title: String,
  val category: String,
  val publishedDate: String,
  val readTimeMinutes: Int,
  val summary: String,
  val content: String,
  val keyPoints: List<String>,
  val isHot: Boolean = false,
  val isPinned: Boolean = false
)

data class LawDoc(
  val id: String,
  val title: String,
  val category: String,
  val issuedBy: String,
  val summary: String,
  val keyArticles: List<Pair<String, String>>
)

data class UserProfile(
  val name: String = "Nguyễn Văn Thắng",
  val rank: String = "Trung úy",
  val role: String = "Trợ lý Chính trị",
  val unit: String = "Lữ đoàn 162 - Vùng 4 Hải quân",
  val militaryId: String = "HQ-V4-2026",
  val joinDate: String = "08/2020",
  val partyStatus: String = "Đảng viên chính thức"
)

enum class StudyMode {
  SLIDE,
  DOCUMENT,
  VIDEO
}
