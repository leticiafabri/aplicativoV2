// ../ui/components/AppNavegador.kt (usando UiState)
package br.pucpr.aplicativov2.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.*
import br.pucpr.aplicativov2.data.Pessoa
import br.pucpr.aplicativov2.ui.*
import br.pucpr.aplicativov2.ui.screens.PrimeiraTela
import br.pucpr.aplicativov2.ui.screens.SegundaTela

@Composable
fun AppNavegador() {
    val nav = rememberNavController()
    val vm: PessoasViewModel = hiltViewModel()
    val uiState by vm.ui.collectAsStateWithLifecycle()

    var editandoId by rememberSaveable { mutableStateOf<Long?>(null) }
    val snackbar = remember { SnackbarHostState() }

    // exibir mensagens de Ready.message
    LaunchedEffect(uiState) {
        val msg = (uiState as? PessoasUiState.Ready)?.message
        if (!msg.isNullOrBlank()) {
            snackbar.showSnackbar(msg)
            vm.consumirMensagem()
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbar) }) { inner ->
        NavHost(
            navController = nav,
            startDestination = "lista",
            modifier = androidx.compose.ui.Modifier.padding(inner)
        ) {
            composable("lista") {
                when (val st = uiState) {
                    PessoasUiState.Loading -> LoadingScreen()
                    is PessoasUiState.Error -> ErrorScreen(
                        message = st.message,
                        onRetry = { /* poderia reacionar, ex.: reiniciar coleta se necessário */ }
                    )
                    is PessoasUiState.Ready -> PrimeiraTela(
                        pessoas = st.pessoas,
                        onAddClick = { editandoId = null; nav.navigate("cadastro") },
                        onItemClick = { p -> editandoId = p.id; nav.navigate("cadastro") },
                        onItemLongPress = { p -> vm.excluir(p) }
                    )
                }
            }
            composable("cadastro") {
                val ready = uiState as? PessoasUiState.Ready
                val pessoa = ready?.pessoas?.firstOrNull { it.id == editandoId }
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
}

// Telas auxiliares simples (mínimas)
@Composable fun LoadingScreen() { androidx.compose.material3.CircularProgressIndicator() }
@Composable fun ErrorScreen(message: String, onRetry: () -> Unit) {
    androidx.compose.material3.Text(text = "Erro: $message")
}
