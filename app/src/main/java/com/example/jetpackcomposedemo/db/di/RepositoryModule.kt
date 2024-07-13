package com.example.jetpackcomposedemo.db.di

import android.content.Context
import com.example.jetpackcomposedemo.db.core.data.local.db.StudentDao
import com.example.jetpackcomposedemo.db.core.data.local.db.StudentDatabase
import com.example.jetpackcomposedemo.db.core.domain.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDao(@ApplicationContext context : Context) = StudentDatabase.getDatabse(context).studentDao()

    @Singleton
    @Provides
    fun provideDbRepository(studentDao: StudentDao) = DatabaseRepository(studentDao)
}