package fr.upjv.projet_coop.domain.model

data class AppConfig(
    val backgroundColor: String = "#FFFFFF",
    val welcomeText: String = "Welcome to Feature 3",
    val isPromoActive: Boolean = false,
    val promoTitle: String = "",
    val promoCode: String = "",
    val promoImageUrl: String = ""
)
