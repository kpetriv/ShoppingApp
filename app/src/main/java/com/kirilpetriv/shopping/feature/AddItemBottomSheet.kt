package com.kirilpetriv.shopping.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kirilpetriv.shopping.R
import com.kirilpetriv.shopping.model.Aisle
import com.kirilpetriv.shopping.ui.theme.ShoppingTheme

// Using material 3 components for simplicity.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemBottomSheet(
    onDismiss: () -> Unit,
    onAddItem: (String, Aisle) -> Unit,
    modalBottomSheetState: SheetState = rememberModalBottomSheetState()
) {
    var name by remember { mutableStateOf("") }
    var selectedAisle by remember { mutableStateOf(Aisle.Unknown) }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.add_new_item),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(Modifier.height(8.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.item_name)) },
                isError = name.isBlank()
            )
            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.select_aisle),
                style = MaterialTheme.typography.titleMedium
            )

            var expanded by remember { mutableStateOf(false) }
            Box {
                TextButton(onClick = { expanded = true }) {
                    Text("$selectedAisle")
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    Aisle.entries.forEach { aisle ->
                        DropdownMenuItem(text = { Text("$aisle") }, onClick = {
                            selectedAisle = aisle
                            expanded = false
                        })
                    }
                }
            }
            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.cancel))
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = { onAddItem(name, selectedAisle) }
                ) {
                    Text(stringResource(R.string.add))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AddItemBottomSheetPreview() {
    ShoppingTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                AddItemBottomSheet(
                    onDismiss = {},
                    onAddItem = { _, _ -> },
                    modalBottomSheetState = rememberStandardBottomSheetState()
                )
            }
        }
    }
}