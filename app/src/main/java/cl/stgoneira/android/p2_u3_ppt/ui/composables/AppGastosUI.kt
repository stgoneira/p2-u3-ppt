package cl.stgoneira.android.p2_u3_ppt.ui.composables

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.stgoneira.android.p2_u3_ppt.ui.ListaGastosViewModel

@Composable
fun AppGastosUI(
    navController: NavHostController = rememberNavController(),
    vmListaGastos: ListaGastosViewModel = viewModel(factory = ListaGastosViewModel.Factory)
) {
    NavHost(
        navController = navController,
        startDestination = "form")
    {
        composable("inicio") {
            PantallaListaGastos(
                gastos = vmListaGastos.gastos,
                onAdd = { navController.navigate("form") }
            )
        }
        composable("form") {
            PantallaFormGasto()
        }
    }
}