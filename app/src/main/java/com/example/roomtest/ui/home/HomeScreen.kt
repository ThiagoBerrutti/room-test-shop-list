package com.example.roomtest.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomtest.database.entities.Item
import com.example.roomtest.database.entities.ItemWithStoreAndList
import com.example.roomtest.ui.theme.Shapes
import com.example.roomtest.ui.utils.Utils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun HomeScreen(onNavigate: (Int) -> Unit) {
    val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
    val homeState = homeViewModel.state

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigate(-1) }) {
                Icon(imageVector = Icons.Default.Add, null)
            }
        }
    ) {padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            item {
                LazyRow {
                    items(Utils.category) { category ->

                        CategoryItem(
                            iconRes = category.resId,
                            title = category.title,
                            selected = category == homeState.category
                        ) {
                            homeViewModel.onCategoryChange(category)
                        }
                        Spacer(modifier = Modifier.size(16.dp))

                    }
                }
            }
            items(homeState.items) { itemWithStoreAndList ->
                ShoppingItem(
                    item = itemWithStoreAndList,
                    isChecked = itemWithStoreAndList.item.isChecked,
                    onCheckedChange = homeViewModel::onItemCheckedChange
                ) {
                    onNavigate.invoke(itemWithStoreAndList.item.id)
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    @DrawableRes iconRes: Int,
    title: String,
    selected: Boolean,
    onItemClick: () -> Unit
) {
    val mutableInteractionSource = remember { MutableInteractionSource() }
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
            .clip(Shapes.large)
            .selectable(selected = selected,
                interactionSource = mutableInteractionSource,
                indication = rememberRipple(),
                onClick = { onItemClick.invoke() }
            ),

        border = BorderStroke(
            1.dp,
            if (selected) MaterialTheme.colorScheme.primary.copy(.5f)
            else MaterialTheme.colorScheme.primary
        ),
        shape = Shapes.large,
        colors = CardDefaults.cardColors()
            .copy(
                containerColor = if (selected) MaterialTheme.colorScheme.primary.copy(.5f)
                else MaterialTheme.colorScheme.surface,
                contentColor = if (selected) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurface
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                title, style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )

        }

    }

}

@Composable
fun ShoppingItem(
    item: ItemWithStoreAndList,
    isChecked: Boolean,
    onCheckedChange: (Item, Boolean) -> Unit,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke()
            }
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = item.item.name ?: "-",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(item.store.storeName)
                Spacer(modifier = Modifier.height(4.dp))
                Text(formatDate(item.item.date), style = MaterialTheme.typography.titleMedium)


            }
            Column(modifier = Modifier.padding(8.dp)){
                Text(
                    "Qty: ${item.item.quantity}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Checkbox(
                    checked = item.item.isChecked,
                    onCheckedChange = {onCheckedChange.invoke(item.item, it)}
                )
            }
        }
    }

}
    
fun formatDate(date: Date):String =
    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)
