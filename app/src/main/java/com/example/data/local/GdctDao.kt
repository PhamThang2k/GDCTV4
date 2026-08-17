package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyDao {
  @Query("SELECT * FROM study_progress")
  fun getAllProgress(): Flow<List<StudyProgressEntity>>

  @Query("SELECT * FROM study_progress WHERE lessonId = :lessonId LIMIT 1")
  fun getProgressForLesson(lessonId: String): Flow<StudyProgressEntity?>

  @Query("SELECT * FROM study_progress WHERE lessonId = :lessonId LIMIT 1")
  suspend fun getProgressForLessonDirect(lessonId: String): StudyProgressEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveProgress(progress: StudyProgressEntity)
}

@Dao
interface QuizDao {
  @Query("SELECT * FROM quiz_submissions ORDER BY timestamp DESC")
  fun getAllSubmissions(): Flow<List<QuizSubmissionEntity>>

  @Query("SELECT * FROM quiz_submissions WHERE lessonId = :lessonId ORDER BY timestamp DESC LIMIT 1")
  fun getLatestSubmissionForLesson(lessonId: String): Flow<QuizSubmissionEntity?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSubmission(submission: QuizSubmissionEntity): Long

  @Query("DELETE FROM quiz_submissions WHERE id = :id")
  suspend fun deleteSubmission(id: Long)
}

@Dao
interface NoteDao {
  @Query("SELECT * FROM personal_notes ORDER BY timestamp DESC")
  fun getAllNotes(): Flow<List<PersonalNoteEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNote(note: PersonalNoteEntity)

  @Query("DELETE FROM personal_notes WHERE id = :id")
  suspend fun deleteNote(id: Long)
}

@Dao
interface BookmarkDao {
  @Query("SELECT * FROM bookmarked_articles")
  fun getAllBookmarks(): Flow<List<BookmarkedArticleEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addBookmark(bookmark: BookmarkedArticleEntity)

  @Query("DELETE FROM bookmarked_articles WHERE articleId = :articleId")
  suspend fun removeBookmark(articleId: String)
}
