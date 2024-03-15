package com.example.roomtest.database.entities

import androidx.room.Embedded

data class ItemWithStoreAndList(
    @Embedded
//    @Embedded(prefix = "shopping_list_")
    val list: ShoppingList,
    @Embedded
//    @Embedded("items_")
    val item: Item,
    @Embedded
//    @Embedded("stores_")
    val store: Store
)