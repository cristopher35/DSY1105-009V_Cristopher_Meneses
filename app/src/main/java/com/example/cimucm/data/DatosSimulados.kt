package com.example.cimucm.data

import com.example.cimucm.model.Oportunidad
import com.example.cimucm.model.Proyecto
import com.example.cimucm.model.Publicacion

// Datos en memoria para alimentar el MVP sin necesidad de backend
object DatosSimulados {
    val proyectos = listOf(
        Proyecto(
            id = "p1",
            titulo = "Torre Experimental Peñuelas",
            lineaInvestigacion = "Edificación en Altura",
            descripcionCorta = "Pionero edificio de madera en altura con monitoreo sísmico.",
            descripcionCompleta = "Investigación aplicada para analizar el comportamiento dinámico, térmico y estructural de edificios construidos en madera contralaminada (CLT).",
            lider = "Dr. Juan José Ugarte",
            anio = "2024"
        ),
        Proyecto(
            id = "p2",
            titulo = "Vivienda Social Sustentable",
            lineaInvestigacion = "Sustentabilidad y Clima",
            descripcionCorta = "Soluciones habitacionales de alta eficiencia energética.",
            descripcionCompleta = "Desarrollo de prototipos industrializados en madera para reducir la huella de carbono y los tiempos de construcción en soluciones sociales.",
            lider = "Dra. Francisca Latorre",
            anio = "2025"
        ),
        Proyecto(
            id = "p3",
            titulo = "Biomateriales y Adhesivos Verdes",
            lineaInvestigacion = "Tecnología de Materiales",
            descripcionCorta = "Resinas ecológicas a partir de cortezas forestales.",
            descripcionCompleta = "Reemplazo de adhesivos sintéticos por compuestos biobasados de baja emisión para tableros y vigas laminadas.",
            lider = "Ing. Carlos Morales",
            anio = "2024"
        )
    )

    val oportunidades = listOf(
        Oportunidad(
            id = "o1",
            titulo = "Pasantía en Ensayos Sísmicos",
            area = "Estructuras",
            tipo = "Pasantía Investigacion y Desarrollo",
            descripcion = "Apoyo en el laboratorio estructural midiendo aceleraciones en marcos de madera."
        ),
        Oportunidad(
            id = "o2",
            titulo = "Tesis en Huella de Carbono CLT",
            area = "Sustentabilidad",
            tipo = "Memoria / Tesis",
            descripcion = "Modelamiento de ciclo de vida en faenas constructivas en el sur de Chile."
        )
    )
}