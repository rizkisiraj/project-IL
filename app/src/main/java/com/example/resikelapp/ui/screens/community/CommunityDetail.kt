package com.example.resikelapp.ui.screens.community

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.resikelapp.R
import com.example.resikelapp.data.model.AcaraData
import com.example.resikelapp.data.repository.ResikelRepository
import com.example.resikelapp.ui.components.AcaraItem
import com.example.resikelapp.ui.components.CommunityItem
import com.example.resikelapp.ui.theme.ResikelAppTheme
import com.example.resikelapp.utils.UiState
import com.example.resikelapp.utils.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityDetail(
    communityId: Long,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CommunityViewModel = viewModel(factory = ViewModelFactory(ResikelRepository())),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getOrderCommunityById(communityId)
            }
            is UiState.Success -> {
                val data = uiState.data

                // Wrapping the main content in LazyColumn for scrollability
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    // TopAppBar item
                    item {
                        TopAppBar(
                            title = { Text(text = "", fontSize = 20.sp) },
                            navigationIcon = {
                                IconButton(onClick = { navigateBack() }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.arrow_left),
                                        contentDescription = "Back",
                                        tint = colorResource(id = R.color.primary)
                                    )
                                }
                            },
                            actions = {}
                        )
                    }

                    // Community Detail Section
                    item {
                        Row(
                            modifier = Modifier
                                .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                                .fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.gbr_detail),
                                contentDescription = "Gambar Detail",
                                modifier = Modifier.size(136.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = data.community.nama,
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    style = TextStyle(fontSize = 26.sp)
                                )
                                Text(
                                    text = data.community.description,
                                    style = TextStyle(fontSize = 12.sp)
                                )
                                Button(
                                    onClick = { /* Handle Join Action */ },
                                    shape = RoundedCornerShape(50),
                                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary)),
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.Add,
                                            contentDescription = "Add",
                                            tint = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(text = "Gabung", color = Color.White)
                                    }
                                }
                            }
                        }
                    }

                    // Community Description
                    item {
                        Text(
                            modifier = Modifier.padding(30.dp),
                            text = "Paper Fighter, adalah komunitas yang berfokus pada daur ulang sampah kertas demi menjaga lingkungan. Anggota kami berkumpul untuk berbagi pengetahuan dalam mengolah kertas bekas menjadi produk baru yang kreatif dan bermanfaat. \n" +
                                    "\n" +
                                    "Melalui berbagai kegiatan menarik, kami bertujuan mengurangi limbah kertas serta meningkatkan kesadaran masyarakat tentang pentingnya daur ulang. Kami berkomitmen untuk membangun jaringan yang solid di antara para pecinta lingkungan.",
                            textAlign = TextAlign.Justify
                        )
                    }

                    // Section Title for "Acara"
                    item {
                        Text(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                            text = "Acara",
                            color = colorResource(R.color.primary),
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    // List of Acara Items
                    items(AcaraData.acara) { acara ->
                        AcaraItem(
                            acara = acara,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }
            is UiState.Error -> {
                // Display an error message or handle the error state
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityDetailPreview() {
    ResikelAppTheme {
        CommunityDetail(communityId = 1, navigateBack = {})
    }
}

