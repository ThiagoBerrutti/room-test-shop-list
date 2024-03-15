package com.example.roomtest.repositories

import com.example.roomtest.database.daos.ItemDao
import com.example.roomtest.database.daos.ShoppingListDao
import com.example.roomtest.database.daos.StoreDao
import com.example.roomtest.database.entities.Item
import com.example.roomtest.database.entities.ItemWithStoreAndList
import com.example.roomtest.database.entities.ShoppingList
import com.example.roomtest.database.entities.Store
import kotlinx.coroutines.flow.Flow

class Repository(
    private val listDao: ShoppingListDao,
    private val itemDao: ItemDao,
    private val storeDao: StoreDao,
) {
    val store: Flow<List<Store>> = storeDao.getAll()
    val getItemsWithListsAndStores: Flow<List<ItemWithStoreAndList>> =
        listDao.getAllItemsWithStoreAndList()

    fun getItemWithListAndStore(id: Int) = listDao.getItemWithStoreAndListById(id)

    fun getItemsWithListAndStoreByListId(listId: Int) =
        listDao.getItemsWithStoreAndListByListId(listId)

    suspend fun insertList(shoppingList: ShoppingList) = listDao.insert(shoppingList)

    suspend fun insertStore(store: Store) = storeDao.insert(store)
    suspend fun insertItem(item: Item) = itemDao.insert(item)

    suspend fun updateList(shoppingList: ShoppingList) = listDao.update(shoppingList)
    suspend fun updateItem(item: Item) = itemDao.update(item)
    suspend fun updateStore(store: Store) = storeDao.update(store)

    suspend fun deleteList(shoppingList: ShoppingList) = listDao.delete(shoppingList)
    suspend fun deleteItem(item: Item) = itemDao.delete(item)
    suspend fun deleteStore(store: Store) = storeDao.delete(store)
}