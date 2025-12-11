package fr.upjv.projet_coop.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.upjv.projet_coop.architecture.CustomApplication
import fr.upjv.projet_coop.domain.repository.ConfigRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// UI State for Feature 3
data class Feature3UiState(
    val backgroundColor: Color = Color.White,
    val welcomeText: String = "Loading...",
    val isPromoActive: Boolean = false,
    val promoTitle: String = "",
    val promoCode: String = "",
    val promoImageUrl: String = ""
)

class Feature3ViewModel : ViewModel() {

    // Manual DI: Get repository from Application
    private val repository: ConfigRepository = CustomApplication.instance.configRepository

    // Transform Domain Model (AppConfig) -> UI State
    val uiState: StateFlow<Feature3UiState> = repository.config
        .map { config ->
            Feature3UiState(
                backgroundColor = parseColorSafe(config.backgroundColor),
                welcomeText = config.welcomeText,
                isPromoActive = config.isPromoActive,
                promoTitle = config.promoTitle,
                promoCode = config.promoCode,
                promoImageUrl = config.promoImageUrl
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Feature3UiState()
        )

    fun refreshConfig() {
        repository.refreshConfig()
    }

    private fun parseColorSafe(colorString: String): Color {
        var trimmedColor = colorString.trim()
        android.util.Log.d("Feature3ViewModel", "Parsing color: '$trimmedColor'")
        
        // Fix: Auto-prepend '#' if missing
        if (trimmedColor.isNotEmpty() && !trimmedColor.startsWith("#")) {
            trimmedColor = "#$trimmedColor"
        }

        return try {
            Color(android.graphics.Color.parseColor(trimmedColor))
        } catch (e: Exception) {
            android.util.Log.e("Feature3ViewModel", "Failed to parse color: '$trimmedColor'", e)
            Color.White // Fallback
        }
    }
}
