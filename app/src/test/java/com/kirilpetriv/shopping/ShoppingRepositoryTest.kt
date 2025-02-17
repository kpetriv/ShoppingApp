package com.kirilpetriv.shopping

import com.kirilpetriv.shopping.data.ShoppingRepositoryImpl
import com.kirilpetriv.shopping.data.toModel
import com.kirilpetriv.shopping.database.ShoppingDao
import com.kirilpetriv.shopping.database.ShoppingItemEntity
import com.kirilpetriv.shopping.domain.repository.ShoppingRepository
import com.kirilpetriv.shopping.model.ShoppingItem
import com.kirilpetriv.shopping.model.SortType
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ShoppingRepositoryTest {

    private val shoppingDao = mockk<ShoppingDao>()

    private fun createRepository(scheduler: TestCoroutineScheduler): ShoppingRepository =
        ShoppingRepositoryImpl(
            dao = shoppingDao,
            dispatcher = StandardTestDispatcher(scheduler = scheduler)
        )

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Test
    fun `repository successfully emits expected result converted to domain model`() = runTest {
        val entity = ShoppingItemEntity(
            id = 1,
            name = "Orange",
            aisleNumber = 1,
            isChecked = false
        )

        every { shoppingDao.getItemsSorted(any()) } returns flowOf(listOf(entity))

        val emits = mutableListOf<List<ShoppingItem>>()
        createRepository(testScheduler)
            .getItemsSorted(sortType = SortType.Chronological)
            .toList(emits)

        assertEquals(
            expected = listOf(listOf(entity.toModel())),
            actual = emits
        )
    }
}