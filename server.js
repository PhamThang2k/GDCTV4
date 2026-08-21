const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = 3000;
const WEB_ADMIN_DIR = path.join(__dirname, 'web-admin');

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

const server = http.createServer((req, res) => {
  const parsedUrl = new URL(req.url, `http://${req.headers.host}`);
  let pathname = parsedUrl.pathname;

  // Set CORS headers
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type');

  if (req.method === 'OPTIONS') {
    res.writeHead(204);
    res.end();
    return;
  }

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
  console.log(`[GDCT Web Admin Server] Running at http://0.0.0.0:${PORT}`);
});
