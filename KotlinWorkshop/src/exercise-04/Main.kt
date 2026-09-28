package exercise04

fun main() {
    val enteros = listOf(12,45,56,78,34,23,47,83,63,46)
    println(enteros)

    var suma = enteros.sum()
    println(suma)

    var promedio = suma.toDouble()/10
    println(promedio)

    var mayor = 0
    for (i in enteros.indices) {
        if (enteros[i] > mayor) {
        mayor = enteros[i]
        }
    }
    println(mayor)

    var menor = 1000
    for (i in enteros.indices) {
        if (enteros[i] < menor) {
            menor = enteros[i]
        }
    }
    println(menor)

    var pares = 0
    var impares = 0
    for (i in enteros.indices) {
        if ( enteros[i] % 2 == 0) {
            pares += 1
        }else{
            impares += 1
        }
    }
    println(pares)
    println(impares)
}
