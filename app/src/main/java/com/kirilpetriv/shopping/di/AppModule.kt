package com.kirilpetriv.shopping.di

import com.kirilpetriv.shopping.data.ShoppingRepositoryImpl
import com.kirilpetriv.shopping.database.ShoppingDatabase
import com.kirilpetriv.shopping.domain.repository.ShoppingRepository
import com.kirilpetriv.shopping.domain.usecase.AddShoppingItemUseCase
import com.kirilpetriv.shopping.domain.usecase.ClearShoppingItemsUseCase
import com.kirilpetriv.shopping.domain.usecase.GetShoppingItemsBySortTypeUseCase
import com.kirilpetriv.shopping.domain.usecase.SortAndGroupShoppingItemsUseCase
import com.kirilpetriv.shopping.domain.usecase.UpdateCheckedStatusUseCase
import com.kirilpetriv.shopping.feature.ShoppingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

// Note: This module would be in the App, and there would be other modules for each actual module
// based on the modules dependencies each individual module di file would include others as needed
// good split is per domain/feature with layering: network/db -> data -> domain -> feature
val shoppingModule = module {
    single { ShoppingDatabase.getInstance(androidContext()) }
    single { get<ShoppingDatabase>().shoppingDao() }
    single<ShoppingRepository> { ShoppingRepositoryImpl(get()) }

    single { AddShoppingItemUseCase(get()) }
    single { ClearShoppingItemsUseCase(get()) }
    single { GetShoppingItemsBySortTypeUseCase(get()) }
    single { UpdateCheckedStatusUseCase(get()) }
    single { SortAndGroupShoppingItemsUseCase() }

    single { ShoppingViewModel(get(), get(), get(), get(), get()) }
}