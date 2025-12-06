package modelo
import kotlinx.serialization.Serializable
@Serializable
class UsuarioLogin(val email: String, val contrasena: String) {
}