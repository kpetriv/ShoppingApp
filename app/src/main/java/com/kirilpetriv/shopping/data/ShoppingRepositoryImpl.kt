package com.kirilpetriv.shopping.data

import com.kirilpetriv.shopping.database.ShoppingDao
import com.kirilpetriv.shopping.database.ShoppingItemEntity
import com.kirilpetriv.shopping.database.buildShoppingItemsSortQuery
import com.kirilpetriv.shopping.model.ShoppingItem
import com.kirilpetriv.shopping.model.SortType
import com.kirilpetriv.shopping.domain.repository.ShoppingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ShoppingRepositoryImpl(
    private val dao: ShoppingDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ShoppingRepository {
    override fun getItemsSorted(sortType: SortType): Flow<List<ShoppingItem>> {
        return dao.getItemsSorted(buildShoppingItemsSortQuery(sortType)).map { flow ->
            flow.map { item -> item.toModel() }
        }.flowOn(dispatcher)
    }

    override suspend fun insertShoppingItemWithNameAndAisleNumber(name: String, aisleNumber: Int) =
        withContext(dispatcher) {
            dao.insert(ShoppingItemEntity(name = name, aisleNumber = aisleNumber))
        }

    override suspend fun updateCheckedStatus(itemId: Long, isChecked: Boolean) =
        withContext(dispatcher) {
            dao.updateCheckedStatus(itemId, isChecked)
        }

    override suspend fun clearAll() = withContext(dispatcher) { dao.clearAll() }

}