package com.example.jetpackcomposedemo

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.ui.model.MedicleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationViewModel @Inject constructor(
    private val repository: MedicationRepository
) : ViewModel() {
    private val TAG = "MedicationViewModel"
    private val _medications = MutableStateFlow<MedicleResponse/*MedicationResponse*/?>(null)
    val medications: StateFlow<MedicleResponse/*MedicationResponse*/?> = _medications

    fun fetchMedications() {
        viewModelScope.launch {
            try {
                val response = repository.getMedications()
                _medications.value = response
            } catch (e: Exception){
                Log.d(TAG, "fetchMedications: ${e.message}")
            }
        }
    }
}