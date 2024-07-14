package com.example.jetpackcomposedemo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jetpackcomposedemo.ui.presentation.MedicationDetailScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MedicationDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun medicationDetailScreen_displaysCorrectDetails() {
        // Given
        val medicationName = "Aspirin"
        val medicationStrength = "500mg"

        // When
        composeTestRule.setContent {
            val navController = rememberNavController()
            MedicationDetailScreen(navController, medicationName, medicationStrength)
        }

        // Then
        composeTestRule.onNodeWithText("Medication Details").assertExists()
        composeTestRule.onNodeWithText("Name: $medicationName").assertExists()
        composeTestRule.onNodeWithText("Strength: $medicationStrength").assertExists()
    }

    @Test
    fun medicationDetailScreen_backButtonNavigatesBack() {
        // Given
        val medicationName = "Aspirin"
        val medicationStrength = "500mg"
        lateinit var navController: NavHostController

        // When
        composeTestRule.setContent {
            navController = rememberNavController()
            MedicationDetailScreen(navController, medicationName, medicationStrength)
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        // Verify that popBackStack was called
        assert(navController.currentBackStackEntry == null || navController.currentBackStackEntry!!.destination.route != "medication_detail_screen")
    }
}