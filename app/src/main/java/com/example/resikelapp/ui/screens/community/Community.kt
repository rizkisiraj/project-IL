package com.example.resikelapp.ui.screens.community

import android.graphics.Color
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
    navigateToDetail: (String) -> Unit,
) {

    val groupedCommunity by viewModel.groupedComunities.collectAsState()
    val query by viewModel.filteredCommunities.collectAsState()

    var isSearchFocused by remember { mutableStateOf(false) }

    val joinedCommunities = groupedCommunity.filter { it.gabungStatus }
    val notJoinedCommunities = groupedCommunity.filter { !it.gabungStatus }

    val isDataAvailable = groupedCommunity.isNotEmpty()
    Box(
        modifier = modifier
    ) {
        if (isDataAvailable) {
            val scope = rememberCoroutineScope()
            val listState = rememberLazyListState()
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(bottom = 80.dp),
                modifier = Modifier
                    .background(colorResource(R.color.white))

            ) {
                item {
                    Search(
                        query = viewModel.query.collectAsState().value,
                        onQueryChange = viewModel::search,
                        modifier = Modifier
                            .onFocusChanged { focusState ->
                                isSearchFocused = focusState.isFocused
                            }
                    )
                }

                if (isSearchFocused) {
                    items(query) { community ->
                        CommunityItem(
                            nama = community.nama,
                            description = community.description,
                            jenisSampah = community.jenisSampah,
                            modifier = Modifier.clickable {
                                navigateToDetail(community.idCommunity)
                            }
                        )
                    }
                } else {
                    if (joinedCommunities.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 16.dp)
                            ) {
                                HorizontalDivider(
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .fillMaxWidth(),
                                    thickness = 1.dp,
                                    color = colorResource(id = R.color.primary)
                                )

                                Box(
                                    modifier = Modifier
                                        .padding(start = 30.dp)
                                        .zIndex(1f)
                                ) {
                                    Text(
                                        text = "Sudah Diikuti",
                                        modifier = Modifier
                                            .background(colorResource(R.color.white))
                                            .align(Alignment.CenterStart)
                                            .padding(horizontal = 14.dp)
                                    )
                                }

                            }
                        }
                        items(joinedCommunities) { community ->
                            CommunityItem(
                                nama = community.nama,
                                description = community.description,
                                jenisSampah = community.jenisSampah,
                                modifier = Modifier.clickable {
                                    navigateToDetail(community.idCommunity)
                                }
                            )
                        }
                    }

                    if (notJoinedCommunities.isNotEmpty() && joinedCommunities.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                            ) {
                                // Garis horizontal
                                HorizontalDivider(
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .fillMaxWidth(),
                                    thickness = 1.dp,
                                    color = colorResource(id = R.color.primary)
                                )

                                Box(
                                    modifier = Modifier
                                        .padding(start = 30.dp)
                                        .zIndex(1f)
                                ) {
                                    Text(
                                        text = "Belum Diikuti",
                                        modifier = Modifier
                                            .background(colorResource(R.color.white))
                                            .align(Alignment.CenterStart)
                                            .padding(horizontal = 14.dp)
                                    )
                                }

                            }
                        }
                        items(notJoinedCommunities) { community ->
                            CommunityItem(
                                nama = community.nama,
                                description = community.description,
                                jenisSampah = community.jenisSampah,
                                modifier = Modifier.clickable {
                                    navigateToDetail(community.idCommunity)
                                }
                            )
                        }
                    } else {
                        item {
                            Column {
                                Image(
                                    painter = painterResource(id = R.drawable.gambar_belum_bergabung),
                                    contentDescription = "Centered Image",
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                Text(
                                    text = "Kamu Belum Bergabung Komunitas Nih,\n Yuk Gabung Sekarang",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                )
                            }
                        }
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
        navigateToDetail={}
    )
}