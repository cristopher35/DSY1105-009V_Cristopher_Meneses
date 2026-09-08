import java.time.LocalDateTime

fun main() {
    println(">>> INICIANDO SISTEMA BIKECITY <<<\n")
    val bikeCity = BikeCity(capacidad = 10)


    println("--- PRUEBA R6: CÓDIGO INVÁLIDO ---")
    try {
        BiciCiudad("123ABC", "Trek Mal", LocalDateTime.now(), TipoCliente.TURISTA)
    } catch (e: IllegalArgumentException) {
        println("[CONTROLADO R6] Capturado con éxito: ${e.message}\n")
    }


    println("--- REGISTRANDO ENTRADAS ---")
    val bici1 = BiciCiudad("BC12CD", "Trek FX3", LocalDateTime.now(), TipoCliente.ABONADO)
    val bici2 = BiciCiudad("BC99ZA", "Giant Escape", LocalDateTime.now(), TipoCliente.TURISTA)
    val bici3 = BiciMontana("BM22TO", "Scott Aspect", LocalDateTime.now(), TipoCliente.TURISTA)
    val bici4 = BiciElectrica("BE44RG", "Specialized Vado", LocalDateTime.now(), TipoCliente.DISCAPACITADO, esLargaAutonomia = true)
    val bici5 = BiciElectrica("BE77RG", "Trek Allant", LocalDateTime.now(), TipoCliente.TURISTA, esLargaAutonomia = false)

    bikeCity.registrarEntrada(bici1)
    bikeCity.registrarEntrada(bici2)
    bikeCity.registrarEntrada(bici3)
    bikeCity.registrarEntrada(bici4)
    bikeCity.registrarEntrada(bici5)


    println("\n--- PRUEBA R6: SALIDA DE BICICLETA INEXISTENTE ---")
    bikeCity.registrarSalida("XX99XX", 25)
    println()


    println("--- REGISTRANDO SALIDAS Y EMITIENDO TICKETS ---")
    bikeCity.registrarSalida("BC12CD", 75)   // BiciCiudad (Abonado: 20% desc)
    bikeCity.registrarSalida("BC99ZA", 180)  // BiciCiudad (Turista)
    bikeCity.registrarSalida("BM22TO", 18)   // BiciMontana (<20 min: $0)
    bikeCity.registrarSalida("BE44RG", 120)  // BiciElectrica (Larga autonomía + 50% desc discapacitado)
    bikeCity.registrarSalida("BE77RG", 45)   // BiciElectrica (Turista)


    println("\n--- CONSULTAS DE NEGOCIO (R4) ---")
    println("1. Slots disponibles actualmente: ${bikeCity.slotsDisponibles()}")
    println("2. Códigos de bicis finalizadas: ${bikeCity.codigosFinalizados()}")
    val abonados = bikeCity.bicisDeAbonados().map { it.codigo }
    println("3. Bicis de abonados atendidas: $abonados")
    println("4. Ingreso promedio por bici: $${String.format("%.2f", bikeCity.ingresoPromedio())}")
    println("5. Bicicleta con mayor tiempo de uso: ${bikeCity.biciConMayorUso()?.codigo ?: "Ninguna"}")


    bikeCity.imprimirReporteCierre()
}