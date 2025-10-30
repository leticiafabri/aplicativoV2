// ../app/data/PessoaDao.kt
package br.pucpr.aplicativov2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PessoaDao {
    @Query("SELECT * FROM pessoas ORDER BY id DESC")
    fun observarTodas(): Flow<List<Pessoa>>

    @Insert
    suspend fun inserir(pessoa: Pessoa): Long

    @Update
    suspend fun atualizar(pessoa: Pessoa)

    @Delete
    suspend fun deletar(pessoa: Pessoa)
}