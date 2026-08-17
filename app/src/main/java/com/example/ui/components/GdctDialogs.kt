package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.PersonalNoteEntity
import com.example.data.local.QuizSubmissionEntity
import com.example.data.model.LawDoc
import com.example.data.model.NewsArticle
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun DailyQuoteDialog(onDismiss: () -> Unit) {
  var isAudioPlaying by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(6.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column {
        Surface(color = CrimsonRed, modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.FormatQuote, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(24.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text("LỜI BÁC DẠY HẢI QUÂN", color = Color.White, fontWeight = FontWeight.Black, fontSize = 15.sp)
            }
            IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
              Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White)
            }
          }
        }

        Column(modifier = Modifier.padding(18.dp)) {
          Text(
            text = "\"Ngày trước ta chỉ có đêm và rừng. Ngày nay ta có ngày, có trời, có biển. Bờ biển ta dài, tươi đẹp, ta phải biết giữ gìn lấy nó.\"",
            color = NavyDeep,
            fontSize = 15.5.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            lineHeight = 24.sp
          )

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "— Lời Chủ tịch Hồ Chí Minh căn dặn khi về thăm Bộ đội Hải quân (15/3/1961)",
            color = CrimsonRed,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
          )

          HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

          Text(
            text = "💡 Ý nghĩa & Liên hệ thực tiễn:",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = NavyPrimary
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Lời dạy của Bác là kim chỉ nam cho các thế hệ chiến sĩ Vùng 4 Hải quân. Mỗi cán bộ, chiến sĩ trên các đảo Trường Sa và trên các biên đội tàu chiến luôn nêu cao tinh thần cảnh giác, quyết tâm bảo vệ vững chắc từng sải biển, tấc đảo thiêng liêng của Tổ quốc.",
            fontSize = 13.sp,
            color = Color(0xFF334155),
            lineHeight = 19.sp
          )

          Spacer(modifier = Modifier.height(16.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = if (isAudioPlaying) GoldYellow else Color(0xFFEFF6FF),
              modifier = Modifier.clickable { isAudioPlaying = !isAudioPlaying }
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.VolumeUp,
                  contentDescription = "Nghe đọc",
                  tint = if (isAudioPlaying) NavyDeep else NavyPrimary,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (isAudioPlaying) "Đang phát giọng đọc..." else "Nghe phát thanh",
                  color = if (isAudioPlaying) NavyDeep else NavyPrimary,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            Button(
              onClick = onDismiss,
              colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
            ) {
              Text("Đã tiếp thu", fontSize = 12.sp)
            }
          }
        }
      }
    }
  }
}

@Composable
fun NewsDetailDialog(
  article: NewsArticle,
  isBookmarked: Boolean,
  onToggleBookmark: () -> Unit,
  onDismiss: () -> Unit
) {
  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.95f)
        .fillMaxHeight(0.9f)
        .clip(RoundedCornerShape(20.dp)),
      color = Color.White,
      tonalElevation = 6.dp
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        Surface(color = NavyPrimary, modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(shape = RoundedCornerShape(6.dp), color = CrimsonRed) {
              Text(
                text = article.category,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }

            Row {
              IconButton(onClick = onToggleBookmark) {
                Icon(
                  imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                  contentDescription = "Lưu tin",
                  tint = GoldYellow
                )
              }
              IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White)
              }
            }
          }
        }

        LazyColumn(
          modifier = Modifier
            .weight(1f)
            .padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          item {
            Text(
              text = article.title,
              color = NavyDeep,
              fontSize = 17.sp,
              fontWeight = FontWeight.Black,
              lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Ngày đăng: ${article.publishedDate} • Thời gian đọc: ${article.readTimeMinutes} phút",
              color = Color(0xFF64748B),
              fontSize = 11.5.sp
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
          }

          item {
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = Color(0xFFF1F5F9),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = article.summary,
                color = Color(0xFF334155),
                fontSize = 13.5.sp,
                fontStyle = FontStyle.Italic,
                lineHeight = 20.sp,
                modifier = Modifier.padding(12.dp)
              )
            }
          }

          item {
            Text(
              text = article.content,
              color = Color(0xFF1E293B),
              fontSize = 14.sp,
              lineHeight = 23.sp
            )
          }

          if (article.keyPoints.isNotEmpty()) {
            item {
              Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE))
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Text(
                    text = "📌 NỘI DUNG CỐT LÕI CẦN NẮM:",
                    color = NavyPrimary,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Spacer(modifier = Modifier.height(6.dp))
                  article.keyPoints.forEach { point ->
                    Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
                      Text("• ", color = CrimsonRed, fontWeight = FontWeight.Bold)
                      Text(point, color = Color(0xFF1E3A8A), fontSize = 12.5.sp, lineHeight = 18.sp)
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun LawDetailDialog(law: LawDoc, onDismiss: () -> Unit) {
  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.95f)
        .fillMaxHeight(0.9f)
        .clip(RoundedCornerShape(20.dp)),
      color = Color.White,
      tonalElevation = 6.dp
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        Surface(color = NavyDeep, modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Gavel, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text("TỦ SÁCH PHÁP LUẬT VÙNG 4", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            IconButton(onClick = onDismiss) {
              Icon(Icons.Default.Close, contentDescription = "Đóng", tint = Color.White)
            }
          }
        }

        LazyColumn(
          modifier = Modifier
            .weight(1f)
            .padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          item {
            Text(text = law.title, color = NavyPrimary, fontSize = 16.sp, fontWeight = FontWeight.Black)
            Text(text = "Cơ quan ban hành: ${law.issuedBy} • Phân loại: ${law.category}", color = Color(0xFF64748B), fontSize = 12.sp)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text(text = law.summary, color = Color(0xFF334155), fontSize = 13.5.sp, lineHeight = 20.sp)
          }

          items(law.keyArticles) { (articleNum, content) ->
            Card(
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
              border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Text(text = articleNum, color = CrimsonRed, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = content, color = Color(0xFF1E293B), fontSize = 13.sp, lineHeight = 20.sp)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun AddNoteDialog(
  lessonTitle: String,
  onSave: (String, String, String) -> Unit,
  onDismiss: () -> Unit
) {
  var noteTitle by remember { mutableStateOf("Thu hoạch: $lessonTitle") }
  var noteContent by remember { mutableStateOf("") }
  var category by remember { mutableStateOf("Liên hệ thực tiễn bản thân") }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(6.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.EditNote, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("SỔ TAY GHI CHÉP THU HOẠCH", color = NavyDeep, fontSize = 14.sp, fontWeight = FontWeight.Black)
          }
          IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Đóng")
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = noteTitle,
          onValueChange = { noteTitle = it },
          label = { Text("Tiêu đề ghi chép") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = noteContent,
          onValueChange = { noteContent = it },
          label = { Text("Nội dung thu hoạch / Liên hệ công tác tại tàu, đơn vị") },
          placeholder = { Text("Ví dụ: Nhận thức về nhiệm vụ canh trực, giữ nghiêm kỷ luật, giúp đỡ đồng đội...") },
          modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
          shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          Button(
            onClick = onDismiss,
            colors = ButtonDefaults.outlinedButtonColors()
          ) {
            Text("Hủy", color = Color(0xFF64748B))
          }
          Spacer(modifier = Modifier.width(8.dp))
          Button(
            onClick = {
              if (noteContent.isNotBlank()) {
                onSave(noteTitle, noteContent, category)
              }
            },
            enabled = noteContent.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
            modifier = Modifier.testTag("btn_save_note")
          ) {
            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Lưu vào sổ tay")
          }
        }
      }
    }
  }
}
