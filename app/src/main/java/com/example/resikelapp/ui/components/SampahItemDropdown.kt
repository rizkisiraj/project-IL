package com.example.resikelapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resikelapp.data.model.SampahData

@Composable
fun SampahItemDropdown(
    selectedSampahItem: String,
    onSampahItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = { expanded = !expanded },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                )
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedSampahItem,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E5631))
        ) {
            SampahData.sampahItems.forEach { sampahItem ->
                DropdownMenuItem(
                    onClick = {
                        onSampahItemSelected(sampahItem.jenisSampah)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = sampahItem.jenisSampah,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                )
            }
        }
    }
}
