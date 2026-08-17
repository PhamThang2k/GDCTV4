package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.QuizSubmissionEntity
import com.example.data.model.Lesson
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun QuizEngineDialog(
  lesson: Lesson,
  answers: Map<Int, Int>,
  submittedResult: QuizSubmissionEntity?,
  onAnswerSelected: (Int, Int) -> Unit,
  onSubmitQuiz: () -> Unit,
  onDismiss: () -> Unit
) {
  var currentQuestionIndex by remember { mutableIntStateOf(0) }
  var showConfirmSubmit by remember { mutableStateOf(false) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.95f)
        .fillMaxHeight(0.92f)
        .clip(RoundedCornerShape(20.dp)),
      color = MaterialTheme.colorScheme.background,
      tonalElevation = 6.dp
    ) {
      if (submittedResult != null) {
        // Result Screen
        QuizResultView(
          lesson = lesson,
          result = submittedResult,
          answers = answers,
          onClose = onDismiss
        )
      } else {
        // Active Quiz Questions
        val questions = lesson.quizQuestions
        if (questions.isEmpty()) {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Không có câu hỏi kiểm tra cho bài học này.")
          }
          return@Surface
        }

        val safeIndex = currentQuestionIndex.coerceIn(0, questions.size - 1)
        val currentQuestion = questions[safeIndex]
        val answeredCount = answers.size

        Column(modifier = Modifier.fillMaxSize()) {
          // Quiz Header
          Surface(
            color = NavyPrimary,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.MilitaryTech,
                    contentDescription = null,
                    tint = GoldYellow,
                    modifier = Modifier.size(24.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "KIỂM TRA ĐÁNH GIÁ GDCT",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                  )
                }

                IconButton(
                  onClick = onDismiss,
                  modifier = Modifier.testTag("btn_close_quiz")
                ) {
                  Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Đóng",
                    tint = Color.White
                  )
                }
              }

              Text(
                text = lesson.title,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 12.sp,
                maxLines = 1
              )

              Spacer(modifier = Modifier.height(10.dp))

              // Timer & Progress stats
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = null,
                    tint = GoldYellow,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "Thời gian: 15:00",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                  )
                }

                Surface(
                  shape = RoundedCornerShape(12.dp),
                  color = Color.White.copy(alpha = 0.2f)
                ) {
                  Text(
                    text = "Đã làm: $answeredCount/${questions.size} câu",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(8.dp))

              LinearProgressIndicator(
                progress = { (safeIndex + 1).toFloat() / questions.size },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(6.dp)
                  .clip(RoundedCornerShape(3.dp)),
                color = GoldYellow,
                trackColor = Color.White.copy(alpha = 0.3f),
              )
            }
          }

          // Question Body
          Column(
            modifier = Modifier
              .weight(1f)
              .padding(16.dp)
          ) {
            // Question index indicator pills
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              questions.forEachIndexed { idx, q ->
                val isAnswered = answers.containsKey(q.id)
                val isCurrent = idx == safeIndex
                Surface(
                  shape = CircleShape,
                  color = when {
                    isCurrent -> CrimsonRed
                    isAnswered -> SuccessGreen
                    else -> Color(0xFFE2E8F0)
                  },
                  modifier = Modifier
                    .size(32.dp)
                    .clickable { currentQuestionIndex = idx }
                    .testTag("quiz_step_$idx"),
                ) {
                  Box(contentAlignment = Alignment.Center) {
                    Text(
                      text = "${idx + 1}",
                      color = if (isCurrent || isAnswered) Color.White else Color(0xFF475569),
                      fontSize = 12.sp,
                      fontWeight = FontWeight.Bold
                    )
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Question Card
            Card(
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = Color.White),
              elevation = CardDefaults.cardElevation(2.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Text(
                  text = "Câu hỏi ${safeIndex + 1}:",
                  color = NavyPrimary,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = currentQuestion.question,
                  color = Color(0xFF1E293B),
                  fontSize = 15.sp,
                  fontWeight = FontWeight.SemiBold,
                  lineHeight = 22.sp
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Options List
            LazyColumn(
              verticalArrangement = Arrangement.spacedBy(10.dp),
              modifier = Modifier.weight(1f)
            ) {
              itemsIndexed(currentQuestion.options) { optionIdx, optionText ->
                val isSelected = answers[currentQuestion.id] == optionIdx
                val optionLetter = ('A' + optionIdx).toString()

                Surface(
                  shape = RoundedCornerShape(12.dp),
                  color = if (isSelected) Color(0xFFE3F2FD) else Color.White,
                  border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, NavyPrimary)
                           else androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                  modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAnswerSelected(currentQuestion.id, optionIdx) }
                    .testTag("quiz_option_${currentQuestion.id}_$optionIdx")
                ) {
                  Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Box(
                      modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) NavyPrimary else Color(0xFFF1F5F9)),
                      contentAlignment = Alignment.Center
                    ) {
                      Text(
                        text = optionLetter,
                        color = if (isSelected) Color.White else Color(0xFF475569),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                      )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                      text = optionText,
                      color = if (isSelected) NavyPrimary else Color(0xFF334155),
                      fontSize = 13.5.sp,
                      fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                      modifier = Modifier.weight(1f)
                    )

                    RadioButton(
                      selected = isSelected,
                      onClick = { onAnswerSelected(currentQuestion.id, optionIdx) },
                      colors = RadioButtonDefaults.colors(
                        selectedColor = NavyPrimary,
                        unselectedColor = Color(0xFF94A3B8)
                      )
                    )
                  }
                }
              }
            }
          }

          // Bottom Action Bar
          Surface(
            color = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              OutlinedButton(
                onClick = {
                  if (safeIndex > 0) currentQuestionIndex--
                },
                enabled = safeIndex > 0,
                modifier = Modifier.testTag("btn_quiz_prev")
              ) {
                Text("Câu trước")
              }

              if (safeIndex < questions.size - 1) {
                Button(
                  onClick = { currentQuestionIndex++ },
                  colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                  modifier = Modifier.testTag("btn_quiz_next")
                ) {
                  Text("Câu tiếp theo")
                }
              } else {
                Button(
                  onClick = { showConfirmSubmit = true },
                  colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                  modifier = Modifier.testTag("btn_submit_quiz")
                ) {
                  Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text("Nộp bài thi", fontWeight = FontWeight.Bold)
                }
              }
            }
          }
        }
      }
    }
  }

  // Confirmation Alert Dialog
  if (showConfirmSubmit) {
    Dialog(onDismissRequest = { showConfirmSubmit = false }) {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.HelpOutline,
              contentDescription = null,
              tint = CrimsonRed,
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Xác nhận nộp bài",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = NavyPrimary
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Đồng chí đã trả lời ${answers.size}/${lesson.quizQuestions.size} câu hỏi. Kết quả sẽ được ghi vào Sổ theo dõi GDCT và gửi lên Chỉ huy đơn vị.",
            fontSize = 13.sp,
            color = Color(0xFF475569),
            lineHeight = 19.sp
          )

          Spacer(modifier = Modifier.height(16.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            OutlinedButton(onClick = { showConfirmSubmit = false }) {
              Text("Kiểm tra lại")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
              onClick = {
                showConfirmSubmit = false
                onSubmitQuiz()
              },
              colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
              modifier = Modifier.testTag("btn_confirm_submit_quiz")
            ) {
              Text("Nộp ngay")
            }
          }
        }
      }
    }
  }
}

