package com.example.roomtest.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stores")
data class Store(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="store_id")
    val id:Int =0,
    val listIdFK:Int,
    val storeName:String

)
