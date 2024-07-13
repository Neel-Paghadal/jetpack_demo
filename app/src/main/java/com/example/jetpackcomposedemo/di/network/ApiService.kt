package com.example.jetpackcomposedemo.di.network

import com.example.jetpackcomposedemo.ui.model.MedicleResponse
import com.example.jetpackcomposedemo.ui.theme.GET_DATA
import retrofit2.http.GET

interface ApiService {
    @GET(GET_DATA)
    suspend fun getMedications(): MedicleResponse/*MedicationResponse*/
}