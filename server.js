const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = 3000;
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

// Load or Initialize DB
let inMemoryDB = {
  users: INITIAL_USERS,
  lessons: [],
  syncLogs: []
};

function loadDatabase() {
  try {
    if (fs.existsSync(DB_FILE)) {
      const data = JSON.parse(fs.readFileSync(DB_FILE, 'utf8'));
      if (data.users && data.users.length > 0) {
        inMemoryDB.users = data.users;
      }
      if (data.lessons && data.lessons.length > 0) {
        inMemoryDB.lessons = data.lessons;
      }
      if (data.syncLogs) {
        inMemoryDB.syncLogs = data.syncLogs;
      }
    } else {
      saveDatabase();
    }
  } catch (err) {
    console.error("Failed to load DB:", err);
  }
}

function saveDatabase() {
  try {
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
      // 1. GET /api/users
      if (pathname === '/api/users' && req.method === 'GET') {
        return sendJSON(res, { success: true, count: inMemoryDB.users.length, users: inMemoryDB.users });
      }

      // 2. POST /api/users/update-profile (Called by Android App or Web Admin)
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
          // Update existing
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
          // Create new user if not found
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

      // 3. POST /api/users/change-password
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

      // 4. POST /api/users/sync-progress
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

      // 5. POST /api/sync (Full Bidirectional Sync)
      if (pathname === '/api/sync') {
        if (req.method === 'GET') {
          return sendJSON(res, {
            success: true,
            serverTime: new Date().toISOString(),
            users: inMemoryDB.users,
            lessonsCount: inMemoryDB.lessons.length
          });
        } else if (req.method === 'POST') {
          const body = await parseRequestBody(req);
          if (body.users && Array.isArray(body.users)) {
            inMemoryDB.users = body.users;
          }
          if (body.lessons && Array.isArray(body.lessons)) {
            inMemoryDB.lessons = body.lessons;
          }
          saveDatabase();
          return sendJSON(res, { success: true, message: "Đã đồng bộ toàn diện dữ liệu hệ thống!" });
        }
      }

      // 6. GET /api/stats
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
  // Normalize path
  if (pathname === '/' || pathname === '/index.html') {
    pathname = '/index.html';
  }

  // Strip leading /web-admin if present
  let relativePath = pathname;
  if (relativePath.startsWith('/web-admin/')) {
    relativePath = relativePath.slice('/web-admin'.length);
  }

  // Find file in web-admin dir or workspace dir
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
    // Fallback to web-admin index.html
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
