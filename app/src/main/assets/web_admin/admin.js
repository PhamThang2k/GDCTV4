/**
 * BỘ TƯ LỆNH VÙNG 4 HẢI QUÂN - HỆ THỐNG QUẢN TRỊ NỘI DUNG & TÀI KHOẢN GDCT
 * Standalone CMS Web Engine
 */

// Initial Seed Data (matching GDCT Mobile App Schema)
const DEFAULT_LESSONS = [
  {
    id: "cd-01",
    title: "Nâng cao nhận thức, trách nhiệm của cán bộ, chiến sĩ Vùng 4 đối với nhiệm vụ bảo vệ chủ quyền biển, đảo trong tình hình mới",
    category: "Sĩ quan & QNCN",
    targetAudience: "Toàn thể cán bộ, chiến sĩ, QNCN Vùng 4",
    estimatedMinutes: 45,
    summary: "Quán triệt sâu sắc các quan điểm của Đảng về bảo vệ vững chắc chủ quyền biển đảo, thềm lục địa thiêng liêng của Tổ quốc; xây dựng Vùng 4 cách mạng, chính quy, tinh nhuệ, hiện đại.",
    lecturer: "Đại tá Bùi Xuân Thắng",
    unit: "Phòng Chính trị Vùng 4",
    slidesCount: 8,
    docxAttachment: "Chuyen_de_01_Chu_quyen_bien_dao.docx",
    pdfAttachment: "Chuyen_de_01_Chu_quyen_bien_dao.pdf",
    videoUrl: "https://video.gdct.vung4.vn/cd-01-hd.mp4",
    audioUrl: "https://audio.gdct.vung4.vn/cd-01-phat-thanh.mp3",
    quizCount: 4,
    sections: [
      {
        sectionNumber: 1,
        heading: "Phần 1: Bối cảnh tình hình Biển Đông và yêu cầu nhiệm vụ bảo vệ chủ quyền",
        content: "Tình hình an ninh hàng hải, tranh chấp chủ quyền trên Biển Đông tiếp tục diễn biến phức tạp, khó lường. Vùng 4 Hải quân quản lý vùng biển rộng lớn, trọng điểm có vị trí chiến lược đặc biệt quan trọng.",
        keyTakeaway: "Kiên quyết, kiên trì đấu tranh bảo vệ vững chắc độc lập, chủ quyền, thống nhất, toàn vẹn lãnh thổ."
      },
      {
        sectionNumber: 2,
        heading: "Phần 2: Quan điểm, chủ trương, phương châm của Đảng về giải quyết tranh chấp biển đảo",
        content: "Thực hiện nhất quán phương châm 'Kiên quyết, kiên trì, bình tĩnh, khôn khéo', giải quyết tranh chấp bằng biện pháp hòa bình trên cơ sở luật pháp quốc tế, đặc biệt là UNCLOS 1982 và DOC.",
        keyTakeaway: "Giữ vững môi trường hòa bình, ổn định đồng thời bảo vệ vững chắc từng sải biển, tấc đảo."
      },
      {
        sectionNumber: 3,
        heading: "Phần 3: Trách nhiệm và quyết tâm chiến đấu của cán bộ, chiến sĩ Vùng 4",
        content: "Cán bộ, chiến sĩ toàn Vùng luôn nêu cao tinh thần cảnh giác cách mạng, sẵn sàng chiến đấu cao, làm chủ tàu thuyền, vũ khí trang bị hiện đại, quyết tâm hoàn thành xuất sắc mọi nhiệm vụ được giao.",
        keyTakeaway: "Sẵn sàng chiến đấu, hy sinh để bảo vệ chủ quyền biển, đảo thiêng liêng của Tổ quốc."
      }
    ]
  },
  {
    id: "cd-02",
    title: "Xây dựng bản lĩnh chính trị kiên định, vững vàng cho bộ đội tàu ngầm, tàu mặt nước chiến đấu Vùng 4 Hải quân",
    category: "Sĩ quan & QNCN",
    targetAudience: "Sĩ quan, QNCN, Thủy thủ các Lữ đoàn tàu",
    estimatedMinutes: 50,
    summary: "Xây dựng ý chí quyết tâm, tinh thần dũng cảm, sẵn sàng hy sinh bảo vệ biển đảo; không ngại sóng to gió lớn và các tình huống phức tạp trên biển.",
    lecturer: "Thượng tá Nguyễn Văn Dương",
    unit: "Lữ đoàn 162",
    slidesCount: 10,
    docxAttachment: "Chuyen_de_02_Ban_linh_chinh_tri_thuy_thu.docx",
    pdfAttachment: "Chuyen_de_02_Ban_linh_chinh_tri_thuy_thu.pdf",
    videoUrl: "https://video.gdct.vung4.vn/cd-02-hd.mp4",
    audioUrl: "https://audio.gdct.vung4.vn/cd-02-phat-thanh.mp3",
    quizCount: 4,
    sections: [
      {
        sectionNumber: 1,
        heading: "Phần 1: Đặc thù hoạt động và yêu cầu bản lĩnh thép của thủy thủ tàu chiến đấu",
        content: "Hoạt động dài ngày trên biển, đối mặt với sóng to gió lớn và áp lực tác chiến đòi hỏi mỗi thủy thủ phải có thể lực dẻo dai, tâm lý vững vàng và bản lĩnh chính trị kiên định tuyệt đối.",
        keyTakeaway: "Bản lĩnh chính trị là nhân tố quyết định sức mạnh chiến đấu của bộ đội tàu."
      },
      {
        sectionNumber: 2,
        heading: "Phần 2: Rèn luyện ý chí vượt khó, đoàn kết hiệp đồng, lập công tập thể",
        content: "Trên một con tàu, tất cả cán bộ chiến sĩ là một khối thống nhất 'Cùng chung một con tàu, cùng chung một ý chí'. Tinh thần đồng đội và kỷ luật nghiêm minh là chìa khóa chiến thắng.",
        keyTakeaway: "Đoàn kết, hiệp đồng chặt chẽ, phát huy sức mạnh tổng hợp của toàn tàu."
      },
      {
        sectionNumber: 3,
        heading: "Phần 3: Làm chủ vũ khí trang bị kỹ thuật hiện đại, sẵn sàng lập công",
        content: "Khắc phục khó khăn, làm chủ công nghệ và vũ khí trang bị mới của tàu mặt nước và tàu ngầm; giữ tốt, dùng bền, an toàn, tiết kiệm.",
        keyTakeaway: "Giỏi chuyên môn, thuần thục trang bị, sẵn sàng đánh thắng mọi kẻ thù xâm phạm."
      }
    ]
  },
  {
    id: "cd-03",
    title: "Truyền thống anh hùng của Bộ đội Hải quân và Vùng 4 Hải quân - 50 năm xây dựng, chiến đấu và trưởng thành",
    category: "Lịch sử & Truyền thống",
    targetAudience: "Toàn thể cán bộ, chiến sĩ, nhân viên",
    estimatedMinutes: 40,
    summary: "Khắc sâu truyền thống Chiến đấu anh dũng, mưu trí sáng tạo, làm chủ vùng biển, quyết chiến quyết thắng; gương hy sinh anh dũng bảo vệ đảo Gạc Ma, Cô Lin, Len Đao.",
    lecturer: "Đại tá Trần Hữu Quân",
    unit: "Ban Tuyên huấn Vùng 4",
    slidesCount: 12,
    docxAttachment: "Chuyen_de_03_Truyen_thong_Vung4.docx",
    pdfAttachment: "Chuyen_de_03_Truyen_thong_Vung4.pdf",
    videoUrl: "https://video.gdct.vung4.vn/cd-03-hd.mp4",
    audioUrl: "https://audio.gdct.vung4.vn/cd-03-phat-thanh.mp3",
    quizCount: 4,
    sections: [
      {
        sectionNumber: 1,
        heading: "Phần 1: Những mốc son lịch sử chói lọi của Hải quân nhân dân Việt Nam",
        content: "Chiến công đánh thắng trận đầu ngày 2 và 5/8/1964; con đường huyền thoại Hồ Chí Minh trên biển; chiến dịch giải phóng quần đảo Trường Sa năm 1975.",
        keyTakeaway: "Kế thừa và phát huy truyền thống anh hùng của thế hệ cha anh đi trước."
      },
      {
        sectionNumber: 2,
        heading: "Phần 2: Khúc tráng ca Trường Sa và truyền thống vẻ vang của Vùng 4 Hải quân",
        content: "Vùng 4 Hải quân tự hào là lực lượng nòng cốt bảo vệ quần đảo Trường Sa, với biểu tượng 'Vòng tròn bất tử' tại Gạc Ma ngày 14/3/1988 ngời sáng tinh thần yêu nước.",
        keyTakeaway: "'Không lùi bước, sẵn sàng lấy máu mình để tô thắm lá cờ Tổ quốc'."
      },
      {
        sectionNumber: 3,
        heading: "Phần 3: Phát huy truyền thống trong sự nghiệp xây dựng Vùng 4 hiện đại",
        content: "Mỗi cán bộ, chiến sĩ hôm nay nguyện kế tục xứng đáng truyền thống anh hùng, nỗ lực học tập, rèn luyện, hoàn thành xuất sắc mọi nhiệm vụ Đảng và Nhân dân giao phó.",
        keyTakeaway: "Giữ vững danh hiệu Đơn vị Anh hùng lực lượng vũ trang nhân dân."
      }
    ]
  },
  {
    id: "cd-04",
    title: "Tăng cường quản lý, chấp hành nghiêm pháp luật Nhà nước, kỷ luật Quân đội và an toàn tuyệt đối trong mọi hoạt động",
    category: "Pháp luật & Kỷ luật",
    targetAudience: "Sĩ quan, QNCN, Hạ sĩ quan - Binh sĩ",
    estimatedMinutes: 45,
    summary: "Nâng cao ý thức chấp hành Thông tư 143/2023/TT-BQP, Điều lệnh Quân đội; tuyệt đối không vi phạm nồng độ cồn, vay mượn trái phép, giữ nghiêm tác phong quân nhân.",
    lecturer: "Trung tá Lê Hồng Minh",
    unit: "Phòng Chính trị Vùng 4",
    slidesCount: 9,
    docxAttachment: "Chuyen_de_04_Ky_luat_an_toan.docx",
    pdfAttachment: "Chuyen_de_04_Ky_luat_an_toan.pdf",
    videoUrl: "https://video.gdct.vung4.vn/cd-04-hd.mp4",
    audioUrl: "https://audio.gdct.vung4.vn/cd-04-phat-thanh.mp3",
    quizCount: 4,
    sections: [
      {
        sectionNumber: 1,
        heading: "Phần 1: Quán triệt Thông tư 143/2023/TT-BQP và các quy định kỷ luật mới của Quân đội",
        content: "Nắm vững các nhóm hành vi vi phạm kỷ luật quân đội và các hình thức xử lý nghiêm minh; thực hiện văn hóa pháp luật trong đời sống quân ngũ.",
        keyTakeaway: "'Kỷ luật là sức mạnh của Quân đội', là cội nguồn của mọi thắng lợi."
      },
      {
        sectionNumber: 2,
        heading: "Phần 2: Các biện pháp phòng ngừa vi phạm kỷ luật, tệ nạn và mất an toàn giao thông",
        content: "Nghiêm cấm tuyệt đối uống rượu bia khi tham gia giao thông và trong giờ làm việc; cấm đánh bạc, vay nợ trái phép qua mạng xã hội; chấp hành nghiêm quy định bảo mật thông tin quân sự.",
        keyTakeaway: "Nói không với vi phạm nồng độ cồn, cờ bạc mạng và vay nợ bất hợp pháp."
      },
      {
        sectionNumber: 3,
        heading: "Phần 3: Xây dựng nền nếp chính quy, môi trường văn hóa quân sự lành mạnh",
        content: "Duy trì nghiêm 11 chế độ trong ngày, 3 chế độ trong tuần; xưng hô chào hỏi đúng điều lệnh, xây dựng mối quan hệ cán - binh thân ái, gắn bó.",
        keyTakeaway: "Tự giác chấp hành kỷ luật, xây dựng đơn vị vững mạnh toàn diện 'Mẫu mực, tiêu biểu'."
      }
    ]
  },
  {
    id: "cd-05",
    title: "Học tập và làm theo tư tưởng, đạo đức, phong cách Hồ Chí Minh về tinh thần trách nhiệm, nêu gương của người quân nhân cách mạng",
    category: "Học tập Bác Hồ",
    targetAudience: "Cán bộ, Đảng viên, Đoàn viên thanh niên",
    estimatedMinutes: 40,
    summary: "Học tập phong cách tận tụy, cần kiệm liêm chính, chí công vô tư của Bác; lời căn dặn của Bác với Hải quân: Ngày nay ta có ngày, có trời, có biển, ta phải biết giữ gìn lấy nó.",
    lecturer: "Bộ Tư lệnh Vùng 4 Hải quân",
    unit: "Phòng Chính trị Vùng 4",
    slidesCount: 8,
    docxAttachment: "Chuyen_de_05_Tu_tuong_Bac_Ho.docx",
    pdfAttachment: "Chuyen_de_05_Tu_tuong_Bac_Ho.pdf",
    videoUrl: "https://video.gdct.vung4.vn/cd-05-hd.mp4",
    audioUrl: "https://audio.gdct.vung4.vn/cd-05-phat-thanh.mp3",
    quizCount: 4,
    sections: [
      {
        sectionNumber: 1,
        heading: "Phần 1: Tư tưởng Hồ Chí Minh về tinh thần trách nhiệm và phụng sự Tổ quốc",
        content: "Chủ tịch Hồ Chí Minh luôn đặt lợi ích của Tổ quốc và nhân dân lên trên hết. Người chỉ rõ: 'Bất kỳ ai, ở địa vị nào, làm công tác gì, gặp hoàn cảnh nào, đều phải có tinh thần trách nhiệm'. Đối với Quân đội nhân dân Việt Nam, trách nhiệm là sẵn sàng chiến đấu, hy sinh vì độc lập tự do.",
        keyTakeaway: "Luôn đặt lợi ích của Đảng, Tổ quốc và Nhân dân lên trên hết, trước hết."
      },
      {
        sectionNumber: 2,
        heading: "Phần 2: Lời căn dặn thiêng liêng của Bác đối với Bộ đội Hải quân",
        content: "Ngày 15/3/1961, khi về thăm Bộ đội Hải quân, Bác Hồ đã ân cần căn dặn: 'Ngày trước ta chỉ có đêm và rừng. Ngày nay ta có ngày, có trời, có biển. Bờ biển ta dài, tươi đẹp, ta phải biết giữ gìn lấy nó'. Lời dạy ấy đã trở thành kim chỉ nam cho các thế hệ cán bộ, chiến sĩ Vùng 4 Hải quân.",
        keyTakeaway: "Khắc sâu và thực hiện xuất sắc lời căn dặn của Bác Hồ: 'Bờ biển ta dài tươi đẹp, ta phải biết giữ gìn lấy nó'."
      },
      {
        sectionNumber: 3,
        heading: "Phần 3: Trách nhiệm nêu gương và hành động của cán bộ, chiến sĩ Vùng 4 hôm nay",
        content: "Mỗi cán bộ, chiến sĩ Vùng 4 Hải quân cần nêu cao tinh thần tự giác, gương mẫu trong học tập và công tác. Giữ nghiêm kỷ luật, thành thạo làm chủ vũ khí trang bị kỹ thuật mới, kiên quyết bảo vệ vững chắc từng tấc đảo, sải biển thiêng liêng.",
        keyTakeaway: "Nêu cao tính gương mẫu, tinh thần đoàn kết và ý chí quyết tâm bảo vệ chủ quyền biển đảo."
      }
    ]
  }
];

