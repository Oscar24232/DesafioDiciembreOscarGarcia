package modelo
import kotlinx.serialization.Serializable
@Serializable
class Login_usuario(val email: String, val contraseña: String) {
}