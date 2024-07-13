package com.example.jetpackcomposedemo.db.presenter

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.db.core.data.local.db.Student
import com.example.jetpackcomposedemo.db.core.domain.repository.DataState
import com.example.jetpackcomposedemo.db.core.interactors.AddStudent
import com.example.jetpackcomposedemo.db.core.interactors.DeleteStudent
import com.example.jetpackcomposedemo.db.core.interactors.GetStudents
import com.example.jetpackcomposedemo.db.core.interactors.UpdateStudent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
@Inject constructor(
    private val addStudent: AddStudent,
    private val updateStudent: UpdateStudent,
    private val getStudents: GetStudents,
    private val delStudent: DeleteStudent
) : ViewModel() {

    private val _isSplashScreen = MutableStateFlow(true)
    val isSplashScreen = _isSplashScreen

    private var _listStudents = SnapshotStateList<Student>()
    val listStudent = _listStudents



    init {
        viewModelScope.launch {
            delay(2000)
            _isSplashScreen.value = false
        }

        getStudents().onEach { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    _listStudents.clear()
                    _listStudents.addAll(dataState.data)
                }
            }
        }.launchIn(viewModelScope)
    }
    fun deleteStudent(student: Student){
        delStudent(student).onEach {
            when (it) {
                is DataState.Success -> {
                    _listStudents.clear()
                    _listStudents.addAll(it.data)

                }
                is DataState.Loading -> {
                }
                is DataState.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addStudent(name: String, lastName: String,image: String) {

        addStudent(Student(name = name, lastName = lastName, image = image)).onEach { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    _listStudents.clear()
                    _listStudents.addAll(dataState.data)

                }
                is DataState.Loading -> {
                }
                is DataState.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    // Function to update a student
    fun updateStudentData(student: Student) {
        viewModelScope.launch {
            // Invoke the updateStudent functionality by passing the student and setting isUpdate to true
            updateStudent(student).collect { dataState ->
                // Handle the different DataState cases (Loading, Success, Error) as needed
                when (dataState) {
                    is DataState.Loading -> {
                        // Handle loading state
                    }
                    is DataState.Success -> {
                        // Handle success state, e.g., update UI with the new list of students
                        val updatedStudents = dataState.data
                        _listStudents.clear()
                        _listStudents.addAll(dataState.data)
                        // Update your UI or perform other actions
                    }
                    is DataState.Error -> {
                        // Handle error state, e.g., show an error message
                        val errorMessage = dataState.exception.message
                        // Display or log the error message
                    }
                }
            }
        }
    }
}