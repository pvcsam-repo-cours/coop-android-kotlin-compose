package fr.upjv.projet_coop.data.repository

import android.util.Log
import fr.upjv.projet_coop.data.dao.MealDao
import fr.upjv.projet_coop.data.mapper.toData
import fr.upjv.projet_coop.data.mapper.toEntity
import fr.upjv.projet_coop.data.model.MealData
import fr.upjv.projet_coop.data.remote.MealEndpoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MealRepository(
    private val mealEndpoint: MealEndpoint,
    private val mealDao: MealDao
) {
    fun getAllMeals(): Flow<List<MealData>> {
        return mealDao.getAllMeals().map { entities ->
            entities.map { it.toData() }
        }
    }

    suspend fun getMealById(id: String): MealData? {
        return mealDao.getMealById(id)?.toData()
    }

    suspend fun addRandomMeal(): Result<MealData> {
        return try {
            Log.d("MealRepository", "Calling API endpoint...")
            val response = mealEndpoint.getRandomMeal()
            Log.d("MealRepository", "API reponse received: ${response.meals?.size} meals")
            val mealDto = response.meals?.firstOrNull()
            
            if (mealDto != null && !mealDto.idMeal.isNullOrBlank()) {
                Log.d("MealRepository", "meal foud: ${mealDto.strMeal}, Id: ${mealDto.idMeal}")
                val entity = mealDto.toEntity()
                Log.d("MealRepository", "Inserting meal into database ...")
                mealDao.insertMeal(entity)
                Log.d("MealRepository", "Meal inserted successfully")
                Result.success(entity.toData())
            } else {
                Log.e("MealRepository", "No meal found in api response")
                Result.failure(Exception("No meal found in API respoonse"))
            }
        } catch (e: Exception) {
            Log.e("MealRepository", "Error in addRandomMeal", e)
            Result.failure(e)
        }
    }

    suspend fun deleteAllMeals() {
        mealDao.deleteAllMeals()
    }
}

