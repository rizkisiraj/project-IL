package com.example.resikelapp.ui.screens.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.resikelapp.R
import com.example.resikelapp.data.model.Community
import com.example.resikelapp.data.model.CommunityData
import com.example.resikelapp.ui.components.CommunityItem
import com.example.resikelapp.ui.components.Search

@Composable
fun CommunityListScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var searchText by remember { mutableStateOf("") }

    val filteredCommunities = CommunityData.communities.filter {
        it.nama.contains(searchText, ignoreCase = true) ||
                it.description.contains(searchText, ignoreCase = true)
    }

    Column(modifier = modifier.fillMaxSize()) {
        Search()
        if (filteredCommunities.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(filteredCommunities) { community ->
                    CommunityItem(community = community, onClick = {
                        navController.navigate("communityDetail")
                    })
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.gambar_belum_bergabung),
                        contentDescription = "Centered Image"
                    )
                    Text(
                        text = "Kamu Belum Bergabung Komunitas Nih,\n Yuk Gabung Sekarang",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun CommunityListScreenPreview() {
//    CommunityListScreen()
//}
