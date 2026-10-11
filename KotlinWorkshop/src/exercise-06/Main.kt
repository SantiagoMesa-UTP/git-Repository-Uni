package exercise06

class Product(val nombre: String, val precio: Double, val cantidad: Int) {

    fun valorTotal(): Double {
        return precio * cantidad
    }
    fun mostrarInfo() {
        println("Producto: $nombre | Precio: $precio | Cantidad: $cantidad | Valor total: ${valorTotal()}")
    }
}
fun main() {
    val producto1 = Product("Laptop", 2500000.0, 5)
    val producto2 = Product("Mouse", 45000.0, 20)
    val producto3 = Product("Teclado", 120000.0, 12)
    producto1.mostrarInfo()
    producto2.mostrarInfo()
    producto3.mostrarInfo()
}
