package fr.upjv.projet_coop.domain.model

// Domain Model for Configuration (Independent of Firebase)
data class AppConfig(
    val backgroundColor: String = "#FFFFFF", // Default White
    val welcomeText: String = "Welcome to Feature 3",
    // Flash Promotion Fields
    val isPromoActive: Boolean = false,
    val promoTitle: String = "",
    val promoCode: String = "",
    val promoImageUrl: String = ""
)
