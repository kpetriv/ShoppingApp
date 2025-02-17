package com.kirilpetriv.shopping.data

import com.kirilpetriv.shopping.database.ShoppingItemEntity
import com.kirilpetriv.shopping.domain.model.ShoppingItem

internal fun ShoppingItemEntity.toModel() = ShoppingItem(
    id = id,
    name = name,
    aisleNumber = aisleNumber,
    isChecked = isChecked
)