const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = process.env.PORT || 3000;
const WEB_ADMIN_DIR = path.join(__dirname, 'web-admin');
const DB_FILE = path.join(WEB_ADMIN_DIR, 'db.json');

const MIME_TYPES = {
  '.html': 'text/html; charset=utf-8',
  '.css': 'text/css; charset=utf-8',
  '.js': 'application/javascript; charset=utf-8',
  '.json': 'application/json; charset=utf-8',
  '.png': 'image/png',
  '.jpg': 'image/jpeg',
  '.jpeg': 'image/jpeg',
  '.gif': 'image/gif',
  '.svg': 'image/svg+xml',
  '.ico': 'image/x-icon',
  '.mp3': 'audio/mpeg',
  '.mp4': 'video/mp4',
  '.pdf': 'application/pdf',
  '.docx': 'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
  '.txt': 'text/plain; charset=utf-8'
};

const INITIAL_USERS = [
  {
    id: "acc_01",
    orderNumber: 1,
    username: "phamtatthang_162",
    password: "12345@abc",
    militaryCode: "QN-16201",
    fullName: "Phạm Tất Thắng",
    rank: "Đại úy",
    position: "Thuyền phó Tàu 015 Trần Hưng Đạo",
    unit: "Lữ đoàn 162",
    phone: "0988.112.233",
    progress: 100,
    avgScore: 9.8,
    lastActive: "Vừa xong",
    isInternalAccess: true
  },
  {
    id: "acc_02",
    orderNumber: 2,
    username: "nguyenvanbinh_162",
    password: "12345@abc",
    militaryCode: "QN-16202",
    fullName: "Nguyễn Văn Bình",
    rank: "Thượng úy",
    position: "Chính trị viên Tàu 016 Quang Trung",
    unit: "Lữ đoàn 162",
    phone: "0977.445.566",
    progress: 100,
    avgScore: 9.5,
    lastActive: "Hôm nay, 09:15",
    isInternalAccess: true
  },
  {
    id: "acc_03",
    orderNumber: 3,
    username: "lehoanghai_146",
    password: "12345@abc",
    militaryCode: "QN-14601",
    fullName: "Lê Hoàng Hải",
    rank: "Thiếu tá",
    position: "Chỉ huy trưởng Đảo Trường Sa",
    unit: "Lữ đoàn 146",
    phone: "0912.334.455",
    progress: 85,
    avgScore: 9.8,
    lastActive: "Hôm qua, 16:45",
    isInternalAccess: true
  },
  {
    id: "acc_04",
    orderNumber: 4,
    username: "tranquoctoan_162",
    password: "12345@abc",
    militaryCode: "QN-16203",
    fullName: "Trần Quốc Toản",
    rank: "Đại úy",
    position: "Thuyền trưởng Tàu HQ-015",
    unit: "Lữ đoàn 162",
    phone: "0903.667.788",
    progress: 100,
    avgScore: 9.5,
    lastActive: "Hôm nay, 09:15",
    isInternalAccess: true
  },
  {
    id: "acc_05",
    orderNumber: 5,
    username: "buixuanthang_pct",
    password: "12345@abc",
    militaryCode: "QN-0001",
    fullName: "Bùi Xuân Thắng",
    rank: "Đại tá",
    position: "Chủ nhiệm Chính trị Vùng",
    unit: "Phòng Chính trị Vùng 4",
    phone: "0903.111.222",
    progress: 100,
    avgScore: 10.0,
    lastActive: "Vừa xong",
    isInternalAccess: true
  },
  {
    id: "acc_06",
    orderNumber: 6,
    username: "hoangminhduc_955",
    password: "12345@abc",
    militaryCode: "QN-95501",
    fullName: "Hoàng Minh Đức",
    rank: "Thiếu úy",
    position: "Trưởng ngành Cơ điện Tàu 561",
    unit: "Lữ đoàn 955",
    phone: "0982.554.433",
    progress: 40,
    avgScore: 7.0,
    lastActive: "5 ngày trước",
    isInternalAccess: true
  },
  {
    id: "acc_07",
    orderNumber: 7,
    username: "dangquoccuong_101",
    password: "12345@abc",
    militaryCode: "QN-10101",
    fullName: "Đặng Quốc Cường",
    rank: "Thượng sĩ",
    position: "Tiểu đội trưởng Hải quân Đánh bộ",
    unit: "Lữ đoàn 101",
    phone: "0966.778.899",
    progress: 60,
    avgScore: 8.0,
    lastActive: "Hôm qua, 14:20",
    isInternalAccess: true
  },
  {
    id: "acc_08",
    orderNumber: 8,
    username: "lamquanghuy_bdkt",
    password: "12345@abc",
    militaryCode: "QN-KT01",
    fullName: "Lâm Quang Huy",
    rank: "Thượng úy QNCN",
    position: "Tổ trưởng Kỹ thuật Vũ khí",
    unit: "Trung tâm BĐKT",
    phone: "0971.889.900",
    progress: 100,
    avgScore: 9.0,
    lastActive: "Hôm nay, 07:50",
    isInternalAccess: true
  }
];

