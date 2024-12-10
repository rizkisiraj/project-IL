package com.example.resikelapp.ui.screens.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.compose.AsyncImage
import com.example.resikelapp.R
import com.example.resikelapp.data.repository.ResikelRepository
import com.example.resikelapp.utils.ViewModelFactory
import com.example.resikelapp.utils.formatTimestampToDate

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailNewsScreen(
    newsId: String,
    viewModel: NewsViewModel = viewModel(factory = ViewModelFactory(ResikelRepository()))
) {
    val newsDetail by viewModel.newsDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.isError.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getNewsById(newsId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        if(isLoading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                CircularProgressIndicator()
                Spacer(Modifier.height(8.dp))
                Text("Sedang memuat artikel")
            }
        }

        if(isError != null) {
            Text(viewModel.isError.value!!)
        }

        if(!isLoading && isError == null) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = newsDetail?.imageUrl,
                    contentDescription = "Sampah",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(136.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        newsDetail!!.judul,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        "Updated ${formatTimestampToDate(newsDetail!!.date)}",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                newsDetail!!.content,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}