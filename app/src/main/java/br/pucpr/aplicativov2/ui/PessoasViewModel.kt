// ../ui/PessoasViewModel.kt
package br.pucpr.aplicativov2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pucpr.aplicativov2.data.Pessoa
import br.pucpr.aplicativov2.data.PessoaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PessoasViewModel @Inject constructor(
    private val repo: PessoaRepository
) : ViewModel() {

    val pessoas: StateFlow<List<Pessoa>> =
        repo.pessoas.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun salvar(editandoId: Long?, nome: String, idade: Int) = viewModelScope.launch {
        if (editandoId == null) repo.criar(nome, idade)
        else {
            val atual = pessoas.value.firstOrNull { it.id == editandoId } ?: return@launch
            repo.atualizar(atual.copy(nome = nome, idade = idade))
        }
    }

    fun excluir(p: Pessoa) = viewModelScope.launch { repo.remover(p) }
}
