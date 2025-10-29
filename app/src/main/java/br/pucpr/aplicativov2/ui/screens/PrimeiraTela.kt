// ../ui/screens/PrimeiraTela.kt
package br.pucpr.aplicativov2.ui.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
    Scaffold(
        topBar = { TopAppBar(title = { Text("Pessoas") }) },
        floatingActionButton = { FloatingActionButton(onClick = onAddClick) { Text("+") } }
    ) { inner ->
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = inner
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
                                onLongPress = { onItemLongPress(pessoa) }
                            )
                        }
                )
                HorizontalDivider(thickness = 0.5.dp)
            }
        }
    }
}