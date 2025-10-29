// ../ui/screens/PrimeiraTela.kt
package br.pucpr.aplicativov2.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.pucpr.aplicativov2.data.Pessoa

@Composable
fun PrimeiraTela(
    pessoas: List<Pessoa>
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(pessoas, key = { it.id }) { pessoa ->
            ListItem(
                headlineContent = { Text(pessoa.nome) },
                supportingContent = { Text("Idade: ${pessoa.idade}") },
            )
            HorizontalDivider(thickness = 0.5.dp)
        }
    }
}