package com.kirilpetriv.shopping.domain.repository

import com.kirilpetriv.shopping.domain.model.ShoppingItem
import com.kirilpetriv.shopping.domain.model.SortType
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    fun getItemsSorted(sortType: SortType): Flow<List<ShoppingItem>>
    suspend fun insertShoppingItemWithNameAndAisleNumber(name: String, aisleNumber: Int)
    suspend fun updateCheckedStatus(itemId: Long, isChecked: Boolean)
    suspend fun clearAll()
}