package com.example.resikelapp.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resikelapp.ui.theme.GreenBase
import com.example.resikelapp.ui.theme.GreenLogo
import com.example.resikelapp.utils.StoreUser
import com.example.resikelapp.utils.formatFirebaseTimestamp


@Composable
fun SuccessScreen(
    onClick: () -> Unit = {},
    sharedViewModel: SharedViewModel
) {

    val offsetY = remember { Animatable(0f) } // Initialize with 0f (Float)
    val isExpanded = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val dataStore = StoreUser(context)
    val name = dataStore.getName.collectAsState(initial = "")

    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded.value) 180f else 0f,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    LaunchedEffect(Unit) {
        while (true) {
            animateVerticalBounce(offsetY)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(bottomEndPercent = 30, bottomStartPercent = 30))
                .background(GreenLogo)
                .offset(y = offsetY.value.dp)

        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(GreenBase)
            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(16.dp),
                    tint = Color.White
                )
            }

        }
        Spacer(modifier = Modifier
            .size(16.dp))
        Text(
            "Terima kasih, permintaanmu sedang kami proses!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier
            .size(16.dp))

        Text(
            formatFirebaseTimestamp(sharedViewModel.sampahTransaksi.value.tanggal),
            color = Color.Gray
        )
        Spacer(modifier = Modifier
            .size(20.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(elevation = 8.dp, shape = MaterialTheme.shapes.medium)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(vertical = 16.dp)
                .animateContentSize()

        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Total Poin")
                Text(
                    "${sharedViewModel.totalPoin.value} Poin",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Nama")
                Text("${name.value}")
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable(onClick = {
                        isExpanded.value = !isExpanded.value
                    })

            ) {
                Text(
                    "Detail Item",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier
                        .graphicsLayer {
                            rotationZ = rotationAngle
                        }
                )

            }

            if(isExpanded.value) {
                LazyColumn {
                    items(sharedViewModel.sampahItems.value) { sampahItem ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                sampahItem.jenisSampah
                                )
                            Text("${sampahItem.points / 10} kg", textAlign = TextAlign.End)
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                    item {
                        Spacer(Modifier.height(24.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                "Total",
                                fontWeight = FontWeight.Bold,
                                )
                            Text(
                                "${sharedViewModel.totalPoin.value/10} kg",
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 19.dp, end = 19.dp, bottom = 16.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenBase)
        ) {
            Text(
                text = "Ke Beranda",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

suspend fun animateVerticalBounce(offsetY: Animatable<Float, *>) {
    offsetY.animateTo(
        targetValue = 20f,
        animationSpec = tween(durationMillis = 300)
    )

    offsetY.animateTo(
        targetValue = 0f,
        animationSpec = tween(durationMillis = 300)
    )
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewKalkulasiScreen() {
//    SuccessScreen(
//        sharedViewModel = SharedViewModel()
//    )
//}