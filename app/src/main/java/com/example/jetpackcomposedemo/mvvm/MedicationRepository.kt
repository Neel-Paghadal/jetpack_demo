package com.example.jetpackcomposedemo.mvvm

import com.example.jetpackcomposedemo.di.network.ApiService
import com.example.jetpackcomposedemo.ui.model.MedicleResponse
import javax.inject.Inject

class MedicationRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getMedications(): MedicleResponse/*MedicationResponse*/ {
        return apiService.getMedications()
    }
//    suspend fun getMedications() = apiService.getMedications()
}