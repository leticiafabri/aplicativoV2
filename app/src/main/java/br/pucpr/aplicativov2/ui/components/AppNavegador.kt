// ../ui/components/AppNavegador.kt
package br.pucpr.aplicativov2.ui.components

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import br.pucpr.aplicativov2.data.AppDatabase
import br.pucpr.aplicativov2.data.Pessoa
import br.pucpr.aplicativov2.ui.screens.PrimeiraTela
import br.pucpr.aplicativov2.ui.screens.SegundaTela

enum class Tela { LISTA, CADASTRO }

@Composable
fun AppNavegador() {

    val context = LocalContext.current

    // DAO do Room
    val dao = remember { AppDatabase.get(context).pessoaDao() }

    // LiveData -> State do Compose: reatividade automática
    val pessoas by dao.observarTodas().observeAsState(initial = emptyList())

    // Navegação simples
    var tela by rememberSaveable { mutableStateOf(Tela.LISTA) }
    var idPessoa by rememberSaveable { mutableStateOf<Long?>(null) }

    fun navegarTelaCriar() { idPessoa = null; tela = Tela.CADASTRO }
    fun navegarTelaEditar(p: Pessoa) { idPessoa = p.id; tela = Tela.CADASTRO }
    fun excluir(p: Pessoa) { dao.deletar(p) }
    fun voltar() { tela = Tela.LISTA }

    fun salvar(nome: String, idade: Int) {
        val id = idPessoa
        if (id == null) {
            dao.inserir(Pessoa(nome = nome, idade = idade))
        } else {
            val atual = pessoas.firstOrNull { it.id == id } ?: return
            dao.atualizar(atual.copy(nome = nome, idade = idade))
        }
        voltar()
    }

    when (tela) {
        Tela.LISTA -> PrimeiraTela(
            pessoas = pessoas,
            onAddClick = { navegarTelaCriar() },
            onItemClick = { pessoa -> navegarTelaEditar(pessoa) },
            onItemLongPress = { pessoa -> excluir(pessoa) }
        )
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