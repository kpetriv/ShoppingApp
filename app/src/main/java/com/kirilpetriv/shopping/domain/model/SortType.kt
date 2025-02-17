package com.kirilpetriv.shopping.domain.model

enum class SortType {
    Name,
    Aisle,
    Chronological;

    val comparator: Comparator<ShoppingItem>
        get() = when (this) {
            Name -> compareBy { it.name }
            Aisle -> compareBy { it.aisleNumber }
            Chronological -> compareBy { it.id }
        }
}