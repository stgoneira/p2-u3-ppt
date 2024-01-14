package cl.stgoneira.android.p2_u3_ppt.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Gasto(
    @PrimaryKey(autoGenerate = true) var id:Long? = null,
    var monto:Long,
    var fecha: LocalDate,
    var categoria: String,
    var descripcion: String
)