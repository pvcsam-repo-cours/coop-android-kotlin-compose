package fr.upjv.projet_coop.architecture

import fr.upjv.projet_coop.data.remote.MealEndpoint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val mealEndpoint: MealEndpoint = retrofit.create(MealEndpoint::class.java)
}
