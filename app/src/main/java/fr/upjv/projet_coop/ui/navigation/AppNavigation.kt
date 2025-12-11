package fr.upjv.projet_coop.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import fr.upjv.projet_coop.architecture.getCustomApplication
import fr.upjv.projet_coop.ui.screen.HomeScreen
import fr.upjv.projet_coop.ui.screen.MealDetailScreen
import fr.upjv.projet_coop.ui.screen.MealListScreen
import fr.upjv.projet_coop.ui.viewmodel.MealViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val application = context.applicationContext.getCustomApplication()

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
            val viewModel: MealViewModel = viewModel {
                MealViewModel(application.mealRepository)
            }
            MealListScreen(
                viewModel = viewModel,
                onMealClick = { mealId ->
                    navController.navigate(AppDestinations.MealDetail(mealId))
                }
            )
        }

        composable<AppDestinations.MealDetail> { backStackEntry ->
            val mealDetail = backStackEntry.toRoute<AppDestinations.MealDetail>()
            MealDetailScreen(
                mealId = mealDetail.mealId,
                mealRepository = application.mealRepository,
                onBack = { navController.popBackStack() }
            )
        }

        composable<AppDestinations.Feature3> {
            fr.upjv.projet_coop.ui.screen.Feature3Screen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
