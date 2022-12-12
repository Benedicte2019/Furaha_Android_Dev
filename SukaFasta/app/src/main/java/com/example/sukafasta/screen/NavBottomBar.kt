import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sukafasta.screen.*
import com.example.sukafasta.R
import com.example.sukafasta.model.AppointmentViewModel
import com.example.sukafasta.ui.theme.primaryColor

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun NavBottomBar(viewModel: AppointmentViewModel) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.bookAppointment))
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                contentColor = Color.White,
                backgroundColor = primaryColor,

                )
        },
        content = { NavigationHandler(navController = navController, viewModel)},
        bottomBar = { NewBottomBar(navController = navController) }
    )
}

// function to handle navigation
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun NavigationHandler(
    navController: NavHostController, viewModel: AppointmentViewModel
){
    NavHost(
        navController = navController,
        startDestination = Routes.Booking.route
    ){
        // Home composable
//        composable(Routes.Home.route){
//            Home()
//        }

        // Appointment composable
        composable(Routes.Booking.route){
            Booking(viewModel)
        }

//        // Account composable
//        composable(Routes.Account.route){
//            Account()
//        }

        composable(Routes.AddService.route){
            AddService()
        }

        composable(Routes.Appointments.route){
            ClientAppointmentsScreen(viewModel.appointmentList, {viewModel.deleteAppointment(it)})
        }
    }
}

// new BottomBar for testing new implementation
@Composable
fun NewBottomBar(navController: NavHostController){
    BottomNavigation {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        // handling state for each item selected on bottom bar
        BottomNavItems.BottomItems.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                icon = {
                    Icon(imageVector = navItem.image,
                        contentDescription = navItem.title)
                },
                label = {
                    Text(text = navItem.title)
                },
            )
        }

    }
}