package exercise05

fun isPrime(numero: Int): Boolean {
    if (numero < 2) return false
    for (i in 2 until numero) {
        if (numero % i == 0) return false
    }
    return true
}

fun factorial(numero: Int): Long {
    if (numero < 0) return -1
    var resultado = 1L
    for (i in 2..numero) {
        resultado *= i
    }
    return resultado
}

fun isEven(numero: Int): Boolean {
    return numero % 2 == 0
}

fun main() {
    val numero = 10
    println("$numero es primo: ${isPrime(numero)}")
    println("$numero es par: ${isEven(numero)}")
    println("Factorial de $numero: ${factorial(numero)}")
}