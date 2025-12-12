package com.example.desafiohogwarts.ViewModel

import Api.UserNetwork
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafiohogwarts.modelo.Asignaturas
import kotlinx.coroutines.launch

class ProfesorViewModel : ViewModel() {

    private val _asignaturas = MutableLiveData<List<Asignaturas>>()
    val asignaturas: LiveData<List<Asignaturas>> = _asignaturas

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje


    fun cargarAsignaturas(idProfesor: Int?) {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.getAsignaturasDelProfesor(idProfesor)
                Log.d("DEBUG_PROF", "Código respuesta: ${resp.code()}")
                Log.d("DEBUG_PROF", "Body: ${resp.body()}")
                if (resp.isSuccessful && resp.body() != null) {
                    _asignaturas.value = resp.body()
                } else {
                    _mensaje.value = "Error al cargar asignaturas"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error de conexión"
            }
        }
    }
}
