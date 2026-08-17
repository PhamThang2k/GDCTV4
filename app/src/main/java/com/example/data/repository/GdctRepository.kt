package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.BookmarkedArticleEntity
import com.example.data.local.PersonalNoteEntity
import com.example.data.local.QuizSubmissionEntity
import com.example.data.local.StudyProgressEntity
import com.example.data.model.LawDoc
import com.example.data.model.Lesson
import com.example.data.model.LessonSection
import com.example.data.model.NewsArticle
import com.example.data.model.QuizQuestion
import com.example.data.model.SlideItem
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

class GdctRepository(private val database: AppDatabase) {

  val allProgress: Flow<List<StudyProgressEntity>> = database.studyDao().getAllProgress()
  val allQuizSubmissions: Flow<List<QuizSubmissionEntity>> = database.quizDao().getAllSubmissions()
  val allNotes: Flow<List<PersonalNoteEntity>> = database.noteDao().getAllNotes()
  val allBookmarks: Flow<List<BookmarkedArticleEntity>> = database.bookmarkDao().getAllBookmarks()

  suspend fun saveStudyProgress(
    lessonId: String,
    progressPercent: Int,
    completedSectionsCount: Int,
    totalSectionsCount: Int,
    isCompleted: Boolean,
    lastMode: String
  ) {
    val entity = StudyProgressEntity(
      lessonId = lessonId,
      progressPercent = progressPercent.coerceIn(0, 100),
      completedSectionsCount = completedSectionsCount,
      totalSectionsCount = totalSectionsCount,
      isCompleted = isCompleted || progressPercent >= 100,
      lastStudiedTimestamp = System.currentTimeMillis(),
      lastMode = lastMode
    )
    database.studyDao().saveProgress(entity)
  }

  suspend fun submitQuiz(
    lessonId: String,
    lessonTitle: String,
    score: Int,
    totalQuestions: Int
  ): Long {
    val percentage = if (totalQuestions > 0) (score * 100) / totalQuestions else 0
    val passed = percentage >= 60
    val reviewStatus = if (percentage >= 80) "Chính trị viên đã duyệt - Xếp loại Giỏi"
                       else if (percentage >= 65) "Chính trị viên đã duyệt - Xếp loại Khá"
                       else "Chính trị viên yêu cầu ôn luyện lại"
    val comment = when {
      percentage >= 90 -> "Đồng chí nắm rất vững nội dung chính trị, liên hệ sâu sắc thực tiễn tàu và đơn vị."
      percentage >= 75 -> "Nắm tốt kiến thức cơ bản, cần phát huy trong rèn luyện kỷ luật và sẵn sàng chiến đấu."
      percentage >= 60 -> "Đạt yêu cầu nhận thức, tiếp tục đào sâu nghiên cứu tài liệu chuyên đề."
      else -> "Cần chủ động tự học và thảo luận tổ 3 người để nâng cao nhận thức chính trị."
    }

    val submission = QuizSubmissionEntity(
      lessonId = lessonId,
      lessonTitle = lessonTitle,
      score = score,
      totalQuestions = totalQuestions,
      percentage = percentage,
      passed = passed,
      timestamp = System.currentTimeMillis(),
      syncedToAdmin = true,
      commanderReviewStatus = reviewStatus,
      commanderComment = comment
    )
    return database.quizDao().insertSubmission(submission)
  }

  suspend fun addNote(lessonId: String, title: String, content: String, category: String) {
    val note = PersonalNoteEntity(
      lessonId = lessonId,
      title = title,
      content = content,
      category = category,
      timestamp = System.currentTimeMillis()
    )
    database.noteDao().insertNote(note)
  }

  suspend fun deleteNote(id: Long) {
    database.noteDao().deleteNote(id)
  }

  suspend fun toggleBookmark(articleId: String, isBookmarked: Boolean) {
    if (isBookmarked) {
      database.bookmarkDao().removeBookmark(articleId)
    } else {
      database.bookmarkDao().addBookmark(BookmarkedArticleEntity(articleId, System.currentTimeMillis()))
    }
  }

  fun getUserProfile(): UserProfile = UserProfile()

