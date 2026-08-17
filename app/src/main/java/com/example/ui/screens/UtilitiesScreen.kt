package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PersonalNoteEntity
import com.example.data.model.LawDoc
import com.example.data.model.Lesson
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.NavyContainer
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun UtilitiesScreen(
  lessons: List<Lesson>,
  lawDocs: List<LawDoc>,
  notes: List<PersonalNoteEntity>,
  onOpenLaw: (LawDoc) -> Unit,
  onStartQuiz: (Lesson) -> Unit,
  onAddNewNote: () -> Unit,
  onDeleteNote: (Long) -> Unit,
  onOpenCommanderReport: () -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF4F7FA)),
    contentPadding = PaddingValues(14.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header
    item {
      Surface(
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "TIỆN ÍCH GIÁO DỤC CHÍNH TRỊ VÙNG 4",
            color = NavyDeep,
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 0.5.sp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Tra cứu pháp luật, sổ tay thu hoạch, lịch học tập và kiểm tra đánh giá",
            color = Color(0xFF64748B),
            fontSize = 12.sp
          )
        }
      }
    }

    // 1. Sổ tay Đảng viên / Thu hoạch cá nhân
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(CrimsonRed.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(Icons.Default.EditNote, contentDescription = null, tint = CrimsonRed, modifier = Modifier.size(20.dp))
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text("SỔ TAY GHI CHÉP THU HOẠCH", color = NavyDeep, fontSize = 13.5.sp, fontWeight = FontWeight.Bold)
                Text("${notes.size} bản ghi chép cá nhân", color = Color(0xFF64748B), fontSize = 11.sp)
              }
            }

            Button(
              onClick = onAddNewNote,
              colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
              shape = RoundedCornerShape(10.dp),
              contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
              modifier = Modifier.testTag("btn_add_note_util")
            ) {
              Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Ghi chép", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          if (notes.isEmpty()) {
            Text(
              text = "Chưa có ghi chép nào. Đồng chí hãy ghi lại những thu hoạch trọng tâm sau mỗi bài học chính trị để rèn luyện bản thân.",
              color = Color(0xFF94A3B8),
              fontSize = 12.sp,
              modifier = Modifier.padding(vertical = 8.dp)
            )
          } else {
            notes.forEach { note ->
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFF8FAFC),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp)
              ) {
                Row(
                  modifier = Modifier.padding(10.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column(modifier = Modifier.weight(1f)) {
                    Text(text = note.title, color = NavyDeep, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = note.content, color = Color(0xFF475569), fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                  }
                  IconButton(onClick = { onDeleteNote(note.id) }, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Default.Delete, contentDescription = "Xóa", tint = Color(0xFF94A3B8), modifier = Modifier.size(16.dp))
                  }
                }
              }
            }
          }
        }
      }
    }

    // 2. Tủ sách Pháp luật & Điều lệnh Quân đội
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(NavyPrimary.copy(alpha = 0.12f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.Gavel, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text("TỦ SÁCH PHÁP LUẬT & ĐIỀU LỆNH", color = NavyDeep, fontSize = 13.5.sp, fontWeight = FontWeight.Bold)
              Text("Văn bản quy phạm, 10 Lời thề, 12 Điều kỷ luật", color = Color(0xFF64748B), fontSize = 11.sp)
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          lawDocs.forEach { law ->
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = Color(0xFFF8FAFC),
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable { onOpenLaw(law) }
                .testTag("item_law_${law.id}")
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Surface(shape = RoundedCornerShape(4.dp), color = NavyContainer) {
                    Text(
                      text = law.category,
                      color = NavyPrimary,
                      fontSize = 9.5.sp,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                  }
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(text = law.title, color = NavyDeep, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }

                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(16.dp))
              }
            }
          }
        }
      }
    }

    // 3. Lịch học tập & Sinh hoạt GDCT Vùng 4
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFFD97706).copy(alpha = 0.12f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text("LỊCH HỌC TẬP & SINH HOẠT VÙNG 4", color = NavyDeep, fontSize = 13.5.sp, fontWeight = FontWeight.Bold)
              Text("Thời gian biểu học tập GDCT tháng 8/2026", color = Color(0xFF64748B), fontSize = 11.sp)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          listOf(
            "Thứ 2 (08h00)" to "Chào cờ toàn Vùng & Thông báo thời sự tuần",
            "Thứ 3 (14h00)" to "Học tập Chuyên đề: Bản lĩnh chính trị người chiến sĩ Hải quân",
            "Thứ 5 (19h30)" to "Diễn đàn thanh niên / Tọa đàm học tập và làm theo Bác",
            "Thứ 6 (15h30)" to "Ôn tập & Kiểm tra trắc nghiệm đánh giá chất lượng"
          ).forEach { (time, content) ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFFFEF3C7)) {
                Text(
                  text = time,
                  color = Color(0xFF92400E),
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = content,
                color = Color(0xFF334155),
                fontSize = 12.5.sp,
                modifier = Modifier.weight(1f)
              )
            }
          }
        }
      }
    }
  }
}
