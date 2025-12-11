package fr.upjv.projet_coop.ui.model

import fr.upjv.projet_coop.data.model.MealData

sealed interface MealUiItem {
    data class Header(val title: String) : MealUiItem
    data class MealItem(val meal: MealData) : MealUiItem
    data class Footer(val itemCount: Int, val categoryTitle: String) : MealUiItem
}

data class MealUiState(
    val items: List<MealUiItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val sortOption: SortOption = SortOption.COUNTRY
)

