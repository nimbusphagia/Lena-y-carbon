package com.example.lenaycarbon.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun register(
        email: String,
        password: String,
        onResult: (Boolean, String, FirebaseUser?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, "Usuario registrado correctamente", auth.currentUser)
                } else {
                    onResult(
                        false,
                        task.exception?.localizedMessage ?: "Error al registrar usuario",
                        null
                    )
                }
            }
    }

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String, FirebaseUser?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, "Inicio de sesión correcto", auth.currentUser)
                } else {
                    onResult(
                        false,
                        task.exception?.localizedMessage ?: "Error al iniciar sesión",
                        null
                    )
                }
            }
    }

    fun loginWithGoogle(
        idToken: String,
        onResult: (Boolean, String, FirebaseUser?) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, "Inicio de sesión con Google correcto", auth.currentUser)
                } else {
                    onResult(
                        false,
                        task.exception?.localizedMessage ?: "Error al iniciar sesión con Google",
                        null
                    )
                }
            }
    }

    fun logout() {
        auth.signOut()
    }
}
