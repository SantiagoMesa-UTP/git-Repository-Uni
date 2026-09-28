package exercise03

fun main() {
    val multiplo = 3
    var suma  = 0
    for (i in 1..10) {
        val result = multiplo * i
        println("$i * $multiplo = $result")
        suma = suma + result
    }
    println("La suma es $suma")
}
