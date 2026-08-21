package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.BookmarkedArticleEntity
import com.example.data.local.PersonalNoteEntity
import com.example.data.local.QuizSubmissionEntity
import com.example.data.local.StudyProgressEntity
import com.example.data.model.DocAttachment
import com.example.data.model.LawDoc
import com.example.data.model.Lesson
import com.example.data.model.LessonSection
import com.example.data.model.QuizQuestion
import com.example.data.model.SlideItem
import com.example.data.model.UserAccount
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

  fun getUserProfile(): UserProfile = UserProfile(
    isLoggedIn = false,
    isInternalAccess = false,
    name = "Khách (Chưa đăng nhập)",
    username = "",
    rank = "Chiến sĩ",
    role = "Tự do tham khảo GDCT",
    unit = "Vùng 4 Hải quân",
    militaryId = "GUEST-V4",
    orderNumber = 0,
    joinDate = "08/2026",
    partyStatus = "Chưa xác thực tài khoản"
  )

  fun getUserAccounts(): List<UserAccount> {
    return listOf(
      UserAccount(
        id = "acc_01",
        orderNumber = 1,
        username = "phamtatthang_162",
        password = "12345@abc",
        militaryId = "QN-16201",
        fullName = "Phạm Tất Thắng",
        rank = "Đại úy",
        role = "Thuyền phó Tàu 015 Trần Hưng Đạo",
        unit = "Lữ đoàn 162",
        completedLessonsCount = 6,
        totalLessonsCount = 6,
        averageScore = 9.8,
        lastActive = "Hôm nay, 08:30",
        status = "Hoàn thành tốt",
        isInternalAccess = true,
        phone = "0988.112.233"
      ),
      UserAccount(
        id = "acc_02",
        orderNumber = 2,
        username = "nguyenvanbinh_162",
        password = "12345@abc",
        militaryId = "QN-16202",
        fullName = "Nguyễn Văn Bình",
        rank = "Thượng úy",
        role = "Chính trị viên Tàu 016 Quang Trung",
        unit = "Lữ đoàn 162",
        completedLessonsCount = 6,
        totalLessonsCount = 6,
        averageScore = 9.5,
        lastActive = "Hôm nay, 09:15",
        status = "Hoàn thành tốt",
        isInternalAccess = true,
        phone = "0977.445.566"
      ),
      UserAccount(
        id = "acc_03",
        orderNumber = 3,
        username = "lehoanghai_146",
        password = "12345@abc",
        militaryId = "QN-14601",
        fullName = "Lê Hoàng Hải",
        rank = "Thiếu tá",
        role = "Chỉ huy trưởng Đảo Trường Sa",
        unit = "Lữ đoàn 146",
        completedLessonsCount = 5,
        totalLessonsCount = 6,
        averageScore = 9.8,
        lastActive = "Hôm qua, 16:45",
        status = "Hoàn thành tốt",
        isInternalAccess = true,
        phone = "0912.334.455"
      ),
      UserAccount(
        id = "acc_04",
        orderNumber = 4,
        username = "tranquoctoan_162",
        password = "12345@abc",
        militaryId = "QN-16203",
        fullName = "Trần Quốc Toản",
        rank = "Đại úy",
        role = "Thuyền trưởng Tàu HQ-015",
        unit = "Lữ đoàn 162",
        completedLessonsCount = 6,
        totalLessonsCount = 6,
        averageScore = 9.5,
        lastActive = "Hôm nay, 09:15",
        status = "Hoàn thành tốt",
        isInternalAccess = true,
        phone = "0903.667.788"
      ),
      UserAccount(
        id = "acc_05",
        orderNumber = 5,
        username = "buixuanthang_pct",
        password = "12345@abc",
        militaryId = "QN-0001",
        fullName = "Bùi Xuân Thắng",
        rank = "Đại tá",
        role = "Chủ nhiệm Chính trị Vùng",
        unit = "Phòng Chính trị Vùng 4",
        completedLessonsCount = 6,
        totalLessonsCount = 6,
        averageScore = 10.0,
        lastActive = "Vừa xong",
        status = "Hoàn thành tốt",
        isInternalAccess = true,
        phone = "0903.111.222"
      ),
      UserAccount(
        id = "acc_06",
        orderNumber = 6,
        username = "hoangminhduc_955",
        password = "12345@abc",
        militaryId = "QN-95501",
        fullName = "Hoàng Minh Đức",
        rank = "Thiếu úy",
        role = "Trưởng ngành Cơ điện Tàu 561",
        unit = "Lữ đoàn 955",
        completedLessonsCount = 3,
        totalLessonsCount = 6,
        averageScore = 7.0,
        lastActive = "5 ngày trước",
        status = "Đang học",
        isInternalAccess = true,
        phone = "0982.554.433"
      ),
      UserAccount(
        id = "acc_07",
        orderNumber = 7,
        username = "dangquoccuong_101",
        password = "12345@abc",
        militaryId = "QN-10101",
        fullName = "Đặng Quốc Cường",
        rank = "Thượng sĩ",
        role = "Tiểu đội trưởng Hải quân Đánh bộ",
        unit = "Lữ đoàn 101",
        completedLessonsCount = 4,
        totalLessonsCount = 6,
        averageScore = 8.0,
        lastActive = "Hôm qua, 14:20",
        status = "Đang học",
        isInternalAccess = true,
        phone = "0966.778.899"
      ),
      UserAccount(
        id = "acc_08",
        orderNumber = 8,
        username = "lamquanghuy_bdkt",
        password = "12345@abc",
        militaryId = "QN-KT01",
        fullName = "Lâm Quang Huy",
        rank = "Thượng úy QNCN",
        role = "Tổ trưởng Kỹ thuật Vũ khí",
        unit = "Trung tâm BĐKT",
        completedLessonsCount = 6,
        totalLessonsCount = 6,
        averageScore = 9.0,
        lastActive = "Hôm nay, 07:50",
        status = "Hoàn thành tốt",
        isInternalAccess = true,
        phone = "0971.889.900"
      )
    )
  }

  fun getLessons(): List<Lesson> {
    return listOf(
      // === PUBLIC LESSONS (Ai cũng xem được) ===
      Lesson(
        id = "bai_1",
        code = "CĐ-01/2026",
        title = "Nâng cao bản lĩnh chính trị, ý chí quyết chiến quyết thắng của cán bộ, chiến sĩ Vùng 4 Hải quân",
        category = "Chuyên đề Sĩ quan & QNCN",
        targetAudience = "Cán bộ, Sĩ quan, QNCN toàn Vùng",
        durationMinutes = 45,
        lecturer = "Đại tá Nguyễn Văn Bách - Phó Tư lệnh Vùng 4",
        videoUrl = "https://example.com/gdct/bai1_vung4.mp4",
        videoDuration = "18:40",
        audioUrl = "https://audio.vung4.vn/bai1_banlinh_chitrong.mp3",
        audioDuration = "18:40",
        audioSpeaker = "Thượng tá Nguyễn Văn A - Ban Tuyên huấn",
        isInternal = false,
        securityLevel = "Công khai",
        docAttachments = listOf(
          DocAttachment(
            id = "doc_1_pdf",
            fileName = "CD01_BanLinhChinhTri_Vung4_Full.pdf",
            fileType = "PDF",
            fileSize = "2.8 MB",
            downloadUrl = "https://docs.vung4.vn/pdf/CD01_Full.pdf",
            pageCount = 18,
            isInternal = false
          ),
          DocAttachment(
            id = "doc_1_docx",
            fileName = "CD01_DeCuong_HuongDanThaoLuan.docx",
            fileType = "DOCX",
            fileSize = "1.4 MB",
            downloadUrl = "https://docs.vung4.vn/docx/CD01_ThaoLuan.docx",
            pageCount = 8,
            isInternal = false
          )
        ),
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
        lecturer = "Thượng tá Nguyễn Văn A - Trưởng ban Tuyên huấn",
        videoUrl = "https://example.com/gdct/bai2_lichsu_vung4.mp4",
        videoDuration = "15:20",
        audioUrl = "https://audio.vung4.vn/bai2_truyenthong_vung4.mp3",
        audioDuration = "15:20",
        audioSpeaker = "Trung tá Lê Hồng Minh - Ban Tuyên huấn",
        isInternal = false,
        securityLevel = "Công khai",
        docAttachments = listOf(
          DocAttachment(
            id = "doc_2_pdf",
            fileName = "CD02_LichSuTruyenThong_Vung4.pdf",
            fileType = "PDF",
            fileSize = "3.2 MB",
            downloadUrl = "https://docs.vung4.vn/pdf/CD02_Full.pdf",
            pageCount = 24,
            isInternal = false
          ),
          DocAttachment(
            id = "doc_2_docx",
            fileName = "CD02_GiaoAn_GiangDay_TruyenThong.docx",
            fileType = "DOCX",
            fileSize = "1.6 MB",
            downloadUrl = "https://docs.vung4.vn/docx/CD02_GiaoAn.docx",
            pageCount = 10,
            isInternal = false
          )
        ),
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
          )
        ),
        sections = listOf(
          LessonSection(
            sectionNumber = 1,
            heading = "1. Mốc son lịch sử thành lập và phát triển",
            content = "Trải qua hơn 50 năm xây dựng, chiến đấu và trưởng thành, các thế hệ cán bộ, chiến sĩ Vùng 4 Hải quân đã lập nên nhiều chiến công xuất sắc, góp phần viết nên trang sử hào hùng của Quân chủng Hải quân Nhân dân Việt Nam anh hùng.",
            keyTakeaway = "Ngày truyền thống Vùng 4 Hải quân là ngày 26 tháng 10 năm 1975."
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
        lecturer = "Đại tá Trần Văn Dũng - Phòng Pháp chế",
        videoUrl = "https://example.com/gdct/bai3_phapluat_bien.mp4",
        videoDuration = "22:15",
        audioUrl = "https://audio.vung4.vn/bai3_phapluat_bien.mp3",
        audioDuration = "22:15",
        audioSpeaker = "Trung tá Hoàng Minh Đức - Binh chủng Tuyên huấn",
        isInternal = false,
        securityLevel = "Công khai",
        docAttachments = listOf(
          DocAttachment(
            id = "doc_3_pdf",
            fileName = "CD03_LuatBienVietNam_UNCLOS1982.pdf",
            fileType = "PDF",
            fileSize = "4.1 MB",
            downloadUrl = "https://docs.vung4.vn/pdf/CD03_LuatBien.pdf",
            pageCount = 32,
            isInternal = false
          )
        ),
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
          )
        ),
        sections = listOf(
          LessonSection(
            sectionNumber = 1,
            heading = "1. Các vùng biển thuộc chủ quyền và quyền tài phán của Việt Nam",
            content = "Luật Biển Việt Nam 2012 quy định rõ ranh giới lãnh hải 12 hải lý, vùng tiếp giáp lãnh hải 24 hải lý, vùng đặc quyền kinh tế 200 hải lý tính từ đường cơ sở.",
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
          )
        )
      ),

      Lesson(
        id = "bai_4",
        code = "CĐ-04/2026",
        title = "Học tập và làm theo tư tưởng, đạo đức, phong cách Hồ Chí Minh về tinh thần nêu gương",
        category = "Học tập & Làm theo Bác",
        targetAudience = "Đảng viên, Cán bộ Đoàn, Cán bộ chỉ huy",
        durationMinutes = 45,
        lecturer = "Đại tá Vũ Đình Hiển - Chính ủy Lữ đoàn 162",
        videoUrl = "https://example.com/gdct/bai4_neuguong_hcm.mp4",
        videoDuration = "20:10",
        audioUrl = "https://audio.vung4.vn/bai4_neuguong_bac.mp3",
        audioDuration = "20:10",
        audioSpeaker = "Thượng tá Nguyễn Văn A - Ban Tuyên huấn",
        isInternal = false,
        securityLevel = "Công khai",
        docAttachments = listOf(
          DocAttachment(
            id = "doc_4_pdf",
            fileName = "CD04_HocTapTuTuongDaoDucBacHo.pdf",
            fileType = "PDF",
            fileSize = "2.9 MB",
            downloadUrl = "https://docs.vung4.vn/pdf/CD04_Full.pdf",
            pageCount = 20,
            isInternal = false
          )
        ),
        summary = "Nêu cao trách nhiệm nêu gương của cán bộ, đảng viên; xây dựng chi bộ '4 tốt', đảng bộ cơ sở '4 tốt'; giữ gìn sự đoàn kết thống nhất trong đơn vị.",
        slides = listOf(
          SlideItem(
            slideNumber = 1,
            title = "1. Tư tưởng Hồ Chí Minh về sự nêu gương của người cán bộ",
            bullets = listOf(
              "\"Một tấm gương sống còn có giá trị hơn một trăm bài diễn văn tuyên truyền\".",
              "Cán bộ cấp trên phải làm gương cho cấp dưới, đảng viên làm gương cho quần chúng."
            ),
            highlightQuote = "\"Cán bộ là cái gốc của mọi công việc. Muôn việc thành công hoặc thất bại, đều do cán bộ tốt hoặc kém.\"",
            note = "Thực hiện 'Nói đi đôi với làm', chống bệnh hình thức, quan liêu."
          )
        ),
        sections = listOf(
          LessonSection(
            sectionNumber = 1,
            heading = "1. Tiêu chí rèn luyện người cán bộ Hải quân mẫu mực",
            content = "Thấm nhuần lời dạy của Bác: 'Ngày trước ta chỉ có đêm và rừng. Ngày nay ta có ngày, có trời, có biển. Bờ biển ta dài, tươi đẹp, ta phải biết giữ gìn lấy nó'.",
            keyTakeaway = "Lời dạy của Bác Hồ là kim chỉ nam cho mọi thế hệ chiến sĩ Hải quân."
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
      ),

      // === NỘI DUNG NỘI BỘ (Chỉ dành cho tài khoản cấp phát bởi Web Quản trị) ===
      Lesson(
        id = "cd_nb_01",
        code = "CĐ-NB-01/2026",
        title = "Nghị quyết chuyên đề Đảng ủy Vùng 4 về nâng cao chất lượng SSCĐ và bảo vệ Quần đảo Trường Sa",
        category = "Lưu hành nội bộ - Đảng ủy Vùng 4",
        targetAudience = "Sĩ quan, Đảng viên, Chính trị viên cấp Hải đội, Tàu",
        durationMinutes = 60,
        lecturer = "Thiếu tướng Bùi Xuân Thắng - Bí thư Đảng ủy, Chính ủy Vùng 4",
        videoUrl = "https://example.com/gdct/cd_nb01_nghiquyet.mp4",
        videoDuration = "28:30",
        audioUrl = "https://audio.vung4.vn/cd_nb01_nghiquyet_audio.mp3",
        audioDuration = "28:30",
        audioSpeaker = "Đại tá Trần Hữu Quân - Trưởng phòng Chính trị",
        isInternal = true,
        securityLevel = "Lưu hành nội bộ",
        docAttachments = listOf(
          DocAttachment(
            id = "doc_nb1_pdf",
            fileName = "NQ_ChuyenDe_DangUy_Vung4_BaoVeTruongSa_2026.pdf",
            fileType = "PDF",
            fileSize = "3.8 MB",
            downloadUrl = "https://docs.vung4.vn/pdf/NQ_DangUy_Vung4.pdf",
            pageCount = 28,
            isInternal = true
          ),
          DocAttachment(
            id = "doc_nb1_docx",
            fileName = "KeHoach_TrienKhai_ChiBo_DangVien.docx",
            fileType = "DOCX",
            fileSize = "1.9 MB",
            downloadUrl = "https://docs.vung4.vn/docx/KeHoach_TrienKhai_ChiBo.docx",
            pageCount = 12,
            isInternal = true
          )
        ),
        summary = "[LƯU HÀNH NỘI BỘ] Quán triệt phương hướng lãnh đạo, các chỉ tiêu CTĐ - CTCT trọng tâm năm 2026 và các phương án hiệp đồng tác chiến bảo vệ chủ quyền Quần đảo Trường Sa trong mọi tình huống.",
        slides = listOf(
          SlideItem(
            slideNumber = 1,
            title = "1. Đánh giá tình hình tác chiến và nhiệm vụ bảo vệ Trường Sa",
            bullets = listOf(
              "Phân tích âm mưu, thủ đoạn mới của các bên tranh chấp trên thực địa khu vực Trường Sa.",
              "Yêu cầu nâng cao khả năng hiệp đồng giữa Tàu chiến đấu, Tên lửa bờ, Không quân Hải quân và Đảo.",
              "Đảm bảo thông tin liên lạc thông suốt trong mọi tình huống chiến tranh công nghệ cao."
            ),
            highlightQuote = "\"Bảo vệ vững chắc chủ quyền biển đảo là nhiệm vụ chính trị trọng yếu hàng đầu của Vùng 4.\"",
            note = "Tài liệu mật nội bộ: Nghiêm cấm sao chép, chụp ảnh đưa lên mạng xã hội."
          ),
          SlideItem(
            slideNumber = 2,
            title = "2. Các chỉ tiêu CTĐ - CTCT xây dựng Chi bộ, Đảng bộ trong sạch vững mạnh",
            bullets = listOf(
              "100% cán bộ, đảng viên an tâm tư tưởng, sẵn sàng nhận và hoàn thành xuất sắc nhiệm vụ đi biển dài ngày.",
              "Không để xảy ra vi phạm kỷ luật nghiêm trọng, mất an toàn thông tin và vi phạm nồng độ cồn.",
              "Xây dựng Đảng bộ Vùng 4 vững mạnh về chính trị, tư tưởng, đạo đức, tổ chức và cán bộ."
            ),
            highlightQuote = "\"Giữ vững sự lãnh đạo tuyệt đối, trực tiếp về mọi mặt của Đảng đối với Quân đội.\"",
            note = "Ghi chép thu hoạch vào Sổ tay Đảng viên cá nhân."
          )
        ),
        sections = listOf(
          LessonSection(
            sectionNumber = 1,
            heading = "1. Trọng tâm công tác SSCĐ của Vùng 4 trong tình hình mới",
            content = "Đảng ủy xác định tiếp tục duy trì nghiêm chế độ trực chỉ huy, trực ban, trực SSCĐ ở tất cả các cấp; chủ động nắm chắc tình hình từ sớm, từ xa, xử lý kịp thời, chính xác mọi tình huống theo đúng đối sách của Thường vụ Quân ủy Trung ương, không để bị động bất ngờ.",
            keyTakeaway = "Tuyệt đối không để bị động, bất ngờ trong mọi tình huống trên vùng biển Vùng 4 quản lý."
          )
        ),
        quizQuestions = listOf(
          QuizQuestion(
            id = 1,
            question = "Mục tiêu then chốt trong Nghị quyết chuyên đề của Đảng ủy Vùng 4 là gì?",
            options = listOf(
              "Bảo vệ vững chắc chủ quyền Quần đảo Trường Sa và giữ vững môi trường hòa bình, ổn định",
              "Mở rộng diện tích đóng quân đơn phương không có kế hoạch",
              "Giảm bớt thời gian trực sẵn sàng chiến đấu trên đảo",
              "Hạn chế tổ chức tuần tra trên biển"
            ),
            correctOptionIndex = 0,
            explanation = "Nghị quyết khẳng định mục tiêu cao nhất là kiên quyết, kiên trì bảo vệ vững chắc chủ quyền Trường Sa, thềm lục địa và giữ vững hòa bình."
          )
        )
      ),

      Lesson(
        id = "cd_nb_02",
        code = "CĐ-NB-02/2026",
        title = "Định hướng tư tưởng và phương pháp xử lý tình huống tâm lý bộ đội tàu chiến đấu trên biển dài ngày",
        category = "Lưu hành nội bộ - Nghiệp vụ CTĐ, CTCT",
        targetAudience = "Chính trị viên tàu, Bí thư chi bộ, Thuyền trưởng",
        durationMinutes = 50,
        lecturer = "Đại tá Lê Hồng Minh - Phòng Chính trị Vùng 4",
        videoUrl = "https://example.com/gdct/cd_nb02_tamly.mp4",
        videoDuration = "24:10",
        audioUrl = "https://audio.vung4.vn/cd_nb02_tamly_audio.mp3",
        audioDuration = "24:10",
        audioSpeaker = "Thượng tá Nguyễn Văn Dương - Lữ đoàn 162",
        isInternal = true,
        securityLevel = "Lưu hành nội bộ",
        docAttachments = listOf(
          DocAttachment(
            id = "doc_nb2_pdf",
            fileName = "SoTay_NghiepVu_ChinhTriVien_TauChienDau.pdf",
            fileType = "PDF",
            fileSize = "3.1 MB",
            downloadUrl = "https://docs.vung4.vn/pdf/SoTay_ChinhTriVien.pdf",
            pageCount = 22,
            isInternal = true
          )
        ),
        summary = "[LƯU HÀNH NỘI BỘ] Cẩm nang nghiệp vụ dành cho Chính trị viên, Bí thư chi bộ trong việc quản lý, định hướng tư tưởng thủy thủ đoàn khi làm nhiệm vụ tuần tra, trực SSCĐ dài ngày trên biển xa.",
        slides = listOf(
          SlideItem(
            slideNumber = 1,
            title = "1. Đặc điểm tâm lý chiến sĩ tàu chiến đấu trong hải trình dài ngày",
            bullets = listOf(
              "Tác động của sóng gió, không gian hẹp, say sóng và xa gia đình đến tâm sinh lý quân nhân.",
              "Hiện tượng căng thẳng (stress) và suy giảm thể lực sau 15-30 ngày liên tục trên biển.",
              "Vai trò của tổ 3 người, tổ tư vấn tâm lý và hoạt động văn hóa tinh thần trên tàu."
            ),
            highlightQuote = "\"Thuyền trưởng là linh hồn chỉ huy tác chiến, Chính trị viên là chỗ dựa tinh thần của toàn tàu.\"",
            note = "Thường xuyên lắng nghe tâm tư, nắm bắt diễn biến tư tưởng ngay từ những biểu hiện nhỏ nhất."
          )
        ),
        sections = listOf(
          LessonSection(
            sectionNumber = 1,
            heading = "1. Quy trình 4 bước giải quyết tư tưởng nảy sinh trên tàu",
            content = "Bước 1: Nắm bắt phát hiện sớm. Bước 2: Phân loại, đánh giá nguyên nhân. Bước 3: Động viên, chia sẻ trực tiếp và phối hợp với cấp ủy, gia đình. Bước 4: Kiểm tra kết quả chuyển biến tư tưởng.",
            keyTakeaway = "Chính trị viên phải như người anh, người chị, người bạn đồng hành tin cậy của chiến sĩ."
          )
        ),
        quizQuestions = listOf(
          QuizQuestion(
            id = 1,
            question = "Quy trình giải quyết tư tưởng trên tàu chiến đấu gồm mấy bước cơ bản?",
            options = listOf("4 bước", "2 bước", "6 bước", "1 bước"),
            correctOptionIndex = 0,
            explanation = "Quy trình gồm 4 bước: Nắm bắt, phân loại đánh giá, động viên giải quyết, kiểm tra theo dõi."
          )
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
        ),
        docxDownloadUrl = "https://docs.vung4.vn/docx/LuatBien_2012.docx",
        pdfDownloadUrl = "https://docs.vung4.vn/pdf/LuatBien_2012.pdf",
        fileSize = "2.1 MB"
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
        ),
        docxDownloadUrl = "https://docs.vung4.vn/docx/10LoiTheDanhDu.docx",
        pdfDownloadUrl = "https://docs.vung4.vn/pdf/10LoiTheDanhDu.pdf",
        fileSize = "0.8 MB"
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
        ),
        docxDownloadUrl = "https://docs.vung4.vn/docx/12DieuKyLuatDanVan.docx",
        pdfDownloadUrl = "https://docs.vung4.vn/pdf/12DieuKyLuatDanVan.pdf",
        fileSize = "0.9 MB"
      )
    )
  }
}
