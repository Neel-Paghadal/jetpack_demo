package com.example.jetpackcomposedemo.db.di

import com.example.jetpackcomposedemo.db.core.domain.repository.DatabaseRepository
import com.example.jetpackcomposedemo.db.core.interactors.AddStudent
import com.example.jetpackcomposedemo.db.core.interactors.DeleteStudent
import com.example.jetpackcomposedemo.db.core.interactors.GetStudents
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {

    @Singleton
    @Provides
    fun getAdStudent(databaseRepository: DatabaseRepository) = AddStudent(databaseRepository)

    @Singleton
    @Provides
    fun getStudents(databaseRepository: DatabaseRepository) = GetStudents(databaseRepository)

    @Singleton
    @Provides
    fun getDeleteStudent(databaseRepository: DatabaseRepository) = DeleteStudent(databaseRepository)
}