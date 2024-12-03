package com.example.resikelapp.ui.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resikelapp.R

@Composable
fun SampahItemCard(
    selectedWasteType: String,
    onWasteTypeSelected: (String) -> Unit,
    onPointChanged: (Int) -> Unit,
    onRemoveCard: () -> Unit,
    onQuantityChanged: (Int) -> Unit
) {
    var text = remember { mutableStateOf("")}

    var pointPerCard = remember { mutableStateOf(0) }

    val change : (String) -> Unit = { it ->
        if(it.isEmpty()) {
            text.value = "0"
        } else {
            text.value = it
        }
        if(selectedWasteType.isNotEmpty()) {
            pointPerCard.value = text.value.toInt()*10
            onPointChanged(pointPerCard.value)
//            onQuantityChanged(pointPerCard.value)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E5631))
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(19.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    SampahItemDropdown(
                        selectedSampahItem = selectedWasteType,
                        onSampahItemSelected = onWasteTypeSelected
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = text.value,
                            textStyle = TextStyle(
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 14.sp
                            ),
                            modifier = Modifier.width(60.dp).padding(vertical = 0.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.White,
                                cursorColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            onValueChange = change
                        )
                        Text(
                            text = "Kg",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Text(
                text = "${pointPerCard.value} Pts",
                style = TextStyle(
                    fontSize = 25.sp,
                    lineHeight = 36.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFFFFF),
                ),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
            )

            IconButton(
                onClick = { onRemoveCard() },
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Close",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.White
                )
            }
        }
    }
}
