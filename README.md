# CIM UC - Aplicacion Movil (Evaluacion 2)

Aplicacion movil desarrollada en Kotlin y Jetpack Compose para el Centro de Innovacion y Modelado UC (CIM UC), enfocada en la gestion de proyectos, catalogo de iniciativas, sistema de favoritos y canal de contacto institucional.

---

## Stack Tecnologico y Arquitectura

* Lenguaje: Kotlin
* Framework UI: Jetpack Compose con Material Design 3
* Arquitectura: MVVM (Model - View - ViewModel)
* Navegacion: Jetpack Navigation Compose con paso de argumentos
* Gestion de Estados: ViewModel, StateFlow y remember / mutableStateOf
* Persistencia: Capa de datos en memoria y simulacion local estructurada

---

## Modulos y Pantallas

* Inicio (PantallaInicio): Presentacion institucional, accesos directos y metricas principales.
* Busqueda y Catalogo (PantallaBuscar): Listado filtrable de proyectos y servicios con actualizacion reactiva.
* Oportunidades (PantallaOportunidades): Seccion de convocatorias, pasantias y colaboraciones vigentes.
* Detalle (PantallaDetalleReal): Vista individual con informacion detallada de la iniciativa, recepcion de identificador por ruta y opcion de agregar a favoritos.
* Favoritos (PantallaFavoritos): Gestion y visualizacion de proyectos guardados por el usuario mediante FavoritosViewModel.
* Contacto (PantallaContacto y PantallaConfirmacion): Formulario con validacion de campos obligatorios en tiempo real, manejo visual de errores y pantalla de confirmacion de envio.

---

## Estructura del Proyecto

```text
app/src/main/java/com/example/cimucm/
|-- data/          # Fuentes de datos y repositorios simulados
|-- model/         # Modelos de datos y entidades de dominio
|-- navigation/    # Definicion de rutas y control de navegacion
|-- ui/
|   |-- screens/   # Componentes y pantallas en Jetpack Compose
|   `-- theme/     # Configuracion de tema, paleta de colores y tipografia
`-- viewmodel/     # ViewModels y logica de presentacion
