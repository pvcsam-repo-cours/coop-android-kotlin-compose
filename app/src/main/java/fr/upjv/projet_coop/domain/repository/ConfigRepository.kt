package fr.upjv.projet_coop.domain.repository

import fr.upjv.projet_coop.domain.model.AppConfig
import kotlinx.coroutines.flow.StateFlow

interface ConfigRepository {
    val config: StateFlow<AppConfig>
    fun refreshConfig()
}
