package com.example.desafiohogwarts.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import Api.UserNetwork
import kotlinx.coroutines.launch

class Viewmodel_activity_alumno : ViewModel() {

    private val _asignaturasAlumno = MutableLiveData<List<Int>>()
    val asignaturasAlumno: LiveData<List<Int>> get() = _asignaturasAlumno

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // 🔥 FUNCIÓN PRINCIPAL
    fun cargarAsignaturas(idAlumno: Int?) {

        viewModelScope.launch {

            _loading.value = true
            _error.value = null

            try {
                val resp = UserNetwork.retrofit.obtenerAsignaturasAlumno(idAlumno)

                if (resp.isSuccessful && resp.body() != null) {
                    _asignaturasAlumno.value = resp.body()!!
                } else {
                    _error.value = "No se pudieron cargar las asignaturas"
                }

            } catch (e: Exception) {
                _error.value = "Error de conexión con el servidor"
            }

            _loading.value = false
        }
    }
}
