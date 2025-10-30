// ../ui/UiState.kt
package br.pucpr.aplicativov2.ui

import br.pucpr.aplicativov2.data.Pessoa

sealed interface PessoasUiState {
    object Loading : PessoasUiState
    data class Ready(val pessoas: List<Pessoa>, val message: String? = null) : PessoasUiState
    data class Error(val message: String) : PessoasUiState
}
