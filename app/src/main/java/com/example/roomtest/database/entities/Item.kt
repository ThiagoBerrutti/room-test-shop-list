package com.example.roomtest.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "items")
data class Item (
    @ColumnInfo(name="item_id")
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

    @ColumnInfo(name= "item_name") val name:String?,
    val price:Float?,
    val quantity:String,
    @ColumnInfo("item_list_id_FK") val listIdFK: Int,
    @ColumnInfo("item_store_id_FK") val storeIdFK:Int,
    val date:Date,
    val isChecked:Boolean
)