package fr.upjv.projet_coop.architecture

import android.app.Application
import fr.upjv.projet_coop.domain.repository.ConfigRepository
import fr.upjv.projet_coop.data.repository.ConfigRepositoryImpl

class CustomApplication : Application() {

    companion object {
        lateinit var instance: CustomApplication
            private set
    }
    
    // Manual DI for Repository
    // Will be initialized in onCreate
    lateinit var configRepository: ConfigRepository
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        // Initialize Repository
        val dataSource = fr.upjv.projet_coop.data.remote.FirebaseDataSource()
        configRepository = ConfigRepositoryImpl(dataSource)
    }
}
