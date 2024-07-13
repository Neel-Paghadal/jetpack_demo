package com.example.jetpackcomposedemo

import com.example.jetpackcomposedemo.ui.model.MedicleResponse
import retrofit2.http.GET

interface ApiService {
    @GET("v3/2e34d6d2-53a4-44c1-a5b2-ea7ee202f35e")
    suspend fun getMedications(): MedicleResponse/*MedicationResponse*/
}