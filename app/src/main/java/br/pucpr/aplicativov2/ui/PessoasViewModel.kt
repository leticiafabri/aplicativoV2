// ../ui/PessoasViewModel.kt (versão com UiState)
package br.pucpr.aplicativov2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pucpr.aplicativov2.data.Pessoa
import br.pucpr.aplicativov2.data.PessoaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PessoasViewModel @Inject constructor(
    private val repo: PessoaRepository
) : ViewModel() {

    private val _ui = MutableStateFlow<PessoasUiState>(PessoasUiState.Loading)
    val ui: StateFlow<PessoasUiState> = _ui.asStateFlow()

    init {
        // carrega fluxo reativo e converte em UiState
        viewModelScope.launch {
            repo.pessoas
                .onStart { _ui.value = PessoasUiState.Loading }
                .catch { e -> _ui.value = PessoasUiState.Error(e.message ?: "Erro ao carregar") }
                .collect { lista ->
                    _ui.value = PessoasUiState.Ready(pessoas = lista)
                }
        }
    }

    fun salvar(editandoId: Long?, nome: String, idade: Int) {
        // validação simples no VM (garante regra mesmo sem UI)
        val idadeValida = idade >= 0
        val nomeValido = nome.isNotBlank()
        if (!idadeValida || !nomeValido) {
            _ui.update {
                when (it) {
                    is PessoasUiState.Ready -> it.copy(message = "Nome/idade inválidos")
                    else -> PessoasUiState.Error("Nome/idade inválidos")
                }
            }
            return
        }

        viewModelScope.launch {
            try {
                if (editandoId == null) repo.criar(nome, idade)
                else {
                    val atual = (ui.value as? PessoasUiState.Ready)?.pessoas?.firstOrNull { it.id == editandoId }
                        ?: return@launch
                    repo.atualizar(atual.copy(nome = nome, idade = idade))
                }
                // feedback de sucesso
                _ui.update { st ->
                    val lista = (st as? PessoasUiState.Ready)?.pessoas ?: emptyList()
                    PessoasUiState.Ready(lista, message = "Salvo com sucesso")
                }
            } catch (e: Exception) {
                _ui.value = PessoasUiState.Error(e.message ?: "Erro ao salvar")
            }
        }
    }

    fun excluir(p: Pessoa) = viewModelScope.launch {
        try {
            repo.remover(p)
            _ui.update { st ->
                val lista = (st as? PessoasUiState.Ready)?.pessoas ?: emptyList()
                PessoasUiState.Ready(lista, message = "Excluído")
            }
        } catch (e: Exception) {
            _ui.value = PessoasUiState.Error(e.message ?: "Erro ao excluir")
        }
    }

    fun consumirMensagem() {
        _ui.update { st ->
            if (st is PessoasUiState.Ready) st.copy(message = null) else st
        }
    }
}
