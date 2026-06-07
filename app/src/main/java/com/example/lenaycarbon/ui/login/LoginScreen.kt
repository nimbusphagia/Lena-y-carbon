package com.example.lenaycarbon.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.lenaycarbon.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lenaycarbon.ui.theme.AppPrimaryOrange
import com.example.lenaycarbon.ui.theme.LenaYCarbonTheme

@Composable
fun LoginScreen(modifier: Modifier = Modifier,
                onLoginSuccess: () -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    var mostrarError by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    Scaffold { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_lenaycarbon),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Leña y Carbón\nAPP",
                color = AppPrimaryOrange,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Campo: Correo
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Correo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    placeholder = { Text("correo@ejemplo.com", color = Color.Gray) },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppPrimaryOrange,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Contraseña",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    placeholder = { Text("*********", color = Color.Gray) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppPrimaryOrange,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(40.dp))


            Button(
                onClick = {
                    val correoTrim = correo.trim()
                    val hasUpper = contrasena.any { it.isUpperCase() }
                    val hasLower = contrasena.any { it.isLowerCase() }
                    val hasDigit = contrasena.any { it.isDigit() }
                    val hasSymbol = contrasena.any { !it.isLetterOrDigit() }

                    if (correoTrim.isEmpty() || contrasena.isEmpty()) {
                        mensajeError = "Por favor, llena todos los campos."
                        mostrarError = true
                    } else if (!correoTrim.contains("@") || !correoTrim.contains(".")) {
                        mensajeError = "El formato del correo electrónico no es válido."
                        mostrarError = true
                    } else if (contrasena.length <= 6) {
                        mensajeError = "La contraseña debe tener más de 6 caracteres."
                        mostrarError = true
                    } else if (!(hasUpper && hasLower && hasDigit && hasSymbol)) {
                        mensajeError = "La contraseña debe incluir mayúsculas, minúsculas, números y signos."
                        mostrarError = true
                    } else {
                        mensajeError = "Inicio de sesión exitoso."
                        mostrarError = true
                        onLoginSuccess()
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppPrimaryOrange),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "INGRESAR",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedButton(
                onClick = { },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.5.dp, AppPrimaryOrange),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AppPrimaryOrange),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "REGISTRAR",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }


    if (mostrarError) {
        AlertDialog(
            onDismissRequest = { mostrarError = false },
            title = { Text("Validación") },
            text = { Text(mensajeError) },
            confirmButton = {
                TextButton(onClick = { mostrarError = false }) {
                    Text("Ok", color = AppPrimaryOrange)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LenaYCarbonTheme {
        LoginScreen(onLoginSuccess = {})
    }
}