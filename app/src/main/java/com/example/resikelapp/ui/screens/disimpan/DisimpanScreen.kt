package com.example.resikelapp.ui.screens.disimpan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextOverflow
import com.example.resikelapp.R
import com.example.resikelapp.ui.theme.ResikelAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisimpanScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 56.dp) // Adjust padding to account for the top bar height
                    .systemBarsPadding() // Optional: if you want to ensure safe area consideration
            ) {
                // Konten utama
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(sampleItems) { item ->
                        DisimpanCard(
                            imageRes = item.imageRes,
                            title = item.title,
                            description = item.description
                        )
                    }
                }
            }
        }
    )
}





@Composable
fun DisimpanCard(
    imageRes: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(150.dp)
            .wrapContentHeight(), // Card height adjusts to content
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(colorResource(R.color.third))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // Image Section
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Title Section
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Description Section
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )
        }
    }
}

// Sample Data Model
data class DisimpanItem(val imageRes: Int, val title: String, val description: String)

// Sample Items
val sampleItems = listOf(
    DisimpanItem(R.drawable.disimpan_dummy, "Title 1", "This is the description for item 1."),
    DisimpanItem(R.drawable.disimpan_dummy, "Title 2", "This is the description for item 2."),
    DisimpanItem(R.drawable.disimpan_dummy, "Title 3", "This is the description for item 3."),
    DisimpanItem(R.drawable.disimpan_dummy, "Title 4", "This is the description for item 4.")
)


@Preview(showBackground = true)
@Composable
fun DisimpanPreview() {
    ResikelAppTheme {
        DisimpanScreen()
    }
}
