package com.example.desafiohogwarts.ViewModel

import Api.UserNetwork
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafiohogwarts.Adapters.Alumnos.UsuarioAdapter
import kotlinx.coroutines.launch
import modelo.Usuario


class GestionUsuariosViewModel : ViewModel() {
    private val _usuarioResponse = MutableLiveData<Usuario>()
    val usuarioResponse: LiveData<Usuario> get() = _usuarioResponse

    private val _usuarioList = MutableLiveData<List<Usuario>>()
    val usuarioList: LiveData<List<Usuario>> get() = _usuarioList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val listaLocal = ArrayList<Usuario>()

    // Adapter (se inyecta desde el fragment)
    lateinit var adapter: UsuarioAdapter


    // OBTENER TODOS LOS USUARIOS DESDE KTOR
    fun cargarUsuarios() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.getUsuarios()
                Log.d("API", "Código: ${resp.code()}  Body: ${resp.body()}")

                if (resp.isSuccessful && resp.body() != null) {
                    val usuarios = resp.body()!!

                    // LiveData para el fragment
                    _usuarioList.value = usuarios

                    // copia local interna
                    listaLocal.clear()
                    listaLocal.addAll(usuarios)

                } else {
                    Log.e("API", "Error en respuesta: ${resp.code()}")
                }

            } catch (e: Exception) {
                Log.e("API", "Error de conexión", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // BORRAR UN USUARIO POR EMAIL
    fun borrarUsuario(email: String) {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.borrarUsuario(email)

                if (resp.isSuccessful && resp.body()?.status == 0) {

                    if (::adapter.isInitialized) {
                        adapter.removeUsuarioPorEmail(email)
                    }

                    Log.d("API", "Usuario $email eliminado")

                } else {
                    Log.e(
                        "API",
                        "No se pudo eliminar (${resp.code()}) ${resp.body()?.message}"
                    )
                }

            } catch (e: Exception) {
                Log.e("API", "Error al eliminar del servidor", e)
            }
        }
    }
    fun actualizarUsuarioRemoto(usuario: Usuario) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.actualizarUsuario(usuario)

                if (resp.isSuccessful && resp.body()?.status == 0) {

                    // 1. Actualizamos lista local
                    val idx = listaLocal.indexOfFirst { it.email == usuario.email }
                    if (idx != -1) {
                        listaLocal[idx] = usuario
                    }

                    // 2. Actualizamos LiveData para refrescar el Recycler
                    _usuarioList.value = listaLocal.toList()

                    Log.d("API", "Usuario ${usuario.email} actualizado")

                } else {
                    Log.e("API", "No se pudo actualizar: ${resp.code()}")
                }

            } catch (e: Exception) {
                Log.e("API", "Error al actualizar en el servidor", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}