const DEFAULT_USERS = [
  { id: "usr-01", militaryCode: "QN-16201", fullName: "Trần Văn Bình", rank: "Đại úy", position: "Thuyền trưởng Tàu 015 Trần Hưng Đạo", unit: "Lữ đoàn 162", phone: "0988.112.233", progress: 100, avgScore: 10.0, lastActive: "Hôm nay, 08:30" },
  { id: "usr-02", militaryCode: "QN-16202", fullName: "Lê Hoàng Nam", rank: "Thượng úy", position: "Chính trị viên Tàu 016 Quang Trung", unit: "Lữ đoàn 162", phone: "0977.445.566", progress: 100, avgScore: 9.5, lastActive: "Hôm nay, 09:15" },
  { id: "usr-03", militaryCode: "QN-14601", fullName: "Nguyễn Văn Tuấn", rank: "Thiếu tá", position: "Chỉ huy trưởng Đảo Sinh Tồn", unit: "Lữ đoàn 146", phone: "0912.334.455", progress: 100, avgScore: 9.8, lastActive: "Hôm qua, 16:45" },
  { id: "usr-04", militaryCode: "QN-14602", fullName: "Phạm Hải Đăng", rank: "Trung úy", position: "Chính trị viên Đảo Cô Lin", unit: "Lữ đoàn 146", phone: "0903.667.788", progress: 80, avgScore: 8.5, lastActive: "2 ngày trước" },
  { id: "usr-05", militaryCode: "QN-95501", fullName: "Hoàng Minh Đức", rank: "Thiếu úy", position: "Trưởng ngành Cơ điện Tàu 561", unit: "Lữ đoàn 955", phone: "0982.554.433", progress: 40, avgScore: 7.0, lastActive: "5 ngày trước" },
  { id: "usr-06", militaryCode: "QN-10101", fullName: "Đặng Quốc Cường", rank: "Thượng sĩ", position: "Tiểu đội trưởng Hải quân Đánh bộ", unit: "Lữ đoàn 101", phone: "0966.778.899", progress: 60, avgScore: 8.0, lastActive: "Hôm qua, 14:20" },
  { id: "usr-07", militaryCode: "QN-68501", fullName: "Vũ Đình Trọng", rank: "Trung sĩ", position: "Trắc thủ Tên lửa bờ", unit: "Lữ đoàn 685", phone: "0934.112.299", progress: 20, avgScore: 6.5, lastActive: "1 tuần trước" },
  { id: "usr-08", militaryCode: "QN-KT01", fullName: "Lâm Quang Huy", rank: "Thượng úy QNCN", position: "Tổ trưởng Kỹ thuật Vũ khí", unit: "Trung tâm BĐKT", phone: "0971.889.900", progress: 100, avgScore: 9.0, lastActive: "Hôm nay, 07:50" }
];

