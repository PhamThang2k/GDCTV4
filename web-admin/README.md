# CỔNG QUẢN TRỊ NỘI DUNG VÀ TÀI KHOẢN GDCT - BỘ TƯ LỆNH VÙNG 4 HẢI QUÂN
*Hệ thống Web CMS chuyên dụng dành cho Cán bộ Tuyên huấn, Trợ lý Chính trị và Chỉ huy đơn vị*

---

## 🌟 Chức năng Chính của Website Quản trị:

1. **Tổng quan & Báo cáo Tiến độ Toàn Vùng (Dashboard Overview)**:
   - Thống kê tổng số chuyên đề bài giảng GDCT, tổng quân số theo dõi (1,280 quân nhân).
   - Tỷ lệ hoàn thành toàn Vùng và phân loại điểm thi trắc nghiệm (*Giỏi, Khá, Đạt*).
   - Theo dõi tiến độ chi tiết theo từng đơn vị: Lữ đoàn 162, Lữ đoàn 146 Trường Sa, Lữ đoàn 955, Lữ đoàn 101, Lữ đoàn 685, Trung tâm BĐKT...
   - Phân bổ hình thức học tập: Slide, Đọc tài liệu DOCX/PDF, Video bài giảng, Audio phát thanh.

2. **Quản lý Bài giảng GDCT Đa phương tiện (Lesson CMS)**:
   - Soạn thảo, cập nhật bài giảng theo chuyên đề và đối tượng (Sĩ quan, QNCN, Hạ sĩ quan - Binh sĩ).
   - Quản lý slide trình chiếu, tệp đính kèm DOCX/PDF, link video HD, link audio phát thanh.
   - Chức năng xem trước (Live Preview) mô phỏng bài giảng.

3. **Quản lý Tài khoản & Hồ sơ Quân nhân (User Account Management)**:
   - Danh sách quân nhân kèm mã số, cấp bậc, chức vụ, đơn vị, tiến độ và điểm thi.
   - Thêm, sửa, xóa, tìm kiếm và lọc quân nhân theo đơn vị và trạng thái.
   - **Tính năng Đôn đốc học tập**: Gửi cảnh báo/nhắc nhở tự động qua SMS và thông báo App đến các quân nhân hoặc toàn đơn vị.
   - Xuất dữ liệu ra file Excel/CSV (`Export CSV`) để nộp báo cáo Phòng Chính trị.

4. **Ngân hàng Đề thi Trắc nghiệm (Quiz Bank)**:
   - Quản lý bộ câu hỏi trắc nghiệm kiểm tra nhận thức chính trị theo từng bài.

5. **Tủ sách Pháp luật & Kỷ luật (Law Library CMS)**:
   - Quản lý các văn bản luật, thông tư kỷ luật (Luật Biển Việt Nam, Thông tư 143/2023/TT-BQP...).

6. **Sao lưu & Phục hồi Cơ sở Dữ liệu (Backup / Restore)**:
   - Tự động lưu trên LocalStorage trình duyệt.
   - Hỗ trợ xuất file JSON sao lưu hệ thống và nạp dữ liệu khôi phục.

---

## 🚀 Cách Mở & Triển khai Website:

- **Mở trực tiếp trên trình duyệt**: Nhấp đúp vào tệp `index.html` trong thư mục `/web-admin/` trên bất kỳ trình duyệt nào (Chrome, Edge, Firefox, Safari).
- **Triển khai máy chủ nội bộ hoặc Cloud**: Có thể đưa toàn bộ thư mục `web-admin` lên máy chủ web Apache/Nginx nội bộ của Vùng 4, hoặc GitHub Pages, Vercel, Netlify.
