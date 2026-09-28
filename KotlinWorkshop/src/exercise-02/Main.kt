package exercise02

fun main() {
    val nombre = "Juan Pérez"
    val nota1 = 4.2
    val nota2 = 3.8
    val nota3 = 4.6
    val promedio = (nota1 + nota2 + nota3) / 3
    val estado = if (promedio >= 3.0) "Aprobado" else "Reprobado"
    println("Estudiante: $nombre")
    println("Promedio: $promedio")
    println("Estado: $estado")

    if (promedio >= 4.5) {
        println("¡Promedio excelente!")
    }
}