const DEFAULT_UNITS_DATA = [
  { name: "Lữ đoàn 162 (Tàu mặt nước chiến đấu)", count: 320, progress: 94, avgScore: 9.2, status: "Xuất sắc" },
  { name: "Lữ đoàn 146 (Đoàn Trường Sa)", count: 410, progress: 88, avgScore: 8.9, status: "Tốt" },
  { name: "Lữ đoàn 955 (Tàu Vận tải - Đổ bộ)", count: 210, progress: 82, avgScore: 8.5, status: "Đạt" },
  { name: "Lữ đoàn 101 (Hải quân Đánh bộ)", count: 180, progress: 85, avgScore: 8.6, status: "Tốt" },
  { name: "Lữ đoàn 685 (Tên lửa bờ)", count: 160, progress: 79, avgScore: 8.2, status: "Cần đôn đốc" }
];

const DEFAULT_LAWS = [
  { id: "law-01", title: "Luật Biển Việt Nam năm 2012", issuedBy: "Quốc hội Nước CHXHCN Việt Nam", category: "Pháp luật Nhà nước", summary: "Quy định về đường cơ sở, nội thủy, lãnh hải, vùng tiếp giáp lãnh hải, vùng đặc quyền kinh tế, thềm lục địa, các đảo và quần đảo Hoàng Sa, Trường Sa thuộc chủ quyền Việt Nam." },
  { id: "law-02", title: "Thông tư 143/2023/TT-BQP", issuedBy: "Bộ Quốc phòng", category: "Kỷ luật Quân đội", summary: "Quy định xử lý kỷ luật trong Quân đội nhân dân Việt Nam; các chế tài nghiêm khắc đối với hành vi vi phạm pháp luật, vi phạm nồng độ cồn, gây mất an toàn." },
  { id: "law-03", title: "Luật Sĩ quan Quân đội nhân dân Việt Nam", issuedBy: "Quốc hội Nước CHXHCN Việt Nam", category: "Chế độ chính sách", summary: "Quy định chức vụ, cấp bậc quân hàm, quyền lợi, trách nhiệm và nghĩa vụ vẻ vang của Sĩ quan QĐND Việt Nam." }
];

