package com.example.jetpackcomposedemo.ui.model

import com.google.gson.annotations.SerializedName

data class Lab(
    @SerializedName("missing_field")
    val missing_field: String
)