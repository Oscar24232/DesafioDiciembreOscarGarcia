package com.example.desafiohogwarts.ViewModel

import Api.UserNetwork
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafiohogwarts.modelo.Hechizo
import kotlinx.coroutines.launch

class Viewmodel_hechizos_alumno : ViewModel() {
        private val _hechizos = MutableLiveData<List<Hechizo>>()
        val hechizos: LiveData<List<Hechizo>> = _hechizos

        private val _puedeEntrar = MutableLiveData<Boolean>()
        val puedeEntrar: LiveData<Boolean> = _puedeEntrar

        private val _mensaje = MutableLiveData<String>()
        val mensaje: LiveData<String> = _mensaje

        private val _aprendidos = MutableLiveData<List<Int>>()
        val aprendidos: LiveData<List<Int>> = _aprendidos

        fun cargarHechizosAprendidos(idAlumno: Int) {
            viewModelScope.launch {
                try {
                    val resp = UserNetwork.retrofit.getHechizosAprendidos(idAlumno)
                    if (resp.isSuccessful && resp.body() != null) {
                        _aprendidos.value = resp.body()
                    }
                } catch (e: Exception) {
                    _mensaje.value = "Error obteniendo aprendidos"
                }
            }
        }


        // 1) Comprobar si el alumno tiene la asignatura (1 = Hechizos)
        fun comprobarAsignatura(idAlumno: Int?) {
            viewModelScope.launch {
                try {
                    val resp = UserNetwork.retrofit.tieneAsignatura(idAlumno, 2) // 1 = asignatura Hechizos

                    if (resp.isSuccessful && resp.body() != null) {
                        val tiene = resp.body()!!["tiene"] ?: false
                        _puedeEntrar.value = tiene
                    } else {
                        _mensaje.value = "No se pudo comprobar asignatura"
                    }

                } catch (e: Exception) {
                    _mensaje.value = "Error de conexión"
                }
            }
        }


        // 2) Cargar todos los hechizos
        fun cargarHechizos() {
            viewModelScope.launch {
                try {
                    val resp = UserNetwork.retrofit.getHechizos()

                    if (resp.isSuccessful && resp.body() != null) {
                        _hechizos.value = resp.body()
                    } else {
                        _mensaje.value = "Error al cargar los hechizos"
                    }

                } catch (e: Exception) {
                    _mensaje.value = "Error de conexión"
                }
            }
        }


        // 3) Aprender un hechizo
        fun aprenderHechizo(idAlumno: Int?, idHechizo: Int) {
            viewModelScope.launch {
                try {
                    val body = mapOf(
                        "id_hechizo" to idHechizo,
                        "id_alumno" to idAlumno
                    )

                    val resp = UserNetwork.retrofit.aprenderHechizo(body as Map<String, Int>)

                    if (resp.isSuccessful) {
                        _mensaje.value = "Hechizo aprendido"
                    } else {
                        _mensaje.value = "No se pudo aprender el hechizo"
                    }

                } catch (e: Exception) {
                    _mensaje.value = "Error de conexión"
                }
            }
        }
}


