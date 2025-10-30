// ../app/data/PessoaRepository.kt
package br.pucpr.aplicativov2.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PessoaRepository(private val dao: PessoaDao) {
    val pessoas = dao.observarTodas() // Flow<List<Pessoa>>

    suspend fun criar(nome: String, idade: Int): Pessoa = withContext(Dispatchers.IO) {
        val temp = Pessoa(nome = nome, idade = idade)
        val id = dao.inserir(temp)
        temp.copy(id = id)
    }

    suspend fun atualizar(p: Pessoa) = withContext(Dispatchers.IO) { dao.atualizar(p) }
    suspend fun remover(p: Pessoa) = withContext(Dispatchers.IO) { dao.deletar(p) }
}