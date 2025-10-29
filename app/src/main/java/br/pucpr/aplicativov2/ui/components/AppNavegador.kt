// ../ui/components/AppNavegador.kt
package br.pucpr.aplicativov2.ui.components

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import br.pucpr.aplicativov2.data.Pessoa
import br.pucpr.aplicativov2.ui.screens.PrimeiraTela
import br.pucpr.aplicativov2.ui.screens.SegundaTela

enum class Tela { LISTA, CADASTRO }

@Composable
fun AppNavegador() {

    var tela by rememberSaveable { mutableStateOf(Tela.LISTA) }
    var idPessoa by rememberSaveable { mutableStateOf<Long?>(null) }
    var nextId by rememberSaveable { mutableStateOf(4L) } // 3 já existem

    val pessoas = remember {
        mutableStateListOf(
            Pessoa(id = 1, nome = "Ana", idade = 22),
            Pessoa(id = 2, nome = "Bruno", idade = 31),
            Pessoa(id = 3, nome = "Carla", idade = 27),
        )
    }

    fun navegarParaCriar() { idPessoa = null; tela = Tela.CADASTRO }
    fun navegarParaEditar(p: Pessoa) { idPessoa = p.id; tela = Tela.CADASTRO }
    fun excluir(p: Pessoa) { pessoas.removeAll { it.id == p.id } }

    fun voltar() { tela = Tela.LISTA }

    fun salvar(nome: String, idade: Int) {
        val idAlvo = idPessoa
        if (idAlvo == null) {
            pessoas.add(0, Pessoa(id = nextId, nome = nome, idade = idade))
            nextId += 1
        } else {
            val idx = pessoas.indexOfFirst { it.id == idAlvo }
            if (idx >= 0) pessoas[idx] = pessoas[idx].copy(nome = nome, idade = idade)
        }
        voltar()
    }

    when (tela) {
        Tela.LISTA -> {
            PrimeiraTela(
                pessoas = pessoas,
                onAddClick = { navegarParaCriar() },
                onItemClick = { pessoa -> navegarParaEditar(pessoa) },
                onItemLongPress = { pessoa -> excluir(pessoa) }
            )
        }
        Tela.CADASTRO -> {
            val pessoaSelecionada = pessoas.firstOrNull { it.id == idPessoa }
            SegundaTela(
                pessoa = pessoaSelecionada,
                onSalvar = { nome, idade -> salvar(nome, idade) },
                onCancelar = { voltar() }
            )
        }
    }
}