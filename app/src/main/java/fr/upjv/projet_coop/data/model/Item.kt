package fr.upjv.projet_coop.data.model

/**
 * Modèle de données de base pour un élément
 * Adaptez ce modèle selon les besoins de votre projet Swift
 */
data class Item(
    val id: String,
    val title: String,
    val description: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)



