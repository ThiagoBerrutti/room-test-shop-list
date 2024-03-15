package com.example.roomtest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.roomtest.database.converters.DateConverter
import com.example.roomtest.database.daos.ItemDao
import com.example.roomtest.database.daos.ShoppingListDao
import com.example.roomtest.database.daos.StoreDao
import com.example.roomtest.database.entities.Item
import com.example.roomtest.database.entities.ShoppingList
import com.example.roomtest.database.entities.Store

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [Item::class, ShoppingList::class, Store::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingListDatabase:RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun storeDao(): StoreDao
    abstract fun shoppingListDao(): ShoppingListDao

    companion object{
        @Volatile
        var INSTANCE: ShoppingListDatabase? = null
        fun getDatabase(context: Context):ShoppingListDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    ShoppingListDatabase::class.java,
                    "shopping_db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}