package fr.upjv.projet_coop.data.repository

import fr.upjv.projet_coop.data.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Repository pour gérer les données
 * Adaptez selon les besoins de votre projet Swift
 */
class ItemRepository {
    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: Flow<List<Item>> = _items.asStateFlow()

    suspend fun getAllItems(): List<Item> {
        return _items.value
    }

    suspend fun getItemById(id: String): Item? {
        return _items.value.find { it.id == id }
    }

    suspend fun addItem(item: Item) {
        _items.value = _items.value + item
    }

    suspend fun updateItem(item: Item) {
        _items.value = _items.value.map { if (it.id == item.id) item else it }
    }

    suspend fun deleteItem(id: String) {
        _items.value = _items.value.filter { it.id != id }
    }
}



