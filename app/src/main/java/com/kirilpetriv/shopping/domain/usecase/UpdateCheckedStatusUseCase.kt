package com.kirilpetriv.shopping.domain.usecase

import com.kirilpetriv.shopping.domain.repository.ShoppingRepository

class UpdateCheckedStatusUseCase(private val repository: ShoppingRepository) {
    suspend operator fun invoke(itemId: Long, isChecked: Boolean): Result<Unit> {
        return try {
            repository.updateCheckedStatus(itemId, isChecked)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}