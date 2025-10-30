package br.pucpr.aplicativov2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import br.pucpr.aplicativov2.ui.components.AppNavegador
import br.pucpr.aplicativov2.ui.theme.AplicativoV2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicativoV2Theme {
                AppNavegador()
            }
        }
    }
}