// App State Management with LocalStorage
class AdminDataStore {
  constructor() {
    this.lessons = this.load("gdct_admin_lessons", DEFAULT_LESSONS);
    this.users = this.load("gdct_admin_users", DEFAULT_USERS);
    this.laws = this.load("gdct_admin_laws", DEFAULT_LAWS);
  }

  load(key, defaultValue) {
    try {
      const stored = localStorage.getItem(key);
      return stored ? JSON.parse(stored) : defaultValue;
    } catch (e) {
      return defaultValue;
    }
  }

  save(key, data) {
    localStorage.setItem(key, JSON.stringify(data));
  }

  saveAll() {
    this.save("gdct_admin_lessons", this.lessons);
    this.save("gdct_admin_users", this.users);
    this.save("gdct_admin_laws", this.laws);
  }
}

const store = new AdminDataStore();

// DOM READY & EVENT LISTENERS
document.addEventListener("DOMContentLoaded", () => {
  initNavigation();
  renderOverview();
  renderLessonsTable();
  renderUsersTable();
  renderQuizBank();
  renderLaws();
  initActionButtons();
});

// Toast notification helper
function showToast(message, type = "info") {
  const container = document.getElementById("toast-container");
  const toast = document.createElement("div");
  toast.className = "toast";
  
  let icon = '<i class="fa-solid fa-circle-check" style="color: var(--green-600);"></i>';
  if (type === "warning") icon = '<i class="fa-solid fa-bell" style="color: var(--orange-500);"></i>';
  if (type === "danger") icon = '<i class="fa-solid fa-triangle-exclamation" style="color: var(--crimson-red);"></i>';

  toast.innerHTML = `${icon} <span>${message}</span>`;
  container.appendChild(toast);

  setTimeout(() => {
    toast.style.opacity = "0";
    toast.style.transform = "translateY(20px)";
    toast.style.transition = "all 0.3s ease";
    setTimeout(() => toast.remove(), 300);
  }, 3500);
}

// Navigation Tabs
function initNavigation() {
  const navItems = document.querySelectorAll(".sidebar-nav .nav-item");
  const tabPanes = document.querySelectorAll(".tab-pane");
  const pageTitle = document.getElementById("page-title");

  const titles = {
    overview: '<i class="fa-solid fa-chart-pie"></i><span>Tổng quan & Báo cáo Tiến độ Toàn Vùng</span>',
    lessons: '<i class="fa-solid fa-book-open"></i><span>Quản lý Kho Bài giảng GDCT Đa phương tiện</span>',
    users: '<i class="fa-solid fa-users"></i><span>Quản lý Danh sách & Hồ sơ Quân nhân</span>',
    quizzes: '<i class="fa-solid fa-list-check"></i><span>Ngân hàng Đề thi Trắc nghiệm</span>',
    laws: '<i class="fa-solid fa-scale-balanced"></i><span>Tủ sách Pháp luật & Kỷ luật Quân đội</span>',
    settings: '<i class="fa-solid fa-database"></i><span>Cơ sở Dữ liệu & Đồng bộ Hệ thống</span>'
  };

  navItems.forEach(item => {
    item.addEventListener("click", () => {
      const tabId = item.getAttribute("data-tab");
      navItems.forEach(n => n.classList.remove("active"));
      tabPanes.forEach(p => p.classList.remove("active"));

      item.classList.add("active");
      const targetPane = document.getElementById(`tab-tab-${tabId}`) || document.getElementById(`tab-${tabId}`);
      if (targetPane) targetPane.classList.add("active");

      if (titles[tabId] && pageTitle) {
        pageTitle.innerHTML = titles[tabId];
      }
    });
  });
}

// 1. RENDER OVERVIEW
function renderOverview() {
  document.getElementById("stat-total-lessons").textContent = store.lessons.length;
  document.getElementById("stat-total-users").textContent = store.users.length > 8 ? store.users.length : "1,280";

  const unitTbody = document.getElementById("unit-progress-tbody");
  if (!unitTbody) return;

  unitTbody.innerHTML = DEFAULT_UNITS_DATA.map(u => `
    <tr>
      <td><b>${u.name}</b></td>
      <td>${u.count} đồng chí</td>
      <td style="min-width: 140px;">
        <div style="display: flex; align-items: center; gap: 8px;">
          <div class="progress-bar-container" style="flex: 1;">
            <div class="progress-bar-fill" style="width: ${u.progress}%;"></div>
          </div>
          <b>${u.progress}%</b>
        </div>
      </td>
      <td><b>${u.avgScore}đ</b></td>
      <td>
        <span class="badge ${u.status === 'Xuất sắc' || u.status === 'Tốt' ? 'badge-green' : u.status === 'Cần đôn đốc' ? 'badge-orange' : 'badge-navy'}">
          ${u.status}
        </span>
      </td>
      <td>
        <button class="btn btn-outline btn-sm" onclick="urgeUnit('${u.name}')" title="Đôn đốc đơn vị">
          <i class="fa-solid fa-bell" style="color: var(--crimson-red);"></i> Đôn đốc
        </button>
      </td>
    </tr>
  `).join("");

  const activityList = document.getElementById("recent-activity-list");
  if (activityList) {
    activityList.innerHTML = `
      <div>• <b>Tàu 015 (Lữ đoàn 162)</b>: 100% thủy thủ hoàn thành Chuyên đề 01 (10.0đ)</div>
      <div>• <b>Đảo Sinh Tồn (Lữ đoàn 146)</b>: Vừa nộp 45 bài thu hoạch chính trị</div>
      <div>• <b>Tàu 561 (Lữ đoàn 955)</b>: Đã tải 12 tài liệu PDF tự học trên biển</div>
      <div>• <b>Lữ đoàn 101</b>: Hoàn thành đợt kiểm tra nhận thức quý 1</div>
    `;
  }
}