  fun getLessons(): List<Lesson> {
    return listOf(
      Lesson(
        id = "bai_1",
        code = "CĐ-01/2026",
        title = "Nâng cao bản lĩnh chính trị, ý chí quyết chiến quyết thắng của cán bộ, chiến sĩ Vùng 4 Hải quân",
        category = "Chuyên đề Sĩ quan & QNCN",
        targetAudience = "Cán bộ, Sĩ quan, QNCN toàn Vùng",
        durationMinutes = 45,
        videoUrl = "https://example.com/gdct/bai1_vung4.mp4",
        videoDuration = "18:40",
        summary = "Quán triệt sâu sắc tình hình nhiệm vụ bảo vệ chủ quyền biển, đảo, thềm lục địa phía Nam của Tổ quốc trong tình hình mới; xây dựng bản lĩnh kiên định, vững vàng trước mọi thử thách sóng gió.",
        slides = listOf(
          SlideItem(
            slideNumber = 1,
            title = "I. Tình hình thế giới, khu vực và Biển Đông hiện nay",
            bullets = listOf(
              "Tình hình an ninh hàng hải, tranh chấp chủ quyền trên Biển Đông tiếp tục diễn biến phức tạp, khó lường.",
              "Các thế lực thù địch tăng cường chống phá, xuyên tạc đường lối quân sự, quốc phòng của Đảng.",
              "Vùng 4 Hải quân quản lý vùng biển rộng lớn, trọng điểm có vị trí chiến lược đặc biệt quan trọng."
            ),
            highlightQuote = "\"Kiên quyết, kiên trì đấu tranh bảo vệ vững chắc độc lập, chủ quyền, thống nhất, toàn vẹn lãnh thổ của Tổ quốc.\"",
            note = "Nhấn mạnh tinh thần cảnh giác cách mạng, không để bị động, bất ngờ trong mọi tình huống."
          ),
          SlideItem(
            slideNumber = 2,
            title = "II. Vị trí, vai trò của bản lĩnh chính trị đối với Bộ đội Hải quân",
            bullets = listOf(
              "Bản lĩnh chính trị là nhân tố quyết định sức mạnh chiến đấu của bộ đội tàu, đảo và các lực lượng mặt đất.",
              "Thể hiện ở sự tuyệt đối trung thành với Đảng, Tổ quốc và Nhân dân.",
              "Sẵn sàng chấp nhận hy sinh, gian khổ, quyết tâm bảo vệ từng tấc đảo, sải biển thiêng liêng."
            ),
            highlightQuote = "\"Bộ đội Hải quân: Còn người, còn tàu, còn đảo, còn chủ quyền biển đảo Tổ quốc.\"",
            note = "Liên hệ tinh thần 64 chiến sĩ Gạc Ma 1988 và truyền thống Đoàn Tàu Không Số."
          ),
          SlideItem(
            slideNumber = 3,
            title = "III. Những yêu cầu, giải pháp xây dựng bản lĩnh kiên định",
            bullets = listOf(
              "1. Tích cực học tập chủ nghĩa Mác - Lênin, tư tưởng Hồ Chí Minh, đường lối quân sự của Đảng.",
              "2. Nâng cao chất lượng công tác dự báo tư tưởng, quản lý bộ đội và định hướng dư luận.",
              "3. Đẩy mạnh phong trào thi đua Quyết thắng gắn với Cuộc vận động 'Phát huy truyền thống, cống hiến tài năng, xứng danh Bộ đội Cụ Hồ - Người chiến sĩ Hải quân'.",
              "4. Chăm lo đời sống vật chất, tinh thần cho bộ đội nơi đầu sóng ngọn gió."
            ),
            highlightQuote = "\"Càng khó khăn, gian khổ, hiểm nguy, người chiến sĩ Vùng 4 càng vững vàng tay lái, chắc tay súng.\"",
            note = "Chi bộ và chỉ huy các cấp phải luôn gương mẫu, đi đầu trong mọi nhiệm vụ."
          ),
          SlideItem(
            slideNumber = 4,
            title = "IV. Trách nhiệm của cán bộ, đảng viên trong thực hiện chức trách",
            bullets = listOf(
              "Nêu cao tinh thần tự giác tu dưỡng, rèn luyện phẩm chất đạo đức cách mạng.",
              "Chủ động khắc phục khó khăn, làm chủ vũ khí trang bị kỹ thuật mới, hiện đại.",
              "Giữ nghiêm kỷ luật Quân đội, kỷ luật dân vận, đoàn kết nội bộ keo sơn."
            ),
            highlightQuote = "\"Kỷ luật là sức mạnh của Quân đội, là danh dự của người quân nhân cách mạng.\"",
            note = "Từng đồng chí viết bản cam kết tu dưỡng rèn luyện sát với chức trách được giao."
          )
        ),
        sections = listOf(
          LessonSection(
            sectionNumber = 1,
            heading = "1. Bối cảnh tình hình và những nhân tố tác động đến tư tưởng bộ đội",
            content = "Trong giai đoạn hiện nay, nhiệm vụ quản lý, bảo vệ chủ quyền biển, đảo của Vùng 4 Hải quân đặt ra những yêu cầu rất cao. Khu vực biển do Vùng quản lý có vị trí chiến lược then chốt về quốc phòng - an ninh và phát triển kinh tế biển. Đặc điểm khí hậu thời tiết khắc nghiệt, hoạt động dài ngày trên biển xa đòi hỏi cán bộ, chiến sĩ phải có sức khỏe dẻo dai và đặc biệt là bản lĩnh chính trị vững vàng, ý chí quyết tâm cao.",
            keyTakeaway = "Bản lĩnh chính trị là nền tảng cốt lõi giúp người lính biển vượt qua mọi gian nan thử thách."
          ),
          LessonSection(
            sectionNumber = 2,
            heading = "2. Nhận diện các biểu hiện suy thoái, dao động tư tưởng cần phòng tránh",
            content = "Cần kịp thời phát hiện và ngăn ngừa tư tưởng ngại khó, ngại khổ, ngại đi biển dài ngày; biểu hiện thiếu tinh thần trách nhiệm trong bảo quản, bảo dưỡng vũ khí trang bị kỹ thuật; tư tưởng đơn giản, chủ quan, mất cảnh giác. Toàn Vùng kiên quyết thực hiện phương châm: 'Lấy xây để chống, lấy cái đẹp dẹp cái xấu, lấy tích cực đẩy lùi tiêu cực'.",
            keyTakeaway = "Chủ động phòng ngừa từ sớm, từ xa mọi biểu hiện dao động tư tưởng."
          ),
          LessonSection(
            sectionNumber = 3,
            heading = "3. Giải pháp nâng cao chất lượng giáo dục chính trị tại đơn vị",
            content = "Đổi mới hình thức, phương pháp giáo dục chính trị theo hướng trực quan, sinh động, kết hợp giữa thuyết trình với trình chiếu slide đa phương tiện, video tư liệu, sân khấu hóa và tự học trên ứng dụng số. Phát huy vai trò của tổ chức Đoàn, Hội đồng quân nhân và các thiết chế văn hóa ở cơ sở.",
            keyTakeaway = "Tận dụng chuyển đổi số và ứng dụng di động để nâng cao hiệu quả GDCT mọi lúc, mọi nơi."
          )
        ),
        quizQuestions = listOf(
          QuizQuestion(
            id = 1,
            question = "Phương châm tư tưởng cốt lõi của người chiến sĩ Hải quân khi thực hiện nhiệm vụ trên biển là gì?",
            options = listOf(
              "Còn người, còn tàu, còn đảo, còn chủ quyền biển đảo Tổ quốc",
              "Thận trọng, giữ khoảng cách, tránh né thử thách",
              "Chỉ hành động khi có chỉ đạo trực tiếp từ bờ",
              "Ưu tiên bảo toàn trang bị hơn nhiệm vụ chính trị"
            ),
            correctOptionIndex = 0,
            explanation = "Phương châm 'Còn người, còn tàu, còn đảo, còn chủ quyền biển đảo Tổ quốc' là lời thề sắt son của người lính Hải quân bảo vệ Tổ quốc."
          ),
          QuizQuestion(
            id = 2,
            question = "Cuộc vận động lớn đang được triển khai sâu rộng trong toàn Quân chủng Hải quân là gì?",
            options = listOf(
              "Phát huy truyền thống, cống hiến tài năng, xứng danh Bộ đội Cụ Hồ - Người chiến sĩ Hải quân",
              "Thanh niên Hải quân xung kích làm giàu",
              "Huấn luyện giỏi, sẵn sàng cơ động cao",
              "Toàn dân đoàn kết xây dựng đời sống văn hóa"
            ),
            correctOptionIndex = 0,
            explanation = "Cuộc vận động 'Phát huy truyền thống, cống hiến tài năng, xứng danh Bộ đội Cụ Hồ - Người chiến sĩ Hải quân' là trọng tâm rèn luyện phẩm chất."
          ),
          QuizQuestion(
            id = 3,
            question = "Yếu tố nào được coi là nhân tố quyết định sức mạnh chiến đấu của bộ đội Vùng 4 Hải quân?",
            options = listOf(
              "Bản lĩnh chính trị và sự tuyệt đối trung thành với Đảng, Tổ quốc",
              "Số lượng vũ khí trang bị thuần túy",
              "Điều kiện cơ sở vật chất thuận lợi",
              "Thời tiết và môi trường khách quan"
            ),
            correctOptionIndex = 0,
            explanation = "Bản lĩnh chính trị vững vàng, ý chí quyết chiến quyết thắng là cội nguồn sức mạnh làm nên mọi thắng lợi của Quân đội ta."
          ),
          QuizQuestion(
            id = 4,
            question = "Trách nhiệm của từng cán bộ, chiến sĩ khi học tập chính trị là gì?",
            options = listOf(
              "Chủ động tự học, ghi chép thu hoạch, liên hệ thực tiễn và chấp hành nghiêm kỷ luật",
              "Chỉ nghe giảng thụ động mà không cần ôn tập",
              "Chỉ tham gia khi có kiểm tra đột xuất",
              "Giao phó toàn bộ việc học tập cho Chính trị viên"
            ),
            correctOptionIndex = 0,
            explanation = "Mỗi quân nhân phải nêu cao tính tự giác, biến quá trình đào tạo thành tự đào tạo, tự rèn luyện bản thân."
          )
        )
      ),

      Lesson(
        id = "bai_2",
        code = "CĐ-02/2026",
        title = "Truyền thống vẻ vang của Vùng 4 Hải quân - Nơi đầu sóng ngọn gió",
        category = "Lịch sử & Truyền thống",
        targetAudience = "Toàn thể cán bộ, chiến sĩ, tân binh",
        durationMinutes = 40,
        videoUrl = "https://example.com/gdct/bai2_lichsu_vung4.mp4",
        videoDuration = "15:20",
        summary = "Ôn lại chặng đường lịch sử xây dựng, chiến đấu và trưởng thành của Vùng 4 Hải quân Anh hùng; từ Căn cứ Cam Ranh lịch sử đến quần đảo Trường Sa thiêng liêng.",
        slides = listOf(
          SlideItem(
            slideNumber = 1,
            title = "1. Quá trình thành lập và những chiến công đầu tiên",
            bullets = listOf(
              "Vùng 4 Duyên hải (tiền thân của Vùng 4 Hải quân) được thành lập ngày 26/10/1975.",
              "Tiếp quản, củng cố và xây dựng Căn cứ quân sự Cam Ranh trở thành căn cứ hải quân chiến lược.",
              "Vinh dự được Đảng, Nhà nước phong tặng danh hiệu Đơn vị Anh hùng Lực lượng vũ trang nhân dân."
            ),
            highlightQuote = "\"Chiến đấu anh dũng, khắc phục khó khăn, làm chủ vùng biển, quyết chiến quyết thắng.\"",
            note = "Nhấn mạnh mốc lịch sử 26/10 hàng năm là Ngày truyền thống vẻ vang của Vùng 4."
          ),
          SlideItem(
            slideNumber = 2,
            title = "2. Sứ mệnh bảo vệ Quần đảo Trường Sa thiêng liêng",
            bullets = listOf(
              "Lực lượng Vùng 4 trực tiếp đóng quân, quản lý và bảo vệ vững chắc 21 đảo, điểm đảo (33 điểm đóng quân) trên quần đảo Trường Sa.",
              "Xây dựng Trường Sa 'Mạnh về phòng thủ, tốt về lối sống, đẹp về cảnh quan môi trường, mẫu mực về đoàn kết quân dân'.",
              "Là điểm tựa vững chắc cho ngư dân vươn khơi bám biển."
            ),
            highlightQuote = "\"Đảo là nhà, biển cả là quê hương, bảo vệ Trường Sa là mệnh lệnh từ trái tim.\"",
            note = "Kể về các tấm gương chỉ huy, chiến sĩ anh dũng ngày đêm bám đảo giữ biển."
          ),
          SlideItem(
            slideNumber = 3,
            title = "3. Phát huy truyền thống trong thời kỳ chính quy, hiện đại hóa",
            bullets = listOf(
              "Được trang bị các tàu mặt nước hiện đại (Khinh hạm Gepard 3.9, tàu Tên lửa Molniya...).",
              "Lữ đoàn 162 - 'Lữ đoàn Thép', Lữ đoàn 101 Hải quân đánh bộ tinh nhuệ, Lữ đoàn 146 Trường Sa.",
              "Tích cực cứu hộ, cứu nạn, giúp đỡ nhân dân trong thiên tai, bão lũ (Hải quân làm điểm tựa cho ngư dân vươn khơi bám biển)."
            ),
            highlightQuote = "\"Xây dựng Vùng 4 Cách mạng, chính quy, tinh nhuệ, hiện đại, sẵn sàng chiến đấu cao.\"",
            note = "Tự hào về màu cờ sắc áo của người lính Hải quân Vùng 4."
          )
        ),
        sections = listOf(
          LessonSection(
            sectionNumber = 1,
            heading = "1. Mốc son lịch sử thành lập và phát triển",
            content = "Trải qua hơn 50 năm xây dựng, chiến đấu và trưởng thành, các thế hệ cán bộ, chiến sĩ Vùng 4 Hải quân đã lập nên nhiều chiến công xuất sắc, góp phần viết nên trang sử hào hùng của Quân chủng Hải quân Nhân dân Việt Nam anh hùng.",
            keyTakeaway = "Ngày truyền thống Vùng 4 Hải quân là ngày 26 tháng 10 năm 1975."
          ),
          LessonSection(
            sectionNumber = 2,
            heading = "2. Nét đẹp văn hóa 'Bộ đội Cụ Hồ - Người chiến sĩ Hải quân'",
            content = "Hình ảnh người chiến sĩ Vùng 4 với bộ quân phục yếm trắng viền xanh, nụ cười kiên nghị bên cột mốc chủ quyền Trường Sa đã trở thành biểu tượng thiêng liêng của lòng yêu nước và ý chí kiên cường của dân tộc Việt Nam.",
            keyTakeaway = "Mỗi cán bộ, chiến sĩ là một đại sứ mang hình ảnh đẹp của Hải quân nhân dân đến với nhân dân cả nước."
          )
        ),
        quizQuestions = listOf(
          QuizQuestion(
            id = 1,
            question = "Ngày truyền thống của Vùng 4 Hải quân là ngày nào?",
            options = listOf(
              "26 tháng 10 năm 1975",
              "07 tháng 5 năm 1955",
              "22 tháng 12 năm 1944",
              "19 tháng 8 năm 1945"
            ),
            correctOptionIndex = 0,
            explanation = "Ngày 26/10/1975 là ngày thành lập Vùng 4 Duyên hải (nay là Bộ Tư lệnh Vùng 4 Hải quân)."
          ),
          QuizQuestion(
            id = 2,
            question = "Khẩu hiệu hành động thể hiện tình cảm gắn bó của cán bộ, chiến sĩ Trường Sa là gì?",
            options = listOf(
              "Đảo là nhà, biển cả là quê hương",
              "Đi không dấu, nấu không khói",
              "Đâu cần thanh niên có, đâu khó có thanh niên",
              "Nước rút đến đâu, gieo trồng đến đó"
            ),
            correctOptionIndex = 0,
            explanation = "Khẩu hiệu 'Đảo là nhà, biển cả là quê hương' gắn liền với tâm hồn và trách nhiệm của người lính Trường Sa."
          )
        )
      ),

      Lesson(
        id = "bai_3",
        code = "CĐ-03/2026",
        title = "Pháp luật về Biển đảo Việt Nam và Quy tắc ứng xử của lực lượng làm nhiệm vụ trên biển",
        category = "Pháp luật & Kỷ luật",
        targetAudience = "Sĩ quan thuyền trưởng, chính trị viên, thủy thủ đoàn",
        durationMinutes = 50,
        videoUrl = "https://example.com/gdct/bai3_phapluat_bien.mp4",
        videoDuration = "22:15",
        summary = "Nghiên cứu Luật Biển Việt Nam 2012, UNCLOS 1982, các quy tắc đối sách khi xử lý các tình huống trên biển, bảo đảm đúng đối sách, kiên quyết, kiên trì, không để xảy ra xung đột.",
        slides = listOf(
          SlideItem(
            slideNumber = 1,
            title = "1. Cơ sở pháp lý quốc tế và trong nước về biển đảo",
            bullets = listOf(
              "Công ước Liên Hợp Quốc về Luật Biển năm 1982 (UNCLOS 1982).",
              "Luật Biển Việt Nam năm 2012: Xác định các vùng biển nội thủy, lãnh hải, tiếp giáp lãnh hải, vùng đặc quyền kinh tế (EEZ) và thềm lục địa.",
              "Việt Nam có đầy đủ bằng chứng lịch sử và căn cứ pháp lý khẳng định chủ quyền đối với hai quần đảo Hoàng Sa và Trường Sa."
            ),
            highlightQuote = "\"Việt Nam chủ trương giải quyết mọi tranh chấp trên biển bằng biện pháp hòa bình trên cơ sở luật pháp quốc tế.\"",
            note = "Cán bộ trên tàu phải nắm chắc phạm vi tọa độ các vùng biển chủ quyền."
          ),
          SlideItem(
            slideNumber = 2,
            title = "2. Nguyên tắc và quy trình xử lý tình huống trên biển",
            bullets = listOf(
              "Kiên quyết, kiên trì, tỉnh táo, kiềm chế, không nổ súng trước, không khiêu khích.",
              "Thực hiện nghiêm chế độ báo cáo theo phân cấp, xử lý theo đúng phương án tác chiến và quy định pháp luật.",
              "Tuyên truyền, xua đuổi tàu thuyền nước ngoài vi phạm vùng biển Việt Nam bằng biện pháp hòa bình, đúng đối sách."
            ),
            highlightQuote = "\"Bảo vệ vững chắc chủ quyền nhưng đồng thời giữ vững môi trường hòa bình, ổn định để phát triển đất nước.\"",
            note = "Ghi chép nhật ký, quay phim, chụp ảnh làm tư liệu đấu tranh pháp lý và ngoại giao."
          )
        ),
        sections = listOf(
          LessonSection(
            sectionNumber = 1,
            heading = "1. Các vùng biển thuộc chủ quyền và quyền tài phán của Việt Nam",
            content = "Luật Biển Việt Nam 2012 quy định rõ ranh giới lãnh hải 12 hải lý, vùng tiếp giáp lãnh hải 24 hải lý, vùng đặc quyền kinh tế 200 hải lý tính từ đường cơ sở. Lực lượng Hải quân có trách nhiệm tuần tra, kiểm soát, bảo vệ an ninh trật tự và chủ quyền quốc gia trong các vùng biển này.",
            keyTakeaway = "Nắm chắc các ranh giới biển là yêu cầu bắt buộc đối với mọi thuyền trưởng và thủy thủ."
          )
        ),
        quizQuestions = listOf(
          QuizQuestion(
            id = 1,
            question = "Theo Luật Biển Việt Nam 2012, chiều rộng lãnh hải của nước CHXHCN Việt Nam là bao nhiêu hải lý?",
            options = listOf("12 hải lý", "24 hải lý", "200 hải lý", "3 hải lý"),
            correctOptionIndex = 0,
            explanation = "Theo Điều 11 Luật Biển Việt Nam 2012, Lãnh hải của Việt Nam rộng 12 hải lý tính từ đường cơ sở ra."
          ),
          QuizQuestion(
            id = 2,
            question = "Nguyên tắc cốt lõi khi xử lý tình huống tàu thuyền nước ngoài xâm phạm vùng biển của ta là gì?",
            options = listOf(
              "Kiên quyết, kiên trì, tỉnh táo, kiềm chế, xử lý đúng đối sách pháp luật",
              "Tự ý nổ súng ngay lập tức không cần báo cáo",
              "Không can thiệp, để tàu nước ngoài tự do di chuyển",
              "Tự ý thương lượng riêng với tàu đối phương"
            ),
            correctOptionIndex = 0,
            explanation = "Phương châm chỉ đạo của Đảng và Nhà nước là kiên quyết, kiên trì bảo vệ chủ quyền, xử lý đúng đối sách, giữ vững hòa bình ổn định."
          )
        )
      ),

      Lesson(
        id = "bai_4",
        code = "CĐ-04/2026",
        title = "Học tập và làm theo tư tưởng, đạo đức, phong cách Hồ Chí Minh về tinh thần nêu gương",
        category = "Tư tưởng Hồ Chí Minh",
        targetAudience = "Đảng viên, Cán bộ Đoàn, Cán bộ chỉ huy",
        durationMinutes = 45,
        videoUrl = "https://example.com/gdct/bai4_neuguong_hcm.mp4",
        videoDuration = "20:10",
        summary = "Nêu cao trách nhiệm nêu gương của cán bộ, đảng viên; xây dựng chi bộ '4 tốt', đảng bộ cơ sở '4 tốt'; giữ gìn sự đoàn kết thống nhất trong đơn vị.",
        slides = listOf(
          SlideItem(
            slideNumber = 1,
            title = "1. Tư tưởng Hồ Chí Minh về sự nêu gương của người cán bộ",
            bullets = listOf(
              "\"Một tấm gương sống còn có giá trị hơn một trăm bài diễn văn tuyên truyền\".",
              "Cán bộ cấp trên phải làm gương cho cấp dưới, đảng viên làm gương cho quần chúng.",
              "Nêu gương trên cả 3 phương diện: Tư tưởng chính trị, đạo đức lối sống và tác phong công tác."
            ),
            highlightQuote = "\"Cán bộ là cái gốc của mọi công việc. Muôn việc thành công hoặc thất bại, đều do cán bộ tốt hoặc kém.\"",
            note = "Thực hiện 'Nói đi đôi với làm', chống bệnh hình thức, quan liêu."
          )
        ),
        sections = listOf(
          LessonSection(
            sectionNumber = 1,
            heading = "1. Tiêu chí rèn luyện người cán bộ Hải quân mẫu mực",
            content = "Thấm nhuần lời dạy của Bác: 'Ngày trước ta chỉ có đêm và rừng. Ngày nay ta có ngày, có trời, có biển. Bờ biển ta dài, tươi đẹp, ta phải biết giữ gìn lấy nó'. Người cán bộ Hải quân hôm nay phải luôn tiên phong, mẫu mực trong mọi nhiệm vụ gian khó nhất.",
            keyTakeaway = "Lời dạy của Bác Hồ là kim chỉ nam cho mọi thế hệ chiến sĩ Hải quân Nhân dân Việt Nam."
          )
        ),
        quizQuestions = listOf(
          QuizQuestion(
            id = 1,
            question = "Bác Hồ đã căn dặn bộ đội Hải quân câu nói lịch sử nào khi về thăm bộ đội năm 1961?",
            options = listOf(
              "Bờ biển ta dài, tươi đẹp, ta phải biết giữ gìn lấy nó",
              "Hễ còn một tên xâm lược trên đất nước ta, thì ta còn phải tiếp tục chiến đấu",
              "Không có gì quý hơn độc lập tự do",
              "Các vua Hùng đã có công dựng nước, Bác cháu ta phải cùng nhau giữ lấy nước"
            ),
            correctOptionIndex = 0,
            explanation = "Năm 1961, khi thăm bộ đội Hải quân, Bác dặn: 'Ngày trước ta chỉ có đêm và rừng. Ngày nay ta có ngày, có trời, có biển. Bờ biển ta dài, tươi đẹp, ta phải biết giữ gìn lấy nó'."
          )
        )
      )
    )
  }

