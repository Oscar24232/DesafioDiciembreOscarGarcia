import Api.UserNetwork
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafiohogwarts.modelo.PreferenciasCasa
import kotlinx.coroutines.launch
import modelo.Usuario

class GestionPreferenciasYRegistrosViewModel : ViewModel() {

    val idCasaAsignada = MutableLiveData<Int>()
    val error = MutableLiveData<String>()
    private val _casaAsignadaMensaje = MutableLiveData<String>()
    val casaAsignadaMensaje: LiveData<String> get() = _casaAsignadaMensaje

    private val _usuarioCompleto = MutableLiveData<Usuario?>()
    val usuarioCompleto: LiveData<Usuario?> get() = _usuarioCompleto

    val exito = MutableLiveData<String>()
    fun cargarUsuarioPorEmail(email: String) {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.getUsuarioByEmail(email)

                if (resp.isSuccessful && resp.body() != null) {
                    _usuarioCompleto.value = resp.body()
                } else {
                    error.value = "Error obteniendo datos del usuario"
                }

            } catch (e: Exception) {
                error.value = "Error de conexión al obtener usuario"
            }
        }
    }

    fun insertarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.insertarUsuario(usuario)

                if (resp.isSuccessful) {
                    exito.value = resp.body()!!.message
                } else {
                    error.value = "Error del servidor: ${resp.code()}"
                }

            } catch (e: Exception) {
                error.value = "Error de conexión: ${e.message}"
            }
        }
    }
    fun enviarPreferencias(pref: PreferenciasCasa) {
        viewModelScope.launch {

            try {
                val resp = UserNetwork.retrofit.evaluarPreferencias(pref)

                if (resp.isSuccessful && resp.body() != null) {
                    idCasaAsignada.value = resp.body()!!.idCasa
                    Log.d("La casa elegida es:"," ${idCasaAsignada.value}")
                } else {
                    error.value = "No se pudo calcular la casa"
                }

            } catch (e: Exception) {
                error.value = "Error de conexión: ${e.message}"
            }
        }

    }
    fun mostrarMensajeCasa(casaId: Int) {

        val mensaje = when (casaId) {
            1 -> "🦁 ¡Bienvenido a Gryffindor! Valor, coraje y determinación te guiarán."
            2 -> "🦡 Hufflepuff te recibe. Lealtad, paciencia y trabajo duro serán tus aliados."
            3 -> "🦅 En Ravenclaw encontrarás sabiduría, creatividad e intelecto."
            4 -> "🐍 Slytherin te elige... Ambición, astucia y grandeza te esperan."
            else -> "Bienvenido al Colegio Hogwarts de Magia y Hechicería."
        }
        _casaAsignadaMensaje.value = mensaje

    }


}
