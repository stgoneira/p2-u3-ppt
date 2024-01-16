package cl.stgoneira.android.p2_u3_ppt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.stgoneira.android.p2_u3_ppt.data.Gasto
import cl.stgoneira.android.p2_u3_ppt.ui.ListaGastosViewModel

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
    navController: NavHostController = rememberNavController(),
    vmListaGastos: ListaGastosViewModel = viewModel(factory = ListaGastosViewModel.Factory)
) {
    NavHost(
        navController = navController,
        startDestination = "inicio")
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

@Composable
fun OpcionesCategoriasUI() {
    val categorias = listOf("Salud", "Hogar", "Alimentación", "Diversión")
    var categoriaSeleccionada by rememberSaveable { mutableStateOf( categorias[0]) }
    Column(Modifier.selectableGroup()) {
        categorias.forEach { categoria ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (categoria == categoriaSeleccionada),
                        onClick = { categoriaSeleccionada = categoria },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (categoria == categoriaSeleccionada),
                    onClick = null // null recommended for accessibility with screenreaders
                )
                Text(
                    text = categoria,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun PantallaFormGasto() {
    var monto by rememberSaveable { mutableIntStateOf(0) }
    var fecha by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        // monto, fecha, descripcion
        TextField(
            value = fecha,
            onValueChange = { fecha = it},
            placeholder = {Text("2024-12-25")},
            label = { Text("Fecha")}
        )
        TextField(
            value = monto.toString(),
            onValueChange = { monto = it.toIntOrNull() ?: 0},
            label = { Text("Monto")}
        )
        Text("Categoría:")
        OpcionesCategoriasUI()
        TextField(
            value = descripcion.toString(),
            onValueChange = { descripcion = it },
            label = { Text("Glosa")}
        )
        Button(onClick = {

        }) {
            Text("Guardar registro de gasto")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PantallaListaGastos(
    gastos: List<Gasto> = listOf(),
    onAdd:() -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAdd()
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Agregar registro de gasto")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(vertical = it.calculateTopPadding())
        ) {
            items(gastos) {
                Text(it.descripcion)
            }
        }
    }

}