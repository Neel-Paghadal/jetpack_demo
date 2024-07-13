package com.example.jetpackcomposedemo.db.core.interactors

import com.example.jetpackcomposedemo.db.core.data.local.db.Student
import com.example.jetpackcomposedemo.db.core.domain.repository.DataState
import com.example.jetpackcomposedemo.db.core.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddStudent(private val databaseRepository: DatabaseRepository) {

    operator fun invoke(student: Student): Flow<DataState<List<Student>>> = flow {

        emit(DataState.Loading)

        databaseRepository.insertStudent(student)
        emit(DataState.Success(databaseRepository.getStudents()))
    }
}