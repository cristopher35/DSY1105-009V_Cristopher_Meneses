package com.example.cimucm.ui.screens
import androidx.compose.ui.graphics.Color
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cimucm.ui.theme.Blanco
import com.example.cimucm.ui.theme.VerdePrincipal
import com.example.cimucm.viewmodel.ContactoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaContacto(
    viewModel: ContactoViewModel,
    alVolver: () -> Unit,
    onEnvioExitoso: () -> Unit
) {
    val contexto = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contacto y Colaboración", color = Blanco, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = alVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Blanco)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdePrincipal)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Formulario de Solicitud",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = VerdePrincipal
            )

            if (viewModel.asunto.isNotEmpty()) {
                Text(
                    text = "Referencia: ${viewModel.asunto}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Campo Nombre con validación visual (IE 2.1.2)
            OutlinedTextField(
                value = viewModel.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre Completo *") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                trailingIcon = {
                    if (viewModel.errorNombre != null) {
                        Icon(Icons.Default.Error, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                isError = viewModel.errorNombre != null,
                supportingText = {
                    if (viewModel.errorNombre != null) {
                        Text(viewModel.errorNombre!!, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo Correo Electrónico con validación visual (IE 2.1.2)
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Correo Institucional / Empresa *") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                trailingIcon = {
                    if (viewModel.errorEmail != null) {
                        Icon(Icons.Default.Error, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                isError = viewModel.errorEmail != null,
                supportingText = {
                    if (viewModel.errorEmail != null) {
                        Text(viewModel.errorEmail!!, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo Teléfono (opcional pero validado si se escribe)
            OutlinedTextField(
                value = viewModel.telefono,
                onValueChange = { viewModel.onTelefonoChange(it) },
                label = { Text("Teléfono de Contacto (9 dígitos)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                trailingIcon = {
                    if (viewModel.errorTelefono != null) {
                        Icon(Icons.Default.Error, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                isError = viewModel.errorTelefono != null,
                supportingText = {
                    if (viewModel.errorTelefono != null) {
                        Text(viewModel.errorTelefono!!, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo Mensaje / Propuesta con validación visual (IE 2.1.2)
            OutlinedTextField(
                value = viewModel.mensaje,
                onValueChange = { viewModel.onMensajeChange(it) },
                label = { Text("Mensaje o Propuesta *") },
                isError = viewModel.errorMensaje != null,
                supportingText = {
                    if (viewModel.errorMensaje != null) {
                        Text(viewModel.errorMensaje!!, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Aviso educativo de simulación del mockup
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFFFF4D6),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Envío simulado, no se enviará ningún mensaje real.",
                    color = Color(0xFF2B2B2B),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }

            // Botón de envío que integra el Recurso Nativo 2
            Button(
                onClick = {
                    if (viewModel.esFormularioValido()) {
                        // Recurso Nativo 2: Intent para redactar correo oficial en la app nativa de correo
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:contacto@cim.uc.cl")
                            putExtra(Intent.EXTRA_SUBJECT, "Contacto CIM UC: ${viewModel.asunto}")
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Nombre: ${viewModel.nombre}\nTeléfono: ${viewModel.telefono}\nCorreo: ${viewModel.email}\n\nMensaje:\n${viewModel.mensaje}"
                            )
                        }
                        try {
                            contexto.startActivity(emailIntent)
                        } catch (e: Exception) {
                            // Si no hay app de correo configurada, continúa al éxito sin crashear
                        }
                        onEnvioExitoso()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = VerdePrincipal),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Enviar Solicitud", fontSize = 16.sp, color = Blanco)
            }
        }
    }
}