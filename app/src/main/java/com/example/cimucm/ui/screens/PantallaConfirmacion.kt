package com.example.cimucm.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cimucm.ui.theme.Blanco
import com.example.cimucm.ui.theme.VerdePrincipal

// P07 Confirmación de envío simulado
@Composable
fun PantallaConfirmacion(
    onVolverInicio: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Éxito",
            tint = VerdePrincipal,
            modifier = Modifier.size(90.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¡Solicitud Enviada!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = VerdePrincipal
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tu mensaje ha sido registrado exitosamente. El equipo de CIM UC se pondrá en contacto a la brevedad.",
            textAlign = TextAlign.Center,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = onVolverInicio,
            colors = ButtonDefaults.buttonColors(containerColor = VerdePrincipal),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Volver", color = Blanco)
        }
    }
}