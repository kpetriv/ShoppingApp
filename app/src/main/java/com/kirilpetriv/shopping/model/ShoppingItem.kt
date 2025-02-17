package com.kirilpetriv.shopping.model

// For simplicity the state is incorporated into the domain object, although it should not be
// Similarly for aisleNumber, it should be a separate domain object and the only reference
// should be the id
data class ShoppingItem(
    val id: Long,
    val name: String,
    val isChecked: Boolean,
    val aisleNumber: Int,
)