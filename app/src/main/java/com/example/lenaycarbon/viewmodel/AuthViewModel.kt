package com.example.lenaycarbon.viewmodel

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lenaycarbon.data.repository.AuthRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    var currentUser: FirebaseUser? by mutableStateOf(repository.getCurrentUser())
        private set

    var loading: Boolean by mutableStateOf(false)
        private set

    var message: String by mutableStateOf("")
        private set

    fun clearMessage() {
        message = ""
    }

    fun register(email: String, password: String, onSuccess: () -> Unit) {
        loading = true
        message = ""
        repository.register(email, password) { success, msg, user ->
            loading = false
            message = msg
            if (success) {
                currentUser = user
                onSuccess()
            }
        }
    }

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        loading = true
        message = ""
        repository.login(email, password) { success, msg, user ->
            loading = false
            message = msg
            if (success) {
                currentUser = user
                onSuccess()
            }
        }
    }

    fun loginGoogle(context: Context, onSuccess: () -> Unit) {
        val activity = context.findActivity()
        if (activity == null) {
            message = "Contexto no válido para iniciar sesión con Google (se requiere una Actividad)."
            return
        }

        viewModelScope.launch {
            loading = true
            message = ""
            val credentialManager = CredentialManager.create(activity)

            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId("746721120529-e6p24l5aop6t919kam1ajc31kg9s66h9.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            try {
                val result = credentialManager.getCredential(context = activity, request = request)
                val credential = result.credential

                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken

                    repository.loginWithGoogle(idToken) { success, msg, user ->
                        loading = false
                        message = msg
                        if (success) {
                            currentUser = user
                            onSuccess()
                        }
                    }
                } else {
                    loading = false
                    message = "Tipo de credencial no soportado."
                }
            } catch (e: GetCredentialException) {
                loading = false
                message = e.localizedMessage ?: "Error al obtener credenciales de Google."
            } catch (e: Exception) {
                loading = false
                message = e.localizedMessage ?: "Error desconocido en el inicio de sesión con Google."
            }
        }
    }

    fun logout(showMessage: Boolean = false, onSuccess: () -> Unit) {
        repository.logout()
        currentUser = null
        if (showMessage) {
            message = "Cierre de sesión exitoso"
        }
        onSuccess()
    }

    private tailrec fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}
