package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
  entities = [
    StudyProgressEntity::class,
    QuizSubmissionEntity::class,
    PersonalNoteEntity::class,
    BookmarkedArticleEntity::class,
    UserSessionEntity::class
  ],
  version = 2,
  exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun studyDao(): StudyDao
  abstract fun quizDao(): QuizDao
  abstract fun noteDao(): NoteDao
  abstract fun bookmarkDao(): BookmarkDao
  abstract fun userSessionDao(): UserSessionDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "gdct_vung4_database"
        ).fallbackToDestructiveMigration().build()
        INSTANCE = instance
        instance
      }
    }
  }
}
