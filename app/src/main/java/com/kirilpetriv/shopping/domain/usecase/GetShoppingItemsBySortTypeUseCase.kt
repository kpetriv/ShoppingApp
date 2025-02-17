package com.kirilpetriv.shopping.domain.usecase

import com.kirilpetriv.shopping.model.ShoppingItem
import com.kirilpetriv.shopping.model.SortType
import com.kirilpetriv.shopping.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow

class GetShoppingItemsBySortTypeUseCase(private val repository: ShoppingRepository) {
    operator fun invoke(sortType: SortType): Flow<List<ShoppingItem>> {
        return repository.getItemsSorted(sortType)
    }
}