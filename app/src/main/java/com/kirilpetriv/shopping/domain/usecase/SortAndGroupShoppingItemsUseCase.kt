package com.kirilpetriv.shopping.domain.usecase

import com.kirilpetriv.shopping.model.ShoppingItem
import com.kirilpetriv.shopping.model.SortType

class SortAndGroupShoppingItemsUseCase {
    operator fun invoke(items: List<ShoppingItem>, sortType: SortType): List<ShoppingItem> {
        return items.partition { !it.isChecked }
            .let { (unchecked, checked) ->
                unchecked.sortedWith(sortType.comparator) + checked.sortedWith(sortType.comparator)
            }
    }
}
