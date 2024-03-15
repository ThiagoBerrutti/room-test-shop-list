package com.example.roomtest.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roomtest.database.entities.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insert(item:Item)

    @Update
    suspend fun update(item:Item)

    @Delete
    suspend fun delete(item:Item)

    @Query("SELECT * FROM items")
    fun getAll(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE item_id = :itemId")
    fun getById(itemId:Int):Item?



}