package com.kirilpetriv.shopping.domain.usecase

import com.kirilpetriv.shopping.model.Aisle
import com.kirilpetriv.shopping.domain.repository.ShoppingRepository

class AddShoppingItemUseCase(private val repository: ShoppingRepository) {
    suspend operator fun invoke(name: String, aisle: Aisle): Result<Unit> {
        if (name.isBlank()) return Result.failure(IllegalArgumentException("Item name cannot be empty"))

        return try {
            repository.insertShoppingItemWithNameAndAisleNumber(name, aisle.number)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}