package com.example.data.model

data class DocAttachment(
  val id: String,
  val fileName: String,
  val fileType: String, // "PDF" or "DOCX"
  val fileSize: String, // e.g. "2.4 MB"
  val downloadUrl: String,
  val pageCount: Int = 12,
  val isDownloaded: Boolean = false,
  val isInternal: Boolean = false
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
  val updatedDate: String = "20/08/2026",
  val isInternal: Boolean = false, // true: Tài liệu Lưu hành nội bộ yêu cầu đăng nhập tài khoản cấp phát
  val securityLevel: String = "Công khai" // "Công khai" hoặc "Lưu hành nội bộ"
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
  val status: String, // "Hoàn thành tốt", "Đang học", "Cần đôn đốc"
  val pinCode: String = "123456",
  val isInternalAccess: Boolean = true,
  val phone: String = "0988.123.456"
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
  val fileSize: String = "1.5 MB",
  val isInternal: Boolean = false
)

data class UserProfile(
  val isLoggedIn: Boolean = false,
  val isInternalAccess: Boolean = false,
  val name: String = "Khách (Chưa đăng nhập)",
  val rank: String = "Chiến sĩ",
  val role: String = "Tự do tham khảo GDCT",
  val unit: String = "Vùng 4 Hải quân",
  val militaryId: String = "GUEST-V4",
  val joinDate: String = "08/2026",
  val partyStatus: String = "Chưa xác thực tài khoản",
  val phone: String = ""
)

enum class StudyMode {
  SLIDE,
  DOCUMENT,
  VIDEO,
  AUDIO
}

