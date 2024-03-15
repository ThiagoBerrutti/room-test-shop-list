package com.example.roomtest.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roomtest.database.entities.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(store:Store)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(store:Store)

    @Delete
    suspend fun delete(store:Store)

    @Query("SELECT * FROM stores")
    fun getAll():Flow<List<Store>>

    @Query("SELECT * FROM stores WHERE store_id = :storeId")
    fun getById(storeId:Int):Flow<Store?>




}