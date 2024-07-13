package com.example.jetpackcomposedemo

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jetpackcomposedemo.ui.presentation.LoginScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoginScreenDisplaysCorrectly() {
        // Set up the composable
        composeTestRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }

        // Check if the Login text is displayed
        composeTestRule.onNodeWithText("Login!").assertIsDisplayed()

        // Check if the Username TextField is displayed
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()

        // Check if the Password TextField is displayed
        composeTestRule.onNodeWithText("password").assertIsDisplayed()

        // Check if the Login button is displayed
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()

        // Check if the Room DB button is displayed
        composeTestRule.onNodeWithText("Room DB").assertIsDisplayed()
    }

    @Test
    fun testLoginButtonNavigatesToMedications() {
        // Set up the composable
//        val navController = mock(NavHostController::class.java)

        // Set up the composable
        composeTestRule.setContent {
//            LoginScreen(navController = navController)
        }

        // Input username
        composeTestRule.onNodeWithText("Username").performTextInput("testuser")

        // Input password
        composeTestRule.onNodeWithText("password").performTextInput("password")

        // Click Login button
        composeTestRule.onNodeWithText("Login").performClick()

        // Verify that the navController navigated to the medications screen with the correct username
//        verify(navController).navigate("medications?username=testuser")
    }
}