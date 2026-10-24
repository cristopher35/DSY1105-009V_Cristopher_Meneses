package com.example.cimucm.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cimucm.data.DatosSimulados
import com.example.cimucm.model.Oportunidad
import com.example.cimucm.ui.theme.Blanco
import com.example.cimucm.ui.theme.CafeSecundario
import com.example.cimucm.ui.theme.VerdeClaro
import com.example.cimucm.ui.theme.VerdePrincipal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaOportunidades(
    onPostular: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Oportunidades", fontWeight = FontWeight.Bold, color = Blanco) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdePrincipal)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(
                    text = "Convocatorias Abiertas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = CafeSecundario
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Postula a tesis, pasantías y proyectos aplicados en conjunto con la industria.",
                    fontSize = 13.sp
                )
            }

            items(DatosSimulados.oportunidades) { oportunidad ->
                TarjetaOportunidad(
                    oportunidad = oportunidad,
                    onPostular = { onPostular(oportunidad.titulo) }
                )
            }
        }
    }
}

@Composable
fun TarjetaOportunidad(
    oportunidad: Oportunidad,
    onPostular: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Blanco),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(
                color = VerdeClaro,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = oportunidad.tipo,
                    color = VerdePrincipal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = oportunidad.titulo,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Área: ${oportunidad.area}",
                fontSize = 13.sp,
                color = CafeSecundario,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = oportunidad.descripcion,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onPostular,
                colors = ButtonDefaults.buttonColors(containerColor = VerdePrincipal),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Postular / Consultar", color = Blanco)
            }
        }
    }
}