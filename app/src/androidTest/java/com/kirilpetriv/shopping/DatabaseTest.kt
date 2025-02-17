package com.kirilpetriv.shopping

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.kirilpetriv.shopping.database.ShoppingDao
import com.kirilpetriv.shopping.database.ShoppingDatabase
import com.kirilpetriv.shopping.database.ShoppingItemEntity
import com.kirilpetriv.shopping.database.buildShoppingItemsSortQuery
import com.kirilpetriv.shopping.model.SortType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DatabaseTest {

    private lateinit var database: ShoppingDatabase
    private lateinit var shoppingDao: ShoppingDao

    @Before
    fun setupDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ShoppingDatabase::class.java).build()
        shoppingDao = database.shoppingDao()
    }

    @After
    fun closeDatabase() {
        database.clearAllTables()
        database.close()
    }

    @Test
    fun insert_works_correctly() = runBlocking {
        val shoppingItem1 = ShoppingItemEntity(name = "Banana", aisleNumber = 1)
        val shoppingItem2 = ShoppingItemEntity(name = "Apple", aisleNumber = 2)
        shoppingDao.insert(shoppingItem1)
        shoppingDao.insert(shoppingItem2)
        val shoppingItems = shoppingDao.getItemsSorted(
            query = (buildShoppingItemsSortQuery(SortType.Name))
        )
        assertEquals(
            // adding expected id's based on the order of insertion
            expected = listOf(shoppingItem2.copy(id = 2), shoppingItem1.copy(id = 1)),
            actual = shoppingItems.first()
        )
    }
}