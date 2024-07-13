package com.example.jetpackcomposedemo.db.core.interactors

import com.example.jetpackcomposedemo.db.core.data.local.db.Student
import com.example.jetpackcomposedemo.db.core.domain.repository.DataState
import com.example.jetpackcomposedemo.db.core.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UpdateStudent @Inject constructor(private val databaseRepository: DatabaseRepository) {
//    private var dao = DatabaseRepository(context).getStudentDao()

    operator fun invoke(student: Student): Flow<DataState<List<Student>>> = flow {

        emit(DataState.Loading)
//        dao.insertStudent(student)
        databaseRepository.updateStudent(student)
        emit(DataState.Success(databaseRepository.getStudents()))
    }


}