package com.example.cimucm.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// ViewModel para gestionar y validar el formulario de contacto (IE 2.2.1)
class ContactoViewModel : ViewModel() {
    var nombre by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var telefono by mutableStateOf("")
        private set
    var mensaje by mutableStateOf("")
        private set
    var asunto by mutableStateOf("")
        private set

    // Errores de validación
    var errorNombre by mutableStateOf<String?>(null)
        private set
    var errorEmail by mutableStateOf<String?>(null)
        private set
    var errorTelefono by mutableStateOf<String?>(null)
        private set
    var errorMensaje by mutableStateOf<String?>(null)
        private set

    fun iniciarConAsunto(nuevoAsunto: String) {
        asunto = nuevoAsunto
    }

    fun onNombreChange(valor: String) {
        nombre = valor
        errorNombre = if (valor.trim().length < 3) "El nombre debe tener al menos 3 caracteres" else null
    }

    fun onEmailChange(valor: String) {
        email = valor
        val patronEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        errorEmail = when {
            valor.isBlank() -> "El correo es obligatorio"
            !valor.matches(patronEmail) -> "Formato de correo no válido (ej: usuario@uc.cl)"
            else -> null
        }
    }

    fun onTelefonoChange(valor: String) {
        // Solo permite ingresar números hasta 9 dígitos
        if (valor.all { it.isDigit() } && valor.length <= 9) {
            telefono = valor
            errorTelefono = if (valor.isNotEmpty() && valor.length < 9) "Debe contener 9 dígitos" else null
        }
    }

    fun onMensajeChange(valor: String) {
        mensaje = valor
        errorMensaje = if (valor.trim().length < 10) "El mensaje debe tener al menos 10 caracteres" else null
    }

    // Valida todos los campos antes de enviar
    fun esFormularioValido(): Boolean {
        val vNombre = nombre.trim().length >= 3
        val patronEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        val vEmail = email.matches(patronEmail)
        val vMensaje = mensaje.trim().length >= 10
        val vTelefono = telefono.isEmpty() || telefono.length == 9

        if (!vNombre && errorNombre == null) errorNombre = "El nombre es obligatorio"
        if (!vEmail && errorEmail == null) errorEmail = "Correo no válido"
        if (!vMensaje && errorMensaje == null) errorMensaje = "El mensaje es obligatorio"

        return vNombre && vEmail && vMensaje && vTelefono
    }

    fun limpiarFormulario() {
        nombre = ""
        email = ""
        telefono = ""
        mensaje = ""
        errorNombre = null
        errorEmail = null
        errorTelefono = null
        errorMensaje = null
    }
}