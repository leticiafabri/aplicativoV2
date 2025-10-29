// ../ui/components/AppNavegador.kt
package br.pucpr.aplicativov2.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import br.pucpr.aplicativov2.data.Pessoa
import br.pucpr.aplicativov2.ui.screens.PrimeiraTela

@Composable
fun AppNavegador() {
    val pessoas = remember {
        mutableStateListOf(
            Pessoa(id = 1, nome = "Ana", idade = 22),
            Pessoa(id = 2, nome = "Bruno", idade = 31),
            Pessoa(id = 3, nome = "Carla", idade = 27),
        )
    }
    PrimeiraTela(pessoas = pessoas)
}