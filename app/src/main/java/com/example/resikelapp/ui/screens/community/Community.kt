package com.example.resikelapp.ui.screens.community

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resikelapp.R
import com.example.resikelapp.data.repository.ResikelRepository
import com.example.resikelapp.ui.components.CommunityItem
import com.example.resikelapp.ui.components.Search
import com.example.resikelapp.utils.ViewModelFactory

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Community(
    modifier: Modifier = Modifier,
    viewModel: CommunityViewModel = viewModel(factory = ViewModelFactory(ResikelRepository())),
    navigateToDetail: (Long) -> Unit,
) {
    val groupedCommunity by viewModel.groupedComunities.collectAsState()
    val query by viewModel.query
    var isSearchFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        LazyColumn (
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ){
            item {
                Search(
                    query = query,
                    onQueryChange = viewModel::search,
                    modifier = Modifier
                        .onFocusChanged { focusState ->
                            isSearchFocused = focusState.isFocused
                        }
                )
            }
            if (isSearchFocused) {
                groupedCommunity.forEach { (_, communities) ->
                    items(communities, key = { it.id }) { community ->
                        CommunityItem(
                            nama = community.nama,
                            description = community.description,
                            jenisSampah = community.jenisSampah,
                            modifier = Modifier.clickable {
                                navigateToDetail(community.id)
                            }
                        )
                    }
                }
            } else {
                item {
                    Column{
                        Image(
                            painter = painterResource(id = R.drawable.gambar_belum_bergabung),
                            contentDescription = "Centered Image",
                            modifier = Modifier.align(Alignment.CenterHorizontally) // Mengatur gambar di tengah
                        )
                        Text(
                            text = "Kamu Belum Bergabung Komunitas Nih,\n Yuk Gabung Sekarang",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth() // Membuat teks menempati seluruh lebar untuk center align
                                .padding(top = 8.dp) // Jarak opsional antara gambar dan teks
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CommunityPreview() {
    Community(
        navigateToDetail = {}
    )
}