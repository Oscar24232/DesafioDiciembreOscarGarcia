package DAO

import com.example.modelo.Casa
import com.example.modelo.Persona
import modelo.Usuario


interface UsuarioDAO {
    fun insertar(usuario: Usuario): Boolean
    fun obtener(dni: String): Persona?
    fun actualizar(persona: Persona): Boolean
    fun eliminar(dni: String): Boolean
    fun obtenerTodos(): List<Casa>
    fun existeEmail(email: String): Boolean
    fun getUsuarioByEmail(email: String): Usuario?
    fun login(email: String, contrasena: String): Usuario?

}