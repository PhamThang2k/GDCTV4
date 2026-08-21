package com.example.data.model

data class DocAttachment(
  val id: String,
  val fileName: String,
  val fileType: String, // "PDF" or "DOCX"
  val fileSize: String, // e.g. "2.4 MB"
  val downloadUrl: String,
  val pageCount: Int = 12,
  val isDownloaded: Boolean = false
)

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
  val lecturer: String = "Ban Tuyên huấn Vùng 4",
  val videoUrl: String,
  val videoDuration: String,
  val audioUrl: String = "https://audio.vung4.vn/gdct_bai.mp3",
  val audioDuration: String = "18:30",
  val audioSpeaker: String = "Thượng tá Nguyễn Văn A - Ban Tuyên huấn",
  val docAttachments: List<DocAttachment> = emptyList(),
  val slides: List<SlideItem>,
  val sections: List<LessonSection>,
  val quizQuestions: List<QuizQuestion>,
  val status: String = "Đã xuất bản",
  val updatedDate: String = "16/08/2026"
)

data class UserAccount(
  val id: String,
  val militaryId: String,
  val fullName: String,
  val rank: String,
  val role: String,
  val unit: String,
  val completedLessonsCount: Int,
  val totalLessonsCount: Int,
  val averageScore: Double,
  val lastActive: String,
  val status: String // "Hoàn thành tốt", "Đang học", "Cần đôn đốc"
)

data class LawDoc(
  val id: String,
  val title: String,
  val category: String,
  val issuedBy: String,
  val summary: String,
  val keyArticles: List<Pair<String, String>>,
  val docxDownloadUrl: String = "https://docs.vung4.vn/law_vanban.docx",
  val pdfDownloadUrl: String = "https://docs.vung4.vn/law_vanban.pdf",
  val fileSize: String = "1.5 MB"
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
  VIDEO,
  AUDIO
}

