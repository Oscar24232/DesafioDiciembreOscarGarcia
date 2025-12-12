package com.example.DAO

import com.example.modelo.Casa

interface HogwartsDAO {
    fun obtenerTodos(): List<Casa>
    fun obtenerPorNombre(nombre: String): Casa?
    fun obtenerPorId(id: Int): Casa?
    fun getRankingCasas(): MutableList<Casa>

}