@Composable
fun QuizResultView(
  lesson: Lesson,
  result: QuizSubmissionEntity,
  answers: Map<Int, Int>,
  onClose: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
  ) {
    // Header Banner
    Surface(
      color = if (result.passed) NavyPrimary else CrimsonRed,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Icon(
          imageVector = if (result.passed) Icons.Default.CheckCircle else Icons.Default.HelpOutline,
          contentDescription = null,
          tint = GoldYellow,
          modifier = Modifier.size(54.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = if (result.passed) "HOÀN THÀNH BÀI KIỂM TRA" else "CHƯA ĐẠT YÊU CẦU",
          color = Color.White,
          fontSize = 18.sp,
          fontWeight = FontWeight.Black,
          letterSpacing = 0.5.sp
        )

        Text(
          text = if (result.percentage >= 80) "Xếp loại: GIỎI"
                 else if (result.percentage >= 65) "Xếp loại: KHÁ"
                 else if (result.percentage >= 50) "Xếp loại: ĐẠT"
                 else "Yêu cầu tự học & kiểm tra lại",
          color = GoldYellow,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Score Card
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color.White.copy(alpha = 0.15f),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text("Điểm số", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
              Text("${result.score}/${result.totalQuestions}", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Black)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text("Tỉ lệ đúng", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
              Text("${result.percentage}%", color = GoldYellow, fontSize = 18.sp, fontWeight = FontWeight.Black)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text("Trạng thái nộp", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
              Text("Đã đồng bộ", color = Color(0xFF81C784), fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // Commander Note Card
    Card(
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.MilitaryTech,
            contentDescription = null,
            tint = CrimsonRed,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Nhận xét của Chỉ huy / Chính trị viên:",
            fontSize = 12.5.sp,
            fontWeight = FontWeight.Bold,
            color = NavyPrimary
          )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = result.commanderComment,
          fontSize = 12.sp,
          color = Color(0xFF334155),
          lineHeight = 18.sp
        )
      }
    }

    // Questions Review List
    Text(
      text = "Chi tiết các câu hỏi & Giải thích:",
      fontSize = 13.sp,
      fontWeight = FontWeight.Bold,
      color = NavyDeep,
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
    )

    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      itemsIndexed(lesson.quizQuestions) { idx, q ->
        val userChoice = answers[q.id]
        val isCorrect = userChoice == q.correctOptionIndex

        Card(
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (isCorrect) Color(0xFFF0FDF4) else Color(0xFFFEF2F2)
          ),
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isCorrect) Color(0xFF86EFAC) else Color(0xFFFECACA)
          )
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "Câu ${idx + 1}: ${if (isCorrect) "✓ Đúng" else "✗ Sai"}",
                color = if (isCorrect) SuccessGreen else CrimsonRed,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
              )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = q.question,
              color = Color(0xFF1E293B),
              fontSize = 13.sp,
              fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
              text = "Đáp án đúng: ${q.options[q.correctOptionIndex]}",
              color = SuccessGreen,
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = "💡 Giải thích: ${q.explanation}",
              color = Color(0xFF64748B),
              fontSize = 11.5.sp,
              lineHeight = 16.sp
            )
          }
        }
      }
    }

    // Close button
    Surface(
      color = Color.White,
      tonalElevation = 6.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Button(
        onClick = onClose,
        colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
          .testTag("btn_close_quiz_result")
      ) {
        Text("Hoàn tất & Lưu tiến độ", fontWeight = FontWeight.Bold)
      }
    }
  }
}
