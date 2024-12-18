package com.example.resikelapp.ui.screens.aktivitas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resikelapp.data.repository.ResikelRepository
import com.example.resikelapp.ui.screens.BerandaScreenViewModel
import com.example.resikelapp.ui.screens.dropShadow
import com.example.resikelapp.utils.ViewModelFactory
import com.example.resikelapp.utils.formatTimestampToDate
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AktivitasScreen(
    viewModel: AktivitasViewModel = AktivitasViewModel(),
) {

    val listTransaksi by viewModel.listTransaksi.collectAsState()
    val userFirebase = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(userFirebase) {
        if(userFirebase != null) {
            viewModel.getAktivitasFirebase(userFirebase.uid)
        }

    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {
            Spacer(modifier = Modifier.height(60.dp))
            if(listTransaksi.size <= 0) {
                Text("Belum ada aktivitas")
            }
        }

        items(listTransaksi) { transaksi ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .dropShadow(
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        transaksi.status.capitalize(locale = Locale.current),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(
                        modifier = Modifier.size(8.dp)
                    )
                    Text(
                        formatTimestampToDate(transaksi.tanggal),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

                Text("${transaksi.totalPoints} Pts")

            }
        }
    }
}