enum class EstadoSlot {
    LIBRE,
    ARRENDADA,
    EN_PROCESO,
    EN_MANTENCION
}

class Slot(
    val numero: Int,
    var estado: EstadoSlot = EstadoSlot.LIBRE,
    var bicicleta: Bicicleta? = null,
    var motivo: String = ""
)