package com.example.DAO

import com.example.modelo.Casa
import com.example.modelo.Encuesta
import com.example.modelo.Persona

interface HogwartsDAO {
    fun obtenerTodos(): List<Casa>
    fun obtener(nombre: String): Casa?
}