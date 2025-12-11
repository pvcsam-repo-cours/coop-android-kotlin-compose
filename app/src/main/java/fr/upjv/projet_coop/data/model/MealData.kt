package fr.upjv.projet_coop.data.model

data class MealData(
    val idMeal: String,
    val strMeal: String?,
    val strMealAlternate: String?,
    val strCategory: String?,
    val strArea: String?,
    val strInstructions: String?,
    val strMealThumb: String?,
    val strTags: String?,
    val strYoutube: String?,
    val ingredients: List<IngredientMeasure>,
    val strSource: String?,
    val strImageSource: String?,
    val strCreativeCommonsConfirmed: String?,
    val dateModified: String?,
    val timestamp: Long
)

data class IngredientMeasure(
    val ingredient: String,
    val measure: String
)


