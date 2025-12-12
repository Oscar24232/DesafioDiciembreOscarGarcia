package com.example.desafiohogwarts.ViewModel

import Api.UserNetwork
import android.util.Log
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

    private val _aprendidos = MutableLiveData<List<Int>>()   // SOLO IDs
    val aprendidos: LiveData<List<Int>> = _aprendidos


    fun cargarHechizos() {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.getHechizos()
                Log.d("HECHIZOS_DEBUG", "Código = ${resp.code()}")
                if (resp.isSuccessful && resp.body() != null) {
                    _hechizos.value = resp.body()
                    Log.d("HECHIZOS_DEBUG", "Hechizos recibidos: ${resp.body()!!.size}")
                    _hechizos.value = resp.body()

                } else {
                    _mensaje.value = "Error al cargar hechizos"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error de conexión"
            }
        }
    }

    fun cargarHechizosAprendidos(idAlumno: Int) {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.getHechizosAprendidos(idAlumno)


                if (resp.isSuccessful && resp.body() != null) {
                    // Solo guardamos la LISTA DE IDS
                    _aprendidos.value = resp.body()!!.map { it.id }
                }

            } catch (e: Exception) {
                _mensaje.value = "Error cargando aprendidos"
            }
        }
    }

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

                    //Recargar aprendidos tras aprender
                    cargarHechizosAprendidos(idAlumno!!)
                } else {
                    _mensaje.value = "No se pudo aprender"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error de conexión"
            }
        }
    }
}


