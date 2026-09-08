import java.time.LocalDateTime

enum class TipoCliente {
    TURISTA,
    ABONADO,
    DISCAPACITADO
}

open class Bicicleta(
    val codigo: String,
    val marcaModelo: String,
    val fechaIngreso: LocalDateTime,
    val tipoCliente: TipoCliente
) {
    init {
        val regex = Regex("^[A-Z]{2}[0-9]{2}[A-Z]{2}$")
        if (!codigo.matches(regex)) {
            throw IllegalArgumentException("Código inválido: $codigo. Formato requerido: 2 letras, 2 dígitos, 2 letras (ej. BC12CD).")
        }
    }

    open val tarifaBase: Double = 0.0

    open fun calcularMontoBase(minutosUso: Long): Double {
        val horas = minutosUso / 60.0
        return horas * tarifaBase
    }
}

class BiciCiudad(
    codigo: String,
    marcaModelo: String,
    fechaIngreso: LocalDateTime,
    tipoCliente: TipoCliente
) : Bicicleta(codigo, marcaModelo, fechaIngreso, tipoCliente) {

    override val tarifaBase: Double = 800.0

    override fun calcularMontoBase(minutosUso: Long): Double {
        val base = super.calcularMontoBase(minutosUso)
        return if (tipoCliente == TipoCliente.ABONADO) {
            base * 0.80
        } else {
            base
        }
    }
}

class BiciMontana(
    codigo: String,
    marcaModelo: String,
    fechaIngreso: LocalDateTime,
    tipoCliente: TipoCliente
) : Bicicleta(codigo, marcaModelo, fechaIngreso, tipoCliente) {

    override val tarifaBase: Double = 1500.0

    override fun calcularMontoBase(minutosUso: Long): Double {
        if (minutosUso < 20) {
            return 0.0
        }
        return super.calcularMontoBase(minutosUso)
    }
}

class BiciElectrica(
    codigo: String,
    marcaModelo: String,
    fechaIngreso: LocalDateTime,
    tipoCliente: TipoCliente,
    val esLargaAutonomia: Boolean
) : Bicicleta(codigo, marcaModelo, fechaIngreso, tipoCliente) {

    override val tarifaBase: Double = 2200.0

    override fun calcularMontoBase(minutosUso: Long): Double {
        var monto = super.calcularMontoBase(minutosUso)
        if (esLargaAutonomia) {
            monto *= 1.30
        }
        return monto
    }
}