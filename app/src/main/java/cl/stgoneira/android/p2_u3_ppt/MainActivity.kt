package cl.stgoneira.android.p2_u3_ppt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cl.stgoneira.android.p2_u3_ppt.ui.composables.AppGastosUI

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppGastosUI()
        }
    }
}

