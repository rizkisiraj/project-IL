package com.example.resikelapp.ui.screens

import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.resikelapp.R
import com.example.resikelapp.data.model.SampahItem
import com.example.resikelapp.data.model.SampahTransaksi
import com.example.resikelapp.ui.components.SampahItemCard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

@Composable
fun KalkulasiScreen(onBackClick: () -> Unit = {}, navController: NavController, sharedViewModel: SharedViewModel) {
    var sampahCards by remember { mutableStateOf(listOf<SampahItem>(SampahItem(
        jenisSampah = "Organik"
    ))) }
    var totalPoints by remember { mutableStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }
    val db = Firebase.firestore
    val context = LocalContext.current
    val uid = FirebaseAuth.getInstance().currentUser?.uid


    if (isLoading) {
        Dialog(
            onDismissRequest = { isLoading = false },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.resikel_logo),
                    contentDescription = "Zooming Image",
                    modifier = Modifier
                        .size(100.dp)
                )
                Text("Mohon tunggu sebentar...", color = White)
                Spacer(modifier = Modifier.size(8.dp))
                CircularProgressIndicator(
                    color = White,
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Filled.Cancel,
                    contentDescription = "Back",
                    tint = Color.Red
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Kalkulasi Poin",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E5631)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn for SampahItemCards
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if(sampahCards.isEmpty()) {
                item {
                    Text("Tidak ada data")
                }

            }
            itemsIndexed(sampahCards) { index, sampahItem ->
                SampahItemCard(
                    selectedWasteType = sampahItem.jenisSampah,
                    onWasteTypeSelected = { newSelection ->
                        sampahCards = sampahCards.toMutableList().apply {
                            this[index] = sampahItem.copy(jenisSampah = newSelection)
                        }
                    },
                    onRemoveCard = { // Panggil untuk menghapus kartu dari daftar
                        sampahCards = sampahCards.toMutableList().apply {
                            removeAt(index)
                        }
                    },
                    onPointChanged =  { point ->
                        sampahCards = sampahCards.toMutableList().apply {
                            this[index] = sampahItem.copy(kuantitas = point/10, points = point)
                        }

                        totalPoints = sampahCards.sumOf { it.points }
                    },
                    onQuantityChanged = { quantitais ->

                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Icon Add in Center Bottom
        IconButton(
            onClick = {
                sampahCards = sampahCards + SampahItem(
                    jenisSampah = "Organik"
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(48.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Tambah",
                tint = Color(0xFF1E5631)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Total Points Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0E0E0))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${totalPoints} Pts", // Assuming each item gives 50 points
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Confirmation Button
        Button(
            onClick = {
                isLoading = true
                sampahCards.forEach { sampahItem ->
                    if(sampahItem.points == 0 || sampahItem.kuantitas == 0 || sampahItem.jenisSampah == "") {
                        isLoading = false
                        Toast.makeText(context, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if(uid == null) {
                        isLoading = false
                        Toast.makeText(context, "Tidak ada user", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                }

                val newDocRef = db.collection("transaksi").document()
                val sampahTransaksi = SampahTransaksi(
                    id = newDocRef.id,
                    idUser = uid!!,
                    status = "pending",
                    totalPoints = totalPoints
                )

                newDocRef.set(sampahTransaksi)
                    .addOnSuccessListener {
                        println("Batch write successful!")
                    }
                    .addOnFailureListener { e ->
                        println("Batch write failed: $e")
                        isLoading = false
                        return@addOnFailureListener
                    }

                val batch = db.batch()

                sampahCards.forEach { sampahItem ->
                    val sampahItemRef = db.collection("detailTransaksi").document()
                    sampahItem.idTransaksi = newDocRef.id
                    batch.set(sampahItemRef, sampahItem)
                }

                batch.commit()
                    .addOnSuccessListener {
                        // Handle success
                        println("Batch write successful!")
                        isLoading = false
                        sharedViewModel.updateSampahTransaksi(sampahTransaksi)
                        sharedViewModel.updateSampahItems(sampahCards)
                        sampahCards = listOf()
                        totalPoints = 0
                        navController.navigate("success") {
                            popUpTo("kalkulasi_graph") {
                                inclusive = true
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        // Handle failure
                        println("Batch write failed: $e")
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF1E5631))
        ) {
            Text(text = "Konfirmasi", color = White, fontSize = 16.sp, modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewKalkulasiScreen() {
//    KalkulasiScreen()
//}
