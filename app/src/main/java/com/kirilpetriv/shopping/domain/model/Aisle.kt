package com.kirilpetriv.shopping.domain.model

enum class Aisle(val number: Int) {
    Produce(1),
    Dairy(2),
    Bakery(3),
    Meat(4),
    Beverages(5),
    Frozen(6),
    Snacks(7),
    Household(8),
    Unknown(0);
}