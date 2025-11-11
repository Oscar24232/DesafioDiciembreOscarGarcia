package DAO

import com.example.modelo.Casa
import com.example.modelo.Persona


interface PersonaDAO {
    fun insertar(persona: Persona): Boolean
    fun obtener(dni: String): Persona?
    fun actualizar(persona: Persona): Boolean
    fun eliminar(dni: String): Boolean
    fun obtenerTodos(): List<Casa>
}