package fr.upjv.projet_coop.data.remote

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

// Data Source responsible for interacting with Firebase Remote Config SDK
class FirebaseDataSource {

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

    init {
        val configSettings = remoteConfigSettings {
            // Interval requested by user
            minimumFetchIntervalInSeconds = 10
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    // Real-time config updates flow
    fun fetchConfig(): Flow<Map<String, String>> = callbackFlow {
        
        fun emitCurrentConfig() {
            val result = mapOf(
                "feature3_background_color" to remoteConfig.getString("feature3_background_color"),
                "feature3_welcome_text" to remoteConfig.getString("feature3_welcome_text"),
                "feature3_promo_active" to remoteConfig.getString("feature3_promo_active"),
                "feature3_promo_title" to remoteConfig.getString("feature3_promo_title"),
                "feature3_promo_code" to remoteConfig.getString("feature3_promo_code"),
                "feature3_promo_image_url" to remoteConfig.getString("feature3_promo_image_url")
            )
            trySend(result)
        }

        // 1. Initial Fetch
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("FirebaseDataSource", "Initial fetch successful")
                emitCurrentConfig()
            } else {
                Log.e("FirebaseDataSource", "Initial fetch failed")
                // Still try to emit cached values if any
                emitCurrentConfig()
            }
        }

        // 2. Real-time Listener
        val registration = remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.d("FirebaseDataSource", "Config updated! Keys: ${configUpdate.updatedKeys}")
                remoteConfig.activate().addOnCompleteListener {
                    emitCurrentConfig()
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.w("FirebaseDataSource", "Config update error: code=${error.code}, message=${error.message}")
            }
        })

        awaitClose {
            registration.remove()
        }
    }
}