// 2. RENDER LESSONS TABLE
function renderLessonsTable() {
  const tbody = document.getElementById("lessons-tbody");
  const search = document.getElementById("lesson-search")?.value.toLowerCase() || "";
  const catFilter = document.getElementById("lesson-filter-category")?.value || "ALL";

  const filtered = store.lessons.filter(l => {
    const matchSearch = l.title.toLowerCase().includes(search) || l.id.toLowerCase().includes(search) || l.lecturer.toLowerCase().includes(search);
    const matchCat = catFilter === "ALL" || l.category === catFilter;
    return matchSearch && matchCat;
  });

  if (!tbody) return;

  tbody.innerHTML = filtered.map(l => `
    <tr>
      <td><span class="badge badge-navy"><b>${l.id.toUpperCase()}</b></span></td>
      <td style="max-width: 300px;">
        <div style="font-weight: 700; color: var(--navy-primary); margin-bottom: 2px;">${l.title}</div>
        <div style="font-size: 11px; color: var(--slate-500);">${l.lecturer} • ${l.unit}</div>
      </td>
      <td><span class="badge badge-blue">${l.category}</span></td>
      <td style="font-size: 12px;">${l.targetAudience}</td>
      <td><b>${l.estimatedMinutes} phút</b></td>
      <td>
        <div style="display: flex; gap: 4px; font-size: 13px;">
          <span title="Slide: ${l.slidesCount} trang" style="color: #3B82F6;"><i class="fa-solid fa-chalkboard-user"></i> ${l.slidesCount}</span>
          <span title="Tài liệu DOCX/PDF" style="color: #EF4444;"><i class="fa-solid fa-file-pdf"></i></span>
          <span title="Video HD" style="color: #10B981;"><i class="fa-solid fa-video"></i></span>
          <span title="Audio phát thanh" style="color: #F59E0B;"><i class="fa-solid fa-headphones"></i></span>
        </div>
      </td>
      <td><span class="badge badge-green">${l.quizCount || 4} câu</span></td>
      <td>
        <div style="display: flex; gap: 4px;">
          <button class="btn btn-outline btn-sm" onclick="previewLesson('${l.id}')" title="Xem trước"><i class="fa-solid fa-eye"></i></button>
          <button class="btn btn-outline btn-sm" onclick="editLesson('${l.id}')" title="Sửa bài"><i class="fa-solid fa-pen-to-square"></i></button>
          <button class="btn btn-outline btn-sm" onclick="deleteLesson('${l.id}')" title="Xóa" style="color: var(--crimson-red);"><i class="fa-solid fa-trash"></i></button>
        </div>
      </td>
    </tr>
  `).join("");
}

// 3. RENDER USERS TABLE
function renderUsersTable() {
  const tbody = document.getElementById("users-tbody");
  const search = document.getElementById("user-search")?.value.toLowerCase() || "";
  const unitFilter = document.getElementById("user-filter-unit")?.value || "ALL";
  const statusFilter = document.getElementById("user-filter-status")?.value || "ALL";

  const filtered = store.users.filter(u => {
    const matchSearch = u.fullName.toLowerCase().includes(search) || u.militaryCode.toLowerCase().includes(search) || u.position.toLowerCase().includes(search);
    const matchUnit = unitFilter === "ALL" || u.unit.includes(unitFilter);
    let matchStatus = true;
    if (statusFilter === "COMPLETED") matchStatus = u.progress >= 100;
    if (statusFilter === "IN_PROGRESS") matchStatus = u.progress > 0 && u.progress < 100;
    if (statusFilter === "NEEDS_URGE") matchStatus = u.progress < 50;

    return matchSearch && matchUnit && matchStatus;
  });

  if (!tbody) return;

  tbody.innerHTML = filtered.map(u => `
    <tr>
      <td><b>${u.militaryCode}</b></td>
      <td>
        <div style="font-weight: 700; color: var(--navy-primary);">${u.fullName}</div>
        <div style="font-size: 11px; color: var(--slate-500);"><i class="fa-solid fa-phone"></i> ${u.phone}</div>
      </td>
      <td><span class="badge badge-navy">${u.rank}</span></td>
      <td style="font-size: 12px;">${u.position}</td>
      <td><b>${u.unit}</b></td>
      <td style="min-width: 120px;">
        <div style="display: flex; align-items: center; gap: 6px;">
          <div class="progress-bar-container" style="flex: 1;">
            <div class="progress-bar-fill" style="width: ${u.progress}%; ${u.progress < 50 ? 'background: #EF4444;' : ''}"></div>
          </div>
          <b>${u.progress}%</b>
        </div>
      </td>
      <td><b style="color: ${u.avgScore >= 8 ? 'var(--green-600)' : 'var(--slate-800)'}">${u.avgScore}đ</b></td>
      <td style="font-size: 11px; color: var(--slate-500);">${u.lastActive}</td>
      <td>
        <div style="display: flex; gap: 4px;">
          <button class="btn btn-outline btn-sm" onclick="urgeSingleUser('${u.fullName}', '${u.phone}')" title="Đôn đốc SMS/Thông báo">
            <i class="fa-solid fa-bell" style="color: var(--crimson-red);"></i>
          </button>
          <button class="btn btn-outline btn-sm" onclick="editUser('${u.id}')" title="Sửa thông tin">
            <i class="fa-solid fa-pen-to-square"></i>
          </button>
          <button class="btn btn-outline btn-sm" onclick="deleteUser('${u.id}')" title="Xóa" style="color: var(--crimson-red);">
            <i class="fa-solid fa-trash"></i>
          </button>
        </div>
      </td>
    </tr>
  `).join("");
}

// 4. RENDER QUIZ BANK
function renderQuizBank() {
  const container = document.getElementById("quiz-questions-list");
  if (!container) return;

  container.innerHTML = store.lessons.map((l, lIdx) => `
    <div style="border: 1px solid var(--slate-200); border-radius: 10px; padding: 16px; background: white;">
      <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px;">
        <div style="font-weight: 700; color: var(--navy-primary); font-size: 14px;">
          <span class="badge badge-navy">${l.id.toUpperCase()}</span> ${l.title}
        </div>
        <span class="badge badge-green">4 Câu trắc nghiệm</span>
      </div>

      <div style="font-size: 12.5px; color: var(--slate-700); background: var(--slate-50); padding: 10px; border-radius: 6px; margin-bottom: 8px;">
        <b>Câu 1:</b> Phương châm chỉ đạo xuyên suốt của Đảng ta trong bảo vệ chủ quyền biển đảo là gì?
        <div style="margin-top: 6px; display: grid; grid-template-columns: 1fr 1fr; gap: 6px; font-size: 11.5px;">
          <div>A. Bị động đối phó</div>
          <div style="color: var(--green-600); font-weight: bold;">B. Kiên quyết, kiên trì, bình tĩnh, khôn khéo (Đúng)</div>
          <div>C. Đơn phương giải quyết</div>
          <div>D. Đợi chỉ thị cấp trên</div>
        </div>
      </div>
    </div>
  `).join("");
}

