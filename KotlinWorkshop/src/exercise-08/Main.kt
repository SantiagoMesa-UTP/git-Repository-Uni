package exercise08

fun saludar(usuario: String?): String {
    return if (!usuario.isNullOrBlank()) {
        "Hola $usuario, bienvenido de vuelta."
    } else {
        "Hola, visitante."
    }
}
fun main() {
    println(saludar("Santiago"))
    println(saludar(null))
    println(saludar(""))
    println(saludar("   "))
}
