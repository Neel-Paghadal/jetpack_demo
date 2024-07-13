package com.example.jetpackcomposedemo.ui.presentation

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.db.DashboardActivity

@Composable
fun LoginScreen(navController: NavHostController) {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Login!",
            style = TextStyle(Color.Black, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold))

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            visualTransformation =  PasswordVisualTransformation(),
            onValueChange = { password = it },
            label = { Text("password") }
        )

        Spacer(modifier = Modifier.height(16.dp))
        CustomButton(text = "Login",
            onClick = { navController.navigate("medications?username=${username.text}") },
            backgroundColor = Color.DarkGray,
            contentColor = Color.White)

        CustomButton(text = "Room DB", onClick = {
            val intent = Intent(context, DashboardActivity::class.java)
            context.startActivity(intent)
        }, backgroundColor = Color.Red, contentColor = Color.White)

    }
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    contentColor: Color,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        ),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        Text(text = text)
    }
}