  fun getNewsArticles(): List<NewsArticle> {
    return listOf(
      NewsArticle(
        id = "news_1",
        title = "Bộ Tư lệnh Vùng 4 Hải quân phát động đợt thi đua cao điểm 'Luyện giỏi, rèn nghiêm, giữ vững biển trời'",
        category = "Hoạt động Vùng 4",
        publishedDate = "16/08/2026",
        readTimeMinutes = 4,
        summary = "Toàn Vùng 4 dấy lên khí thế thi đua sôi nổi, quyết tâm hoàn thành xuất sắc 100% chỉ tiêu huấn luyện chiến đấu, giáo dục chính trị năm 2026.",
        content = "Tại Căn cứ Cam Ranh, Hội đồng Thi đua - Khen thưởng Vùng 4 Hải quân đã tổ chức Lễ phát động đợt thi đua cao điểm. Tham dự có các đồng chí trong Thường vụ Đảng ủy, Bộ Tư lệnh Vùng cùng đại diện chỉ huy các Lữ đoàn 162, 101, 146, 955, 957, Trung tâm Huấn luyện Vùng và đông đảo cán bộ, chiến sĩ.\n\nĐợt thi đua tập trung vào 4 nội dung trọng tâm: Nhận thức, trách nhiệm và ý chí quyết tâm cao nhất; Hoàn thành xuất sắc nhiệm vụ chính trị trọng tâm; Chấp hành nghiêm pháp luật, kỷ luật, bảo đảm an toàn tuyệt đối; Xây dựng tổ chức đảng trong sạch vững mạnh, cơ quan đơn vị vững mạnh toàn diện 'Mẫu mực, tiêu biểu'.",
        keyPoints = listOf(
          "100% quân số quán triệt sâu sắc nhiệm vụ và chỉ tiêu thi đua.",
          "Tập trung nâng cao chất lượng huấn luyện làm chủ vũ khí trang bị mới.",
          "Đẩy mạnh chuyển đổi số trong công tác giáo dục chính trị và quản lý bộ đội."
        ),
        isHot = true,
        isPinned = true
      ),
      NewsArticle(
        id = "news_2",
        title = "Chiến sĩ Trường Sa chắc tay súng giữ vững chủ quyền biển đảo thiêng liêng",
        category = "Biển đảo quê hương",
        publishedDate = "15/08/2026",
        readTimeMinutes = 5,
        summary = "Những người lính trẻ kiên cường tại các đảo Song Tử Tây, Nam Yết, Sinh Tồn, Trường Sa Lớn luôn duy trì nghiêm chế độ trực sẵn sàng chiến đấu 24/24.",
        content = "Vượt lên cái nắng gay gắt và những cơn giông bất chợt giữa đại dương, cán bộ, chiến sĩ Lữ đoàn 146 - Đoàn Trường Sa anh hùng luôn giữ vững niềm tin, lạc quan và tinh thần cảnh giác cao độ. Cùng với việc luyện tập các phương án tác chiến sát thực tế, công tác giáo dục chính trị, định hướng tư tưởng luôn được cấp ủy, chỉ huy các đảo đặc biệt coi trọng thông qua hệ thống học tập số hóa và sinh hoạt tổ 3 người.",
        keyPoints = listOf(
          "Duy trì nghiêm chế độ canh trực sẵn sàng chiến đấu 24/7.",
          "Gắn bó mật thiết, hỗ trợ nước ngọt, y tế và cứu hộ cho ngư dân.",
          "Chủ động tăng gia sản xuất, phủ xanh đảo bằng cây bàng vuông, phong ba."
        ),
        isHot = true
      ),
      NewsArticle(
        id = "news_3",
        title = "Tuyên truyền phổ biến pháp luật: Quy định về bảo đảm an toàn thông tin mạng trong Quân đội",
        category = "Văn bản - Chỉ thị",
        publishedDate = "14/08/2026",
        readTimeMinutes = 3,
        summary = "Quán triệt Chỉ thị của Bộ Quốc phòng và Bộ Tư lệnh Quân chủng về việc chấp hành nghiêm kỷ luật bảo mật thông tin, sử dụng điện thoại thông minh đúng quy định.",
        content = "Thực hiện các quy định của Bộ Quốc phòng về bảo vệ bí mật quân sự trên không gian mạng, Phòng Chính trị Vùng 4 yêu cầu toàn thể quân nhân thực hiện nghiêm túc: Tuyệt đối không đăng tải hình ảnh hoạt động huấn luyện, doanh trại, vũ khí khí tài lên mạng xã hội cá nhân; Quản lý và sử dụng thiết bị liên lạc đúng thời gian, địa điểm quy định.",
        keyPoints = listOf(
          "Tuyệt đối giữ bí mật quân sự trên không gian mạng.",
          "Không chụp ảnh, chia sẻ thông tin vị trí đóng quân, tọa độ tuần tra.",
          "Mỗi quân nhân là một chiến sĩ xung kích bảo vệ nền tảng tư tưởng của Đảng trên Internet."
        )
      ),
      NewsArticle(
        id = "news_4",
        title = "Gương sáng Lữ đoàn 162: Thượng úy Lê Hoàng Nam - Cán bộ trẻ xung kích làm chủ tàu tên lửa hiện đại",
        category = "Gương sáng Chiến sĩ",
        publishedDate = "12/08/2026",
        readTimeMinutes = 4,
        summary = "Với tinh thần sáng tạo, Thượng úy Lê Hoàng Nam đã có nhiều sáng kiến cải tiến mô phỏng huấn luyện kỹ thuật tên lửa, đạt giải cao cấp Quân chủng.",
        content = "Là một sĩ quan trẻ trên tàu tên lửa của Lữ đoàn 162, đồng chí Nam luôn gương mẫu trong học tập và công tác. Không chỉ giỏi về chuyên môn kỹ thuật, đồng chí còn là cây văn nghệ, hạt nhân trong các phong tràu thi đua Quyết thắng và tích cực chia sẻ phương pháp học tập chính trị hiệu quả cho các chiến sĩ trẻ.",
        keyPoints = listOf(
          "Chủ nhân sáng kiến phần mềm mô phỏng bảng điều khiển hỏa lực.",
          "Đảng viên trẻ hoàn thành xuất sắc nhiệm vụ 3 năm liên tục.",
          "Tấm gương sáng về tinh thần tự học, tự rèn luyện."
        )
      )
    )
  }