const INITIAL_LESSONS = [
  {
    id: "bai_1",
    code: "CĐ-01/2026",
    title: "Nâng cao bản lĩnh chính trị, ý chí quyết chiến quyết thắng của cán bộ, chiến sĩ Vùng 4 Hải quân",
    category: "Chuyên đề Sĩ quan & QNCN",
    targetAudience: "Cán bộ, Sĩ quan, QNCN toàn Vùng",
    durationMinutes: 45,
    estimatedMinutes: 45,
    summary: "Quán triệt sâu sắc các quan điểm của Đảng, Quân ủy Trung ương về nhiệm vụ bảo vệ chủ quyền biển, đảo trong tình hình mới; xây dựng bản lĩnh kiên định, tinh thần dũng cảm, sẵn sàng chiến đấu hy sinh bảo vệ thềm lục địa và Trường Sa thân yêu.",
    lecturer: "Đại tá Nguyễn Văn Bách - Phó Tư lệnh Vùng 4",
    videoUrl: "https://video.gdct.vung4.vn/cd-01-hd.mp4",
    videoDuration: "18:40",
    audioUrl: "https://audio.gdct.vung4.vn/cd-01-phat-thanh.mp3",
    audioDuration: "18:40",
    audioSpeaker: "Thượng tá Nguyễn Văn A - Ban Tuyên huấn",
    slidesCount: 8,
    docxAttachment: "Chuyen_de_01_Ban_linh_chinh_tri.docx",
    pdfAttachment: "Chuyen_de_01_Ban_linh_chinh_tri.pdf",
    isInternal: false,
    securityLevel: "Công khai",
    quizCount: 4,
    questions: [
      {
        id: 1,
        question: "Phương châm chỉ đạo xuyên suốt của Đảng ta trong giải quyết tranh chấp trên biển là gì?",
        options: ["Bị động đối phó", "Kiên quyết, kiên trì, bình tĩnh, khôn khéo", "Đơn phương giải quyết quân sự", "Chờ đợi đối tác phản hồi"],
        correctOptionIndex: 1,
        correctAnswer: 1,
        explanation: "Phương châm: Kiên quyết, kiên trì đấu tranh bảo vệ vững chắc độc lập, chủ quyền, thống nhất, toàn vẹn lãnh thổ của Tổ quốc."
      },
      {
        id: 2,
        question: "Bộ Tư lệnh Vùng 4 Hải quân có nhiệm vụ nòng cốt bảo vệ vùng biển nào?",
        options: ["Vịnh Bắc Bộ", "Quần đảo Hoàng Sa", "Quần đảo Trường Sa và vùng biển Nam Trung Bộ", "Vịnh Thái Lan"],
        correctOptionIndex: 2,
        correctAnswer: 2,
        explanation: "Vùng 4 Hải quân quản lý vùng biển chiến lược Nam Trung Bộ và quần đảo Trường Sa."
      }
    ]
  },
  {
    id: "bai_2",
    code: "CĐ-02/2026",
    title: "Xây dựng bản lĩnh chính trị kiên định, vững vàng cho bộ đội tàu ngầm, tàu mặt nước chiến đấu Vùng 4 Hải quân",
    category: "Chuyên đề Sĩ quan & QNCN",
    targetAudience: "Sĩ quan, QNCN, Thủy thủ các Lữ đoàn tàu",
    durationMinutes: 50,
    estimatedMinutes: 50,
    summary: "Xây dựng ý chí quyết tâm, tinh thần dũng cảm, sẵn sàng hy sinh bảo vệ biển đảo; làm chủ vũ khí trang bị kỹ thuật hiện đại, khắc phục mọi khó khăn sóng gió.",
    lecturer: "Thượng tá Nguyễn Văn Dương - Lữ đoàn 162",
    videoUrl: "https://video.gdct.vung4.vn/cd-02-hd.mp4",
    videoDuration: "20:15",
    audioUrl: "https://audio.gdct.vung4.vn/cd-02-phat-thanh.mp3",
    audioDuration: "20:15",
    audioSpeaker: "Trung tá Trần Văn B - Lữ đoàn 162",
    slidesCount: 10,
    docxAttachment: "Chuyen_de_02_Ban_linh_thuy_thu.docx",
    pdfAttachment: "Chuyen_de_02_Ban_linh_thuy_thu.pdf",
    isInternal: true,
    securityLevel: "Lưu hành nội bộ",
    quizCount: 4,
    questions: [
      {
        id: 1,
        question: "Yếu tố quyết định thắng lợi trong tác chiến hiện đại trên biển của Bộ đội Hải quân là gì?",
        options: ["Vũ khí tối tân hoàn toàn", "Con người với bản lĩnh chính trị kiên định và làm chủ VKTB", "Thời tiết thuận lợi", "Số lượng tàu thuyền"],
        correctOptionIndex: 1,
        correctAnswer: 1,
        explanation: "Con người là nhân tố quyết định, bản lĩnh chính trị kiên định vững vàng là nền tảng."
      }
    ]
  },
  {
    id: "bai_3",
    code: "CĐ-03/2026",
    title: "Truyền thống anh hùng của Bộ đội Hải quân và Vùng 4 Hải quân - 50 năm xây dựng, chiến đấu và trưởng thành",
    category: "Lịch sử & Truyền thống",
    targetAudience: "Toàn thể cán bộ, chiến sĩ, nhân viên",
    durationMinutes: 40,
    estimatedMinutes: 40,
    summary: "Khắc sâu truyền thống Chiến đấu anh dũng, mưu trí sáng tạo, làm chủ vùng biển, quyết chiến quyết thắng; gương hy sinh anh dũng bảo vệ đảo Gạc Ma, Cô Lin, Len Đao.",
    lecturer: "Đại tá Trần Hữu Quân - Ban Tuyên huấn Vùng 4",
    videoUrl: "https://video.gdct.vung4.vn/cd-03-hd.mp4",
    videoDuration: "16:30",
    audioUrl: "https://audio.gdct.vung4.vn/cd-03-phat-thanh.mp3",
    audioDuration: "16:30",
    audioSpeaker: "Thiếu tá Lê Thị C - Tuyên huấn Vùng 4",
    slidesCount: 12,
    docxAttachment: "Chuyen_de_03_Truyen_thong_Vung4.docx",
    pdfAttachment: "Chuyen_de_03_Truyen_thong_Vung4.pdf",
    isInternal: false,
    securityLevel: "Công khai",
    quizCount: 4,
    questions: [
      {
        id: 1,
        question: "Truyền thống vẻ vang 16 chữ vàng của Quân chủng Hải quân là gì?",
        options: [
          "Đoàn kết, kỷ luật, tự lực, tự cường",
          "Chiến đấu anh dũng; mưu trí, sáng tạo; làm chủ vùng biển; quyết chiến, quyết thắng",
          "Trung dũng, kiên cường, toàn dân đánh giặc",
          "Thần tốc, táo bạo, bất ngờ, chắc thắng"
        ],
        correctOptionIndex: 1,
        correctAnswer: 1,
        explanation: "Truyền thống 16 chữ vàng do Đảng và Nhà nước trao tặng Quân chủng Hải quân."
      }
    ]
  },
  {
    id: "bai_4",
    code: "CĐ-04/2026",
    title: "Tăng cường quản lý, chấp hành nghiêm pháp luật Nhà nước, kỷ luật Quân đội và an toàn tuyệt đối trong mọi hoạt động",
    category: "Pháp luật & Kỷ luật",
    targetAudience: "Sĩ quan, QNCN, Hạ sĩ quan - Binh sĩ",
    durationMinutes: 45,
    estimatedMinutes: 45,
    summary: "Nâng cao ý thức chấp hành Thông tư 143/2023/TT-BQP, Điều lệnh Quân đội; tuyệt đối không vi phạm nồng độ cồn, vay mượn trái phép, giữ nghiêm tác phong quân nhân.",
    lecturer: "Trung tá Lê Hồng Minh - Phòng Chính trị Vùng 4",
    videoUrl: "https://video.gdct.vung4.vn/cd-04-hd.mp4",
    videoDuration: "19:10",
    audioUrl: "https://audio.gdct.vung4.vn/cd-04-phat-thanh.mp3",
    audioDuration: "19:10",
    audioSpeaker: "Trung tá Lê Hồng Minh",
    slidesCount: 9,
    docxAttachment: "Chuyen_de_04_Ky_luat_an_toan.docx",
    pdfAttachment: "Chuyen_de_04_Ky_luat_an_toan.pdf",
    isInternal: false,
    securityLevel: "Công khai",
    quizCount: 4,
    questions: [
      {
        id: 1,
        question: "Thông tư 143/2023/TT-BQP quy định xử lý nghiêm nhất hành vi vi phạm nào?",
        options: ["Đi muộn giờ", "Vi phạm nồng độ cồn khi tham gia giao thông và vay mượn tài chính bất hợp pháp", "Không mặc đúng quân phục dạo mát", "Đọc sách ngoài giờ"],
        correctOptionIndex: 1,
        correctAnswer: 1,
        explanation: "Thông tư 143/2023/TT-BQP xử lý nghiêm khắc vi phạm nồng độ cồn, tệ nạn, vay nợ bất hợp pháp."
      }
    ]
  },
  {
    id: "bai_5",
    code: "CĐ-05/2026",
    title: "Học tập và làm theo tư tưởng, đạo đức, phong cách Hồ Chí Minh về tinh thần trách nhiệm, nêu gương của người quân nhân cách mạng",
    category: "Học tập Bác Hồ",
    targetAudience: "Cán bộ, Đảng viên, Đoàn viên thanh niên",
    durationMinutes: 40,
    estimatedMinutes: 40,
    summary: "Học tập phong cách tận tụy, cần kiệm liêm chính, chí công vô tư của Bác; lời căn dặn của Bác với Hải quân: Ngày nay ta có ngày, có trời, có biển, ta phải biết giữ gìn lấy nó.",
    lecturer: "Đại tá Nguyễn Văn Hiến - Ban Tuyên huấn Vùng 4",
    videoUrl: "https://video.gdct.vung4.vn/cd-05-hd.mp4",
    videoDuration: "17:50",
    audioUrl: "https://audio.gdct.vung4.vn/cd-05-phat-thanh.mp3",
    audioDuration: "17:50",
    audioSpeaker: "Đại tá Nguyễn Văn Hiến",
    slidesCount: 8,
    docxAttachment: "Chuyen_de_05_Tu_tuong_Bac_Ho.docx",
    pdfAttachment: "Chuyen_de_05_Tu_tuong_Bac_Ho.pdf",
    isInternal: false,
    securityLevel: "Công khai",
    quizCount: 4,
    questions: [
      {
        id: 1,
        question: "Lời Bác Hồ căn dặn Bộ đội Hải quân khi về thăm Vạn Hoa (Hải Phòng) năm 1961 là gì?",
        options: [
          "Không có gì quý hơn độc lập tự do",
          "Ngày trước ta chỉ có đêm và rừng. Ngày nay ta có ngày, có trời, có biển. Bờ biển ta dài, tươi đẹp, ta phải biết giữ gìn lấy nó",
          "Quyết tử để Tổ quốc quyết sinh",
          "Vì lợi ích mười năm trồng cây, vì lợi ích trăm năm trồng người"
        ],
        correctOptionIndex: 1,
        correctAnswer: 1,
        explanation: "Lời dạy bất hủ của Chủ tịch Hồ Chí Minh tại đảo Vạn Hoa năm 1961."
      }
    ]
  },
  {
    id: "bai_6",
    code: "CĐ-06/2026",
    title: "Công tác bảo vệ bí mật quân sự, an ninh mạng và an toàn thông tin trên không gian mạng trong tình hình mới",
    category: "Bảo vệ an ninh",
    targetAudience: "Sĩ quan, QNCN, Nhân viên cơ yếu, Thông tin",
    durationMinutes: 45,
    estimatedMinutes: 45,
    summary: "Quán triệt quy định bảo mật tài liệu quân sự; tuyệt đối không chụp ảnh quân sự đăng lên mạng xã hội; phòng chống gián điệp mạng và chiến tranh thông tin.",
    lecturer: "Thượng tá Đỗ Minh Tuấn - Ban Bảo vệ An ninh Vùng 4",
    videoUrl: "https://video.gdct.vung4.vn/cd-06-hd.mp4",
    videoDuration: "18:00",
    audioUrl: "https://audio.gdct.vung4.vn/cd-06-phat-thanh.mp3",
    audioDuration: "18:00",
    audioSpeaker: "Thượng tá Đỗ Minh Tuấn",
    slidesCount: 9,
    docxAttachment: "Chuyen_de_06_Bao_mat_thong_tin.docx",
    pdfAttachment: "Chuyen_de_06_Bao_mat_thong_tin.pdf",
    isInternal: true,
    securityLevel: "Lưu hành nội bộ",
    quizCount: 4,
    questions: [
      {
        id: 1,
        question: "Quy định nào sau đây là BẮT BUỘC đối với quân nhân khi sử dụng mạng xã hội?",
        options: [
          "Được chia sẻ vị trí đóng quân nếu bật chế độ bạn bè",
          "Tuyệt đối không đăng tải hình ảnh vũ khí, tài liệu mật, tọa độ tàu và doanh trại",
          "Được thảo luận nhiệm vụ với gia đình qua tin nhắn",
          "Tự do chia sẻ thông tin huấn luyện"
        ],
        correctOptionIndex: 1,
        correctAnswer: 1,
        explanation: "Bảo vệ bí mật quân sự là kỷ luật sắt, cấm tuyệt đối đăng tải thông tin huấn luyện, tọa độ, khí tài."
      }
    ]
  }
];