// 5. RENDER LAWS
function renderLaws() {
  const container = document.getElementById("laws-grid");
  if (!container) return;

  container.innerHTML = store.laws.map(law => `
    <div style="border: 1px solid var(--slate-200); border-radius: 10px; padding: 16px; background: white; display: flex; flex-direction: column; justify-content: space-between;">
      <div>
        <div style="display: flex; justify-content: space-between; margin-bottom: 8px;">
          <span class="badge badge-blue">${law.category}</span>
          <span style="font-size: 11px; color: var(--slate-500);">${law.issuedBy}</span>
        </div>
        <h4 style="font-size: 14px; color: var(--navy-primary); margin-bottom: 6px;">${law.title}</h4>
        <p style="font-size: 12px; color: var(--slate-600); line-height: 1.5;">${law.summary}</p>
      </div>
      <div style="margin-top: 14px; display: flex; justify-content: flex-end; gap: 6px;">
        <button class="btn btn-outline btn-sm" onclick="showToast('Đã mở toàn văn ${law.title}')"><i class="fa-solid fa-book-open"></i> Đọc toàn văn</button>
      </div>
    </div>
  `).join("");
}

// ACTION HANDLERS & MODAL MANAGEMENT
function openModal(id) {
  document.getElementById(id)?.classList.add("show");
}

function closeModal(id) {
  document.getElementById(id)?.classList.remove("show");
}

// DYNAMIC SECTIONS MANAGEMENT FOR LESSONS
function renderLessonSectionsForm(sections = []) {
  const container = document.getElementById("lesson-sections-container");
  if (!container) return;
  container.innerHTML = "";
  if (!sections || sections.length === 0) {
    sections = [
      {
        sectionNumber: 1,
        heading: "Phần 1: Bối cảnh, mục đích và sự cần thiết",
        content: "Nội dung phân tích bối cảnh lịch sử, tình hình thực tiễn và tính cấp thiết của chuyên đề GDCT.",
        keyTakeaway: "Nắm vững lý do và tính tất yếu của bài học."
      },
      {
        sectionNumber: 2,
        heading: "Phần 2: Nội dung tư tưởng và quan điểm cốt lõi",
        content: "Phân tích các luận điểm then chốt, đường lối của Đảng và yêu cầu nhiệm vụ của Quân chủng Hải quân.",
        keyTakeaway: "Khắc sâu nguyên tắc chỉ đạo và bản lĩnh chính trị kiên định."
      },
      {
        sectionNumber: 3,
        heading: "Phần 3: Trách nhiệm và hành động của cán bộ, chiến sĩ",
        content: "Liên hệ thực tiễn vị trí công tác, quyết tâm hoàn thành tốt mọi nhiệm vụ, giữ nghiêm kỷ luật.",
        keyTakeaway: "Nêu cao tính gương mẫu, tinh thần đoàn kết và ý chí quyết chiến quyết thắng."
      }
    ];
  }
  sections.forEach((sec, idx) => {
    addSectionItemToForm(sec, idx + 1);
  });
}

function addSectionItemToForm(sec = {}, num = null) {
  const container = document.getElementById("lesson-sections-container");
  if (!container) return;
  const currentCount = container.children.length;
  const sectionNum = num || currentCount + 1;
  const itemDiv = document.createElement("div");
  itemDiv.className = "section-form-item";
  itemDiv.style = "border: 1px solid #E2E8F0; background: #F8FAFC; border-radius: 8px; padding: 12px; position: relative;";
  itemDiv.innerHTML = `
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px;">
      <span style="font-size: 12px; font-weight: 700; color: #1E3A8A; background: #DBEAFE; padding: 2px 8px; border-radius: 4px;">
        PHẦN ${sectionNum} (Mục học tập)
      </span>
      <button type="button" class="btn-delete-section" style="background: transparent; border: none; color: #EF4444; cursor: pointer; font-size: 13px;" title="Xóa phần này">
        <i class="fa-solid fa-trash-can"></i> Xóa mục
      </button>
    </div>
    <div style="margin-bottom: 8px;">
      <label style="font-size: 11px; font-weight: 600; color: #334155; display: block; margin-bottom: 4px;">Tiêu đề Phần *</label>
      <input type="text" class="form-control section-heading" value="${sec.heading || `Phần ${sectionNum}: Tiêu đề nội dung`}" placeholder="VD: Phần ${sectionNum}: Quan điểm và tư tưởng chỉ đạo" required style="font-size: 12.5px;">
    </div>
    <div style="margin-bottom: 8px;">
      <label style="font-size: 11px; font-weight: 600; color: #334155; display: block; margin-bottom: 4px;">Nội dung chi tiết phần học *</label>
      <textarea class="form-control section-content" rows="3" placeholder="Nhập nội dung giảng dạy chi tiết của phần này..." required style="font-size: 12px;">${sec.content || ''}</textarea>
    </div>
    <div>
      <label style="font-size: 11px; font-weight: 600; color: #B45309; display: block; margin-bottom: 4px;">Điểm cốt lõi / Ghi nhớ (Key takeaway)</label>
      <input type="text" class="form-control section-takeaway" value="${sec.keyTakeaway || ''}" placeholder="VD: Nắm vững và thực hiện tốt trách nhiệm được giao" style="font-size: 12px; background: #FEF3C7; border-color: #FDE68A;">
    </div>
  `;
  itemDiv.querySelector(".btn-delete-section")?.addEventListener("click", () => {
    if (container.children.length <= 1) {
      alert("Bài giảng cần có ít nhất 1 phần nội dung!");
      return;
    }
    itemDiv.remove();
    renumberSectionItems();
  });
  container.appendChild(itemDiv);
}

function renumberSectionItems() {
  const container = document.getElementById("lesson-sections-container");
  if (!container) return;
  Array.from(container.children).forEach((child, index) => {
    const badge = child.querySelector("span");
    if (badge) badge.textContent = `PHẦN ${index + 1} (Mục học tập)`;
  });
}

function getSectionsFromForm() {
  const container = document.getElementById("lesson-sections-container");
  if (!container) return [];
  const sections = [];
  Array.from(container.children).forEach((child, index) => {
    const heading = child.querySelector(".section-heading")?.value.trim() || `Phần ${index + 1}`;
    const content = child.querySelector(".section-content")?.value.trim() || `Nội dung Phần ${index + 1}`;
    const keyTakeaway = child.querySelector(".section-takeaway")?.value.trim() || `Ghi nhớ cốt lõi Phần ${index + 1}`;
    sections.push({
      sectionNumber: index + 1,
      heading,
      content,
      keyTakeaway
    });
  });
  return sections;
}

