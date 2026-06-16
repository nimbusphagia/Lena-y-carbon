package com.example.lenaycarbon.ui.utils

import com.example.lenaycarbon.R

fun getDrawableId(nombre: String): Int = when (nombre) {
    "pollo_personal" -> R.drawable.pollo_personal
    "pollo_familiar" -> R.drawable.pollo_familiar
    "medio_pollo" -> R.drawable.medio_pollo
    "combo_familiar" -> R.drawable.combo_familiar
    "combo_pareja" -> R.drawable.combo_pareja
    "papas_fritas" -> R.drawable.papas_fritas
    "ensalada" -> R.drawable.ensalada
    "arroz_con_leche" -> R.drawable.arroz_con_leche
    "inca_kola" -> R.drawable.inca_kola
    "chicha_morada" -> R.drawable.chicha_morada
    else -> R.drawable.ic_launcher_foreground
}