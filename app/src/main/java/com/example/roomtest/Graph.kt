package com.example.roomtest

import android.content.Context
import com.example.roomtest.database.ShoppingListDatabase
import com.example.roomtest.repositories.Repository

object Graph {
    lateinit var db: ShoppingListDatabase
        private set;

    val repository by lazy {
        Repository(
            listDao = db.shoppingListDao(),
            itemDao = db.itemDao(),
            storeDao = db.storeDao()
        )
    }

    fun provide(context:Context){
        db = ShoppingListDatabase.getDatabase(context)
    }
}