function initActionButtons() {
  // Global buttons
  document.getElementById("btn-urge-all")?.addEventListener("click", () => {
    showToast("Đã phát lệnh đôn đốc học tập đến toàn thể 1,280 quân nhân Vùng 4 qua SMS và thông báo App!", "warning");
  });

  document.getElementById("btn-quick-export")?.addEventListener("click", () => {
    exportToCSV();
  });

  document.getElementById("btn-logout")?.addEventListener("click", () => {
    if (confirm("Đồng chí có chắc chắn muốn đăng xuất khỏi Cổng Quản trị GDCT?")) {
      showToast("Đã kết thúc phiên làm việc an toàn.");
    }
  });

  // Dynamic Add Section in Lesson Modal
  document.getElementById("btn-add-section-item")?.addEventListener("click", () => {
    addSectionItemToForm();
  });

  // Filter Listeners
  document.getElementById("lesson-search")?.addEventListener("input", renderLessonsTable);
  document.getElementById("lesson-filter-category")?.addEventListener("change", renderLessonsTable);
  document.getElementById("user-search")?.addEventListener("input", renderUsersTable);
  document.getElementById("user-filter-unit")?.addEventListener("change", renderUsersTable);
  document.getElementById("user-filter-status")?.addEventListener("change", renderUsersTable);

  // Lesson Modal
  document.getElementById("btn-add-lesson")?.addEventListener("click", () => {
    document.getElementById("modal-lesson-title").textContent = "Soạn Bài giảng Giáo dục Chính trị mới";
    document.getElementById("form-lesson").reset();
    document.getElementById("lesson-id").value = "";
    renderLessonSectionsForm();
    openModal("modal-lesson");
  });

  document.getElementById("btn-save-lesson")?.addEventListener("click", () => {
    const code = document.getElementById("lesson-code").value.trim();
    const title = document.getElementById("lesson-title").value.trim();
    const category = document.getElementById("lesson-category").value;
    const audience = document.getElementById("lesson-audience").value.trim();
    const duration = parseInt(document.getElementById("lesson-duration").value) || 45;
    const summary = document.getElementById("lesson-summary").value.trim();
    const id = document.getElementById("lesson-id").value || code.toLowerCase();
    const sections = getSectionsFromForm();

    if (!code || !title) {
      alert("Vui lòng nhập đầy đủ Mã bài giảng và Tên chuyên đề!");
      return;
    }

    const existingIdx = store.lessons.findIndex(l => l.id === id);
    const newLesson = {
      id,
      title,
      category,
      targetAudience: audience || "Cán bộ, chiến sĩ Vùng 4",
      estimatedMinutes: duration,
      summary: summary || "Nội dung học tập chính trị trọng tâm năm 2026",
      lecturer: "Phòng Chính trị Vùng 4",
      unit: "Bộ Tư lệnh Vùng 4 Hải quân",
      slidesCount: 8,
      docxAttachment: document.getElementById("lesson-docx-name").value || `${code}_Tai_Lieu.docx`,
      pdfAttachment: document.getElementById("lesson-pdf-name").value || `${code}_Tai_Lieu.pdf`,
      videoUrl: document.getElementById("lesson-video-url").value || `https://video.gdct.vung4.vn/${code}.mp4`,
      audioUrl: document.getElementById("lesson-audio-url").value || `https://audio.gdct.vung4.vn/${code}.mp3`,
      quizCount: 4,
      sections: sections
    };

    if (existingIdx >= 0) {
      store.lessons[existingIdx] = newLesson;
      showToast(`Đã cập nhật bài giảng: ${title}`);
    } else {
      store.lessons.unshift(newLesson);
      showToast(`Đã thêm mới bài giảng: ${title}`);
    }

    store.saveAll();
    closeModal("modal-lesson");
    renderLessonsTable();
    renderOverview();
  });

  // User Modal
  document.getElementById("btn-add-user")?.addEventListener("click", () => {
    document.getElementById("modal-user-title").textContent = "Thêm Quân nhân mới";
    document.getElementById("form-user").reset();
    document.getElementById("user-id").value = "";
    openModal("modal-user");
  });

  document.getElementById("btn-save-user")?.addEventListener("click", () => {
    const code = document.getElementById("user-code").value.trim();
    const name = document.getElementById("user-fullname").value.trim();
    const rank = document.getElementById("user-rank").value;
    const position = document.getElementById("user-position").value.trim();
    const unit = document.getElementById("user-unit").value;
    const phone = document.getElementById("user-phone").value.trim() || "0988.xxx.xxx";
    const progress = parseInt(document.getElementById("user-progress").value) || 0;
    const id = document.getElementById("user-id").value || `usr-${Date.now()}`;

    if (!code || !name) {
      alert("Vui lòng nhập Mã số quân nhân và Họ tên!");
      return;
    }

    const existingIdx = store.users.findIndex(u => u.id === id);
    const userObj = {
      id,
      militaryCode: code,
      fullName: name,
      rank,
      position: position || "Chiến sĩ",
      unit,
      phone,
      progress,
      avgScore: progress === 100 ? 9.5 : (progress / 10).toFixed(1),
      lastActive: "Vừa cập nhật"
    };

    if (existingIdx >= 0) {
      store.users[existingIdx] = userObj;
      showToast(`Đã cập nhật hồ sơ đồng chí ${name}`);
    } else {
      store.users.unshift(userObj);
      showToast(`Đã thêm quân nhân ${name} vào danh sách`);
    }

    store.saveAll();
    closeModal("modal-user");
    renderUsersTable();
  });

  // Export / Import Database
  document.getElementById("btn-export-database")?.addEventListener("click", () => {
    const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(store, null, 2));
    const dlAnchor = document.createElement("a");
    dlAnchor.setAttribute("href", dataStr);
    dlAnchor.setAttribute("download", `gdct_vung4_database_${new Date().toISOString().slice(0,10)}.json`);
    dlAnchor.click();
    showToast("Đã xuất tệp sao lưu cơ sở dữ liệu hệ thống thành công!");
  });

  document.getElementById("btn-restore-database")?.addEventListener("click", () => {
    document.getElementById("input-restore-file")?.click();
  });

  document.getElementById("input-restore-file")?.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = (event) => {
      try {
        const parsed = JSON.parse(event.target.result);
        if (parsed.lessons) store.lessons = parsed.lessons;
        if (parsed.users) store.users = parsed.users;
        if (parsed.laws) store.laws = parsed.laws;
        store.saveAll();
        renderOverview();
        renderLessonsTable();
        renderUsersTable();
        showToast("Đã phục hồi cơ sở dữ liệu thành công!");
      } catch (err) {
        alert("Tệp JSON không hợp lệ!");
      }
    };
    reader.readAsText(file);
  });
}

// Global actions exposed to HTML inline onclick
window.urgeUnit = function(unitName) {
  showToast(`Đã gửi thông báo đôn đốc học tập đến chỉ huy và cán bộ ${unitName}!`, "warning");
};

window.urgeSingleUser = function(name, phone) {
  showToast(`Đã gửi SMS nhắc nhở học tập đến đồng chí ${name} (${phone})`, "warning");
};

