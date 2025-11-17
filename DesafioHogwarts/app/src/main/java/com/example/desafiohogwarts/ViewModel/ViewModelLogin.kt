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
    val usuario: LiveData<Usuario?> = _usuario

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun login(email: String, contraseña: String) {
        viewModelScope.launch {

            try {
                val login = Login_usuario(email, contraseña)
                val resp = UserNetwork.retrofit.login(login)

                if (resp.isSuccessful && resp.body() != null) {
                    _usuario.postValue(resp.body())
                } else {
                    _error.postValue("Email o contraseña incorrectos")
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _error.postValue("Error de conexión con el servidor")
            }
        }
    }
}