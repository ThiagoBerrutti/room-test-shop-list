package com.example.roomtest.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomtest.Graph
import com.example.roomtest.database.entities.Item
import com.example.roomtest.database.entities.ItemWithStoreAndList
import com.example.roomtest.repositories.Repository
import com.example.roomtest.ui.utils.Category
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository = Graph.repository
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        getItems()
    }


    fun deleteItems(item: Item){
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun onItemCheckedChange(item:Item, isChecked:Boolean){
        viewModelScope.launch {
            repository.updateItem(
                item = item.copy(isChecked = isChecked)
            )
        }
    }

    fun onCategoryChange(category:Category){
        Log.d("Testing HomeViewModel",category.toString())
        state = state.copy(category = category)
        filterBy(category.id)
        Log.d("Testing HomeViewModel After",state.toString())
    }

    private fun getItems(){
        viewModelScope.launch {
            repository.getItemsWithListsAndStores.collectLatest {
                state = state.copy(
                    items = it
                )
            }
        }
    }

    private fun filterBy(shoppingListId:Int){
        if (shoppingListId != 10001){
            viewModelScope.launch {
                repository.getItemsWithListAndStoreByListId (shoppingListId)
                    .collectLatest {
                        state = state.copy(items = it)
                }
            }
        }else{
            getItems()
        }
    }


}

data class HomeState(
    val items: List<ItemWithStoreAndList> = emptyList(),
    val itemChecked: Boolean = false,
    val category:Category = Category()
)