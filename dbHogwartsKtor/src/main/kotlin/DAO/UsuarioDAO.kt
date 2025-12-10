package DAO

import com.example.modelo.Casa
import com.example.modelo.Persona
import modelo.Usuario


interface UsuarioDAO {
    fun insertar(usuario: Usuario): Boolean
    fun actualizarUsuario(usuario: Usuario): Boolean
    fun borrarUsuario(email: String): Boolean
    fun obtenerTodosAlumnos():MutableList<Usuario>
    fun obtenerTodasLasCasas(): List<Casa>
    fun existeEmail(email: String): Boolean
    fun getUsuarioByEmail(email: String): Usuario?
    fun login(email: String, contrasena: String): Usuario?

}