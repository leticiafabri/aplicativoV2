// ../ui/components/AppNavegador.kt
package br.pucpr.aplicativov2.ui.components

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.pucpr.aplicativov2.ui.PessoasViewModel
import br.pucpr.aplicativov2.ui.screens.PrimeiraTela
import br.pucpr.aplicativov2.ui.screens.SegundaTela

@Composable
fun AppNavegador() {
    val nav = rememberNavController()
    val vm: PessoasViewModel = hiltViewModel()
    val pessoas by vm.pessoas.collectAsStateWithLifecycle()

    // estado simples para editar/criar
    var editandoId by rememberSaveable { mutableStateOf<Long?>(null) }

    NavHost(navController = nav, startDestination = "lista") {
        composable("lista") {
            PrimeiraTela(
                pessoas = pessoas,
                onAddClick = { editandoId = null; nav.navigate("cadastro") },
                onItemClick = { p -> editandoId = p.id; nav.navigate("cadastro") },
                onItemLongPress = { p -> vm.excluir(p) }
            )
        }
        composable("cadastro") {
            val pessoa = pessoas.firstOrNull { it.id == editandoId }
            SegundaTela(
                pessoa = pessoa,
                onSalvar = { nome, idade ->
                    vm.salvar(editandoId, nome, idade)
                    nav.popBackStack()
                },
                onCancelar = { nav.popBackStack() }
            )
        }
    }
}
