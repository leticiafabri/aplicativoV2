// ../app/ui/PessoasViewModel.kt
package br.pucpr.aplicativov2.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.pucpr.aplicativov2.data.AppDatabase
import br.pucpr.aplicativov2.data.Pessoa
import br.pucpr.aplicativov2.data.PessoaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PessoasViewModel(private val repo: PessoaRepository) : ViewModel() {

    // UI state reativo (lista)
    val pessoas: StateFlow<List<Pessoa>> =
        repo.pessoas.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun salvar(editandoId: Long?, nome: String, idade: Int) {
        viewModelScope.launch {
            if (editandoId == null) {
                repo.criar(nome, idade)
            } else {
                val atual = pessoas.value.firstOrNull { it.id == editandoId } ?: return@launch
                repo.atualizar(atual.copy(nome = nome, idade = idade))
            }
        }
    }

    fun excluir(p: Pessoa) {
        viewModelScope.launch(Dispatchers.IO) { repo.remover(p) }
    }

    // Factory simples (sem Hilt) para injetar DAO/Repo
    companion object {
        fun factory(context: Context) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao = AppDatabase.get(context).pessoaDao()
                val repo = PessoaRepository(dao)
                return PessoasViewModel(repo) as T
            }
        }
    }
}