package com.example.desafiohogwarts.ViewModel

import Api.UserNetwork
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafiohogwarts.modelo.Hechizo
import kotlinx.coroutines.launch

class ViewModelHechizosAprendidos : ViewModel() {
    private val _listaAprendidos = MutableLiveData<List<Hechizo>>()
    val listaAprendidos: LiveData<List<Hechizo>> = _listaAprendidos

    fun cargarHechizosAprendidos(idAlumno: Int) {
        Log.d("API", "ViewModel → cargarHechizosAprendidos($idAlumno)")
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.getHechizosAprendidos(idAlumno)
                Log.d("API", "Respuesta HTTP: ${resp.code()}")

                if (resp.isSuccessful && resp.body() != null) {
                    Log.d("API", "Hechizos recibidos: ${resp.body()!!.size}")
                    _listaAprendidos.value = resp.body()
                }

            } catch (e: Exception) {
                Log.e("API", "Error llamando a API: ${e.message}", e)
            }
        }
    }
}
