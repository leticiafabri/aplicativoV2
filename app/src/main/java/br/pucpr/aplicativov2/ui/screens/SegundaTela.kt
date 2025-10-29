// ../ui/screens/SegundaTela.kt
package br.pucpr.aplicativov2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.pucpr.aplicativov2.data.Pessoa

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegundaTela(
    pessoa: Pessoa?, // null = criando, não-null = editando
    onSalvar: (nome: String, idade: Int) -> Unit,
    onCancelar: () -> Unit
) {

    var nome by rememberSaveable { mutableStateOf(pessoa?.nome ?: "") }
    var idadeTexto by rememberSaveable { mutableStateOf(pessoa?.idade?.toString() ?: "") }
    val isValid = nome.isNotBlank() && idadeTexto.toIntOrNull() != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (pessoa == null) "Nova Pessoa" else "Editar Pessoa") },
                navigationIcon = { TextButton(onClick = onCancelar) { Text("Voltar") } },
                actions = {
                    TextButton(
                        enabled = isValid,
                        onClick = {
                            val idade = idadeTexto.toIntOrNull() ?: 0
                            onSalvar(nome.trim(), idade)
                        }
                    ) { Text("Salvar") }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = idadeTexto,
                onValueChange = { idadeTexto = it.filter { ch -> ch.isDigit() } },
                label = { Text("Idade") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            if (!isValid) {
                Text(
                    text = "Informe um nome e uma idade válida.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}