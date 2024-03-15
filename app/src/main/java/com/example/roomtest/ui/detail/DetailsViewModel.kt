package com.example.roomtest.ui.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.roomtest.Graph
import com.example.roomtest.database.entities.Item
import com.example.roomtest.database.entities.ShoppingList
import com.example.roomtest.database.entities.Store
import com.example.roomtest.repositories.Repository
import com.example.roomtest.ui.utils.Category
import com.example.roomtest.ui.utils.Utils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class DetailsViewModel(
    private val itemId: Int,
    private val repository: Repository = Graph.repository
) : ViewModel() {

    var state by mutableStateOf(DetailsState())
        private set

    init {
        addListItem()
        getStores()
        if (itemId != -1){
            viewModelScope.launch {
                repository.getItemWithListAndStore(itemId).collectLatest {
                    state = state.copy(item = it?.item?.name ?: "-",
                        store = it?.store?.storeName ?: "-",
                        date = it?.item?.date ?: Date(),
                        category = Utils.category.find { c ->
                            c.id == it?.list?.id
                        } ?: Category(),
                        quantity = it?.item?.quantity ?: "-"
                    )

                }
            }
        }
    }

    init{
//        state = if (itemId != -1){
//            state.copy(isUpdatingItem = true)
//        }else {
//            state.copy(isUpdatingItem = false)
//
//        }

        state = state.copy(isUpdatingItem = itemId!= -1)
    }


    val isFieldNotEmpty: Boolean
        get() = state.item.isNotEmpty() &&
                state.store.isNotEmpty() &&
                state.quantity.isNotEmpty()

    fun onItemChange(newValue: String) {
        state = state.copy(item = newValue)
    }

    fun onStoreChange(newValue: String) {
        state = state.copy(store = newValue)
    }

    fun onDateChange(newValue: Date) {
        state = state.copy(date = newValue)
    }

    fun onCategoryChange(newValue: Category) {
        state = state.copy(category = newValue)
    }

    fun onQuantityChange(newValue: String) {
        state = state.copy(quantity = newValue)
    }

    fun onScreenDialogDismissed(newValue: Boolean) {
        state = state.copy(isScreenDialogDismissed = newValue)
    }

    private fun addListItem() {
        viewModelScope.launch {
            Utils.category.forEach {
                val shoppingList = ShoppingList(it.id, it.title)
                repository.insertList(shoppingList)
            }
        }
    }

    fun addShoppingItem() {
        viewModelScope.launch {
            repository.insertItem(
                Item(
                    name = state.item,
                    listIdFK = state.category.id,
                    quantity = state.quantity,
                    date = state.date,
                    storeIdFK = state.storeList.find { it.storeName == state.store }?.id ?: 0,
                    isChecked = false,
                    price = 0f

                )
            )
        }
    }

    fun updateShoppingItem(id: Int) {
        viewModelScope.launch {
            repository.updateItem(
                Item(
                    name = state.item,
                    listIdFK = state.category.id,
                    quantity = state.quantity,
                    date = state.date,
                    storeIdFK = state.storeList.find { it.storeName == state.store }?.id ?: 0,
                    isChecked = false,
                    price = 0f,
                    id = id
                )
            )
        }
    }

    fun addStore() {
        viewModelScope.launch {
            repository.insertStore(
                Store(
                    listIdFK = state.category.id,
                    storeName = state.store
                )
            )
        }
    }

    fun updateStore(id:Int){
        viewModelScope.launch {

        repository.updateStore(
            Store(
                id = state.storeList.find { it.storeName == state.store }?.id ?:0,
                listIdFK = state.category.id,
                storeName = state.store
            )
        )
        }
    }

    fun getStores(){
        viewModelScope.launch {
            repository.store.collectLatest {
                state = state.copy(storeList = it)
            }
        }
    }
}

class DetailsViewModelFactory(private val id:Int):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(id) as T
    }
}


data class DetailsState(
    val storeList: List<Store> = emptyList(),
    val item: String = "",
    val store: String = "",
    val date: Date = Date(),
    val quantity: String = "",
    val isScreenDialogDismissed: Boolean = true,
    val isUpdatingItem: Boolean = false,
    val category: Category = Category()
)