package com.kirilpetriv.shopping.database

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.kirilpetriv.shopping.model.SortType

fun buildShoppingItemsSortQuery(sortType: SortType): SupportSQLiteQuery {
    val query = StringBuilder("SELECT * FROM shopping_items ORDER BY isChecked ASC,")
    when (sortType) {
        SortType.Name -> query.append(" name ASC")
        SortType.Aisle -> query.append(" aisleNumber ASC")
        SortType.Chronological -> query.append(" id ASC")
    }
    return SimpleSQLiteQuery(query.toString())
}