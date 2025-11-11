package com.example.desafiohogwarts.ViewModel

import Api.UserNetwork
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafiohogwarts.MainActivity
import com.example.desafiohogwarts.modelo.Casa
import kotlinx.coroutines.launch
import java.io.IOException


class ViewModelCasa: ViewModel() {

    private val _casa = MutableLiveData<Casa>()
    val casa: LiveData<Casa> get() = _casa

    fun cargarPorCasa(nombre: String) {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.getCasaporNombre(nombre)
                Log.d("API", "Código: ${resp.code()}  Body: ${resp.body()}")

                if (resp.isSuccessful && resp.body() != null) {
                    val casaRecibida = resp.body()!!
                    _casa.value = casaRecibida // asignación directa
                } else {
                    Log.e("API", "Error en respuesta: ${resp.code()} - ${resp.message()}")
                }
            } catch (e: IOException) {
                Log.e("API", "Error de red: ${e.message}", e)
            } catch (e: Exception) {
                Log.e("API", "Error inesperado", e)
            }
        }
    }
}
