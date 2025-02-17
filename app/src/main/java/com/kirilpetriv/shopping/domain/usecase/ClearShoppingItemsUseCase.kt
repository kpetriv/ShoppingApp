package com.kirilpetriv.shopping.domain.usecase

import com.kirilpetriv.shopping.domain.repository.ShoppingRepository

class ClearShoppingItemsUseCase(private val repository: ShoppingRepository) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            repository.clearAll()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}