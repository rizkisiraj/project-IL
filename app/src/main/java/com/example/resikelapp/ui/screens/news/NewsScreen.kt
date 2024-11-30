package com.example.resikelapp.ui.screens.news

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.resikelapp.R
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.data.repository.ResikelRepository
import com.example.resikelapp.ui.components.shimmerEffect
import com.example.resikelapp.ui.screens.community.CommunityViewModel
import com.example.resikelapp.ui.theme.GreenBase
import com.example.resikelapp.ui.theme.GreenSecondary
import com.example.resikelapp.utils.ViewModelFactory
import com.example.resikelapp.utils.formatTimestampToDate


@Composable
fun NewsScreen(
    navController: NavController,
    viewModel: NewsViewModel = viewModel(factory = ViewModelFactory(ResikelRepository()))
    ) {
    val newsList by viewModel.newsList.collectAsState()
    val loading by viewModel.isLoading.collectAsState()
    val pagerState = rememberPagerState(pageCount = {
        newsList.size
    })
    val beritaScrollState = rememberScrollState()
    val screenScrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.getNewsFirebase()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(screenScrollState)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(206.dp),
            contentPadding = PaddingValues(16.dp)
        ) { index ->
            Box(
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = if (index > 0 && index < newsList.size - 1)8.dp else 0.dp)
                  .clip(RoundedCornerShape(28.dp)),
                contentAlignment = Alignment.BottomCenter
            ) {
                AsyncImage(
                    model = newsList[index].imageUrl,
                    contentDescription = "Sampah",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    newsList[index].judul,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 16.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Berita",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = GreenBase,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (loading) Arrangement.Center else Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().scrollable(beritaScrollState, orientation = Orientation.Horizontal)
        ) {
            if(loading) {
                items(count = 3) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .height(150.dp)
                                .width(132.dp)
                                .shimmerEffect()
                                .padding(end = 4.dp)
                        )
                    }
                }
            }
            items(newsList) { item ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = GreenSecondary,
                    ),
                    onClick = {
                        navController.navigate(Screen.DetailNews.createRoute(item.id))
                    },
                    modifier =
                    Modifier
                        .width(132.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        SubcomposeAsyncImage(
                            model = item.imageUrl,
                            loading = {
                                CircularProgressIndicator()
                            },
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(119.dp)
                        )
                        Text(
                            item.judul,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            formatTimestampToDate(item.date),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Edukasi",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = GreenBase,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().scrollable(beritaScrollState, orientation = Orientation.Horizontal)
        ) {
            if(loading) {
                item {
                    Box(
                        modifier = Modifier.height(150.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(64.dp),
                            color = GreenBase,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                }
            }
            items(newsList) { item ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = GreenSecondary,
                    ),
                    modifier =
                    Modifier
                        .width(132.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        SubcomposeAsyncImage(
                            model = item.imageUrl,
                            loading = {
                                CircularProgressIndicator()
                            },
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(119.dp)
                        )
                        Text(
                            item.judul,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            "24 Mei 2024",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}