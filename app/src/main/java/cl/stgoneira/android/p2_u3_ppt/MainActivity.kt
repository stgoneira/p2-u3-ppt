package cl.stgoneira.android.p2_u3_ppt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cl.stgoneira.android.p2_u3_ppt.ui.ListaGastosViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.stgoneira.android.p2_u3_ppt.data.Gasto
import java.time.LocalDate


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppGastosUI()
        }
    }
}

@Composable
fun AppGastosUI(
    vmListaGastos: ListaGastosViewModel = viewModel(factory = ListaGastosViewModel.Factory)
) {
    // se ejecuta 1 vez al iniciar el composable
    LaunchedEffect(Unit) {
        vmListaGastos.obtenerGastos()
    }

    LazyColumn {
        items(vmListaGastos.gastos) {
            Text(it.descripcion)
        }
        item {
            Button(onClick = {
                vmListaGastos.insertarGasto( Gasto(null, 30_000, LocalDate.now(), "SALUD", "Veterinario") )
            }) {
                Text("Agregar")
            }
        }
    }
}