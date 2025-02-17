package com.kirilpetriv.shopping.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirilpetriv.shopping.domain.usecase.AddShoppingItemUseCase
import com.kirilpetriv.shopping.domain.usecase.ClearShoppingItemsUseCase
import com.kirilpetriv.shopping.domain.usecase.GetShoppingItemsBySortTypeUseCase
import com.kirilpetriv.shopping.domain.usecase.SortAndGroupShoppingItemsUseCase
import com.kirilpetriv.shopping.domain.usecase.UpdateCheckedStatusUseCase
import com.kirilpetriv.shopping.model.Aisle
import com.kirilpetriv.shopping.model.ShoppingItem
import com.kirilpetriv.shopping.model.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ShoppingViewModel(
    private val getShoppingItemsBySortTypeUseCase: GetShoppingItemsBySortTypeUseCase,
    private val addShoppingItemUseCase: AddShoppingItemUseCase,
    private val updateCheckedStatusUseCase: UpdateCheckedStatusUseCase,
    private val clearShoppingUseCase: ClearShoppingItemsUseCase,
    private val sortAndGroupUseCase: SortAndGroupShoppingItemsUseCase
) : ViewModel() {
    // sort type is needed to track currently selected sort type. default to chronological
    private val _sortType = MutableStateFlow(SortType.Chronological)
    val sortType: StateFlow<SortType> = _sortType

    // shopping items are loaded based on the selected sort type
    // also sorted into two groups: checked and unchecked so they are distinct in the ui
    val items = _sortType.flatMapLatest { sortType ->
        getShoppingItemsBySortTypeUseCase(sortType).map { items ->
            sortAndGroupUseCase(items = items, sortType = sortType)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // error message to be shown in the ui when not null (e.g. when adding an item fails)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun updateSortType(sortType: SortType) {
        _sortType.update { sortType }
    }

    fun addItem(name: String, aisle: Aisle) {
        viewModelScope.launch {
            addShoppingItemUseCase(name, aisle).onFailure { error ->
                _errorMessage.update { error.message }
            }
        }
    }

    fun toggleItemChecked(item: ShoppingItem) {
        viewModelScope.launch {
            updateCheckedStatusUseCase(item.id, !item.isChecked).onFailure { error ->
                _errorMessage.update { error.message }
            }
        }
    }

    fun clearShopping() {
        viewModelScope.launch {
            clearShoppingUseCase().onFailure { error ->
                _errorMessage.update { error.message }
            }
        }
    }

    fun dismissError() {
        _errorMessage.update { null }
    }
}