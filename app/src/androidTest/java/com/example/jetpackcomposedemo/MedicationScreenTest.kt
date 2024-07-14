package com.example.jetpackcomposedemo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.hilt.lifecycle.ViewModelInject
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

// Mock data classes
data class Medication(val problems: List<Problem>)
data class Problem(val Diabetes: List<Diabetes>)
data class Diabetes(val medications: List<Medications>)
data class Medications(val medicationsClasses: List<MedicationClass>)
data class MedicationClass(val className: List<AssociatedDrugGroup>, val className2: List<AssociatedDrugGroup>)
data class AssociatedDrugGroup(val associatedDrug: List<AssociatedDrug>?, val associatedDrug2: List<AssociatedDrug2>?)
data class AssociatedDrug(val name: String, val dose: String, val strength: String)
data class AssociatedDrug2(val name: String, val dose: String, val strength: String)

// Mock ViewModel
class MedicationViewModel @ViewModelInject constructor() : ViewModel() {
    private val _medications = MutableStateFlow<Medication?>(null)
    val medications: StateFlow<Medication?> = _medications

    fun fetchMedications() {
        viewModelScope.launch {
            // Simulate fetching data
            _medications.value = Medication(
                problems = listOf(
                    Problem(
                        Diabetes = listOf(
                            Diabetes(
                                medications = listOf(
                                    Medications(
                                        medicationsClasses = listOf(
                                            MedicationClass(
                                                className = listOf(
                                                    AssociatedDrugGroup(
                                                        associatedDrug = listOf(
                                                            AssociatedDrug("Drug A", "Dose A", "Strength A")
                                                        ),
                                                        associatedDrug2 = listOf(
                                                            AssociatedDrug2("Drug B", "Dose B", "Strength B")
                                                        )
                                                    )
                                                ),
                                                className2 = listOf(
                                                    AssociatedDrugGroup(
                                                        associatedDrug = listOf(
                                                            AssociatedDrug("Drug C", "Dose C", "Strength C")
                                                        ),
                                                        associatedDrug2 = listOf(
                                                            AssociatedDrug2("Drug D", "Dose D", "Strength D")
                                                        )
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        }
    }
}

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MedicationScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun medicationScreen_displaysGreeting() {
        // Given
        val username = "John"

        // When
        composeTestRule.setContent {
            val navController = rememberNavController()
            val viewModel = MedicationViewModel()
            MedicationScreenTest(navController, username, viewModel)
        }

        // Then
        composeTestRule.onNodeWithText("Good ${getGreetingMessage()}, $username!").assertExists()
    }

    @Test
    fun medicationScreen_displaysMedicationDetails() {
        // Given
        val username = "John"

        // When
        composeTestRule.setContent {
            val navController = rememberNavController()
            val viewModel = MedicationViewModel()
            MedicationScreenTest(navController, username, viewModel)
        }

        // Simulate data fetching
        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithText("Name: Drug A").assertExists()
        composeTestRule.onNodeWithText("Dose: Dose A").assertExists()
        composeTestRule.onNodeWithText("Strength: Strength A").assertExists()
    }

    @Test
    fun medicationScreen_navigatesToMedicationDetail() {
        // Given
        val username = "John"
        lateinit var navController: NavHostController

        // When
        composeTestRule.setContent {
            navController = rememberNavController()
            val viewModel = MedicationViewModel()
            MedicationScreenTest(navController, username, viewModel)
        }

        // Simulate data fetching
        composeTestRule.waitForIdle()

        // Click on the first medication card
        composeTestRule.onNodeWithText("Name: Drug A").performClick()

        // Then
        assert(navController.currentBackStackEntry?.destination?.route == "medicationDetail/Drug A/Strength A")
    }
}

// MedicationScreen Composable
@Composable
fun MedicationScreenTest(navController: NavHostController, username: String, viewModel: MedicationViewModel = hiltViewModel()) {
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

                                items(associatedDrug.associatedDrug2 ?: emptyList()) { drug ->
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
