package com.example.jetpackcomposedemo.ui.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MedicationDetailScreen(navController: NavHostController, medicationName: String, medicationStrength: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Medication Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Name: $medicationName", style = MaterialTheme.typography.h6)
            Text(text = "Dose:   -   ", style = MaterialTheme.typography.body1)
            Text(text = "Strength: $medicationStrength", style = MaterialTheme.typography.body1)
        }
    }
}