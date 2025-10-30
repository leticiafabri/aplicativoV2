// ../app/ui/components/AppNavegador.kt
package br.pucpr.aplicativov2.ui.components

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.pucpr.aplicativov2.data.Pessoa
import br.pucpr.aplicativov2.ui.PessoasViewModel
import br.pucpr.aplicativov2.ui.screens.PrimeiraTela
import br.pucpr.aplicativov2.ui.screens.SegundaTela

enum class Tela { LISTA, CADASTRO }

@Composable
fun AppNavegador() {

    val context = LocalContext.current
    val vm: PessoasViewModel = viewModel(factory = PessoasViewModel.factory(context))

    val pessoas by vm.pessoas.collectAsStateWithLifecycle()  // lista reativa do VM

    var tela by rememberSaveable { mutableStateOf(Tela.LISTA) }
    var idPessoa by rememberSaveable { mutableStateOf<Long?>(null) }

    fun navegarTelaCriar() {
        idPessoa = null; tela = Tela.CADASTRO
    }

    fun navegarTelaEditar(p: Pessoa) {
        idPessoa = p.id; tela = Tela.CADASTRO
    }

    fun voltar() {
        tela = Tela.LISTA
    }

    fun excluir(p: Pessoa) {
        vm.excluir(p)
    }

    fun salvar(nome: String, idade: Int) {
        vm.salvar(idPessoa, nome, idade); voltar()
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