window.editLesson = function(id) {
  const lesson = store.lessons.find(l => l.id === id);
  if (!lesson) return;

  document.getElementById("modal-lesson-title").textContent = `Chỉnh sửa: ${lesson.title}`;
  document.getElementById("lesson-id").value = lesson.id;
  document.getElementById("lesson-code").value = lesson.id;
  document.getElementById("lesson-title").value = lesson.title;
  document.getElementById("lesson-category").value = lesson.category;
  document.getElementById("lesson-audience").value = lesson.targetAudience;
  document.getElementById("lesson-duration").value = lesson.estimatedMinutes;
  document.getElementById("lesson-summary").value = lesson.summary;
  document.getElementById("lesson-docx-name").value = lesson.docxAttachment || "";
  document.getElementById("lesson-pdf-name").value = lesson.pdfAttachment || "";
  document.getElementById("lesson-video-url").value = lesson.videoUrl || "";
  document.getElementById("lesson-audio-url").value = lesson.audioUrl || "";

  renderLessonSectionsForm(lesson.sections || []);
  openModal("modal-lesson");
};

window.deleteLesson = function(id) {
  if (confirm("Đồng chí có chắc chắn muốn xóa bài giảng này khỏi hệ thống?")) {
    store.lessons = store.lessons.filter(l => l.id !== id);
    store.saveAll();
    renderLessonsTable();
    renderOverview();
    showToast("Đã xóa bài giảng thành công.");
  }
};

window.previewLesson = function(id) {
  const lesson = store.lessons.find(l => l.id === id);
  if (!lesson) return;

  const sectionsHtml = (lesson.sections && lesson.sections.length > 0)
    ? lesson.sections.map((sec, sIdx) => `
        <div style="background: white; border: 1.5px solid #CBD5E1; border-radius: 8px; padding: 12px; margin-bottom: 10px;">
          <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 6px;">
            <span style="font-weight: 700; color: var(--navy-primary); font-size: 13px;">${sec.heading}</span>
            <span style="font-size: 11px; background: #DCFCE7; color: #166534; font-weight: 700; padding: 2px 8px; border-radius: 4px; border: 1px solid #86EFAC;">
              <i class="fa-solid fa-square-check"></i> Ô tích Đã học trên App
            </span>
          </div>
          <p style="font-size: 12px; color: #334155; line-height: 1.6; margin-bottom: 8px;">${sec.content}</p>
          <div style="background: #FEF3C7; border-left: 3px solid #D97706; padding: 6px 10px; border-radius: 4px; font-size: 11.5px; color: #92400E;">
            <b><i class="fa-solid fa-lightbulb"></i> Cốt lõi:</b> ${sec.keyTakeaway || 'Ghi nhớ trọng tâm'}
          </div>
        </div>
      `).join("")
    : `<div style="font-size: 12px; color: #64748B;">Chưa thiết lập phần chi tiết.</div>`;

  document.getElementById("preview-modal-title").textContent = `Xem trước: [${lesson.id.toUpperCase()}] ${lesson.title}`;
  const body = document.getElementById("preview-modal-body");
  body.innerHTML = `
    <div style="background: var(--slate-50); border: 1px solid var(--slate-200); border-radius: 8px; padding: 14px; margin-bottom: 12px;">
      <span class="badge badge-navy">${lesson.category}</span>
      <h3 style="color: var(--navy-primary); font-size: 15px; margin: 8px 0;">${lesson.title}</h3>
      <p style="font-size: 12.5px; color: var(--slate-600); line-height: 1.5;">${lesson.summary}</p>
    </div>

    <div style="margin-bottom: 14px;">
      <h4 style="font-size: 13px; color: var(--navy-primary); margin-bottom: 8px; display: flex; align-items: center; gap: 6px;">
        <i class="fa-solid fa-list-check"></i> Danh sách từng phần bài giảng hiển thị trên App (${(lesson.sections || []).length} phần):
      </h4>
      ${sectionsHtml}
    </div>

    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px; font-size: 12px;">
      <div style="background: white; border: 1px solid var(--slate-200); padding: 10px; border-radius: 6px;">
        <i class="fa-solid fa-chalkboard-user" style="color: #3B82F6;"></i> <b>Trình chiếu Slide:</b> ${lesson.slidesCount} trang slide chuẩn
      </div>
      <div style="background: white; border: 1px solid var(--slate-200); padding: 10px; border-radius: 6px;">
        <i class="fa-solid fa-file-word" style="color: #2563EB;"></i> <b>Tài liệu tải về:</b> ${lesson.docxAttachment || 'Co_ban.docx'}
      </div>
      <div style="background: white; border: 1px solid var(--slate-200); padding: 10px; border-radius: 6px;">
        <i class="fa-solid fa-video" style="color: #059669;"></i> <b>Video bài giảng:</b> Chuẩn HD 1080p
      </div>
      <div style="background: white; border: 1px solid var(--slate-200); padding: 10px; border-radius: 6px;">
        <i class="fa-solid fa-headphones" style="color: #D97706;"></i> <b>Audio phát thanh:</b> Giọng đọc Tuyên huấn
      </div>
    </div>
  `;
  openModal("modal-preview");
};

window.editUser = function(id) {
  const user = store.users.find(u => u.id === id);
  if (!user) return;

  document.getElementById("modal-user-title").textContent = `Chỉnh sửa: ${user.fullName}`;
  document.getElementById("user-id").value = user.id;
  document.getElementById("user-code").value = user.militaryCode;
  document.getElementById("user-fullname").value = user.fullName;
  document.getElementById("user-rank").value = user.rank;
  document.getElementById("user-position").value = user.position;
  document.getElementById("user-unit").value = user.unit;
  document.getElementById("user-phone").value = user.phone;
  document.getElementById("user-progress").value = user.progress;

  openModal("modal-user");
};

window.deleteUser = function(id) {
  if (confirm("Xác nhận xóa tài khoản quân nhân này?")) {
    store.users = store.users.filter(u => u.id !== id);
    store.saveAll();
    renderUsersTable();
    showToast("Đã xóa tài khoản quân nhân.");
  }
};

function exportToCSV() {
  let csv = "Mã QN,Họ và Tên,Cấp bậc,Chức vụ,Đơn vị,Tiến độ (%),Điểm TB,Lần học cuối\n";
  store.users.forEach(u => {
    csv += `"${u.militaryCode}","${u.fullName}","${u.rank}","${u.position}","${u.unit}",${u.progress},${u.avgScore},"${u.lastActive}"\n`;
  });

  const blob = new Blob(["\uFEFF" + csv], { type: "text/csv;charset=utf-8;" });
  const url = URL.createObjectURL(blob);
  const link = document.createElement("a");
  link.setAttribute("href", url);
  link.setAttribute("download", `danh_sach_tien_do_gdct_${new Date().toISOString().slice(0,10)}.csv`);
  link.click();
  showToast("Đã xuất danh sách tiến độ học tập ra file CSV!");
}
