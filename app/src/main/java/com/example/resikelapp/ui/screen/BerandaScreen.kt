package com.example.resikelapp.ui.screen


import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resikelapp.R
import com.example.resikelapp.ui.components.ResourceItem
import com.example.resikelapp.ui.components.ScheduleItem
import com.example.resikelapp.ui.theme.GreenBase
import com.example.resikelapp.ui.theme.GreenSecondary

@Preview(showBackground = true)
@Composable
fun BerandaScreen() {
    val beritaScrollState = rememberScrollState()
    val komunitasScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "image profile",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                "Welcome, Resikel",
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
                "Rp1.000"
            )
            Spacer(Modifier.width(64.dp))
            ResourceItem(
                R.drawable.dollar_sign,
                "Rp1.000"
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
                .background(Color.White)
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
                Text(
                    "Belum Terdaftar",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Red
                )
                ScheduleItem(icon = Icons.Outlined.Schedule, value = "08:00 - 12:00")
                ScheduleItem(icon = Icons.Filled.CalendarMonth, value = "12/12/2024")
            }

            Spacer(Modifier.weight(1f))
            Icon(
                Icons.Filled.Add,
                contentDescription = "Tambah",
                tint = GreenBase,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Berita Terkini",
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
            modifier = Modifier.fillMaxWidth().scrollable(beritaScrollState, orientation = Orientation.Horizontal)
        ) {
            items(5) {
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
                        Image(
                            painter = painterResource(R.drawable.sampah_placeholder_image),
                            contentDescription = "Gambar sampah",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(119.dp)
                        )
                        Text(
                            "Sampah Masyarakat Besar Kabupaten",
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
            items(5) {
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
                        Icon(
                            Icons.Outlined.People,
                            contentDescription = null
                        )
                        Text(
                            "Wilayah Kabupaten Komunitas",
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