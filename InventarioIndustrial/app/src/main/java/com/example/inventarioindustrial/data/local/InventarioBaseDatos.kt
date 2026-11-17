package com.example.inventarioindustrial.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.inventarioindustrial.data.materialesIniciales

@Database(
    entities = [MaterialEntidad::class, MovimientoEntidad::class],
    version = 1,
    exportSchema = false
)
abstract class InventarioBaseDatos : RoomDatabase() {

    abstract fun materialDao(): MaterialDao

    abstract fun movimientoDao(): MovimientoDao

    companion object {
        @Volatile
        private var INSTANCIA: InventarioBaseDatos? = null

        fun obtenerBaseDatos(
            contexto: Context
        ): InventarioBaseDatos {

            return INSTANCIA ?: synchronized(this) {

                INSTANCIA ?: Room.databaseBuilder(
                    contexto.applicationContext,
                    InventarioBaseDatos::class.java,
                    "inventario_industrial_bd"
                )
                    // Se ejecuta solo una vez: cuando la base de datos se crea por primera vez
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            cargarMaterialesIniciales(db)
                        }
                    })
                    .build()
                    .also {
                        INSTANCIA = it
                    }
            }
        }

        // Guarda los materiales de ejemplo en la tabla "materiales"
        private fun cargarMaterialesIniciales(db: SupportSQLiteDatabase) {
            for (material in materialesIniciales) {
                val valores = ContentValues()
                valores.put("codigo", material.codigo)
                valores.put("descripcion", material.descripcion)
                valores.put("unidad", material.unidad)
                valores.put("precio", material.precio)
                valores.put("fabricante", material.fabricante)
                valores.put("stock", material.stock)
                valores.put("categoria", material.categoria)
                valores.put("utilidad", material.utilidad)
                valores.put("claveImagen", material.claveImagen)

                db.insert("materiales", SQLiteDatabase.CONFLICT_IGNORE, valores)
            }
        }
    }
}