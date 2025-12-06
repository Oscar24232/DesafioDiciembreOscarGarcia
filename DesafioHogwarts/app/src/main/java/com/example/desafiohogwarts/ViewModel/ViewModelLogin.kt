package com.example.desafiohogwarts.ViewModel

import Api.UserNetwork
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import modelo.UsuarioLogin
import modelo.Usuario

class ViewModelLogin: ViewModel() {
    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> get() = _usuario

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _usuarioCompleto = MutableLiveData<Usuario?>()
    val usuarioCompleto: LiveData<Usuario?> get() = _usuarioCompleto



    fun login(email: String, pass: String) {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.login(
                    UsuarioLogin(email, pass)
                )

                if (resp.isSuccessful && resp.body() != null) {
                    _usuario.value = resp.body()
                } else {
                    _error.value = "Credenciales incorrectas (${resp.code()})"
                }

            } catch (e: Exception) {
                _error.value = "Error de conexión"
            }
        }
    }


    fun cargarUsuarioPorEmail(email: String) {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.getUsuarioByEmail(email)

                if (resp.isSuccessful && resp.body() != null) {
                    _usuarioCompleto.value = resp.body()
                } else {
                    _error.value = "No se encontró el usuario completo"
                }

            } catch (e: Exception) {
                _error.value = "Error de conexión"
            }
        }
    }



}