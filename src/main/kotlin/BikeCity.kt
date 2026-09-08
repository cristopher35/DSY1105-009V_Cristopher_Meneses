class BikeCity(val capacidad: Int = 10) {
    val slots: MutableList<Slot> = MutableList(capacidad) { Slot(numero = it + 1) }
    val historialTickets: MutableList<Ticket> = mutableListOf()
    private var contadorTicket: Int = 1

    fun calcularTarifaFinal(bici: Bicicleta, minutosUso: Long): Double {
        val base = bici.calcularMontoBase(minutosUso)

        if (base == 0.0 && bici is BiciMontana && minutosUso < 20) {
            return 0.0
        }

        val conIva = base * 1.19

        val total = if (bici.tipoCliente == TipoCliente.DISCAPACITADO) {
            conIva * 0.50
        } else {
            conIva
        }

        if (total <= 0.0 && !(bici is BiciMontana && minutosUso < 20)) {
            throw IllegalStateException("Tarifa inválida calculada: $total.")
        }

        return total
    }

    fun registrarEntrada(bici: Bicicleta): Boolean {
        val slot = slots.firstOrNull { it.estado == EstadoSlot.LIBRE }

        if (slot == null) {
            println("[ERROR R6] Capacidad agotada: No hay slots libres para la bicicleta ${bici.codigo}.")
            return false
        }

        println("[SENSOR] Slot #${slot.numero}: Registrando entrada para ${bici.codigo}...")
        slot.estado = EstadoSlot.EN_PROCESO
        slot.motivo = "Registrando entrada en sensor"

        Thread.sleep(1000) // Simulación breve de sensor

        slot.bicicleta = bici
        slot.estado = EstadoSlot.ARRENDADA
        slot.motivo = ""

        println("[ÉXITO] Bicicleta ${bici.codigo} (${bici.marcaModelo}) asignada al Slot #${slot.numero}.")
        return true
    }

    fun registrarSalida(codigoBici: String, minutosUso: Long): Ticket? {
        val slot = slots.firstOrNull {
            it.estado == EstadoSlot.ARRENDADA && it.bicicleta?.codigo == codigoBici
        }

        if (slot == null) {
            println("[ERROR R6] Bicicleta no encontrada: El código '$codigoBici' no está en ningún slot activo.")
            return null
        }

        val bici = slot.bicicleta!!

        println("[SENSOR] Slot #${slot.numero}: Procesando salida para ${bici.codigo}...")
        slot.estado = EstadoSlot.EN_PROCESO
        slot.motivo = "Calculando tarifa y emitiendo salida"

        Thread.sleep(1500)

        try {
            val total = calcularTarifaFinal(bici, minutosUso)
            val ticket = Ticket(contadorTicket++, bici, minutosUso, total)
            historialTickets.add(ticket)

            slot.bicicleta = null
            slot.estado = EstadoSlot.LIBRE
            slot.motivo = ""

            println("-> Ticket #${ticket.numeroTicket} emitido | Bici: ${bici.codigo} | Total: $$total")
            return ticket
        } catch (e: Exception) {
            slot.estado = EstadoSlot.ARRENDADA
            println("[ERROR R6] Error al procesar salida: ${e.message}")
            return null
        }
    }

    fun slotsDisponibles(): Int = slots.count { it.estado == EstadoSlot.LIBRE }

    fun bicisDeAbonados(): List<Bicicleta> =
        historialTickets.map { it.bicicleta }.filter { it.tipoCliente == TipoCliente.ABONADO }

    fun ingresoPromedio(): Double {
        if (historialTickets.isEmpty()) return 0.0
        return historialTickets.sumOf { it.montoPagado } / historialTickets.size
    }

    fun codigosFinalizados(): List<String> =
        historialTickets.map { it.bicicleta.codigo }

    fun biciConMayorUso(): Bicicleta? =
        historialTickets.maxByOrNull { it.minutosUso }?.bicicleta

    fun imprimirReporteCierre() {
        println("\n=======================================================")
        println("             BIKECITY - REPORTE DE CIERRE DE TURNO     ")
        println("=======================================================")
        println(String.format("%-8s | %-14s | %-8s | %-10s | %-10s", "TICKET", "TIPO", "CÓDIGO", "USO (MIN)", "TOTAL"))
        println("-------------------------------------------------------")
        for (t in historialTickets) {
            println(
                String.format(
                    "%-8d | %-14s | %-8s | %-10d | $%-9.2f",
                    t.numeroTicket,
                    t.bicicleta::class.simpleName,
                    t.bicicleta.codigo,
                    t.minutosUso,
                    t.montoPagado
                )
            )
        }

        val tipoMayorIngreso = historialTickets
            .groupBy { it.bicicleta::class.simpleName }
            .mapValues { entry -> entry.value.sumOf { it.montoPagado } }
            .maxByOrNull { it.value }

        println("-------------------------------------------------------")
        println("Slots disponibles al cierre : ${slotsDisponibles()}")
        println("Total de bicis atendidas    : ${historialTickets.size}")
        println("Total recaudado en turno    : $${String.format("%.2f", historialTickets.sumOf { it.montoPagado })}")
        println("Ingreso promedio por bici   : $${String.format("%.2f", ingresoPromedio())}")
        println("Tipo con mayores ingresos   : ${tipoMayorIngreso?.key ?: "N/A"} ($${String.format("%.2f", tipoMayorIngreso?.value ?: 0.0)})")
        println("Bicicleta con mayor uso     : ${biciConMayorUso()?.codigo ?: "Ninguna"}")
        println("=======================================================")
    }
}