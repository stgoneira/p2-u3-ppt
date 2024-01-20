package cl.stgoneira.android.p2_u3_ppt.ui.composables

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.stgoneira.android.p2_u3_ppt.data.Gasto
import cl.stgoneira.android.p2_u3_ppt.ui.ListaGastosViewModel


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
fun PantallaFormGasto(
    onSaveGasto: (gasto:Gasto) -> Unit = {},
    vmListaGastos: ListaGastosViewModel = viewModel(factory = ListaGastosViewModel.Factory)
) {
    val contexto = LocalContext.current

    var monto by rememberSaveable { mutableIntStateOf(0) }
    var fecha by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }

    val lanzadorPermisosUbicacion =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = {
                if ( it.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
                    ||
                    it.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
                ) {
                    // permisos ok, recuperar ubicación
                    vmListaGastos.refrescarUbicacion()
                } else {
                    // mostrar mensaje error,
                    // explicación permisos requeridos
                }
            }
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        // monto, fecha, descripcion
        TextField(
            value = fecha,
            onValueChange = { fecha = it},
            placeholder = { Text("2024-12-25") },
            label = { Text("Fecha") }
        )
        TextField(
            value = monto.toString(),
            onValueChange = { monto = it.toIntOrNull() ?: 0},
            label = { Text("Monto") }
        )
        Button(onClick = {
            lanzadorPermisosUbicacion.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                )
            )
        }) {
            Text("Ubicación")
        }
        vmListaGastos.ubicacion?.let {
            Text("Lat: ${it.latitude} | Long: ${it.longitude}")
        }
        Text("Categoría:")
        OpcionesCategoriasUI()
        TextField(
            value = descripcion.toString(),
            onValueChange = { descripcion = it },
            label = { Text("Glosa") }
        )
        Button(onClick = {

        }) {
            Text("Guardar registro de gasto")
        }
    }
}