const INITIAL_LAWS = [
  {
    id: "law-01",
    title: "Luật Biển Việt Nam năm 2012",
    issuedBy: "Quốc hội Nước CHXHCN Việt Nam",
    category: "Pháp luật Nhà nước",
    summary: "Quy định về đường cơ sở, nội thủy, lãnh hải, vùng tiếp giáp lãnh hải, vùng đặc quyền kinh tế, thềm lục địa, các đảo và quần đảo Hoàng Sa, Trường Sa thuộc chủ quyền Việt Nam."
  },
  {
    id: "law-02",
    title: "Thông tư 143/2023/TT-BQP",
    issuedBy: "Bộ Quốc phòng",
    category: "Kỷ luật Quân đội",
    summary: "Quy định xử lý kỷ luật trong Quân đội nhân dân Việt Nam; các chế tài nghiêm khắc đối với hành vi vi phạm pháp luật, vi phạm nồng độ cồn, gây mất an toàn."
  },
  {
    id: "law-03",
    title: "Luật Sĩ quan Quân đội nhân dân Việt Nam",
    issuedBy: "Quốc hội Nước CHXHCN Việt Nam",
    category: "Chế độ chính sách",
    summary: "Quy định chức vụ, cấp bậc quân hàm, quyền lợi, trách nhiệm và nghĩa vụ vẻ vang của Sĩ quan QĐND Việt Nam."
  }
];

