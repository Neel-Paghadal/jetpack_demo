package com.example.jetpackcomposedemo.ui.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.mvvm.MedicationViewModel
import com.example.jetpackcomposedemo.ui.model.AssociatedDrug
import com.example.jetpackcomposedemo.ui.model.AssociatedDrug2
import java.util.*

@Composable
fun MedicationScreen(navController: NavHostController, username: String, viewModel: MedicationViewModel = hiltViewModel()) {
    val medications by viewModel.medications.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMedications()
    }

    val greeting = getGreetingMessage()


    Column(modifier = Modifier.padding(16.dp), Arrangement.Bottom) {
        Text(text = "Good $greeting, $username!", style = TextStyle(Color.Black, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold))
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            medications?.problems?.forEach { problem ->
                problem.Diabetes.forEach { diabetes ->
                    diabetes.medications.forEach { medication ->
                        medication.medicationsClasses.forEach { medicationClass ->
                            medicationClass.className.forEach { associatedDrug ->
                                items(associatedDrug.associatedDrug ?: emptyList()) { drug ->
                                    MedicationCard(navController, drug)
                                }

                                items(associatedDrug.associatedDrug2 ?: emptyList()) { drug ->
                                    MedicationCard2(navController, drug)
                                }
                            }
                            medicationClass.className2.forEach { associatedDrug ->
                                items(associatedDrug.associatedDrug ?: emptyList()) { drug ->
                                    MedicationCard(navController, drug)
                                }

                                items(associatedDrug.associatedDrug2/*associatedDrug2*/ ?: emptyList()) { drug ->
                                    MedicationCard2(navController, drug)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MedicationCard(navController: NavHostController, drug: AssociatedDrug) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = {
                navController.navigate("medicationDetail/${drug.name}/${drug.strength}")
            })
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${drug.name}")
            Text(text = "Dose: ${drug.dose}")
            Text(text = "Strength: ${drug.strength}")
        }
    }
}


@Composable
fun MedicationCard2(navController: NavHostController, drug: AssociatedDrug2) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("medicationDetail/${drug.name}/${drug.strength}")
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${drug.name}")
            Text(text = "Dose: ${drug.dose}")
            Text(text = "Strength: ${drug.strength}")
        }
    }
}

fun getGreetingMessage(): String {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when {
        currentHour in 0..11 -> "morning"
        currentHour in 12..16 -> "afternoon"
        currentHour in 17..20 -> "evening"
        else -> "night"
    }
}