package fr.upjv.projet_coop.data.remote

import fr.upjv.projet_coop.data.model.MealResponseDto
import retrofit2.http.GET

interface MealEndpoint {
    @GET("random.php")
    suspend fun getRandomMeal(): MealResponseDto
}

