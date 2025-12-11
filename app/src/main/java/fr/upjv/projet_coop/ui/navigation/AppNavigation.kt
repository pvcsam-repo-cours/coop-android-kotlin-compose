package fr.upjv.projet_coop.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.upjv.projet_coop.ui.screen.HomeScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.Home,
        modifier = modifier
    ) {
        composable<AppDestinations.Home> {
            HomeScreen(
                onNavigateToFeature2 = { navController.navigate(AppDestinations.Feature2) },
                onNavigateToFeature3 = { navController.navigate(AppDestinations.Feature3) }
            )
        }

        composable<AppDestinations.Feature2> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Feature 2 Screen (To be implemented)")
            }
        }
        
        composable<AppDestinations.Feature3> {
            fr.upjv.projet_coop.ui.screen.Feature3Screen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
