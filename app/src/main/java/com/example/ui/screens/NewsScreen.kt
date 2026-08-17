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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.FiberNew
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.NewsArticle
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldYellow
import com.example.ui.theme.NavyContainer
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.NavyPrimary

@Composable
fun NewsScreen(
  newsList: List<NewsArticle>,
  selectedCategory: String,
  searchQuery: String,
  bookmarkedIds: Set<String>,
  onCategorySelected: (String) -> Unit,
  onSearchChange: (String) -> Unit,
  onOpenArticle: (NewsArticle) -> Unit,
  onToggleBookmark: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val categories = listOf(
    "Tất cả",
    "Hoạt động Vùng 4",
    "Biển đảo quê hương",
    "Văn bản - Chỉ thị",
    "Gương sáng Chiến sĩ"
  )

  val filteredNews = newsList.filter { article ->
    val matchesCategory = selectedCategory == "Tất cả" || article.category == selectedCategory
    val matchesSearch = searchQuery.isBlank() ||
      article.title.contains(searchQuery, ignoreCase = true) ||
      article.summary.contains(searchQuery, ignoreCase = true)
    matchesCategory && matchesSearch
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF4F7FA))
  ) {
    // Header
    Surface(
      color = Color.White,
      shadowElevation = 2.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Text(
          text = "TIN TỨC - SỰ KIỆN CHÍNH TRỊ VÙNG 4",
          color = NavyDeep,
          fontSize = 15.sp,
          fontWeight = FontWeight.Black,
          letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Search Bar
        OutlinedTextField(
          value = searchQuery,
          onValueChange = onSearchChange,
          placeholder = { Text("Tìm kiếm tin tức, chỉ thị, thông tin thời sự...", fontSize = 12.5.sp) },
          leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(20.dp))
          },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = NavyPrimary,
            unfocusedBorderColor = Color(0xFFE2E8F0),
            focusedContainerColor = Color(0xFFF8FAFC),
            unfocusedContainerColor = Color(0xFFF8FAFC)
          ),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("input_search_news")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Categories filter row
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(categories) { cat ->
            val isSelected = cat == selectedCategory
            FilterChip(
              selected = isSelected,
              onClick = { onCategorySelected(cat) },
              label = {
                Text(
                  text = cat,
                  fontSize = 11.5.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
              },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = NavyPrimary,
                selectedLabelColor = Color.White,
                containerColor = Color(0xFFF1F5F9),
                labelColor = Color(0xFF475569)
              ),
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier.testTag("filter_news_$cat")
            )
          }
        }
      }
    }

    // News Items List
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(14.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      if (filteredNews.isEmpty()) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 40.dp),
            contentAlignment = Alignment.Center
          ) {
            Text("Không tìm thấy tin bài phù hợp.", color = Color(0xFF64748B))
          }
        }
      }

      items(filteredNews) { article ->
        val isBookmarked = bookmarkedIds.contains(article.id)

        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenArticle(article) }
            .testTag("card_news_item_${article.id}")
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            // Badges Row
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = if (article.isHot) CrimsonRed else NavyContainer
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    if (article.isHot) {
                      Icon(Icons.Default.Whatshot, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(12.dp))
                      Spacer(modifier = Modifier.width(3.dp))
                    }
                    Text(
                      text = article.category,
                      color = if (article.isHot) Color.White else NavyPrimary,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold
                    )
                  }
                }

                if (article.isPinned) {
                  Spacer(modifier = Modifier.width(6.dp))
                  Surface(shape = RoundedCornerShape(6.dp), color = GoldYellow) {
                    Row(
                      modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      Icon(Icons.Default.PushPin, contentDescription = null, tint = NavyDeep, modifier = Modifier.size(11.dp))
                      Spacer(modifier = Modifier.width(2.dp))
                      Text("Ghim", color = NavyDeep, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                  }
                }
              }

              IconButton(
                onClick = { onToggleBookmark(article.id) },
                modifier = Modifier.size(28.dp).testTag("btn_bookmark_${article.id}")
              ) {
                Icon(
                  imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                  contentDescription = "Lưu tin",
                  tint = if (isBookmarked) GoldYellow else Color(0xFF94A3B8),
                  modifier = Modifier.size(20.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
              text = article.title,
              color = NavyDeep,
              fontSize = 14.5.sp,
              fontWeight = FontWeight.Bold,
              lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Summary
            Text(
              text = article.summary,
              color = Color(0xFF475569),
              fontSize = 12.5.sp,
              maxLines = 3,
              overflow = TextOverflow.Ellipsis,
              lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Đăng ngày: ${article.publishedDate} • Đọc trong ${article.readTimeMinutes} phút",
                color = Color(0xFF94A3B8),
                fontSize = 10.5.sp
              )

              Text(
                text = "Chi tiết →",
                color = NavyPrimary,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }
  }
}
