package fr.upjv.projet_coop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.upjv.projet_coop.data.dao.MealDao
import fr.upjv.projet_coop.data.model.MealEntity

@Database(
    entities = [MealEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
}
