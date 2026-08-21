/**
 * BỘ TƯ LỆNH VÙNG 4 HẢI QUÂN - HỆ THỐNG QUẢN TRỊ NỘI DUNG & TÀI KHOẢN GDCT
 * Standalone CMS Web Engine & Full Admin Control Suite
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
    isInternal: false,
    questions: [
      {
        question: "Phương châm chỉ đạo xuyên suốt của Đảng ta trong giải quyết tranh chấp trên biển là gì?",
        options: ["Bị động đối phó", "Kiên quyết, kiên trì, bình tĩnh, khôn khéo", "Đơn phương giải quyết quân sự", "Chờ đợi đối tác phản hồi"],
        correctAnswer: 1,
        explanation: "Phương châm: Kiên quyết, kiên trì đấu tranh bảo vệ vững chắc độc lập, chủ quyền, thống nhất, toàn vẹn lãnh thổ của Tổ quốc."
      },
      {
        question: "Bộ Tư lệnh Vùng 4 Hải quân có nhiệm vụ nòng cốt bảo vệ vùng biển nào?",
        options: ["Vịnh Bắc Bộ", "Quần đảo Hoàng Sa", "Quần đảo Trường Sa và vùng biển Nam Trung Bộ", "Vịnh Thái Lan"],
        correctAnswer: 2,
        explanation: "Vùng 4 Hải quân quản lý vùng biển chiến lược Nam Trung Bộ và quần đảo Trường Sa."
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
    isInternal: true,
    questions: [
      {
        question: "Yếu tố quyết định thắng lợi trong tác chiến hiện đại trên biển của Bộ đội Hải quân là gì?",
        options: ["Vũ khí tối tân hoàn toàn", "Con người với bản lĩnh chính trị kiên định và làm chủ VKTB", "Thời tiết thuận lợi", "Số lượng tàu thuyền"],
        correctAnswer: 1,
        explanation: "Con người là nhân tố quyết định, bản lĩnh chính trị kiên định vững vàng là nền tảng."
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
    isInternal: false,
    questions: [
      {
        question: "Truyền thống vẻ vang 16 chữ vàng của Quân chủng Hải quân là gì?",
        options: [
          "Đoàn kết, kỷ luật, tự lực, tự cường",
          "Chiến đấu anh dũng; mưu trí, sáng tạo; làm chủ vùng biển; quyết chiến, quyết thắng",
          "Trung dũng, kiên cường, toàn dân đánh giặc",
          "Thần tốc, táo bạo, bất ngờ, chắc thắng"
        ],
        correctAnswer: 1,
        explanation: "Truyền thống 16 chữ vàng do Đảng và Nhà nước trao tặng Quân chủng Hải quân."
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
    isInternal: false,
    questions: [
      {
        question: "Thông tư 143/2023/TT-BQP quy định xử lý nghiêm nhất hành vi vi phạm nào?",
        options: ["Đi muộn giờ", "Vi phạm nồng độ cồn khi tham gia giao thông và vay mượn tài chính bất hợp pháp", "Không mặc đúng quân phục dạo mát", "Đọc sách ngoài giờ"],
        correctAnswer: 1,
        explanation: "Thông tư 143/2023/TT-BQP xử lý nghiêm khắc vi phạm nồng độ cồn, tệ nạn, vay nợ bất hợp pháp."
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
    lecturer: "Đại tá Nguyễn Văn Hiến",
    unit: "Phòng Chính trị Vùng 4",
    slidesCount: 8,
    docxAttachment: "Chuyen_de_05_Tu_tuong_Bac_Ho.docx",
    pdfAttachment: "Chuyen_de_05_Tu_tuong_Bac_Ho.pdf",
    videoUrl: "https://video.gdct.vung4.vn/cd-05-hd.mp4",
    audioUrl: "https://audio.gdct.vung4.vn/cd-05-phat-thanh.mp3",
    quizCount: 4,
    isInternal: false,
    questions: [
      {
        question: "Lời Bác Hồ căn dặn Bộ đội Hải quân khi về thăm Vạn Hoa (Hải Phòng) năm 1961 là gì?",
        options: [
          "Không có gì quý hơn độc lập tự do",
          "Ngày trước ta chỉ có đêm và rừng. Ngày nay ta có ngày, có trời, có biển. Bờ biển ta dài, tươi đẹp, ta phải biết giữ gìn lấy nó",
          "Quyết tử để Tổ quốc quyết sinh",
          "Vì lợi ích mười năm trồng cây, vì lợi ích trăm năm trồng người"
        ],
        correctAnswer: 1,
        explanation: "Lời dạy bất hủ của Chủ tịch Hồ Chí Minh tại đảo Vạn Hoa năm 1961."
      }
    ]
  }
];

const DEFAULT_USERS = [
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
    lastActive: "Hôm nay, 08:30",
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

// Helpers
function removeVietnameseTones(str) {
  if (!str) return "";
  str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
  str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
  str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
  str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
  str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
  str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
  str = str.replace(/đ/g, "d");
  str = str.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
  str = str.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
  str = str.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
  str = str.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
  str = str.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
  str = str.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
  str = str.replace(/Đ/g, "D");
  return str.toLowerCase().replace(/[^a-z0-9]/g, "");
}

function getUnitCode(unitStr) {
  if (!unitStr) return "v4";
  const str = unitStr.toLowerCase();
  if (str.includes("162")) return "162";
  if (str.includes("146")) return "146";
  if (str.includes("955")) return "955";
  if (str.includes("101")) return "101";
  if (str.includes("685")) return "685";
  if (str.includes("bđkt") || str.includes("bdkt") || str.includes("kỹ thuật")) return "bdkt";
  if (str.includes("chính trị") || str.includes("chinh tri") || str.includes("pct")) return "pct";
  if (str.includes("tham mưu") || str.includes("tham muu")) return "tm";
  if (str.includes("hậu cần") || str.includes("hau can")) return "hc";
  return "v4";
}

function generateUniqueUsername(fullName, unit, currentUserId = null) {
  const nameCode = removeVietnameseTones(fullName);
  const unitCode = getUnitCode(unit);
  if (!nameCode) return `user_${unitCode}`;

  const baseUsername = `${nameCode}_${unitCode}`;
  const existingUsers = store.users.filter(u => u.id !== currentUserId);
  const existingUsernames = new Set(existingUsers.map(u => (u.username || "").toLowerCase()));

  if (!existingUsernames.has(baseUsername)) {
    return baseUsername;
  }

  let counter = 2;
  while (existingUsernames.has(`${nameCode}${counter}_${unitCode}`)) {
    counter++;
  }
  return `${nameCode}${counter}_${unitCode}`;
}

// App State Management with LocalStorage
class AdminDataStore {
  constructor() {
    this.lessons = this.load("gdct_admin_lessons", DEFAULT_LESSONS);
    this.users = this.load("gdct_admin_users", DEFAULT_USERS);
    this.laws = this.load("gdct_admin_laws", DEFAULT_LAWS);
    
    this.users = this.users.map((u, idx) => ({
      ...u,
      orderNumber: u.orderNumber || idx + 1,
      username: u.username || generateUniqueUsername(u.fullName, u.unit, u.id),
      password: u.password || "12345@abc"
    }));
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
    try {
      localStorage.setItem(key, JSON.stringify(data));
    } catch (e) {
      console.warn("Storage save error", e);
    }
  }

  saveAll() {
    this.save("gdct_admin_lessons", this.lessons);
    this.save("gdct_admin_users", this.users);
    this.save("gdct_admin_laws", this.laws);
  }
}

const store = new AdminDataStore();

// ADMIN AUTHENTICATION
const ADMIN_CREDENTIALS = {
  username: "gdct_vung4",
  password: "12345@abc"
};

function checkAdminAuth() {
  const isAuth = sessionStorage.getItem("gdct_admin_authenticated") === "true";
  const overlay = document.getElementById("admin-login-overlay");
  if (overlay) {
    overlay.style.display = isAuth ? "none" : "flex";
  }
}

window.handleAdminLogin = function() {
  const usernameInput = document.getElementById("admin-username-input").value.trim();
  const passwordInput = document.getElementById("admin-password-input").value;
  const errorBox = document.getElementById("admin-login-error");
  const errorText = document.getElementById("admin-login-error-text");

  if (usernameInput === ADMIN_CREDENTIALS.username && passwordInput === ADMIN_CREDENTIALS.password) {
    sessionStorage.setItem("gdct_admin_authenticated", "true");
    if (errorBox) errorBox.style.display = "none";
    const overlay = document.getElementById("admin-login-overlay");
    if (overlay) overlay.style.display = "none";
    showToast("Đăng nhập Cổng Quản trị GDCT Vùng 4 thành công!");
  } else {
    if (errorBox) {
      errorBox.style.display = "flex";
      errorText.textContent = "Tài khoản hoặc Mật khẩu không chính xác! (Yêu cầu: gdct_vung4 / 12345@abc)";
    }
  }
};

window.toggleAdminPasswordVisibility = function() {
  const input = document.getElementById("admin-password-input");
  const eye = document.getElementById("icon-admin-pass-eye");
  if (input) {
    if (input.type === "password") {
      input.type = "text";
      if (eye) eye.className = "fa-solid fa-eye-slash";
    } else {
      input.type = "password";
      if (eye) eye.className = "fa-solid fa-eye";
    }
  }
};

// DOM READY
document.addEventListener("DOMContentLoaded", () => {
  checkAdminAuth();
  initNavigation();
  renderOverview();
  renderLessonsTable();
  renderUsersTable();
  renderQuizBank();
  renderLaws();
  initActionButtons();
  initFileUploadHandlers();
});

// Toast notification helper
function showToast(message, type = "info") {
  const container = document.getElementById("toast-container");
  if (!container) return;

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
      const targetPane = document.getElementById(`tab-${tabId}`);
      if (targetPane) targetPane.classList.add("active");

      if (titles[tabId] && pageTitle) {
        pageTitle.innerHTML = titles[tabId];
      }
    });
  });
}

// 1. RENDER OVERVIEW
function renderOverview() {
  const totalLessonsEl = document.getElementById("stat-total-lessons");
  const totalUsersEl = document.getElementById("stat-total-users");
  if (totalLessonsEl) totalLessonsEl.textContent = store.lessons.length;
  if (totalUsersEl) totalUsersEl.textContent = store.users.length;

  const unitTbody = document.getElementById("unit-progress-tbody");
  if (unitTbody) {
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
  }

  const activityList = document.getElementById("recent-activity-list");
  if (activityList) {
    activityList.innerHTML = `
      <div>• <b>Đ/c Phạm Tất Thắng (Lữ đoàn 162)</b>: Đã hoàn thành 100% bài giảng GDCT (Điểm TB: 9.8)</div>
      <div>• <b>Đ/c Lê Hoàng Hải (Đoàn Trường Sa)</b>: Vừa hoàn thành bài thi trắc nghiệm Chuyên đề 02</div>
      <div>• <b>Lữ đoàn 162</b>: 100% tài khoản đã đổi mật khẩu và truy cập hệ thống nội bộ</div>
      <div>• <b>Tàu 561 (Lữ đoàn 955)</b>: Đã tải 12 tài liệu DOCX/PDF tự học trên biển</div>
    `;
  }
}

// 2. RENDER LESSONS TABLE
function renderLessonsTable() {
  const tbody = document.getElementById("lessons-tbody");
  const search = document.getElementById("lesson-search")?.value.toLowerCase() || "";
  const catFilter = document.getElementById("lesson-filter-category")?.value || "ALL";
  const audFilter = document.getElementById("lesson-filter-audience")?.value || "ALL";

  const filtered = store.lessons.filter(l => {
    const matchSearch = l.title.toLowerCase().includes(search) || 
      l.id.toLowerCase().includes(search) || 
      (l.lecturer && l.lecturer.toLowerCase().includes(search));
    const matchCat = catFilter === "ALL" || l.category === catFilter;
    const matchAud = audFilter === "ALL" || (l.targetAudience && l.targetAudience.includes(audFilter));
    return matchSearch && matchCat && matchAud;
  });

  if (!tbody) return;

  tbody.innerHTML = filtered.map(l => `
    <tr>
      <td>
        <div style="display: flex; flex-direction: column; gap: 3px;">
          <span class="badge badge-navy"><b>${l.id.toUpperCase()}</b></span>
          ${l.isInternal ? '<span class="badge" style="background:#FEE2E2; color:#DC2626; font-size:9.5px;"><i class="fa-solid fa-lock"></i> Nội bộ</span>' : '<span class="badge" style="background:#F1F5F9; color:#475569; font-size:9.5px;">Công khai</span>'}
        </div>
      </td>
      <td style="max-width: 300px;">
        <div style="font-weight: 700; color: var(--navy-primary); margin-bottom: 2px;">${l.title}</div>
        <div style="font-size: 11px; color: var(--slate-500);">${l.lecturer || 'Ban Tuyên huấn Vùng 4'} • ${l.unit || 'Phòng Chính trị'}</div>
      </td>
      <td><span class="badge badge-blue">${l.category}</span></td>
      <td style="font-size: 12px;">${l.targetAudience}</td>
      <td><b>${l.estimatedMinutes} phút</b></td>
      <td>
        <div style="display: flex; gap: 6px; font-size: 13px;">
          <span title="Slide: ${l.slidesCount || 8} trang" style="color: #3B82F6;"><i class="fa-solid fa-chalkboard-user"></i> ${l.slidesCount || 8}</span>
          <span title="Tệp Word: ${l.docxAttachment || 'Tai_Lieu.docx'}" style="color: #2563EB;"><i class="fa-solid fa-file-word"></i></span>
          <span title="Tệp PDF: ${l.pdfAttachment || 'Tai_Lieu.pdf'}" style="color: #EF4444;"><i class="fa-solid fa-file-pdf"></i></span>
          <span title="Video: ${l.videoUrl || 'Có video'}" style="color: #10B981;"><i class="fa-solid fa-video"></i></span>
          <span title="Audio: ${l.audioUrl || 'Có audio'}" style="color: #F59E0B;"><i class="fa-solid fa-headphones"></i></span>
        </div>
      </td>
      <td><span class="badge badge-green">${l.questions ? l.questions.length : (l.quizCount || 4)} câu</span></td>
      <td>
        <div style="display: flex; gap: 4px;">
          <button class="btn btn-outline btn-sm" onclick="previewLesson('${l.id}')" title="Xem trước / Tải file"><i class="fa-solid fa-eye"></i></button>
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

  const sorted = [...store.users].sort((a, b) => (a.orderNumber || 0) - (b.orderNumber || 0));

  const filtered = sorted.filter(u => {
    const matchSearch = u.fullName.toLowerCase().includes(search) || 
      (u.username && u.username.toLowerCase().includes(search)) || 
      u.militaryCode.toLowerCase().includes(search) || 
      u.position.toLowerCase().includes(search);
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
      <td style="text-align: center;">
        <span class="badge badge-navy" style="font-size: 11px; padding: 2px 6px;">#${u.orderNumber || 1}</span>
      </td>
      <td>
        <div style="display: flex; align-items: center; gap: 6px;">
          <code style="font-size: 12.5px; font-weight: 700; color: #1E3A8A; background: #EEF2FF; padding: 2px 6px; border-radius: 4px;">
            ${u.username || 'phamtatthang_162'}
          </code>
          <button class="btn-outline btn-sm" onclick="copyToClipboard('${u.username}')" title="Sao chép tên tài khoản" style="padding: 2px 5px; border: none; background: transparent; cursor: pointer;">
            <i class="fa-solid fa-copy" style="color: #64748B;"></i>
          </button>
        </div>
        <div style="font-size: 10.5px; color: #64748B; margin-top: 2px;">Mã: ${u.militaryCode}</div>
      </td>
      <td>
        <div style="font-weight: 700; color: var(--navy-primary);">${u.fullName}</div>
        <div style="font-size: 11px; color: var(--slate-500);"><i class="fa-solid fa-phone"></i> ${u.phone}</div>
      </td>
      <td>
        <div><span class="badge badge-navy">${u.rank}</span></div>
        <div style="font-size: 11.5px; color: var(--slate-700); margin-top: 2px;">${u.position}</div>
      </td>
      <td>
        <b>${u.unit}</b>
        <div>${u.isInternalAccess !== false ? '<span class="badge" style="background:#DCFCE7; color:#166534; font-size:9.5px;"><i class="fa-solid fa-shield-check"></i> Quyền nội bộ</span>' : '<span class="badge" style="background:#F1F5F9; color:#64748B; font-size:9.5px;">Công khai</span>'}</div>
      </td>
      <td>
        <div style="display: flex; align-items: center; gap: 4px;">
          <input type="password" value="${u.password || '12345@abc'}" readonly style="width: 85px; font-size: 11px; border: 1px solid #CBD5E1; border-radius: 4px; padding: 2px 4px; background: #F8FAFC;" id="pass-${u.id}">
          <button class="btn btn-outline btn-sm" onclick="togglePasswordView('${u.id}')" title="Xem/Ẩn mật khẩu" style="padding: 2px 5px;">
            <i class="fa-solid fa-eye" id="eye-${u.id}" style="font-size: 10px;"></i>
          </button>
        </div>
      </td>
      <td style="min-width: 110px;">
        <div style="display: flex; align-items: center; gap: 6px;">
          <div class="progress-bar-container" style="flex: 1;">
            <div class="progress-bar-fill" style="width: ${u.progress}%; ${u.progress < 50 ? 'background: #EF4444;' : ''}"></div>
          </div>
          <b>${u.progress}%</b>
        </div>
      </td>
      <td><b style="color: ${u.avgScore >= 8 ? 'var(--green-600)' : 'var(--slate-800)'}">${u.avgScore}đ</b></td>
      <td>
        <div style="display: flex; gap: 4px;">
          <button class="btn btn-outline btn-sm" onclick="resetUserPassword('${u.id}')" title="Đặt lại Mật khẩu về 12345@abc" style="color: #D97706;">
            <i class="fa-solid fa-key"></i>
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
  const filterLesson = document.getElementById("quiz-filter-lesson");
  const search = document.getElementById("quiz-search")?.value.toLowerCase() || "";
  const selectedLessonId = filterLesson?.value || "ALL";

  if (filterLesson && filterLesson.options.length <= 1) {
    filterLesson.innerHTML = '<option value="ALL">Tất cả chuyên đề bài giảng</option>' + 
      store.lessons.map(l => `<option value="${l.id}">[${l.id.toUpperCase()}] ${l.title.slice(0, 45)}...</option>`).join("");
  }

  // Populate select in question modal as well
  const modalLessonSelect = document.getElementById("question-lesson-id");
  if (modalLessonSelect) {
    modalLessonSelect.innerHTML = store.lessons.map(l => `<option value="${l.id}">[${l.id.toUpperCase()}] ${l.title}</option>`).join("");
  }

  if (!container) return;

  const matchedLessons = store.lessons.filter(l => selectedLessonId === "ALL" || l.id === selectedLessonId);

  container.innerHTML = matchedLessons.map((l) => {
    const questions = l.questions || [
      {
        question: "Phương châm chỉ đạo xuyên suốt trong giáo dục chính trị tại đơn vị là gì?",
        options: ["Thụ động tiếp thu", "Gắn lý luận với thực tiễn chiến đấu", "Chỉ học lý thuyết", "Tự phát cá nhân"],
        correctAnswer: 1,
        explanation: "Phương châm cơ bản trong GDCT quân đội."
      }
    ];

    const filteredQs = questions.filter(q => q.question.toLowerCase().includes(search) || l.title.toLowerCase().includes(search));

    return `
      <div style="border: 1px solid var(--slate-200); border-radius: 10px; padding: 16px; background: white; margin-bottom: 12px;">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px; border-bottom: 1px solid var(--slate-100); padding-bottom: 8px;">
          <div style="font-weight: 700; color: var(--navy-primary); font-size: 14px;">
            <span class="badge badge-navy">${l.id.toUpperCase()}</span> ${l.title}
          </div>
          <div style="display: flex; gap: 8px; align-items: center;">
            <span class="badge badge-green">${questions.length} Câu trắc nghiệm</span>
            <button class="btn btn-outline btn-sm" onclick="openAddQuestionModalForLesson('${l.id}')">
              <i class="fa-solid fa-plus"></i> Thêm câu hỏi
            </button>
          </div>
        </div>

        <div style="display: flex; flex-direction: column; gap: 10px;">
          ${filteredQs.map((q, idx) => `
            <div style="font-size: 12.5px; color: var(--slate-700); background: var(--slate-50); padding: 12px; border-radius: 6px; border: 1px solid var(--slate-200);">
              <div style="display: flex; justify-content: space-between; align-items: flex-start;">
                <b>Câu ${idx + 1}: ${q.question}</b>
                <button class="btn-outline btn-sm" onclick="deleteQuestion('${l.id}', ${idx})" title="Xóa câu hỏi" style="color: var(--crimson-red); border: none; background: transparent; cursor: pointer; padding: 2px 6px;">
                  <i class="fa-solid fa-trash"></i>
                </button>
              </div>
              <div style="margin-top: 8px; display: grid; grid-template-columns: 1fr 1fr; gap: 6px; font-size: 12px;">
                ${q.options.map((opt, oIdx) => `
                  <div style="padding: 4px 8px; border-radius: 4px; ${oIdx === q.correctAnswer ? 'background: #DCFCE7; color: #166534; font-weight: bold; border: 1px solid #86EFAC;' : 'background: white; border: 1px solid #E2E8F0;'}">
                    <b>${String.fromCharCode(65 + oIdx)}.</b> ${opt} ${oIdx === q.correctAnswer ? '(Đúng)' : ''}
                  </div>
                `).join("")}
              </div>
              ${q.explanation ? `<div style="margin-top: 6px; font-size: 11px; color: var(--slate-500); font-style: italic;"><i class="fa-solid fa-lightbulb" style="color: #EAB308;"></i> ${q.explanation}</div>` : ''}
            </div>
          `).join("")}
        </div>
      </div>
    `;
  }).join("");
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
      <div style="margin-top: 14px; display: flex; justify-content: space-between; align-items: center;">
        <button class="btn btn-outline btn-sm" onclick="deleteLaw('${law.id}')" style="color: var(--crimson-red);"><i class="fa-solid fa-trash"></i> Xóa</button>
        <button class="btn btn-outline btn-sm" onclick="showToast('Đã mở toàn văn: ${law.title}')"><i class="fa-solid fa-book-open"></i> Đọc toàn văn</button>
      </div>
    </div>
  `).join("");
}

// FILE UPLOAD HANDLERS
function initFileUploadHandlers() {
  document.getElementById("file-lesson-docx")?.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (file) {
      document.getElementById("lesson-docx-name").value = file.name;
      const sizeMb = (file.size / (1024 * 1024)).toFixed(2);
      document.getElementById("docx-file-status").innerHTML = `<i class="fa-solid fa-check"></i> Đã chọn: <b>${file.name}</b> (${sizeMb} MB)`;
      showToast(`Đã nạp tệp Word: ${file.name} (${sizeMb} MB)`);
    }
  });

  document.getElementById("file-lesson-pdf")?.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (file) {
      document.getElementById("lesson-pdf-name").value = file.name;
      const sizeMb = (file.size / (1024 * 1024)).toFixed(2);
      document.getElementById("pdf-file-status").innerHTML = `<i class="fa-solid fa-check"></i> Đã chọn: <b>${file.name}</b> (${sizeMb} MB)`;
      showToast(`Đã nạp tệp PDF: ${file.name} (${sizeMb} MB)`);
    }
  });

  document.getElementById("file-lesson-video")?.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (file) {
      document.getElementById("lesson-video-url").value = `local://${file.name}`;
      const sizeMb = (file.size / (1024 * 1024)).toFixed(2);
      document.getElementById("video-file-status").innerHTML = `<i class="fa-solid fa-check"></i> Đã nạp video từ máy: <b>${file.name}</b> (${sizeMb} MB)`;
      showToast(`Đã nạp video bài giảng: ${file.name}`);
    }
  });

  document.getElementById("file-lesson-audio")?.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (file) {
      document.getElementById("lesson-audio-url").value = `local://${file.name}`;
      const sizeMb = (file.size / (1024 * 1024)).toFixed(2);
      document.getElementById("audio-file-status").innerHTML = `<i class="fa-solid fa-check"></i> Đã nạp audio phát thanh: <b>${file.name}</b> (${sizeMb} MB)`;
      showToast(`Đã nạp âm thanh phát thanh: ${file.name}`);
    }
  });

  document.getElementById("btn-auto-username")?.addEventListener("click", () => {
    const name = document.getElementById("user-fullname").value.trim();
    const unit = document.getElementById("user-unit").value;
    const userId = document.getElementById("user-id").value;
    if (!name) {
      alert("Vui lòng nhập Họ và tên quân nhân trước khi tạo tên tài khoản!");
      return;
    }
    const generated = generateUniqueUsername(name, unit, userId);
    document.getElementById("user-username").value = generated;
    showToast(`Đã tạo tên tài khoản: ${generated}`);
  });

  document.getElementById("user-fullname")?.addEventListener("input", (e) => {
    const usernameInput = document.getElementById("user-username");
    if (usernameInput && (!usernameInput.value || usernameInput.dataset.manual !== "true")) {
      const name = e.target.value.trim();
      const unit = document.getElementById("user-unit")?.value || "162";
      const userId = document.getElementById("user-id")?.value;
      if (name.length >= 3) {
        usernameInput.value = generateUniqueUsername(name, unit, userId);
      }
    }
  });

  document.getElementById("user-username")?.addEventListener("input", () => {
    document.getElementById("user-username").dataset.manual = "true";
  });

  // User import file
  document.getElementById("input-import-users-file")?.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (evt) => {
        document.getElementById("textarea-import-users").value = evt.target.result;
        document.getElementById("import-users-file-status").innerHTML = `<i class="fa-solid fa-check"></i> Đã nạp: <b>${file.name}</b>`;
        showToast(`Đã nạp file danh sách: ${file.name}`);
      };
      reader.readAsText(file);
    }
  });
}

// MODAL CONTROLS
window.openModal = function(id) {
  const el = document.getElementById(id);
  if (el) el.classList.add("show");
};

window.closeModal = function(id) {
  const el = document.getElementById(id);
  if (el) el.classList.remove("show");
};

function initActionButtons() {
  document.getElementById("btn-urge-all")?.addEventListener("click", () => {
    showToast("Đã phát lệnh đôn đốc học tập đến toàn thể quân nhân Vùng 4 qua SMS và thông báo App!", "warning");
  });

  document.getElementById("btn-quick-export")?.addEventListener("click", () => {
    exportToCSV();
  });

  document.getElementById("btn-logout")?.addEventListener("click", () => {
    if (confirm("Đồng chí có chắc chắn muốn đăng xuất khỏi Cổng Quản trị GDCT?")) {
      sessionStorage.removeItem("gdct_admin_authenticated");
      checkAdminAuth();
      showToast("Đã kết thúc phiên làm việc an toàn.");
    }
  });

  // Filter Listeners
  document.getElementById("lesson-search")?.addEventListener("input", renderLessonsTable);
  document.getElementById("lesson-filter-category")?.addEventListener("change", renderLessonsTable);
  document.getElementById("lesson-filter-audience")?.addEventListener("change", renderLessonsTable);
  document.getElementById("user-search")?.addEventListener("input", renderUsersTable);
  document.getElementById("user-filter-unit")?.addEventListener("change", renderUsersTable);
  document.getElementById("user-filter-status")?.addEventListener("change", renderUsersTable);
  document.getElementById("quiz-search")?.addEventListener("input", renderQuizBank);
  document.getElementById("quiz-filter-lesson")?.addEventListener("change", renderQuizBank);

  // Lesson Modal
  document.getElementById("btn-add-lesson")?.addEventListener("click", () => {
    document.getElementById("modal-lesson-title").textContent = "Soạn Bài giảng Giáo dục Chính trị mới";
    document.getElementById("form-lesson").reset();
    document.getElementById("lesson-id").value = "";
    document.getElementById("docx-file-status").innerHTML = "";
    document.getElementById("pdf-file-status").innerHTML = "";
    document.getElementById("video-file-status").innerHTML = "";
    document.getElementById("audio-file-status").innerHTML = "";
    openModal("modal-lesson");
  });

  document.getElementById("btn-save-lesson")?.addEventListener("click", () => {
    const code = document.getElementById("lesson-code").value.trim();
    const title = document.getElementById("lesson-title").value.trim();
    const lecturer = document.getElementById("lesson-lecturer").value.trim() || "Phòng Chính trị Vùng 4";
    const category = document.getElementById("lesson-category").value;
    const audience = document.getElementById("lesson-audience").value.trim();
    const duration = parseInt(document.getElementById("lesson-duration").value) || 45;
    const summary = document.getElementById("lesson-summary").value.trim();
    const isInternal = document.getElementById("lesson-is-internal")?.checked || false;
    const id = document.getElementById("lesson-id").value || code.toLowerCase();

    if (!code || !title) {
      alert("Vui lòng nhập đầy đủ Mã bài giảng và Tên chuyên đề!");
      return;
    }

    const existingIdx = store.lessons.findIndex(l => l.id === id);
    const existingLesson = existingIdx >= 0 ? store.lessons[existingIdx] : null;

    const newLesson = {
      id,
      title,
      category,
      targetAudience: audience || "Cán bộ, chiến sĩ Vùng 4",
      estimatedMinutes: duration,
      summary: summary || "Nội dung học tập chính trị trọng tâm năm 2026",
      lecturer: lecturer,
      unit: "Bộ Tư lệnh Vùng 4 Hải quân",
      slidesCount: 8,
      docxAttachment: document.getElementById("lesson-docx-name").value || `${code}_Tai_Lieu.docx`,
      pdfAttachment: document.getElementById("lesson-pdf-name").value || `${code}_Tai_Lieu.pdf`,
      videoUrl: document.getElementById("lesson-video-url").value || `https://video.gdct.vung4.vn/${code}.mp4`,
      audioUrl: document.getElementById("lesson-audio-url").value || `https://audio.gdct.vung4.vn/${code}.mp3`,
      quizCount: existingLesson?.questions ? existingLesson.questions.length : 4,
      isInternal,
      questions: existingLesson?.questions || []
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
    document.getElementById("modal-user-title").textContent = "Thêm / Cấp Tài khoản Quân nhân mới";
    document.getElementById("form-user").reset();
    document.getElementById("user-id").value = "";
    document.getElementById("user-order-number").value = store.users.length + 1;
    document.getElementById("user-password").value = "12345@abc";
    document.getElementById("user-internal-access").checked = true;
    document.getElementById("user-username").dataset.manual = "false";
    openModal("modal-user");
  });

  document.getElementById("btn-save-user")?.addEventListener("click", () => {
    const orderNumber = parseInt(document.getElementById("user-order-number").value) || store.users.length + 1;
    const name = document.getElementById("user-fullname").value.trim();
    const unit = document.getElementById("user-unit").value;
    const code = document.getElementById("user-code").value.trim() || `QN-${getUnitCode(unit)}01`;
    let username = document.getElementById("user-username").value.trim();
    const password = document.getElementById("user-password").value.trim() || "12345@abc";
    const rank = document.getElementById("user-rank").value;
    const position = document.getElementById("user-position").value.trim() || "Chiến sĩ";
    const phone = document.getElementById("user-phone").value.trim() || "0988.112.233";
    const isInternalAccess = document.getElementById("user-internal-access")?.checked !== false;
    const progress = parseInt(document.getElementById("user-progress").value) || 0;
    const id = document.getElementById("user-id").value || `acc_${Date.now()}`;

    if (!name) {
      alert("Vui lòng nhập Họ và tên quân nhân!");
      return;
    }

    if (!username) {
      username = generateUniqueUsername(name, unit, id);
    }

    const existingIdx = store.users.findIndex(u => u.id === id);
    const userObj = {
      id,
      orderNumber,
      username,
      password,
      militaryCode: code,
      fullName: name,
      rank,
      position,
      unit,
      phone,
      progress,
      avgScore: progress === 100 ? 9.8 : (progress / 10).toFixed(1),
      lastActive: "Vừa cập nhật",
      isInternalAccess
    };

    if (existingIdx >= 0) {
      store.users[existingIdx] = userObj;
      showToast(`Đã cập nhật tài khoản [${username}] của đ/c ${name}`);
    } else {
      store.users.push(userObj);
      showToast(`Đã cấp tài khoản [${username}] cho đ/c ${name} (STT: #${orderNumber})`);
    }

    store.saveAll();
    closeModal("modal-user");
    renderUsersTable();
  });

  // Import / Export Users
  document.getElementById("btn-import-users")?.addEventListener("click", () => {
    openModal("modal-import-users");
  });

  document.getElementById("btn-export-users")?.addEventListener("click", () => {
    exportToCSV();
  });

  document.getElementById("btn-execute-import-users")?.addEventListener("click", () => {
    const raw = document.getElementById("textarea-import-users").value.trim();
    if (!raw) {
      alert("Vui lòng dán dữ liệu hoặc tải tệp lên!");
      return;
    }

    try {
      if (raw.startsWith("[") || raw.startsWith("{")) {
        const parsed = JSON.parse(raw);
        const list = Array.isArray(parsed) ? parsed : [parsed];
        list.forEach(item => {
          const id = `acc_${Date.now()}_${Math.floor(Math.random()*1000)}`;
          const unit = item.unit || "Lữ đoàn 162";
          const username = item.username || generateUniqueUsername(item.fullName || "Quân nhân", unit);
          store.users.push({
            id,
            orderNumber: store.users.length + 1,
            username,
            password: item.password || "12345@abc",
            militaryCode: item.militaryCode || `QN-${getUnitCode(unit)}01`,
            fullName: item.fullName || "Quân nhân Vùng 4",
            rank: item.rank || "Chiến sĩ",
            position: item.position || "Chiến sĩ",
            unit: unit,
            phone: item.phone || "0988.112.233",
            progress: item.progress || 0,
            avgScore: item.avgScore || 0,
            lastActive: "Mới tạo",
            isInternalAccess: item.isInternalAccess !== false
          });
        });
      } else {
        // Parse CSV line by line
        const lines = raw.split("\n").filter(l => l.trim().length > 0);
        lines.forEach((line, idx) => {
          if (idx === 0 && line.toLowerCase().includes("họ và tên")) return; // Skip header
          const parts = line.split(",").map(p => p.trim().replace(/^"|"$/g, ''));
          if (parts.length >= 2) {
            const name = parts[0] || parts[1] || "Quân nhân";
            const unit = parts[2] || "Lữ đoàn 162";
            const rank = parts[3] || "Hạ sĩ";
            const position = parts[4] || "Chiến sĩ";
            const username = generateUniqueUsername(name, unit);
            store.users.push({
              id: `acc_${Date.now()}_${idx}`,
              orderNumber: store.users.length + 1,
              username,
              password: "12345@abc",
              militaryCode: `QN-${getUnitCode(unit)}0${idx+1}`,
              fullName: name,
              rank,
              position,
              unit,
              phone: "0988.112.233",
              progress: 0,
              avgScore: 0,
              lastActive: "Mới tạo",
              isInternalAccess: true
            });
          }
        });
      }

      store.saveAll();
      closeModal("modal-import-users");
      renderUsersTable();
      renderOverview();
      showToast("Đã nhập danh sách quân nhân và cấp phát tài khoản thành công!");
    } catch (e) {
      alert("Lỗi khi xử lý dữ liệu nhập! Vui lòng kiểm tra lại định dạng JSON/CSV.");
    }
  });

  // Question Modal
  document.getElementById("btn-add-question")?.addEventListener("click", () => {
    document.getElementById("form-question").reset();
    document.getElementById("question-id").value = "";
    openModal("modal-question");
  });

  document.getElementById("btn-save-question")?.addEventListener("click", () => {
    const lessonId = document.getElementById("question-lesson-id").value;
    const text = document.getElementById("question-text").value.trim();
    const optA = document.getElementById("question-opt-a").value.trim();
    const optB = document.getElementById("question-opt-b").value.trim();
    const optC = document.getElementById("question-opt-c").value.trim();
    const optD = document.getElementById("question-opt-d").value.trim();
    const correct = parseInt(document.getElementById("question-correct").value) || 0;
    const explanation = document.getElementById("question-explanation").value.trim();

    if (!text || !optA || !optB) {
      alert("Vui lòng nhập nội dung câu hỏi và ít nhất 2 đáp án A, B!");
      return;
    }

    const lesson = store.lessons.find(l => l.id === lessonId);
    if (!lesson) {
      alert("Không tìm thấy bài giảng được chọn!");
      return;
    }

    if (!lesson.questions) lesson.questions = [];
    lesson.questions.push({
      question: text,
      options: [optA, optB, optC || "Phương án C", optD || "Phương án D"],
      correctAnswer: correct,
      explanation
    });

    lesson.quizCount = lesson.questions.length;
    store.saveAll();
    closeModal("modal-question");
    renderQuizBank();
    renderLessonsTable();
    showToast("Đã lưu câu hỏi trắc nghiệm mới vào ngân hàng đề thi!");
  });

  // Law Modal
  document.getElementById("btn-add-law")?.addEventListener("click", () => {
    document.getElementById("form-law").reset();
    document.getElementById("law-id").value = "";
    openModal("modal-law");
  });

  document.getElementById("btn-save-law")?.addEventListener("click", () => {
    const title = document.getElementById("law-title").value.trim();
    const issuedBy = document.getElementById("law-issued-by").value.trim();
    const category = document.getElementById("law-category").value;
    const summary = document.getElementById("law-summary").value.trim();
    const id = document.getElementById("law-id").value || `law_${Date.now()}`;

    if (!title || !summary) {
      alert("Vui lòng nhập Tên văn bản và Tóm tắt nội dung!");
      return;
    }

    const newLaw = { id, title, issuedBy: issuedBy || "Bộ Quốc phòng", category, summary };
    const existingIdx = store.laws.findIndex(l => l.id === id);
    if (existingIdx >= 0) {
      store.laws[existingIdx] = newLaw;
      showToast(`Đã cập nhật: ${title}`);
    } else {
      store.laws.push(newLaw);
      showToast(`Đã thêm văn bản: ${title}`);
    }

    store.saveAll();
    closeModal("modal-law");
    renderLaws();
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
        renderQuizBank();
        renderLaws();
        showToast("Đã phục hồi cơ sở dữ liệu thành công!");
      } catch (err) {
        alert("Tệp JSON không hợp lệ!");
      }
    };
    reader.readAsText(file);
  });
}

// Global actions exposed to HTML inline onclick
window.copyToClipboard = function(text) {
  if (navigator.clipboard) {
    navigator.clipboard.writeText(text).then(() => {
      showToast(`Đã sao chép: ${text}`);
    });
  } else {
    showToast(`Tài khoản: ${text}`);
  }
};

window.togglePasswordView = function(id) {
  const input = document.getElementById(`pass-${id}`);
  const eye = document.getElementById(`eye-${id}`);
  if (input) {
    if (input.type === "password") {
      input.type = "text";
      if (eye) eye.className = "fa-solid fa-eye-slash";
    } else {
      input.type = "password";
      if (eye) eye.className = "fa-solid fa-eye";
    }
  }
};

window.resetUserPassword = function(id) {
  const user = store.users.find(u => u.id === id);
  if (!user) return;
  if (confirm(`Đặt lại mật khẩu cho tài khoản "${user.username}" (${user.fullName}) về mật khẩu mặc định "12345@abc"?`)) {
    user.password = "12345@abc";
    store.saveAll();
    renderUsersTable();
    showToast(`Đã đặt lại mật khẩu của ${user.fullName} về: 12345@abc`);
  }
};

window.urgeUnit = function(unitName) {
  showToast(`Đã gửi thông báo đôn đốc học tập đến chỉ huy và cán bộ ${unitName}!`, "warning");
};

window.editLesson = function(id) {
  const lesson = store.lessons.find(l => l.id === id);
  if (!lesson) return;

  document.getElementById("modal-lesson-title").textContent = `Chỉnh sửa: ${lesson.title}`;
  document.getElementById("lesson-id").value = lesson.id;
  document.getElementById("lesson-code").value = lesson.id;
  document.getElementById("lesson-title").value = lesson.title;
  document.getElementById("lesson-lecturer").value = lesson.lecturer || "";
  document.getElementById("lesson-category").value = lesson.category;
  document.getElementById("lesson-audience").value = lesson.targetAudience;
  document.getElementById("lesson-duration").value = lesson.estimatedMinutes;
  document.getElementById("lesson-summary").value = lesson.summary;
  document.getElementById("lesson-is-internal").checked = !!lesson.isInternal;
  document.getElementById("lesson-docx-name").value = lesson.docxAttachment || "";
  document.getElementById("lesson-pdf-name").value = lesson.pdfAttachment || "";
  document.getElementById("lesson-video-url").value = lesson.videoUrl || "";
  document.getElementById("lesson-audio-url").value = lesson.audioUrl || "";

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

  document.getElementById("preview-modal-title").textContent = `Xem trước: [${lesson.id.toUpperCase()}] ${lesson.title}`;
  const body = document.getElementById("preview-modal-body");
  body.innerHTML = `
    <div style="background: var(--slate-50); border: 1px solid var(--slate-200); border-radius: 8px; padding: 14px; margin-bottom: 12px;">
      <div style="display: flex; gap: 6px; align-items: center; margin-bottom: 6px;">
        <span class="badge badge-navy">${lesson.category}</span>
        ${lesson.isInternal ? '<span class="badge" style="background:#FEE2E2; color:#DC2626;"><i class="fa-solid fa-lock"></i> Lưu hành Nội bộ</span>' : '<span class="badge" style="background:#DCFCE7; color:#166534;">Công khai</span>'}
      </div>
      <h3 style="color: var(--navy-primary); font-size: 15px; margin: 4px 0 8px 0;">${lesson.title}</h3>
      <div style="font-size: 12px; color: var(--slate-500); margin-bottom: 6px;">Giảng viên: <b>${lesson.lecturer || 'Ban Tuyên huấn Vùng 4'}</b> • Đối tượng: ${lesson.targetAudience}</div>
      <p style="font-size: 12.5px; color: var(--slate-600); line-height: 1.5;">${lesson.summary}</p>
    </div>

    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px; font-size: 12px; margin-bottom: 14px;">
      <div style="background: white; border: 1px solid var(--slate-200); padding: 10px; border-radius: 6px;">
        <i class="fa-solid fa-chalkboard-user" style="color: #3B82F6;"></i> <b>Trình chiếu Slide:</b> ${lesson.slidesCount || 8} trang slide HD
      </div>
      <div style="background: white; border: 1px solid var(--slate-200); padding: 10px; border-radius: 6px;">
        <i class="fa-solid fa-file-word" style="color: #2563EB;"></i> <b>Tệp Word:</b> ${lesson.docxAttachment || 'Tai_Lieu.docx'}
        <br><button class="btn btn-outline btn-sm" style="margin-top: 5px;" onclick="downloadSampleFile('${lesson.docxAttachment || 'Tai_Lieu.docx'}')"><i class="fa-solid fa-download"></i> Tải về</button>
      </div>
      <div style="background: white; border: 1px solid var(--slate-200); padding: 10px; border-radius: 6px;">
        <i class="fa-solid fa-file-pdf" style="color: #DC2626;"></i> <b>Tệp PDF:</b> ${lesson.pdfAttachment || 'Tai_Lieu.pdf'}
        <br><button class="btn btn-outline btn-sm" style="margin-top: 5px;" onclick="downloadSampleFile('${lesson.pdfAttachment || 'Tai_Lieu.pdf'}')"><i class="fa-solid fa-download"></i> Tải về</button>
      </div>
      <div style="background: white; border: 1px solid var(--slate-200); padding: 10px; border-radius: 6px;">
        <i class="fa-solid fa-video" style="color: #059669;"></i> <b>Video bài giảng:</b> Full HD 1080p
      </div>
    </div>
  `;
  openModal("modal-preview");
};

window.downloadSampleFile = function(fileName) {
  const blob = new Blob([`TÀI LIỆU HỌC TẬP GIÁO DỤC CHÍNH TRỊ - BỘ TƯ LỆNH VÙNG 4 HẢI QUÂN\nTệp tin: ${fileName}\nNgày xuất: ${new Date().toLocaleDateString('vi-VN')}`], { type: "text/plain;charset=utf-8" });
  const url = URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = fileName;
  a.click();
  showToast(`Đang tải tệp: ${fileName}`);
};

window.editUser = function(id) {
  const user = store.users.find(u => u.id === id);
  if (!user) return;

  document.getElementById("modal-user-title").textContent = `Chỉnh sửa: ${user.fullName}`;
  document.getElementById("user-id").value = user.id;
  document.getElementById("user-order-number").value = user.orderNumber || 1;
  document.getElementById("user-code").value = user.militaryCode;
  document.getElementById("user-username").value = user.username || "";
  document.getElementById("user-password").value = user.password || "12345@abc";
  document.getElementById("user-fullname").value = user.fullName;
  document.getElementById("user-rank").value = user.rank;
  document.getElementById("user-position").value = user.position;
  document.getElementById("user-unit").value = user.unit;
  document.getElementById("user-phone").value = user.phone;
  document.getElementById("user-progress").value = user.progress;
  document.getElementById("user-internal-access").checked = user.isInternalAccess !== false;

  openModal("modal-user");
};

window.deleteUser = function(id) {
  if (confirm("Xác nhận xóa tài khoản quân nhân này khỏi hệ thống?")) {
    store.users = store.users.filter(u => u.id !== id);
    store.saveAll();
    renderUsersTable();
    showToast("Đã xóa tài khoản quân nhân.");
  }
};

window.openAddQuestionModalForLesson = function(lessonId) {
  document.getElementById("form-question").reset();
  document.getElementById("question-id").value = "";
  document.getElementById("question-lesson-id").value = lessonId;
  openModal("modal-question");
};

window.deleteQuestion = function(lessonId, qIdx) {
  const lesson = store.lessons.find(l => l.id === lessonId);
  if (!lesson || !lesson.questions) return;
  if (confirm("Xác nhận xóa câu hỏi trắc nghiệm này?")) {
    lesson.questions.splice(qIdx, 1);
    lesson.quizCount = lesson.questions.length;
    store.saveAll();
    renderQuizBank();
    renderLessonsTable();
    showToast("Đã xóa câu hỏi.");
  }
};

window.deleteLaw = function(lawId) {
  if (confirm("Xác nhận xóa văn bản pháp luật này?")) {
    store.laws = store.laws.filter(l => l.id !== lawId);
    store.saveAll();
    renderLaws();
    showToast("Đã xóa văn bản.");
  }
};

function exportToCSV() {
  let csv = "STT,Tên tài khoản (Username),Mật khẩu,Mã QN,Họ và Tên,Cấp bậc,Chức vụ,Đơn vị,Tiến độ (%),Điểm TB,Quyền nội bộ,Lần học cuối\n";
  const sorted = [...store.users].sort((a, b) => (a.orderNumber || 0) - (b.orderNumber || 0));
  sorted.forEach(u => {
    csv += `${u.orderNumber || 1},"${u.username || ''}","${u.password || '12345@abc'}","${u.militaryCode}","${u.fullName}","${u.rank}","${u.position}","${u.unit}",${u.progress},${u.avgScore},"${u.isInternalAccess !== false ? 'CÓ' : 'KHÔNG'}","${u.lastActive}"\n`;
  });

  const blob = new Blob(["\uFEFF" + csv], { type: "text/csv;charset=utf-8;" });
  const url = URL.createObjectURL(blob);
  const link = document.createElement("a");
  link.setAttribute("href", url);
  link.setAttribute("download", `danh_sach_tai_khoan_gdct_vung4_${new Date().toISOString().slice(0,10)}.csv`);
  link.click();
  showToast("Đã xuất danh sách tài khoản & tiến độ học tập ra file CSV!");
}
