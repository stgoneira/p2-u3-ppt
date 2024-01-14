package cl.stgoneira.android.p2_u3_ppt.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cl.stgoneira.android.p2_u3_ppt.Aplicacion
import cl.stgoneira.android.p2_u3_ppt.data.Gasto
import cl.stgoneira.android.p2_u3_ppt.data.GastoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ListaGastosViewModel(private val gastoDao:GastoDao) : ViewModel() {

    var gastos by mutableStateOf(listOf<Gasto>())

    fun insertarGasto(gasto:Gasto) {
        viewModelScope.launch(Dispatchers.IO) {
            gastoDao.insertar(gasto)
            obtenerGastos()
        }
    }

    fun obtenerGastos(): List<Gasto> {
        viewModelScope.launch(Dispatchers.IO) {
            gastos = gastoDao.obtenerTodos()
        }
        return gastos
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val aplicaion = (this[APPLICATION_KEY] as Aplicacion)
                ListaGastosViewModel(aplicaion.gastoDao)
            }
        }
    }
}