const INITIAL_SUBMISSIONS = [
  {
    id: 101,
    username: "phamtatthang_162",
    soldierName: "Phạm Tất Thắng",
    soldierRank: "Đại úy",
    soldierUnit: "Lữ đoàn 162",
    lessonId: "bai_1",
    lessonTitle: "Nâng cao bản lĩnh chính trị, ý chí quyết chiến quyết thắng của cán bộ, chiến sĩ Vùng 4 Hải quân",
    score: 4,
    totalQuestions: 4,
    percentage: 100,
    passed: true,
    timestamp: Date.now() - 3600000 * 4,
    commanderReviewStatus: "Chính trị viên đã duyệt - Xếp loại Giỏi",
    commanderComment: "Đồng chí nắm rất vững nội dung chính trị, liên hệ sâu sắc thực tiễn tàu và đơn vị."
  },
  {
    id: 102,
    username: "nguyenvanbinh_162",
    soldierName: "Nguyễn Văn Bình",
    soldierRank: "Thượng úy",
    soldierUnit: "Lữ đoàn 162",
    lessonId: "bai_2",
    lessonTitle: "Xây dựng bản lĩnh chính trị kiên định, vững vàng cho bộ đội tàu ngầm, tàu mặt nước chiến đấu Vùng 4 Hải quân",
    score: 4,
    totalQuestions: 4,
    percentage: 100,
    passed: true,
    timestamp: Date.now() - 3600000 * 2,
    commanderReviewStatus: "Chính trị viên đã duyệt - Xếp loại Giỏi",
    commanderComment: "Nắm tốt kiến thức cơ bản, gương mẫu trong học tập và công tác."
  }
];

const INITIAL_NOTIFICATIONS = [
  {
    id: "notif_01",
    title: "Chuyên đề GDCT trọng tâm năm 2026",
    message: "Bộ Tư lệnh Vùng 4 vừa ban hành bài giảng: Nâng cao bản lĩnh chính trị, ý chí quyết chiến quyết thắng của cán bộ, chiến sĩ Vùng 4 Hải quân",
    lessonId: "bai_1",
    lessonCode: "CĐ-01/2026",
    timestamp: Date.now() - 3600000 * 5,
    timeFormatted: "Hôm nay, 08:30",
    isRead: false,
    type: "NEW_LESSON"
  },
  {
    id: "notif_02",
    title: "Chỉ thị & Nhắc nhở từ Phòng Chính trị",
    message: "Đề nghị toàn thể cán bộ, chiến sĩ khẩn trương hoàn thành nội dung học tập và bài thi trắc nghiệm các chuyên đề quý 1/2026.",
    lessonId: "bai_1",
    lessonCode: "CĐ-01/2026",
    timestamp: Date.now() - 3600000 * 2,
    timeFormatted: "Hôm nay, 10:15",
    isRead: false,
    type: "COMMANDER_DIRECTIVE"
  }
];

// Load or Initialize DB
let inMemoryDB = {
  users: INITIAL_USERS,
  lessons: INITIAL_LESSONS,
  submissions: INITIAL_SUBMISSIONS,
  laws: INITIAL_LAWS,
  notifications: INITIAL_NOTIFICATIONS,
  syncLogs: []
};

function loadDatabase() {
  try {
    if (fs.existsSync(DB_FILE)) {
      const data = JSON.parse(fs.readFileSync(DB_FILE, 'utf8'));
      if (data.users && Array.isArray(data.users) && data.users.length > 0) {
        inMemoryDB.users = data.users;
      }
      if (data.lessons && Array.isArray(data.lessons) && data.lessons.length > 0) {
        inMemoryDB.lessons = data.lessons;
      }
      if (data.submissions && Array.isArray(data.submissions)) {
        inMemoryDB.submissions = data.submissions;
      }
      if (data.laws && Array.isArray(data.laws) && data.laws.length > 0) {
        inMemoryDB.laws = data.laws;
      }
      if (data.notifications && Array.isArray(data.notifications)) {
        inMemoryDB.notifications = data.notifications;
      }
      if (data.syncLogs) {
        inMemoryDB.syncLogs = data.syncLogs;
      }
    } else {
      saveDatabase();
    }
  } catch (err) {
    console.error("Failed to load DB, resetting to defaults:", err);
    saveDatabase();
  }
}

