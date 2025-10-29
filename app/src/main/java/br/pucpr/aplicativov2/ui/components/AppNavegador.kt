// ../ui/components/AppNavegador.kt
package br.pucpr.aplicativov2.ui.components

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import br.pucpr.aplicativov2.data.Pessoa
import br.pucpr.aplicativov2.ui.screens.SegundaTela
import br.pucpr.aplicativov2.ui.screens.

enum class Tela { LISTA, CADASTRO }

@Composable
fun AppNavegador() {

    var tela by rememberSaveable { mutableStateOf(Tela.CADASTRO) }

    val pessoas = remember {
        mutableStateListOf(
            Pessoa(id = 1, nome = "Ana", idade = 22),
            Pessoa(id = 2, nome = "Bruno", idade = 31),
            Pessoa(id = 3, nome = "Carla", idade = 27),
        )
    }

    when (tela) {
        Tela.LISTA -> {
            PrimeiraTela(pessoas = pessoas)
        }
        Tela.CADASTRO -> {
            SegundaTela()
        }
    }
}
