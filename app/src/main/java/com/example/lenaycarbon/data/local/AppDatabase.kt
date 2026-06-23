package com.example.lenaycarbon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lenaycarbon.R
import com.example.lenaycarbon.data.local.entity.TipoEntrega
import com.example.lenaycarbon.data.local.dao.TipoEntregaDao
import com.example.lenaycarbon.data.local.entity.TipoPago
import com.example.lenaycarbon.data.local.dao.TipoPagoDao
import com.example.lenaycarbon.data.local.entity.CategoriaProducto
import com.example.lenaycarbon.data.local.entity.Producto
import com.example.lenaycarbon.data.local.dao.CategoriaDao
import com.example.lenaycarbon.data.local.dao.ProductoDao
import com.example.lenaycarbon.data.local.dao.PedidoDao
import com.example.lenaycarbon.data.local.entity.PedidoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Producto::class, CategoriaProducto::class, TipoEntrega::class, TipoPago::class, PedidoEntity::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun tipoEntregaDao(): TipoEntregaDao
    abstract fun tipoPagoDao(): TipoPagoDao
    abstract fun pedidoDao(): PedidoDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "lenay_carbon_database"
                ).fallbackToDestructiveMigration().addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = getDatabase(context)
                            // Insertar categorías y obtener sus ids reales
                            val idPollo = database.categoriaDao()
                                .insertarCategoria(CategoriaProducto(nombre = "Pollo"))
                            val idBebida = database.categoriaDao()
                                .insertarCategoria(CategoriaProducto(nombre = "Bebida"))
                            val idAcompanamiento = database.categoriaDao()
                                .insertarCategoria(CategoriaProducto(nombre = "Acompañamiento"))
                            val idCombo = database.categoriaDao()
                                .insertarCategoria(CategoriaProducto(nombre = "Combo"))

                            // Insertar productos usando los ids reales
                            database.productoDao().insertarProductos(
                                listOf(
                                    Producto(
                                        nombre = "Pollo a la Brasa Personal",
                                        precio = 18.90,
                                        descripcion = "1/4 de pollo a la brasa acompañado de papas fritas y ensalada",
                                        imagen = "pollo_personal",
                                        stock = 100,
                                        idCategoria = idPollo.toInt()
                                    ),
                                    Producto(
                                        nombre = "Pollo a la Brasa Familiar",
                                        precio = 65.90,
                                        descripcion = "Pollo entero a la brasa con papas fritas, ensalada y salsas",
                                        imagen = "pollo_familiar",
                                        stock = 100,
                                        idCategoria = idPollo.toInt()
                                    ),
                                    Producto(
                                        nombre = "Medio Pollo",
                                        precio = 34.90,
                                        descripcion = "1/2 pollo a la brasa con papas fritas y ensalada",
                                        imagen = "medio_pollo",
                                        stock = 100,
                                        idCategoria = idPollo.toInt()
                                    ),
                                    Producto(
                                        nombre = "Combo Familiar",
                                        precio = 79.90,
                                        descripcion = "Pollo entero + 2 gaseosas personales + papas fritas extra",
                                        imagen = "combo_familiar",
                                        stock = 100,
                                        idCategoria = idCombo.toInt()
                                    ),
                                    Producto(
                                        nombre = "Combo Pareja",
                                        precio = 42.90,
                                        descripcion = "1/2 pollo + 2 gaseosas personales + papas fritas",
                                        imagen = "combo_pareja",
                                        stock = 100,
                                        idCategoria = idCombo.toInt()
                                    ),
                                    Producto(
                                        nombre = "Papas Fritas",
                                        precio = 8.90,
                                        descripcion = "Porción de papas fritas crocantes con crema huancaína",
                                        imagen = "papas_fritas",
                                        stock = 100,
                                        idCategoria = idAcompanamiento.toInt()
                                    ),
                                    Producto(
                                        nombre = "Ensalada Fresca",
                                        precio = 6.50,
                                        descripcion = "Ensalada de lechuga, tomate, cebolla y zanahoria",
                                        imagen = "ensalada",
                                        stock = 100,
                                        idCategoria = idAcompanamiento.toInt()
                                    ),
                                    Producto(
                                        nombre = "Arroz con Leche",
                                        precio = 7.90,
                                        descripcion = "Postre tradicional de arroz con leche y canela",
                                        imagen = "arroz_con_leche",
                                        disponible = false,
                                        stock = 100,
                                        idCategoria = idAcompanamiento.toInt()
                                    ),
                                    Producto(
                                        nombre = "Inca Kola 500ml",
                                        precio = 5.50,
                                        descripcion = "Gaseosa Inca Kola botella personal 500ml bien fría",
                                        imagen = "inca_kola",
                                        stock = 100,
                                        idCategoria = idBebida.toInt()
                                    ),
                                    Producto(
                                        nombre = "Chicha Morada",
                                        precio = 6.90,
                                        descripcion = "Chicha morada artesanal preparada con maíz morado y frutas",
                                        imagen = "chicha_morada",
                                        stock = 100,
                                        idCategoria = idBebida.toInt()
                                    ),
                                )
                            )

                            //Tipo de entrega

                            database.tipoEntregaDao().insertTipoEntrega(
                                TipoEntrega(
                                    nombre = "Delivery",
                                    precio = 5.0,
                                    imagen = R.drawable.deliveryicon
                                )
                            )
                            database.tipoEntregaDao().insertTipoEntrega(
                                TipoEntrega(
                                    nombre = "Recojo en tienda",
                                    precio = 0.0,
                                    imagen = R.drawable.iconorestuarant
                                )
                            )

                            //Tipo de pago

                            database.tipoPagoDao().insertTipoPago(
                                TipoPago(
                                    nombre = "Yape", imagen = R.drawable.yapeicon
                                )
                            )

                            database.tipoPagoDao().insertTipoPago(
                                TipoPago(
                                    nombre = "Efectivo", imagen = R.drawable.efectivoicon
                                )
                            )

                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}