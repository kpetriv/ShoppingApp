package com.kirilpetriv.shopping.domain.model

enum class SortType {
    NAME,
    AISLE,
    TIME_ADDED;

    val comparator: Comparator<ShoppingItem>
        get() = when (this) {
            NAME -> compareBy { it.name }
            AISLE -> compareBy { it.aisleNumber }
            TIME_ADDED -> compareBy { it.id }
        }
}