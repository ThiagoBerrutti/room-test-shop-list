package com.example.roomtest.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roomtest.database.entities.Item
import com.example.roomtest.database.entities.ItemWithStoreAndList
import com.example.roomtest.database.entities.ShoppingList
import com.example.roomtest.database.entities.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shoppingList: ShoppingList)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(shoppingList: ShoppingList)

    @Delete
    suspend fun delete(shoppingList: ShoppingList)



    //            sl.list_id AS shopping_list_id, sl.list_name AS shopping_list_name,
//            i.item_id AS items_id, i.item_name AS item_name, i.price AS price,
//            i.quantity AS quantity, i.item_list_id_FK AS list_id_fk, i.item_store_id_FK AS store_id_fk,
//            i.date AS date, i.isChecked AS isChecked,
//            s.store_id AS stores_store_id_fk, s.listIdFK AS stores_list_id_fk
    @Query("""
        SELECT * FROM items AS i 
        INNER JOIN shopping_list AS sl ON i.item_list_id_FK = sl.list_id 
        INNER JOIN stores AS s ON sl.list_id = s.listIdFk
    """)
    fun getAllItemsWithStoreAndList(): Flow<List<ItemWithStoreAndList>>

    @Query("""
        SELECT * FROM items AS i INNER JOIN shopping_list AS sl
        ON i.item_list_id_FK = sl.list_id INNER JOIN stores AS s
        ON i.item_store_id_FK = s.store_id 
        WHERE s.listIdFK = :listId
    """)
    fun getItemsWithStoreAndListByListId(listId:Int): Flow<List<ItemWithStoreAndList>>

@Query("""
        SELECT * FROM items AS i INNER JOIN shopping_list AS sl
        ON i.item_list_id_FK = sl.list_id INNER JOIN stores AS s
        ON sl.list_id = s.listIdFK 
        WHERE i.item_id  = :itemId
    """)
    fun getItemWithStoreAndListById(itemId:Int): Flow<ItemWithStoreAndList?>

}

