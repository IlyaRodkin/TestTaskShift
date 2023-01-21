package by.rodkin.testtaskshift.di

import android.content.Context
import androidx.room.Room
import by.rodkin.testtaskshift.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun dbProvider(@ApplicationContext applicationContext: Context) =
        Room.databaseBuilder(
        applicationContext,
        Database::class.java, "database-name"
    ).build()
}