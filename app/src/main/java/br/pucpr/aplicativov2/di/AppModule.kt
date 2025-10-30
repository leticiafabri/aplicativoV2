// ../app/di/AppModule.kt
package br.pucpr.aplicativov2.di

import android.content.Context
import androidx.room.Room
import br.pucpr.aplicativov2.data.AppDatabase
import br.pucpr.aplicativov2.data.PessoaDao
import br.pucpr.aplicativov2.data.PessoaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "pessoas.db").build()

    @Provides
    fun providePessoaDao(db: AppDatabase): PessoaDao = db.pessoaDao()

    @Provides
    @Singleton
    fun providePessoaRepository(dao: PessoaDao): PessoaRepository = PessoaRepository(dao)
}
