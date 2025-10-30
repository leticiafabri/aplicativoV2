// ../ui/screens/PrimeiraTela.kt
package br.pucpr.aplicativov2.ui.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import br.pucpr.aplicativov2.data.Pessoa

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimeiraTela(
    pessoas: List<Pessoa>,
    onAddClick: () -> Unit,
    onItemClick: (Pessoa) -> Unit,
    onItemLongPress: (Pessoa) -> Unit
) {
    // Estado da pessoa selecionada e exibição do diálogo
    var pessoaSelecionada by remember { mutableStateOf<Pessoa?>(null) }
    var mostrarDialog by remember { mutableStateOf(false) }

    // Scaffold com TopAppBar e FAB
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Pessoas") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = innerPadding
        ) {
            items(pessoas, key = { it.id }) { pessoa ->
                ListItem(
                    headlineContent = { Text(pessoa.nome) },
                    supportingContent = { Text("Idade: ${pessoa.idade}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = { onItemClick(pessoa) },
                                onLongPress = {
                                    pessoaSelecionada = pessoa
                                    mostrarDialog = true
                                }
                            )
                        }
                )
                HorizontalDivider(thickness = 0.5.dp)
            }
        }

        // Diálogo de confirmação de exclusão
        if (mostrarDialog && pessoaSelecionada != null) {
            AlertDialog(
                onDismissRequest = { mostrarDialog = false },
                title = { Text("Excluir pessoa") },
                text = { Text("Tem certeza que deseja excluir ${pessoaSelecionada!!.nome}?") },
                confirmButton = {
                    TextButton(onClick = {
                        onItemLongPress(pessoaSelecionada!!)
                        mostrarDialog = false
                    }) {
                        Text("Excluir")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
