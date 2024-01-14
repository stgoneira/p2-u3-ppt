package cl.stgoneira.android.p2_u3_ppt

import android.app.Application
import androidx.room.Room
import cl.stgoneira.android.p2_u3_ppt.data.BaseDatos

class Aplicacion : Application() {

    val db by lazy { Room.databaseBuilder(this, BaseDatos::class.java, "gastos.db").build() }
    val gastoDao by lazy { db.gastoDao() }

}