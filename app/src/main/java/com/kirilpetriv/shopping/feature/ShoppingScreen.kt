package com.kirilpetriv.shopping.feature

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kirilpetriv.shopping.R
import com.kirilpetriv.shopping.model.Aisle
import com.kirilpetriv.shopping.model.ShoppingItem
import com.kirilpetriv.shopping.model.SortType
import com.kirilpetriv.shopping.ui.theme.ShoppingTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShoppingScreen(
    viewModel: ShoppingViewModel = koinViewModel()
) {
    val items by viewModel.items.collectAsState()
    val sortType by viewModel.sortType.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    ShoppingScreen(
        items = items,
        sortType = sortType,
        errorMessage = errorMessage,
        onDismissError = { viewModel.dismissError() },
        onUpdateSortType = { viewModel.updateSortType(it) },
        onClearShopping = { viewModel.clearShopping() },
        onToggleItem = { viewModel.toggleItemChecked(it) },
        onAddItem = { name, aisle -> viewModel.addItem(name, aisle) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(
    items: List<ShoppingItem>,
    sortType: SortType,
    errorMessage: String?,
    onDismissError: () -> Unit,
    onUpdateSortType: (SortType) -> Unit,
    onClearShopping: () -> Unit,
    onToggleItem: (ShoppingItem) -> Unit,
    onAddItem: (String, Aisle) -> Unit,
) {
    var expanded by remember { mutableStateOf(value = false) }
    val snackbarHostState = remember { SnackbarHostState() }
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            onDismissError()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.shopping_list)) },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(painterResource(R.drawable.ic_filter), contentDescription = "Sort")
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        SortType.entries.forEach { type ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        if (type == sortType) {
                                            Icon(
                                                Icons.Default.Check,
                                                contentDescription = "Selected"
                                            )
                                        } else {
                                            Spacer(modifier = Modifier.width(24.dp))
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(type.name)
                                    }
                                },
                                onClick = {
                                    onUpdateSortType(type)
                                    expanded = false
                                }
                            )
                        }
                    }
                    IconButton(onClick = onClearShopping) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Clear All")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showBottomSheet = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(items, key = { it.id }) { item ->
                ListItem(
                    modifier = Modifier
                        .clickable { onToggleItem(item) }
                        .animateItem(
                            fadeInSpec = tween(durationMillis = 450),
                            fadeOutSpec = tween(durationMillis = 450),
                            placementSpec = spring(
                                stiffness = Spring.StiffnessLow,
                                dampingRatio = Spring.DampingRatioMediumBouncy
                            )
                        )
                        .padding(8.dp),
                    leadingContent = {
                        Checkbox(
                            checked = item.isChecked,
                            onCheckedChange = { onToggleItem(item) }
                        )
                    },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null
                        )
                    },
                    headlineContent = {
                        Text(
                            text = item.name,
                            textDecoration = if (item.isChecked)
                                TextDecoration.LineThrough
                            else
                                TextDecoration.None
                        )
                    },
                    supportingContent = { Text(stringResource(R.string.aisle, item.aisleNumber)) }
                )
            }
        }

        if (showBottomSheet) {
            AddItemBottomSheet(
                onDismiss = { showBottomSheet = false },
                onAddItem = { name, aisle ->
                    onAddItem(name, aisle)
                    showBottomSheet = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingScreenPreview() {
    ShoppingTheme {
        ShoppingScreen(
            items = listOf(
                ShoppingItem(
                    id = 1,
                    name = "Milk",
                    aisleNumber = Aisle.Dairy.number,
                    isChecked = true
                ),
                ShoppingItem(
                    id = 2,
                    name = "Bread",
                    aisleNumber = Aisle.Bakery.number,
                    isChecked = false
                ),
                ShoppingItem(
                    id = 3,
                    name = "Eggs",
                    aisleNumber = Aisle.Dairy.number,
                    isChecked = false
                ),
            ),
            sortType = SortType.Aisle,
            errorMessage = null,
            onDismissError = {},
            onUpdateSortType = {},
            onClearShopping = {},
            onToggleItem = {},
            onAddItem = { _, _ -> }
        )
    }
}