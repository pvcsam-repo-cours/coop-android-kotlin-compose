package fr.upjv.projet_coop.architecture

import android.app.Application
import androidx.room.Room
import fr.upjv.projet_coop.architecture.AppDatabase
import fr.upjv.projet_coop.data.repository.ConfigRepositoryImpl
import fr.upjv.projet_coop.data.repository.MealRepository
import fr.upjv.projet_coop.domain.repository.ConfigRepository


class CustomApplication : Application() {
    private val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    companion object {
        lateinit var instance: CustomApplication
            private set
    }

    lateinit var configRepository: ConfigRepository
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        val dataSource = fr.upjv.projet_coop.data.remote.FirebaseDataSource()
        configRepository = ConfigRepositoryImpl(dataSource)
    }

    val mealRepository: MealRepository by lazy {
        MealRepository(
            mealEndpoint = RetrofitBuilder.mealEndpoint,
            mealDao = database.mealDao()
        )
    }
}
