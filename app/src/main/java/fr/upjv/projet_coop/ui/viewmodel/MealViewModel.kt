package fr.upjv.projet_coop.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.upjv.projet_coop.data.model.MealData
import fr.upjv.projet_coop.data.repository.MealRepository
import fr.upjv.projet_coop.ui.model.MealUiItem
import fr.upjv.projet_coop.ui.model.MealUiState
import fr.upjv.projet_coop.ui.model.SortOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MealViewModel(
    private val mealRepository: MealRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MealUiState())
    val uiState: StateFlow<MealUiState> = _uiState.asStateFlow()

    init {
        loadMeals()
    }

    private fun loadMeals() {
        viewModelScope.launch {
            combine(
                mealRepository.getAllMeals(),
                _uiState
            ) { meals, state ->
                meals to state.sortOption
            }.collect { (meals, sortOption) ->
                processMeals(meals, sortOption)
            }
        }
    }

    private fun processMeals(meals: List<fr.upjv.projet_coop.data.model.MealData>, sortOption: SortOption) {
        val items = buildList {
            if (meals.isNotEmpty()) {
                when (sortOption) {
                    SortOption.DATE -> {
                        val groupedByDate = meals.groupBy { meal ->
                            formatDate(meal.timestamp)
                        }
                        
                        val sortedDates = groupedByDate.keys.sortedDescending()
                        
                        sortedDates.forEach { date ->
                            val mealsInDate = groupedByDate[date] ?: emptyList()
                            add(MealUiItem.Header(date))
                            
                            mealsInDate.forEach { meal ->
                                add(MealUiItem.MealItem(meal))
                            }
                            
                            add(MealUiItem.Footer(mealsInDate.size, date))
                        }
                    }
                    SortOption.COUNTRY -> {
                        val groupedByCountry = meals.groupBy { meal ->
                            meal.strArea ?: "Non spécifié"
                        }
                        
                        val sortedCountries = groupedByCountry.keys.sorted()
                        
                        sortedCountries.forEach { country ->
                            val mealsInCountry = groupedByCountry[country] ?: emptyList()
                            add(MealUiItem.Header(country))
                            
                            mealsInCountry.forEach { meal ->
                                add(MealUiItem.MealItem(meal))
                            }
                            
                            add(MealUiItem.Footer(mealsInCountry.size, country))
                        }
                    }
                    SortOption.CATEGORY -> {
                        val groupedByCategory = meals.groupBy { meal ->
                            meal.strCategory ?: "Non spécifié"
                        }
                        
                        val sortedCategories = groupedByCategory.keys.sorted()
                        
                        sortedCategories.forEach { category ->
                            val mealsInCategory = groupedByCategory[category] ?: emptyList()
                            add(MealUiItem.Header(category))
                            
                            mealsInCategory.forEach { meal ->
                                add(MealUiItem.MealItem(meal))
                            }
                            
                            add(MealUiItem.Footer(mealsInCategory.size, category))
                        }
                    }
                }
            }
        }
        
        _uiState.update { it.copy(items = items, error = null) }
    }

    fun setSortOption(sortOption: SortOption) {
        _uiState.update { it.copy(sortOption = sortOption) }
    }

    fun addRandomMeal() {
        Log.d("MealViewModel", "addRandomMeal called")
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                Log.d("MealViewModel", "Calling repository.addRandomMeal()")
                val result = mealRepository.addRandomMeal()
                result.onSuccess { meal ->
                    Log.d("MealViewModel", "Meal added successfully: ${meal.strMeal}")
                    _uiState.update { it.copy(isLoading = false) }
                }.onFailure { exception ->
                    Log.e("MealViewModel", "Error adding meal", exception)
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Erreur lors de l'ajout du repas"
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("MealViewModel", "Exception in addRandomMeal", e)
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Erreur lors de l'ajout du repas"
                    )
                }
            }
        }
    }

    fun deleteAllMeals() {
        viewModelScope.launch {
            mealRepository.deleteAllMeals()
        }
    }

    private fun formatDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH)
        return dateFormat.format(Date(timestamp))
    }
}

