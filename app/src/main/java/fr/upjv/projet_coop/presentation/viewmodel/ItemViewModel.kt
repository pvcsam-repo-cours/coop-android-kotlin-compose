package fr.upjv.projet_coop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.upjv.projet_coop.data.model.Item
import fr.upjv.projet_coop.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel pour gérer l'état de l'UI
 */
class ItemViewModel(
    private val repository: ItemRepository = ItemRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<ItemUiState>(ItemUiState())
    val uiState: StateFlow<ItemUiState> = _uiState.asStateFlow()

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            repository.items.collect { items ->
                _uiState.value = _uiState.value.copy(
                    items = items,
                    isLoading = false
                )
            }
        }
    }

    fun addItem(title: String, description: String? = null) {
        viewModelScope.launch {
            val newItem = Item(
                id = System.currentTimeMillis().toString(),
                title = title,
                description = description
            )
            repository.addItem(newItem)
        }
    }

    fun deleteItem(id: String) {
        viewModelScope.launch {
            repository.deleteItem(id)
        }
    }

    fun updateItem(item: Item) {
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }
}

data class ItemUiState(
    val items: List<Item> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)



