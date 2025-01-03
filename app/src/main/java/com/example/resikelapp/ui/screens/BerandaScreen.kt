package com.example.resikelapp.ui.screens


import android.graphics.BlurMaskFilter
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.resikelapp.R
import com.example.resikelapp.data.model.DaftarPenjemputan
import com.example.resikelapp.data.model.SampahTransaksi
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.data.repository.ResikelRepository
import com.example.resikelapp.ui.components.ResourceItem
import com.example.resikelapp.ui.components.ScheduleItem
import com.example.resikelapp.ui.components.shimmerEffect
import com.example.resikelapp.ui.theme.GreenBase
import com.example.resikelapp.ui.theme.GreenSecondary
import com.example.resikelapp.utils.StoreUser
import com.example.resikelapp.utils.ViewModelFactory
import com.example.resikelapp.utils.formatTimestampToDate
import com.example.resikelapp.utils.formatToRupiah
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import kotlinx.coroutines.launch

@Composable
fun BerandaScreen(
    navController: NavController,
    viewModel: BerandaScreenViewModel = viewModel(factory = ViewModelFactory(ResikelRepository()))
) {
    val beritaScrollState = rememberScrollState()
    val komunitasScrollState = rememberScrollState()
    val newsList by viewModel.newsList.collectAsState()
    val beritaList by viewModel.acaraComunities.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val totalPoints by viewModel.total.collectAsState()
    val jadwalPenjemputan by viewModel.jadwalPenjemputan.collectAsState()
    val submittedValue by viewModel.submittedStatus.collectAsState()
    val context = LocalContext.current
    val db = com.google.firebase.ktx.Firebase.firestore
    var isPausing by remember { mutableStateOf(false) }
    val userData by viewModel.user.collectAsState()
    val scope = rememberCoroutineScope()


    val dataStore = StoreUser(context)
    val userName = dataStore.getName.collectAsState(initial = null)
    val userProfile = dataStore.getPhotoUrl.collectAsState(initial = null)
    val userFirebaseData = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(Unit) {
        viewModel.getNewsFirebase()
        viewModel.getAcaraFirebase()

        if(userFirebaseData != null) {
            viewModel.fetchDataAndSum(userFirebaseData.uid)

            dataStore.getName.collect { name ->
                if(name == null) {
                    viewModel.getUserData(userFirebaseData.uid, dataStore)
                } else {
                    Log.d("DataStore Debug", "Emitted Name: $name")
                    Log.d("ini log", "ini tidak masuk")
                }
            }
        }
    }

    LaunchedEffect(userFirebaseData) {
        viewModel.getTanggalPenjemputanTerdekat()
    }

    LaunchedEffect(userFirebaseData, jadwalPenjemputan, isPausing) {
        if (userFirebaseData != null && jadwalPenjemputan != null) {
            viewModel.checkIfTheUserAlreadySubmittedPenjemputan(userId = userFirebaseData.uid, penjemputanId = jadwalPenjemputan!!.id)
        }
    }

    if (isPausing) {
        var isSubmiting by remember { mutableStateOf(false) }

        if(isSubmiting) {
            Dialog(onDismissRequest = {
                isSubmiting = false
                isPausing = false
            }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Tunggu Sebentar",
                            modifier = Modifier
                                .wrapContentSize(Alignment.Center),
                            textAlign = TextAlign.Center,
                        )
                    }

                }
            }
        } else {
            AlertDialog(
                icon = {
                    Icon(Icons.Default.Info, contentDescription = "Example Icon")
                },
                title = {
                    Text(text = "Konfirmasi Penjemputan")
                },
                text = {
                    Text(text = "Apakah kamu yakin ingin mendaftar penjemputan?")
                },
                onDismissRequest = {
                    isPausing = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            isSubmiting = true
                            if(userFirebaseData != null && jadwalPenjemputan != null) {
                                val newDocRef = db.collection("daftarPenjemputan").document()
                                val daftarPenjemputan = DaftarPenjemputan(
                                    alamat = "Perumnas Jalan Bintan",
                                    idUser = userFirebaseData.uid,
                                    status = "Sedang Diverifikasi",
                                    idPenjemputan = jadwalPenjemputan!!.id,
                                )

                                newDocRef.set(daftarPenjemputan)
                                    .addOnSuccessListener {
                                        isSubmiting = false
                                        isPausing = false
                                        Toast.makeText(
                                            context,
                                            "Pendaftaran berhasil",
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }
                                    .addOnFailureListener { e ->
                                        isSubmiting = false
                                        isPausing = false
                                        Toast.makeText(
                                            context,
                                            "Pendaftaran gagal: $e",
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }
                            }
                        }
                    ) {
                        Text("Ya")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            isPausing = false
                        }
                    ) {
                        Text("Tidak")
                    }
                }
            )
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = userProfile.value,
                contentDescription = "image profile",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                "Welcome, ${userName.value ?: "Guest"}",
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Outlined.Notifications,
                contentDescription = "Notifikasi"
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(GreenBase)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ResourceItem(
                R.drawable.coin_image,
                "${totalPoints}"
            )
            Spacer(Modifier.width(64.dp))
            ResourceItem(
                R.drawable.dollar_sign,
                "Rp0"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .dropShadow(
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .background(White)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.dump_truck),
                contentDescription = "Dump Truck"
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
            ) {
                when(submittedValue.lowercase()) {
                    "Belum Terdaftar".lowercase() -> Text(
                        "Belum Terdaftar",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Red
                    )
                    "Sudah Terdaftar".lowercase() -> Text(
                        "Belum Terdaftar",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Green
                    )
                    else -> Text(
                        "Sedang Diverifikasi",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFF08930)
                    )
                }
                ScheduleItem(icon = Icons.Outlined.Schedule, value = "08:00 - 12:00")
                ScheduleItem(icon = Icons.Filled.CalendarMonth, value = formatTimestampToDate(jadwalPenjemputan?.tanggal))
            }

            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = {
                    if(submittedValue == "Belum Terdaftar") {
                        isPausing = true
                    }
                },
                enabled = if(submittedValue == "Belum Terdaftar") true else false
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Tambah",
                    tint = GreenBase,
                    modifier = Modifier.size(48.dp)
                )
            }

        }

        Spacer(Modifier.height(32.dp))

        TextButton(
            onClick = {
                navController.navigate(Screen.News.route!!)
            }
        ) {
            Text(
                text = "Berita Terkini",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = GreenBase,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().scrollable(beritaScrollState, orientation = Orientation.Horizontal)
        ) {
            if(isLoading) {
                items(3) {
                    Box(
                        modifier = Modifier
                            .width(132.dp)
                            .height(145.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .shimmerEffect()
                    )
                }
            }

            if(!isLoading) {
                items(newsList) { item ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = GreenSecondary,
                        ),
                        modifier =
                        Modifier
                            .width(132.dp)
                            .dropShadow(
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            AsyncImage(
                                model = item.imageUrl,
                                contentDescription = "Gambar cover",
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
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Acara Komunitas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = GreenBase,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().scrollable(komunitasScrollState, orientation = Orientation.Horizontal)
        ) {
            items(beritaList) { berita ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = GreenSecondary,
                    ),
                    modifier =
                    Modifier
                        .width(216.dp)
                        .dropShadow(
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                    ){
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(72.dp)
                                .background(GreenBase)
                                .padding(8.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.people),
                                contentDescription = "People's Icon",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                "Paper Fighter",
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(8.dp)
                        ) {
                            Text(
                                berita.nama,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleMedium
                                )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                "12 September",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp
) = this.drawBehind {

    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint()
    paint.color = color

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}