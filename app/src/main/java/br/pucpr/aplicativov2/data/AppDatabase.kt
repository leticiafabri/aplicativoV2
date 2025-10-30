// ../app/data/AppDatabase.kt
package br.pucpr.aplicativov2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pessoa::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pessoaDao(): PessoaDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pessoas.db"
                )
                    // Apenas POC — não usar em produção
                    .allowMainThreadQueries()
                    .build().also { INSTANCE = it }
            }
        }
    }
}