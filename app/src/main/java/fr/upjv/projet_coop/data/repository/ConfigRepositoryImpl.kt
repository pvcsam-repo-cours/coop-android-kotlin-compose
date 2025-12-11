package fr.upjv.projet_coop.data.repository

import fr.upjv.projet_coop.data.remote.FirebaseDataSource
import fr.upjv.projet_coop.domain.model.AppConfig
import fr.upjv.projet_coop.domain.repository.ConfigRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConfigRepositoryImpl(
    private val dataSource: FirebaseDataSource
) : ConfigRepository {

    private val _config = MutableStateFlow(AppConfig())
    override val config: StateFlow<AppConfig> = _config.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        refreshConfig()
    }

    override fun refreshConfig() {
        scope.launch {
            dataSource.fetchConfig().collect { dataMap ->
                // Map Data DTO (Map) to Domain Model (AppConfig)
                // Use defaults if keys are missing from remote
                if (dataMap.isNotEmpty()) {
                    val newConfig = AppConfig(
                        backgroundColor = dataMap["feature3_background_color"]?.takeIf { it.isNotBlank() } ?: "#FFFFFF",
                        welcomeText = dataMap["feature3_welcome_text"]?.takeIf { it.isNotBlank() } ?: "Welcome to Feature 3",
                        isPromoActive = dataMap["feature3_promo_active"]?.toBooleanStrictOrNull() ?: false,
                        promoTitle = dataMap["feature3_promo_title"]?.takeIf { it.isNotBlank() } ?: "Special Offer",
                        promoCode = dataMap["feature3_promo_code"]?.takeIf { it.isNotBlank() } ?: "PROMO",
                        // Default food image if empty
                        promoImageUrl = dataMap["feature3_promo_image_url"]?.takeIf { it.isNotBlank() } 
                            ?: "https://images.unsplash.com/photo-1504674900247-0877df9cc836?q=80&w=1000&auto=format&fit=crop" 
                    )
                    _config.value = newConfig
                }
            }
        }
    }
}
