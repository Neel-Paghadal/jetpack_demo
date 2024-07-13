package com.example.jetpackcomposedemo.db.core.data.local.db

import androidx.room.*
import com.example.jetpackcomposedemo.db.core.data.local.db.Student

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("SELECT * FROM student")
    suspend fun getAllStudents() : List<Student>
}