package exercise01

fun main() {
    val numero1 = 10.0
    val numero2 = 4.0
    val operacion = '/'

    val resultado: Double? = when (operacion) {
        '+' -> numero1 + numero2
        '-' -> numero1 - numero2
        '*' -> numero1 * numero2
        '/' -> {
            if (numero2 == 0.0) {
                null
            } else {
                numero1 / numero2
            }
        }
        else -> null
    }

    if (resultado != null) {
        println("Resultado: $resultado")
    } else if (operacion == '/' && numero2 == 0.0) {
        println("No se puede dividir entre cero")
    } else {
        println("Operación invalida")
    }
}
