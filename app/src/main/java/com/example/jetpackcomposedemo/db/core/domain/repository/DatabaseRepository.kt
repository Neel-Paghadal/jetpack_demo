package com.example.jetpackcomposedemo.db.core.domain.repository

import com.example.jetpackcomposedemo.db.core.data.local.db.Student
import com.example.jetpackcomposedemo.db.core.data.local.db.StudentDao

class DatabaseRepository(var studentDao: StudentDao) {


    suspend fun getStudents() = studentDao.getAllStudents()
    suspend fun insertStudent(student : Student) = studentDao.insertStudent(student)
    suspend fun updateStudent(student : Student) = studentDao.updateStudent(student)
    suspend fun deleteStudent(student: Student) = studentDao.deleteStudent(student)
}