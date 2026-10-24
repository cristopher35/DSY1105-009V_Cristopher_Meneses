package com.example.cimucm.model

// Modelo básico para Proyectos de I+D
data class Proyecto(
    val id: String,
    val titulo: String,
    val lineaInvestigacion: String,
    val descripcionCorta: String,
    val descripcionCompleta: String,
    val lider: String,
    val anio: String
)

// Modelo básico para Oportunidades de Colaboración
data class Oportunidad(
    val id: String,
    val titulo: String,
    val area: String,
    val tipo: String, // Tesis, Pasantía, Proyecto Empresa
    val descripcion: String
)

// Modelo para Publicaciones científicas
data class Publicacion(
    val id: String,
    val titulo: String,
    val autores: String,
    val anio: String,
    val revista: String
)