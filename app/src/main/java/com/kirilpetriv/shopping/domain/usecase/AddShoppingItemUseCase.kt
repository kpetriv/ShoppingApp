package com.kirilpetriv.shopping.domain.usecase

import com.kirilpetriv.shopping.domain.repository.ShoppingRepository
import com.kirilpetriv.shopping.model.Aisle

// Ideally I would remove the Aisle from this use case and use some ML capabilities to dynamically
// get the aisle based on the item name. For now, this simple approach of manual selection will do.
class AddShoppingItemUseCase(private val repository: ShoppingRepository) {
    suspend operator fun invoke(name: String, aisle: Aisle): Result<Unit> {
        if (name.isBlank()) return Result.failure(
            // simple validation and error handling using kotlin Result
            // generally i use a custom sealed class for results/loading/etc.
            IllegalArgumentException("Item name cannot be empty")
        )

        return try {
            repository.insertShoppingItemWithNameAndAisleNumber(name, aisle.number)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}