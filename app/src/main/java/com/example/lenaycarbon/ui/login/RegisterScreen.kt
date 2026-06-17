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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lenaycarbon.R
import com.example.lenaycarbon.ui.theme.AppPrimaryOrange
import com.example.lenaycarbon.ui.theme.LenaYCarbonTheme
import com.example.lenaycarbon.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }

    var mostrarDialogo by remember { mutableStateOf(false) }
    var mensajeDialogo by remember { mutableStateOf("") }
    var registroExitoso by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Scaffold { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_lenaycarbon),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Crear Cuenta",
                color = AppPrimaryOrange,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Regístrate para realizar tus pedidos",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Campo: Correo
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Correo Electrónico",
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

            Spacer(modifier = Modifier.height(16.dp))

            // Campo: Contraseña
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

            Spacer(modifier = Modifier.height(16.dp))

            // Campo: Confirmar Contraseña
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Confirmar Contraseña",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = confirmarContrasena,
                    onValueChange = { confirmarContrasena = it },
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

            Spacer(modifier = Modifier.height(30.dp))

            if (authViewModel.loading) {
                CircularProgressIndicator(color = AppPrimaryOrange)
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Button(
                    onClick = {
                        val correoTrim = correo.trim()
                        if (correoTrim.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
                            mensajeDialogo = "Por favor, llena todos los campos."
                            mostrarDialogo = true
                        } else if (!correoTrim.contains("@") || !correoTrim.contains(".")) {
                            mensajeDialogo = "El formato del correo electrónico no es válido."
                            mostrarDialogo = true
                        } else if (contrasena.length <= 6) {
                            mensajeDialogo = "La contraseña debe tener más de 6 caracteres."
                            mostrarDialogo = true
                        } else if (contrasena != confirmarContrasena) {
                            mensajeDialogo = "Las contraseñas no coinciden."
                            mostrarDialogo = true
                        } else {
                            authViewModel.register(correoTrim, contrasena) {
                                mensajeDialogo = "Registro exitoso."
                                registroExitoso = true
                                mostrarDialogo = true
                            }
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppPrimaryOrange),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "REGISTRARSE",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onBackToLogin,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.5.dp, AppPrimaryOrange),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AppPrimaryOrange),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "VOLVER AL LOGIN",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    // Monitorear mensajes de error externos desde el ViewModel
    LaunchedEffect(authViewModel.message) {
        if (authViewModel.message.isNotEmpty()) {
            mensajeDialogo = authViewModel.message
            mostrarDialogo = true
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { 
                mostrarDialogo = false
                authViewModel.clearMessage()
                if (registroExitoso) {
                    onRegisterSuccess()
                }
            },
            title = { Text("Registro") },
            text = { Text(mensajeDialogo) },
            confirmButton = {
                TextButton(onClick = { 
                    mostrarDialogo = false
                    authViewModel.clearMessage()
                    if (registroExitoso) {
                        onRegisterSuccess()
                    }
                }) {
                    Text("Ok", color = AppPrimaryOrange)
                }
            }
        )
    }
}