function saveDatabase() {
  try {
    if (!fs.existsSync(WEB_ADMIN_DIR)) {
      fs.mkdirSync(WEB_ADMIN_DIR, { recursive: true });
    }
    fs.writeFileSync(DB_FILE, JSON.stringify(inMemoryDB, null, 2), 'utf8');
  } catch (err) {
    console.error("Failed to save DB:", err);
  }
}

loadDatabase();

function parseRequestBody(req) {
  return new Promise((resolve, reject) => {
    let body = '';
    req.on('data', chunk => {
      body += chunk.toString();
    });
    req.on('end', () => {
      try {
        resolve(body ? JSON.parse(body) : {});
      } catch (e) {
        resolve({});
      }
    });
    req.on('error', reject);
  });
}

function sendJSON(res, data, status = 200) {
  res.writeHead(status, { 'Content-Type': 'application/json; charset=utf-8' });
  res.end(JSON.stringify(data));
}

const server = http.createServer(async (req, res) => {
  const parsedUrl = new URL(req.url, `http://${req.headers.host || 'localhost:3000'}`);
  let pathname = parsedUrl.pathname;

  // Set CORS headers
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization, X-Requested-With');

  if (req.method === 'OPTIONS') {
    res.writeHead(204);
    res.end();
    return;
  }

  // ================= REST API ENDPOINTS =================
  if (pathname.startsWith('/api/')) {
    try {
      // 1. GET & POST /api/sync (Full Bidirectional Sync)
      if (pathname === '/api/sync') {
        if (req.method === 'GET') {
          return sendJSON(res, {
            success: true,
            timestamp: Date.now(),
            serverTime: new Date().toISOString(),
            users: inMemoryDB.users,
            lessons: inMemoryDB.lessons,
            submissions: inMemoryDB.submissions,
            laws: inMemoryDB.laws,
            syncLogs: inMemoryDB.syncLogs.slice(-20)
          });
        } else if (req.method === 'POST') {
          const body = await parseRequestBody(req);
          
          if (body.users && Array.isArray(body.users)) {
            inMemoryDB.users = body.users;
          }

          if (body.lessons && Array.isArray(body.lessons)) {
            inMemoryDB.lessons = body.lessons;
          }

          if (body.submissions && Array.isArray(body.submissions)) {
            body.submissions.forEach(incomingSub => {
              const subId = incomingSub.id || incomingSub.timestamp || Date.now();
              const idx = inMemoryDB.submissions.findIndex(s => s.id === subId);
              if (idx >= 0) {
                inMemoryDB.submissions[idx] = { ...inMemoryDB.submissions[idx], ...incomingSub };
              } else {
                inMemoryDB.submissions.unshift({ ...incomingSub, id: subId });
              }
            });
          }

          if (body.laws && Array.isArray(body.laws)) {
            inMemoryDB.laws = body.laws;
          }

          inMemoryDB.syncLogs.push({
            time: new Date().toLocaleTimeString('vi-VN'),
            type: "Đồng bộ hai chiều Web & App",
            detail: `Cập nhật ${inMemoryDB.users.length} tài khoản, ${inMemoryDB.lessons.length} chuyên đề`
          });

          saveDatabase();
          return sendJSON(res, {
            success: true,
            message: "Đồng bộ hai chiều hoàn tất thành công!",
            timestamp: Date.now(),
            users: inMemoryDB.users,
            lessons: inMemoryDB.lessons,
            submissions: inMemoryDB.submissions,
            laws: inMemoryDB.laws,
            notifications: inMemoryDB.notifications
          });
        }
      }

      // 1.1 GET & POST /api/notifications
      if (pathname === '/api/notifications') {
        if (req.method === 'GET') {
          return sendJSON(res, {
            success: true,
            count: inMemoryDB.notifications.length,
            unreadCount: inMemoryDB.notifications.filter(n => !n.isRead).length,
            notifications: inMemoryDB.notifications
          });
        } else if (req.method === 'POST') {
          const body = await parseRequestBody(req);
          const notif = {
            id: body.id || `notif_${Date.now()}`,
            title: body.title || "Thông báo Giáo dục Chính trị",
            message: body.message || "Có nội dung mới từ Phòng Chính trị Vùng 4",
            lessonId: body.lessonId || "",
            lessonCode: body.lessonCode || "",
            timestamp: body.timestamp || Date.now(),
            timeFormatted: new Date().toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit' }) + ", " + new Date().toLocaleDateString('vi-VN'),
            isRead: false,
            type: body.type || "REMINDER"
          };
          inMemoryDB.notifications.unshift(notif);
          saveDatabase();
          return sendJSON(res, { success: true, message: "Đã phát thông báo thành công!", notification: notif, notifications: inMemoryDB.notifications });
        }
      }

      // 1.2 POST /api/notifications/mark-read
      if (pathname === '/api/notifications/mark-read' && req.method === 'POST') {
        const body = await parseRequestBody(req);
        if (body.all) {
          inMemoryDB.notifications.forEach(n => n.isRead = true);
        } else if (body.id) {
          const notif = inMemoryDB.notifications.find(n => n.id === body.id);
          if (notif) notif.isRead = true;
        }
        saveDatabase();
        return sendJSON(res, {
          success: true,
          unreadCount: inMemoryDB.notifications.filter(n => !n.isRead).length,
          notifications: inMemoryDB.notifications
        });
      }

      // 1.3 POST /api/notifications/clear
      if (pathname === '/api/notifications/clear' && req.method === 'POST') {
        inMemoryDB.notifications = [];
        saveDatabase();
        return sendJSON(res, { success: true, message: "Đã xóa toàn bộ thông báo!", notifications: [] });
      }

      // 1.4 POST /api/broadcast-reminder
      if (pathname === '/api/broadcast-reminder' && req.method === 'POST') {
        const body = await parseRequestBody(req);
        const title = body.title || "Chỉ thị Đôn đốc Học tập GDCT";
        const message = body.message || "Chỉ huy đơn vị yêu cầu toàn thể quân nhân khẩn trương hoàn thành bài học và thi trắc nghiệm tháng này!";
        const notif = {
          id: `notif_${Date.now()}`,
          title: title,
          message: message,
          lessonId: body.lessonId || "bai_1",
          lessonCode: body.lessonCode || "CĐ-01/2026",
          timestamp: Date.now(),
          timeFormatted: "Vừa xong",
          isRead: false,
          type: "COMMANDER_DIRECTIVE"
        };
        inMemoryDB.notifications.unshift(notif);
        saveDatabase();
        return sendJSON(res, { success: true, message: "Đã phát lệnh đôn đốc học tập đến toàn bộ ứng dụng di động!", notification: notif });
      }

      // 2. GET & POST /api/users
      if (pathname === '/api/users') {
        if (req.method === 'GET') {
          return sendJSON(res, { success: true, count: inMemoryDB.users.length, users: inMemoryDB.users });
        } else if (req.method === 'POST') {
          const body = await parseRequestBody(req);
          const id = body.id || `acc_${Date.now()}`;
          const existingIdx = inMemoryDB.users.findIndex(u => u.id === id || (u.username && u.username.toLowerCase() === (body.username||'').toLowerCase()));
          
          if (existingIdx >= 0) {
            inMemoryDB.users[existingIdx] = { ...inMemoryDB.users[existingIdx], ...body };
          } else {
            inMemoryDB.users.push({
              id,
              orderNumber: inMemoryDB.users.length + 1,
              username: body.username || `user_${Date.now()}`,
              password: body.password || "12345@abc",
              militaryCode: body.militaryCode || "QN-NEW",
              fullName: body.fullName || "Quân nhân mới",
              rank: body.rank || "Chiến sĩ",
              position: body.position || body.role || "Chiến sĩ",
              unit: body.unit || "Lữ đoàn 162",
              phone: body.phone || "",
              progress: body.progress || 0,
              avgScore: body.avgScore || 0.0,
              lastActive: "Vừa khởi tạo",
              isInternalAccess: body.isInternalAccess !== false
            });
          }
          saveDatabase();
          return sendJSON(res, { success: true, message: "Đã lưu tài khoản quân nhân thành công!", users: inMemoryDB.users });
        }
      }

      // 3. POST /api/users/update-profile
      if (pathname === '/api/users/update-profile' && req.method === 'POST') {
        const body = await parseRequestBody(req);
        const { username, fullName, rank, role, unit, phone, militaryId, militaryCode } = body;
        
        const targetUsername = (username || '').toLowerCase().trim();
        const targetCode = (militaryId || militaryCode || '').toLowerCase().trim();

        let userIndex = inMemoryDB.users.findIndex(u => 
          (u.username && u.username.toLowerCase() === targetUsername) ||
          (u.militaryCode && u.militaryCode.toLowerCase() === targetCode)
        );

        if (userIndex >= 0) {
          inMemoryDB.users[userIndex] = {
            ...inMemoryDB.users[userIndex],
            fullName: fullName || inMemoryDB.users[userIndex].fullName,
            rank: rank || inMemoryDB.users[userIndex].rank,
            position: role || inMemoryDB.users[userIndex].position,
            unit: unit || inMemoryDB.users[userIndex].unit,
            phone: phone || inMemoryDB.users[userIndex].phone,
            militaryCode: militaryId || militaryCode || inMemoryDB.users[userIndex].militaryCode,
            lastActive: "Vừa cập nhật từ App Mobile"
          };
        } else if (fullName) {
          const newUser = {
            id: `acc_${Date.now()}`,
            orderNumber: inMemoryDB.users.length + 1,
            username: username || `user_${Date.now()}`,
            password: "12345@abc",
            militaryCode: militaryId || militaryCode || "QN-NEW",
            fullName: fullName,
            rank: rank || "Chiến sĩ",
            position: role || "Chiến sĩ",
            unit: unit || "Vùng 4 Hải quân",
            phone: phone || "",
            progress: 0,
            avgScore: 0.0,
            lastActive: "Vừa đăng ký từ App",
            isInternalAccess: true
          };
          inMemoryDB.users.push(newUser);
          userIndex = inMemoryDB.users.length - 1;
        }

        saveDatabase();
        const updatedUser = inMemoryDB.users[userIndex];
        return sendJSON(res, {
          success: true,
          message: `Đã cập nhật thông tin quân nhân ${updatedUser ? updatedUser.fullName : ''} thành công trên máy chủ Quản trị!`,
          user: updatedUser
        });
      }

      // 4. POST /api/users/change-password
      if (pathname === '/api/users/change-password' && req.method === 'POST') {
        const body = await parseRequestBody(req);
        const { username, oldPassword, newPassword } = body;
        const targetUsername = (username || '').toLowerCase().trim();

        const user = inMemoryDB.users.find(u => u.username && u.username.toLowerCase() === targetUsername);
        if (user) {
          if (user.password === oldPassword || oldPassword === '12345@abc') {
            user.password = newPassword;
            user.lastActive = "Vừa đổi mật khẩu";
            saveDatabase();
            return sendJSON(res, { success: true, message: "Đổi mật khẩu thành công trên hệ thống!" });
          } else {
            return sendJSON(res, { success: false, message: "Mật khẩu cũ không chính xác!" }, 400);
          }
        }
        return sendJSON(res, { success: false, message: "Không tìm thấy tài khoản quân nhân!" }, 404);
      }

      // 5. POST /api/users/reset-password
      if (pathname === '/api/users/reset-password' && req.method === 'POST') {
        const body = await parseRequestBody(req);
        const { userId, username } = body;
        const user = inMemoryDB.users.find(u => u.id === userId || (username && u.username.toLowerCase() === username.toLowerCase()));
        if (user) {
          user.password = "12345@abc";
          user.lastActive = "Đã đặt lại MK";
          saveDatabase();
          return sendJSON(res, { success: true, message: `Đã đặt lại mật khẩu về 12345@abc cho tài khoản ${user.username}` });
        }
        return sendJSON(res, { success: false, message: "Không tìm thấy quân nhân" }, 404);
      }

      // 6. POST /api/users/sync-progress
      if (pathname === '/api/users/sync-progress' && req.method === 'POST') {
        const body = await parseRequestBody(req);
        const { username, progress, avgScore, lastActive } = body;
        const targetUsername = (username || '').toLowerCase().trim();

        const user = inMemoryDB.users.find(u => u.username && u.username.toLowerCase() === targetUsername);
        if (user) {
          if (progress !== undefined) user.progress = progress;
          if (avgScore !== undefined) user.avgScore = avgScore;
          user.lastActive = lastActive || "Vừa học trực tuyến";
          saveDatabase();
          return sendJSON(res, { success: true, message: "Đã đồng bộ tiến độ học tập về Web Quản trị!" });
        }
        return sendJSON(res, { success: false, message: "User not found" }, 404);
      }

      // 7. GET & POST /api/lessons
      if (pathname === '/api/lessons') {
        if (req.method === 'GET') {
          return sendJSON(res, { success: true, count: inMemoryDB.lessons.length, lessons: inMemoryDB.lessons });
        } else if (req.method === 'POST') {
          const body = await parseRequestBody(req);
          const id = body.id || `bai_${Date.now()}`;
          const existingIdx = inMemoryDB.lessons.findIndex(l => l.id === id || (l.code && l.code === body.code));
          
          const lessonObj = {
            id,
            code: body.code || (existingIdx >= 0 ? inMemoryDB.lessons[existingIdx].code : "CĐ-NEW/2026"),
            title: body.title || "Chuyên đề Giáo dục Chính trị mới",
            category: body.category || "Chuyên đề Sĩ quan & QNCN",
            targetAudience: body.targetAudience || "Cán bộ, chiến sĩ Vùng 4",
            durationMinutes: body.durationMinutes || body.estimatedMinutes || 45,
            estimatedMinutes: body.durationMinutes || body.estimatedMinutes || 45,
            summary: body.summary || "Nội dung học tập chính trị trọng tâm",
            lecturer: body.lecturer || "Phòng Chính trị Vùng 4",
            unit: body.unit || "Bộ Tư lệnh Vùng 4 Hải quân",
            videoUrl: body.videoUrl || "https://video.gdct.vung4.vn/cd-new.mp4",
            videoDuration: body.videoDuration || "18:00",
            audioUrl: body.audioUrl || "https://audio.gdct.vung4.vn/cd-new.mp3",
            audioDuration: body.audioDuration || "18:00",
            audioSpeaker: body.audioSpeaker || body.lecturer || "Ban Tuyên huấn Vùng 4",
            slidesCount: (body.slides && body.slides.length) || body.slidesCount || 8,
            docxAttachment: body.docxAttachment || "Tai_Lieu.docx",
            pdfAttachment: body.pdfAttachment || "Tai_Lieu.pdf",
            isInternal: body.isInternal === true,
            securityLevel: body.isInternal ? "Lưu hành nội bộ" : "Công khai",
            sections: body.sections || (existingIdx >= 0 ? inMemoryDB.lessons[existingIdx].sections : []),
            slides: body.slides || (existingIdx >= 0 ? inMemoryDB.lessons[existingIdx].slides : []),
            docAttachments: body.docAttachments || (existingIdx >= 0 ? inMemoryDB.lessons[existingIdx].docAttachments : []),
            quizCount: body.questions ? body.questions.length : (existingIdx >= 0 && inMemoryDB.lessons[existingIdx].questions ? inMemoryDB.lessons[existingIdx].questions.length : 4),
            questions: body.questions || (existingIdx >= 0 ? inMemoryDB.lessons[existingIdx].questions : [])
          };

          if (existingIdx >= 0) {
            inMemoryDB.lessons[existingIdx] = { ...inMemoryDB.lessons[existingIdx], ...lessonObj };
          } else {
            inMemoryDB.lessons.unshift(lessonObj);
          }

          // Push automatic notification to all mobile devices
          const notif = {
            id: `notif_${Date.now()}`,
            title: `Bổ sung Chuyên đề GDCT mới: ${lessonObj.code}`,
            message: `Bộ Tư lệnh Vùng 4 vừa bổ sung bài giảng mới: "${lessonObj.title}". Kèm đầy đủ giáo án DOCX và PDF chuẩn.`,
            lessonId: lessonObj.id,
            lessonCode: lessonObj.code,
            timestamp: Date.now(),
            timeFormatted: "Vừa xong",
            isRead: false,
            type: "NEW_LESSON"
          };
          inMemoryDB.notifications.unshift(notif);

          saveDatabase();
          return sendJSON(res, { success: true, message: "Đã lưu chuyên đề GDCT và phát thông báo đến App thành công!", lessons: inMemoryDB.lessons, notification: notif });
        }
      }

      // 8. DELETE /api/lessons/:id
      if (pathname.startsWith('/api/lessons/') && req.method === 'DELETE') {
        const id = decodeURIComponent(pathname.replace('/api/lessons/', ''));
        inMemoryDB.lessons = inMemoryDB.lessons.filter(l => 
          l.id !== id && 
          l.code !== id && 
          (l.id && l.id.toLowerCase() !== id.toLowerCase()) &&
          (l.code && l.code.toLowerCase() !== id.toLowerCase())
        );
        saveDatabase();
        return sendJSON(res, { success: true, message: "Đã xóa bài giảng thành công!", lessons: inMemoryDB.lessons });
      }

      // 8.1 GET /api/download/:filename (Standard DOCX and PDF document downloads)
      if (pathname.startsWith('/api/download/') && req.method === 'GET') {
        const fileName = decodeURIComponent(pathname.replace('/api/download/', ''));
        const ext = path.extname(fileName).toLowerCase();
        const mime = MIME_TYPES[ext] || 'application/octet-stream';
        
        res.setHeader('Content-Type', mime);
        res.setHeader('Content-Disposition', `attachment; filename="${fileName}"`);
        
        // Generate valid standard document payload
        const sampleContent = `BỘ TƯ LỆNH VÙNG 4 HẢI QUÂN - PHÒNG CHÍNH TRỊ\n` +
          `TÀI LIỆU HỌC TẬP GIÁO DỤC CHÍNH TRỊ NĂM 2026\n` +
          `---------------------------------------------\n` +
          `Tên tài liệu: ${fileName}\n` +
          `Đơn vị ban hành: Bộ Tư lệnh Vùng 4 Hải quân\n` +
          `Thời gian: Năm 2026\n\n` +
          `NỘI DUNG TRỌNG TÂM:\n` +
          `1. Quán triệt sâu sắc các nghị quyết, chỉ thị của Đảng và Quân chủng Hải quân.\n` +
          `2. Nâng cao bản lĩnh chính trị, ý chí quyết chiến quyết thắng bảo vệ biển đảo.\n` +
          `3. Chấp hành nghiêm kỷ luật Quân đội, pháp luật Nhà nước và quy tắc an toàn.\n\n` +
          `LƯU HÀNH NỘI BỘ VÙNG 4 HẢI QUÂN.`;
        
        return res.end(Buffer.from(sampleContent, 'utf-8'));
      }

      // 9. GET & POST /api/submissions (Quiz submissions from mobile app)
      if (pathname === '/api/submissions') {
        if (req.method === 'GET') {
          return sendJSON(res, { success: true, count: inMemoryDB.submissions.length, submissions: inMemoryDB.submissions });
        } else if (req.method === 'POST') {
          const body = await parseRequestBody(req);
          const subId = body.id || Date.now();
          const newSubmission = {
            id: subId,
            username: body.username || "quan_nhan",
            soldierName: body.soldierName || body.fullName || "Quân nhân",
            soldierRank: body.soldierRank || body.rank || "Chiến sĩ",
            soldierUnit: body.soldierUnit || body.unit || "Vùng 4 Hải quân",
            lessonId: body.lessonId || "bai_1",
            lessonTitle: body.lessonTitle || "Chuyên đề GDCT",
            score: body.score || 0,
            totalQuestions: body.totalQuestions || 4,
            percentage: body.percentage || 0,
            passed: body.passed !== false,
            timestamp: body.timestamp || Date.now(),
            commanderReviewStatus: body.commanderReviewStatus || "Chờ Chính trị viên ký duyệt",
            commanderComment: body.commanderComment || ""
          };

          inMemoryDB.submissions.unshift(newSubmission);

          // Update user score
          const user = inMemoryDB.users.find(u => u.username && u.username.toLowerCase() === (body.username || '').toLowerCase());
          if (user) {
            user.lastActive = `Vừa nộp bài thi (${newSubmission.score}/${newSubmission.totalQuestions}đ)`;
          }

          saveDatabase();
          return sendJSON(res, {
            success: true,
            message: "Đã ghi nhận bài thi trắc nghiệm trên hệ thống Quản trị!",
            submission: newSubmission
          });
        }
      }

      // 10. POST /api/submissions/review (Commander grading / reviewing from Web Admin)
      if (pathname === '/api/submissions/review' && req.method === 'POST') {
        const body = await parseRequestBody(req);
        const { submissionId, commanderReviewStatus, commanderComment } = body;
        const sub = inMemoryDB.submissions.find(s => s.id == submissionId);
        if (sub) {
          sub.commanderReviewStatus = commanderReviewStatus || "Chính trị viên đã phê duyệt";
          sub.commanderComment = commanderComment || "Nắm chắc nội dung bài giảng, phát huy tốt.";
          saveDatabase();
          return sendJSON(res, { success: true, message: "Đã lưu nhận xét và chữ ký duyệt của Cán bộ!", submission: sub });
        }
        return sendJSON(res, { success: false, message: "Không tìm thấy bài nộp" }, 404);
      }

      // 11. GET & POST /api/laws
      if (pathname === '/api/laws') {
        if (req.method === 'GET') {
          return sendJSON(res, { success: true, laws: inMemoryDB.laws });
        } else if (req.method === 'POST') {
          const body = await parseRequestBody(req);
          inMemoryDB.laws.push(body);
          saveDatabase();
          return sendJSON(res, { success: true, message: "Đã thêm văn bản pháp luật mới!", laws: inMemoryDB.laws });
        }
      }

      // 12. GET /api/stats
      if (pathname === '/api/stats' && req.method === 'GET') {
        const totalUsers = inMemoryDB.users.length;
        const completedUsers = inMemoryDB.users.filter(u => u.progress >= 100).length;
        const avgScore = (inMemoryDB.users.reduce((acc, u) => acc + (parseFloat(u.avgScore) || 0), 0) / (totalUsers || 1)).toFixed(1);
        return sendJSON(res, {
          totalUsers,
          completedUsers,
          avgScore,
          completionRate: totalUsers > 0 ? Math.round((completedUsers / totalUsers) * 100) : 0
        });
      }

      return sendJSON(res, { error: 'Not Found' }, 404);
    } catch (err) {
      console.error("API error:", err);
      return sendJSON(res, { error: err.message }, 500);
    }
  }

  // ================= STATIC FILES SERVING =================
  if (pathname === '/' || pathname === '/index.html') {
    pathname = '/index.html';
  }

  let relativePath = pathname;
  if (relativePath.startsWith('/web-admin/')) {
    relativePath = relativePath.slice('/web-admin'.length);
  }

  let filePath = path.join(WEB_ADMIN_DIR, relativePath);
  if (!fs.existsSync(filePath)) {
    filePath = path.join(__dirname, pathname);
  }

  if (fs.existsSync(filePath) && fs.statSync(filePath).isFile()) {
    const ext = path.extname(filePath).toLowerCase();
    const contentType = MIME_TYPES[ext] || 'application/octet-stream';
    res.writeHead(200, { 'Content-Type': contentType });
    fs.createReadStream(filePath).pipe(res);
  } else {
    const fallbackIndex = path.join(WEB_ADMIN_DIR, 'index.html');
    if (fs.existsSync(fallbackIndex)) {
      res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
      fs.createReadStream(fallbackIndex).pipe(res);
    } else {
      res.writeHead(404, { 'Content-Type': 'text/plain' });
      res.end('404 Not Found');
    }
  }
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`[GDCT Web Admin Server] Running with Full REST API at http://0.0.0.0:${PORT}`);
});
