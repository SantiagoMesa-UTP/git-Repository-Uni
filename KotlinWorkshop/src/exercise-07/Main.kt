package exercise07

class Contact(val nombre: String, val telefono: String, val correo: String)
fun agregar(agenda: MutableList<Contact>, contacto: Contact) {
    agenda.add(contacto)
    println("Contacto agregado: ${contacto.nombre}")
}
fun listar(agenda: List<Contact>) {
    if (agenda.isEmpty()) {
        println("La agenda está vacía")
        return
    }
    for (contacto in agenda) {
        println("${contacto.nombre} | ${contacto.telefono} | ${contacto.correo}")
    }
}
fun buscar(agenda: List<Contact>, nombre: String): Contact? {
    for (contacto in agenda) {
        if (contacto.nombre.equals(nombre, ignoreCase = true)) {
            return contacto
        }
    }
    return null
}
fun eliminar(agenda: MutableList<Contact>, nombre: String) {
    val contacto = buscar(agenda, nombre)
    if (contacto == null) {
        println("El contacto \"$nombre\" no existe")
    } else {
        agenda.remove(contacto)
        println("Contacto eliminado: $nombre")
    }
}
fun main() {
    val agenda = mutableListOf(
        Contact("Liz", "3001112233", "liz@gmail.com"),
        Contact("Luis", "3002223344", "luis@gmail.com"),
        Contact("Marta", "3003334455", "marta@gmail.com"),
        Contact("Pedro", "3004445566", "pedro@gmail.com"),
        Contact("Sofia", "3005556677", "sofia@gmail.com")
    )

    println("Listar")
    listar(agenda)

    println("Agregar")
    agregar(agenda, Contact("Carlos", "3006667788", "carlos@correo.com"))

    println("Buscar")
    val encontrado = buscar(agenda, "Marta")
    if (encontrado != null) {
        println("Encontrado: ${encontrado.nombre} | ${encontrado.telefono} | ${encontrado.correo}")
    } else {
        println("El contacto no existe.")
    }
    if (buscar(agenda, "Inexistente") == null) {
        println("El contacto \"Inexistente\" no existe")
    }

    println("Eliminar")
    eliminar(agenda, "Luis")
    eliminar(agenda, "Inexistente")

    println("Lista final")
    listar(agenda)
}
