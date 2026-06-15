package com.example.lenaycarbon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lenaycarbon.data.dto.CategoriaProducto
import com.example.lenaycarbon.data.dto.Producto
import com.example.lenaycarbon.data.local.dao.CategoriaDao
import com.example.lenaycarbon.data.local.dao.ProductoDao

@Database(
    entities = [Producto::class, CategoriaProducto::class], // ← AGREGAR CATEGORIA
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
    abstract fun categoriaDao(): CategoriaDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lenay_carbon_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}