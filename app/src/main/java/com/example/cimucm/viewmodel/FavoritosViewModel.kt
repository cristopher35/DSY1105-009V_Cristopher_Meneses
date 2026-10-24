package com.example.cimucm.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel

// ViewModel que gestiona favoritos con almacenamiento local en SharedPreferences (IE 2.3.1)
class FavoritosViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = application.getSharedPreferences("cim_favoritos_prefs", Context.MODE_PRIVATE)

    // Lista observable de IDs favoritos
    val favoritosIds = mutableStateListOf<String>()

    init {
        // Carga los favoritos guardados al iniciar la app
        val guardados = prefs.getStringSet("ids_favoritos", emptySet()) ?: emptySet()
        favoritosIds.addAll(guardados)
    }

    // Comprueba si un elemento está marcado como favorito
    fun esFavorito(id: String): Boolean {
        return favoritosIds.contains(id)
    }

    // Alterna favorito y persiste en disco de inmediato
    fun alternarFavorito(id: String) {
        if (favoritosIds.contains(id)) {
            favoritosIds.remove(id)
        } else {
            favoritosIds.add(id)
        }
        // Guardado local permanente
        prefs.edit().putStringSet("ids_favoritos", favoritosIds.toSet()).apply()
    }
}