  fun getLawDocs(): List<LawDoc> {
    return listOf(
      LawDoc(
        id = "law_1",
        title = "Luật Biển Việt Nam năm 2012 (Số 18/2012/QH13)",
        category = "Luật Quốc gia",
        issuedBy = "Quốc hội khóa XIII",
        summary = "Quy định về đường cơ sở, nội thủy, lãnh hải, vùng tiếp giáp lãnh hải, vùng đặc quyền kinh tế, thềm lục địa, các đảo, quần đảo Hoàng Sa, quần đảo Trường Sa và chế độ pháp lý các vùng biển của Việt Nam.",
        keyArticles = listOf(
          "Điều 1" to "Phạm vi điều chỉnh quy định về vùng biển Việt Nam, các hoạt động trong vùng biển Việt Nam, phát triển kinh tế biển, quản lý và bảo vệ biển, đảo.",
          "Điều 11" to "Lãnh hải là vùng biển có chiều rộng 12 hải lý tính từ đường cơ sở ra phía ngoài. Ranh giới ngoài của lãnh hải là biên giới quốc gia trên biển của Việt Nam.",
          "Điều 15" to "Vùng đặc quyền kinh tế là vùng biển tiếp liền và nằm ngoài lãnh hải Việt Nam, hợp với lãnh hải thành một vùng biển có chiều rộng 200 hải lý tính từ đường cơ sở.",
          "Điều 17" to "Thềm lục địa Việt Nam là đáy biển và lòng đất dưới đáy biển của khu vực ngầm dưới biển kéo dài tự nhiên từ đất liền ra mép ngoài của rìa lục địa."
        )
      ),
      LawDoc(
        id = "law_2",
        title = "10 Lời thề Danh dự của Quân nhân Quân đội Nhân dân Việt Nam",
        category = "Điều lệnh - Kỷ luật",
        issuedBy = "Bộ Quốc phòng",
        summary = "10 lời thề thiêng liêng thể hiện bản chất cách mạng, mục tiêu chiến đấu và danh dự cao quý của người chiến sĩ Quân đội Nhân dân Việt Nam.",
        keyArticles = listOf(
          "Lời thề 1" to "Hy sinh tất cả vì Tổ quốc Việt Nam; dưới sự lãnh đạo của Đảng Cộng sản Việt Nam, phấn đấu thực hiện một nước Việt Nam hòa bình, độc lập và xã hội chủ nghĩa.",
          "Lời thề 2" to "Tuyệt đối phục tùng mệnh lệnh cấp trên; khi nhận bất cứ nhiệm vụ gì đều tận tâm, tận lực thi hành nhanh chóng và chính xác.",
          "Lời thề 7" to "Đoàn kết nội bộ như ruột thịt trên tình thương yêu giai cấp; hết lòng giúp đỡ nhau lúc thường cũng như lúc ra trận.",
          "Lời thề 9" to "Khi tiếp xúc với nhân dân làm đúng 3 điều nên: kính trọng dân, giúp đỡ dân, bảo vệ dân; và 3 điều răn: không lấy của dân, không dọa nạt dân, không quấy nhiễu dân."
        )
      ),
      LawDoc(
        id = "law_3",
        title = "12 Điều Kỷ luật khi quan hệ với nhân dân của Quân đội",
        category = "Kỷ luật Dân vận",
        issuedBy = "Bộ Quốc phòng",
        summary = "Nguyên tắc ứng xử 'Đi dân nhớ, ở dân thương', giữ gìn và phát huy phẩm chất cao đẹp 'Bộ đội Cụ Hồ'.",
        keyArticles = listOf(
          "Điều 1" to "Không lấy cái kim, sợi chỉ của nhân dân.",
          "Điều 2" to "Mua bán công bằng, sòng phẳng, mượn cái gì phải trả, làm hỏng phải bồi thường.",
          "Điều 3" to "Tôn trọng phong tục tập quán, tín ngưỡng của nhân dân nơi đóng quân.",
          "Điều 4" to "Tích cực giúp đỡ nhân dân lao động sản xuất, phòng chống thiên tai, dịch bệnh."
        )
      )
    )
  }
}
