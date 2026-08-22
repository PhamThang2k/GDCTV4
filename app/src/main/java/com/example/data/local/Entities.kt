package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_progress")
data class StudyProgressEntity(
  @PrimaryKey val lessonId: String,
  val progressPercent: Int,
  val completedSectionsCount: Int,
  val totalSectionsCount: Int,
  val isCompleted: Boolean,
  val lastStudiedTimestamp: Long,
  val lastMode: String
)

@Entity(tableName = "quiz_submissions")
data class QuizSubmissionEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val lessonId: String,
  val lessonTitle: String,
  val score: Int,
  val totalQuestions: Int,
  val percentage: Int,
  val passed: Boolean,
  val timestamp: Long,
  val syncedToAdmin: Boolean = true,
  val commanderReviewStatus: String = "Đã tiếp nhận & Ghi sổ",
  val commanderComment: String = "Đạt yêu cầu nhận thức chính trị."
)

@Entity(tableName = "personal_notes")
data class PersonalNoteEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val lessonId: String,
  val title: String,
  val content: String,
  val category: String,
  val timestamp: Long
)

@Entity(tableName = "bookmarked_articles")
data class BookmarkedArticleEntity(
  @PrimaryKey val articleId: String,
  val timestamp: Long
)

@Entity(tableName = "user_session")
data class UserSessionEntity(
  @PrimaryKey val id: Int = 1,
  val isLoggedIn: Boolean = false,
  val isInternalAccess: Boolean = false,
  val name: String = "",
  val username: String = "",
  val password: String = "12345@abc",
  val rank: String = "Chiến sĩ",
  val role: String = "Chiến sĩ",
  val unit: String = "Vùng 4 Hải quân",
  val militaryId: String = "",
  val orderNumber: Int = 1,
  val joinDate: String = "08/2026",
  val partyStatus: String = "Đảng viên dự bị",
  val phone: String = ""
)
