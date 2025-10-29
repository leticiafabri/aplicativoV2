// ../app/data/Pessoa.kt
package br.pucpr.aplicativov2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pessoas")
data class Pessoa(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nome: String,
    val idade: Int
)