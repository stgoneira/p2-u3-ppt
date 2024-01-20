package cl.stgoneira.android.p2_u3_ppt

import android.app.Application
import androidx.room.Room
import cl.stgoneira.android.p2_u3_ppt.data.BaseDatos
import cl.stgoneira.android.p2_u3_ppt.data.UbicacionRepository
import com.google.android.gms.location.LocationServices

class Aplicacion : Application() {

    val db by lazy { Room.databaseBuilder(this, BaseDatos::class.java, "gastos.db").build() }
    val gastoDao by lazy { db.gastoDao() }
    val locationService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    val ubicacionRepository by lazy { UbicacionRepository(locationService) }

}