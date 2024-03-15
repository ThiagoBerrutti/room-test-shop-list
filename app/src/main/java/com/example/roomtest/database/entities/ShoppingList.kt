package com.example.roomtest.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class ShoppingList(
    @ColumnInfo(name = "list_id")
    @PrimaryKey(autoGenerate = true) val id:Int =0,
    @ColumnInfo(name= "list_name") val name:String?
)
