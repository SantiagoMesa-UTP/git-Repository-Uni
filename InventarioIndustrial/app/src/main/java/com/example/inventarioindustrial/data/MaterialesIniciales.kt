package com.example.inventarioindustrial.data

import com.example.inventarioindustrial.data.local.MaterialEntidad

// Materiales con los que arranca el almacén la primera vez que se abre la app
val materialesIniciales = listOf(
    MaterialEntidad(
        codigo = "0023NE",
        descripcion = "Cable 1/0 AWG centeflex",
        unidad = "m",
        precio = 45522,
        fabricante = "ERP CLESUS",
        stock = 1200,
        categoria = "Cables",
        utilidad = "Conductor flexible para alimentación de tableros y equipos industriales de alta corriente.",
        claveImagen = "cable_1"
    ),
    MaterialEntidad(
        codigo = "0024NE",
        descripcion = "Cable 2 AWG centeflex",
        unidad = "m",
        precio = 30659,
        fabricante = "ERP CLESUS",
        stock = 950,
        categoria = "Cables",
        utilidad = "Conductor flexible para conexión de motores y circuitos de potencia.",
        claveImagen = "cable_2"
    ),
    MaterialEntidad(
        codigo = "0025NE",
        descripcion = "Cable 2/0 AWG centeflex",
        unidad = "m",
        precio = 54536,
        fabricante = "ERP CLESUS",
        stock = 600,
        categoria = "Cables",
        utilidad = "Conductor flexible para acometidas y alimentación de maquinaria pesada.",
        claveImagen = "cable_3"
    ),
    MaterialEntidad(
        codigo = "0026NE",
        descripcion = "Cable 4 AWG centeflex",
        unidad = "m",
        precio = 16479,
        fabricante = "ERP CLESUS",
        stock = 1500,
        categoria = "Cables",
        utilidad = "Conductor flexible para circuitos de distribución y conexión de equipos.",
        claveImagen = "cable_4"
    ),
    MaterialEntidad(
        codigo = "0027NE",
        descripcion = "Cable 4/0 AWG centeflex",
        unidad = "m",
        precio = 84994,
        fabricante = "ERP CLESUS",
        stock = 400,
        categoria = "Cables",
        utilidad = "Conductor flexible de gran calibre para alimentadores principales.",
        claveImagen = "cable_5"
    ),
    MaterialEntidad(
        codigo = "0028NE",
        descripcion = "Cable Centeflex 6 AWG",
        unidad = "m",
        precio = 10625,
        fabricante = "ERP CLESUS",
        stock = 846,
        categoria = "Cables",
        utilidad = "Conductor flexible para circuitos de control y cargas medias.",
        claveImagen = "cable_6"
    ),
    MaterialEntidad(
        codigo = "0042NE",
        descripcion = "Descargador sobretensiones PSM3-30/750 TNC",
        unidad = "und",
        precio = 525467,
        fabricante = "ERP CLESUS",
        stock = 0,
        categoria = "Protección",
        utilidad = "Protege la instalación eléctrica y los equipos contra sobretensiones transitorias y descargas atmosféricas.",
        claveImagen = "descargador_psm3"
    ),
    MaterialEntidad(
        codigo = "0103IE",
        descripcion = "Nomex 0.13 mm",
        unidad = "kg",
        precio = 62893,
        fabricante = "ERP CLESUS",
        stock = 768,
        categoria = "Aislantes",
        utilidad = "Papel aislante térmico y eléctrico para bobinados de motores y transformadores.",
        claveImagen = "nomex_013"
    )
)