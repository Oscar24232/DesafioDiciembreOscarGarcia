package com.example.desafiohogwarts.ViewModel

import Api.UserNetwork
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import modelo.Login_usuario
import modelo.Usuario

class ViewModelLogin: ViewModel() {
    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> get() = _usuario

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.login(
                    Login_usuario(email, pass)
                )

                if (resp.isSuccessful && resp.body() != null) {
                    _usuario.value = resp.body()
                } else {
                    _error.value = "Credenciales incorrectas"
                }

            } catch (e: Exception) {
                _error.value = "Error de conexión"
            }
        }
    }
}