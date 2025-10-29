// ../app/data/PessoaDao.kt
package br.pucpr.aplicativov2.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PessoaDao {

    // reatividade sem coroutines na UI
    @Query("SELECT * FROM pessoas ORDER BY id DESC")
    fun observarTodas(): LiveData<List<Pessoa>>

    // funções síncronas (apenas para POC)
    @Insert
    fun inserir(pessoa: Pessoa): Long

    @Update
    fun atualizar(pessoa: Pessoa)

    @Delete
    fun deletar(pessoa: Pessoa)
}