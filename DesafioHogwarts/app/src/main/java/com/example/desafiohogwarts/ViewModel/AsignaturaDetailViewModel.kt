package com.example.desafiohogwarts.ViewModel

import Api.UserNetwork
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import modelo.Usuario

class AsignaturaDetailViewModel : ViewModel() {

    private val _alumnos = MutableLiveData<List<Usuario>>()
    val alumnos: LiveData<List<Usuario>> = _alumnos

    private val _todosAlumnos = MutableLiveData<List<Usuario>>()
    val todosAlumnos: LiveData<List<Usuario>> = _todosAlumnos

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje



    fun cargarAlumnosDeAsignatura(idAsignatura: Int?) {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.getAlumnosDeAsignatura(idAsignatura)

                if (resp.isSuccessful && resp.body() != null) {
                    _alumnos.value = resp.body()
                } else {
                    _mensaje.value = "No se pudieron cargar los alumnos"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error de conexión"
            }
        }
    }

    fun cargarTodosLosAlumnos() {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.getUsuarios()

                if (resp.isSuccessful && resp.body() != null) {
                    _todosAlumnos.value = resp.body()
                } else {
                    _mensaje.value = "No se pudieron cargar los alumnos disponibles"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error de conexión"
            }
        }
    }


    fun añadirAlumno(idAsignatura: Int?, idAlumno: Int?) {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.addAlumnoAAsignatura(idAsignatura, idAlumno)

                when {
                    resp.code() == 409 ->
                        _mensaje.value = "Este alumno ya está en la asignatura"

                    resp.isSuccessful -> {
                        _mensaje.value = "Alumno añadido correctamente"
                        cargarAlumnosDeAsignatura(idAsignatura) // Recargar lista
                    }

                    else -> _mensaje.value = "No se pudo añadir alumno"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error de conexión"
            }
        }
    }

    fun quitarAlumno(idAsignatura: Int?, idAlumno: Int?) {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.removeAlumnoDeAsignatura(idAsignatura, idAlumno)

                if (resp.isSuccessful) {
                    _mensaje.value = "Alumno eliminado"
                    cargarAlumnosDeAsignatura(idAsignatura)
                } else {
                    _mensaje.value = "No se pudo eliminar alumno"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error de conexión"
            }
        }
    }
}
